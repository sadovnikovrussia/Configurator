package tech.sadovnikov.configurator.model;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;
//import tech.sadovnikov.configurator.presenter.UiHandler;


/**
 * Класс, прденазначенный для работы с Bluetooth соединением
 */
public class AppBluetoothService implements BluetoothService, BluetoothBroadcastReceiver.Listener {
    private static final String TAG = AppBluetoothService.class.getSimpleName();

    private static final java.util.UUID UUID = java.util.UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    static final int WHAT_CONNECTING_ERROR = 13;

    private static BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    private PublishSubject<String> inputMessagesStream = PublishSubject.create();
    private PublishSubject<Integer> bluetoothState = PublishSubject.create();
    private PublishSubject<List<BluetoothDevice>> pairedDevices = PublishSubject.create();
    private PublishSubject<List<BluetoothDevice>> availableDevicesObservable = PublishSubject.create();
    private List<BluetoothDevice> availableDevices = new ArrayList<>();

    private InputStreamListener inputStreamListener;

    // Потоки
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;

    // private UiHandler handler;
    private DataAnalyzer dataAnalyzer;

//    public AppBluetoothService(OnBluetoothServiceEventsListener onBluetoothServiceEventsListener, UiHandler handler) {
//        // Log.v(TAG, "OnConstructor");
//        listener = onBluetoothServiceEventsListener;
//        this.handler = handler;
//        dataAnalyzer = new DataAnalyzer(handler);
//    }
//

    public AppBluetoothService() {
    }

    @Override
    public boolean enable() {
        return bluetoothAdapter.enable();
    }

    @Override
    public boolean disable() {
        return bluetoothAdapter.disable();
    }

    @Override
    public boolean isEnabled() {
        return bluetoothAdapter.isEnabled();
    }

    @Override
    public void startDiscovery() {
        bluetoothAdapter.startDiscovery();
    }

    @Override
    public void cancelDiscovery() {
        bluetoothAdapter.cancelDiscovery();
    }

    @Override
    public PublishSubject<String> getInputMessagesStream() {
        return inputMessagesStream;
    }

    @Override
    public void setInputStreamListener(InputStreamListener listener) {

    }

    @Override
    public void connectToDevice(BluetoothDevice device) {
        cancelDiscovery();
        onConnecting(device);
    }

    @Override
    public void setBluetoothState(Integer state) {
        Log.w(TAG, "setBluetoothState: " + state);
        bluetoothState.onNext(state);
    }

    @Override
    public PublishSubject<Integer> getBluetoothStateObservable() {
        return bluetoothState;
    }

    @Override
    public void setPairedDevices(List<BluetoothDevice> pairedDevices) {
        this.pairedDevices.onNext(pairedDevices);
    }

    @Override
    public PublishSubject<List<BluetoothDevice>> getPairedDevicesObservable() {
        return pairedDevices;
    }

    @Override
    public List<BluetoothDevice> getAvailableDevices() {
        return availableDevices;
    }

    @Override
    public PublishSubject<List<BluetoothDevice>> getAvailableDevicesObservable() {
        return availableDevicesObservable;
    }

    @Override
    public List<BluetoothDevice> getPairedDevices() {
        return new ArrayList<>(bluetoothAdapter.getBondedDevices());
    }

    @Override
    public void onStateChanged() {
        bluetoothState.onNext(bluetoothAdapter.getState());
    }

    @Override
    public void onBondStateChanged() {
        Log.d(TAG, "onBondStateChanged: ");
        pairedDevices.onNext(new ArrayList<>(bluetoothAdapter.getBondedDevices()));
    }

    @Override
    public void onDiscoveryStarted() {
        availableDevices.clear();
        availableDevicesObservable.onNext(availableDevices);
    }

    @Override
    public void onFoundDevice(BluetoothDevice device) {
        availableDevices.add(device);
        availableDevicesObservable.onNext(availableDevices);
    }


    private synchronized void onConnecting(BluetoothDevice device) {
        //Log.d(TAG, "Connecting to: " + device);

        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Start the thread to onConnecting with the given device
        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
    }

    private synchronized void onConnected(BluetoothSocket socket) {
        // Log.d(TAG, "onConnected to Socket: " + socket.toString());
        // Cancel the thread that completed the connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();
    }

    public void closeAllConnections() {
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
    }

    private class ConnectThread extends Thread {
        BluetoothSocket mSocket;
        BluetoothDevice mDevice;

        ConnectThread(BluetoothDevice device) {
            // Log.d(TAG, "Create ConnectThread");
            BluetoothSocket tmp = null;
            mDevice = device;
            try {
                tmp = device.createRfcommSocketToServiceRecord(UUID);
                // Log.d(TAG, "Получаем socket c помощью createRfcommSocketToServiceRecord(UUID): " + "BluetoothSocket = " + tmp.toString());
            } catch (IOException e) {
                e.printStackTrace();
                // Log.e(TAG, "ConnectThread: не удалось createRfcommSocketToServiceRecord", e);
                // Log.d(TAG, "Socket's createRfcommSocketToServiceRecord(UUID) method failed", e);
            }
            mSocket = tmp;
        }

        @Override
        synchronized public void run() {
            setName("ConnectThread");
            // Log.d(TAG, "Started thread " + "\"" + getName() + "\"");
            bluetoothAdapter.cancelDiscovery();
            // Log.d(TAG, "Выключили поиск: " + "bluetoothAdapter.isDiscovering() = " + bluetoothAdapter.isDiscovering());
            try {
                mSocket.connect();
                Log.d("ConnectThread", "Connected");
            } catch (IOException e) {
                Log.w("ConnectThread", "run: ", e);
//                Message message = new Message();
//                message.what = WHAT_CONNECTING_ERROR;
//                handler.sendMessage(message);
                //listener.onErrorToConnect();
                //Log.d(TAG, "Не получилось. mSocket is onConnected? " + String.valueOf(mSocket.isConnected()) + ", " + e.getMessage());
                //e.printStackTrace();
                try {
                    mSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    // Log.d(TAG, "Не удалось закрыть socket: " + e1.getMessage());
                }
                return;
            }

            synchronized (AppBluetoothService.this) {
                mConnectThread = null;
            }
            onConnected(mSocket);
        }

        void cancel() {
            try {
                mSocket.close();
                // Log.d(TAG, "Закрыли socket");
            } catch (IOException e) {
                Log.e(TAG, "cancel: Не удалось закрыть socket в connectThread", e);
            }
        }
    }

    private class ConnectedThread extends Thread {
        BluetoothSocket mmSocket;
        private final BufferedReader readerSerial;
        private final PrintWriter writerSerial;

        ConnectedThread(BluetoothSocket socket) {
            // Log.d(TAG, "Create ConnectedThread");
            mmSocket = socket;
            BufferedReader tmpReaderSerial = null;
            PrintWriter tmpWriterSerial = null;
            // Get the BluetoothSocket readerSerial and output streams
            try {
                // Log.d(TAG, "Пытаемся получить InputStream");
                tmpReaderSerial = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // Log.d(TAG, "Получили InputStream: " + tmpReaderSerial.toString());
            } catch (IOException e) {
                // Log.d(TAG, "Не удалось получить InputStream: " + e.getMessage());
            }
            readerSerial = tmpReaderSerial;
//            inputStream = StringObservable.from(readerSerial);
//            inputStream.subscribe(new Subscriber<String>() {
//
//                @Override
//                public void onCompleted() {
//
//                }
//
//                @Override
//                public void onError(Throwable e) {
//
//                }
//
//                @Override
//                public void onNext(String s) {
//
//                }
//            });

            try {
                // Log.d(TAG, "Пытаемся создать OutputStream");
                tmpWriterSerial = new PrintWriter(socket.getOutputStream());
                // Log.d(TAG, "Получили OutputStream: " + tmpWriterSerial.toString());
            } catch (IOException e) {
                Log.e(TAG, "Не удалось создать OutputStream: ", e);
            }
            writerSerial = tmpWriterSerial;
        }

        public void run() {
            setName("ConnectedThread");
            // Log.d(TAG, "Start thread " + getName());
            // inputMessagesStream = PublishSubject.create();
            String line;
            try {
                // Log.d(TAG, "Пытаемся прочитать из потока");
                while ((line = readerSerial.readLine()) != null) {
                    Log.v(TAG, AppBluetoothService.this + ", " + inputMessagesStream + ", " + Thread.currentThread().getName() + ": " + line);
                    inputMessagesStream.onNext(line);
                    // dataAnalyzer.analyze(line);
                }
            } catch (IOException e) {
                Log.e(TAG, "Не удалось прочитать из потока", e);
            }
        }

        synchronized void write(String data) {
            Log.d(TAG, "Пишем в порт: " + data);
            writerSerial.write(data);
            writerSerial.flush();
        }

        void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.d(TAG, "close() of onConnecting socket failed", e);
            }
        }
    }

    public void sendData(String data) {
        if (mConnectedThread != null) {
            mConnectedThread.write(data);
        }
    }

    public interface OnBluetoothServiceEventsListener {

        void onConnectingTo(String name);

        void onErrorToConnect();
    }


    interface OnBluetoothBroadcastReceiverEventsListener {
        void onStateConnected(BluetoothDevice device);

        void onBluetoothServiceActionFound(BluetoothDevice device);

        void onBluetoothServiceStateOn();

        void onBluetoothServiceStateOff();
    }


}
