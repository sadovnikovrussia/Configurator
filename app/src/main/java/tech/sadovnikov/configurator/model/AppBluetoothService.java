package tech.sadovnikov.configurator.model;

import android.annotation.SuppressLint;
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
import tech.sadovnikov.configurator.model.entities.LogMessage;
import tech.sadovnikov.configurator.model.entities.Parameter;

import static tech.sadovnikov.configurator.model.entities.LogMessage.LOG_SYMBOL;
import static tech.sadovnikov.configurator.model.entities.LogMessage.LOG_TYPE_CMD;


/**
 * Класс, прденазначенный для работы с Bluetooth соединением
 */
public class AppBluetoothService implements BluetoothService, BluetoothBroadcastReceiver.Listener {
    private static final String TAG = AppBluetoothService.class.getSimpleName();

    private static final java.util.UUID UUID = java.util.UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    private String buffer = "";

    private PublishSubject<String> inputMessagesObservable = PublishSubject.create();

    private PublishSubject<Integer> bluetoothStateObservable = PublishSubject.create();
    private PublishSubject<List<BluetoothDevice>> pairedDevicesObservable = PublishSubject.create();
    private PublishSubject<List<BluetoothDevice>> availableDevicesObservable = PublishSubject.create();
    private List<BluetoothDevice> availableDevices = new ArrayList<>();

    private PublishSubject<LogMessage> logMessageObservable = PublishSubject.create();
    private PublishSubject<Parameter> cmdObservable = PublishSubject.create();

    // Потоки
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;


//    public AppBluetoothService(OnBluetoothServiceEventsListener onBluetoothServiceEventsListener, UiHandler handler) {
//        // LogList.v(TAG, "OnConstructor");
//        listener = onBluetoothServiceEventsListener;
//        this.handler = handler;
//        messageAnalyzer = new MessageAnalyzer(handler);
//    }
//

    public AppBluetoothService() {

    }

    @Override
    public void enable() {
        bluetoothAdapter.enable();
    }

    @Override
    public void disable() {
        bluetoothAdapter.disable();
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
    public void connectToDevice(BluetoothDevice device) {
        cancelDiscovery();
        onConnecting(device);
    }

    @Override
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

    @Override
    public PublishSubject<Integer> getBluetoothStateObservable() {
        return bluetoothStateObservable;
    }

    @Override
    public PublishSubject<List<BluetoothDevice>> getPairedDevicesObservable() {
        return pairedDevicesObservable;
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
    public PublishSubject<String> getInputMessagesObservable() {
        return inputMessagesObservable;
    }

    @Override
    public PublishSubject<LogMessage> getLogMessageObservable() {
        return logMessageObservable;
    }

    @Override
    public PublishSubject<Parameter> getCmdObservable() {
        return cmdObservable;
    }

    @Override
    public List<BluetoothDevice> getPairedDevices() {
        return new ArrayList<>(bluetoothAdapter.getBondedDevices());
    }


    @Override
    public void onStateChanged() {
        bluetoothStateObservable.onNext(bluetoothAdapter.getState());
    }

    @Override
    public void onBondStateChanged() {
        Log.d(TAG, "onBondStateChanged: ");
        pairedDevicesObservable.onNext(new ArrayList<>(bluetoothAdapter.getBondedDevices()));
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
        // LogList.d(TAG, "onConnected to Socket: " + socket.toString());
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


    private void analyzeLine(String line) {
        buffer = buffer + line + "\r\n";
        boolean startsWithLogSymbol = buffer.startsWith(String.valueOf(LOG_SYMBOL));
        if (startsWithLogSymbol) {
            int indexStartNewMessage = buffer.indexOf(LOG_SYMBOL, 1);
            if (indexStartNewMessage != -1) {
                try {
                    String nativeMessage = buffer.substring(0, indexStartNewMessage);
                    buffer = buffer.substring(indexStartNewMessage);
                    LogMessage message = createMessage(nativeMessage);
                    emmitLogMessage(message);
                    //dataManager.addLogMessage(message);
                    analyzeMessage(message);
                } catch (Exception e) {
                    Log.w(TAG, "analyzeLine: ", e);
                }
            }
        } else buffer = "";
    }

    private void emmitLogMessage(LogMessage message) {
        logMessageObservable.onNext(message);
    }

    private LogMessage createMessage(String nativeMessage) {
        String logLevel = nativeMessage.substring(1, 2);
        String logType = nativeMessage.substring(2, nativeMessage.indexOf(" "));
        int beginIndexOfTime = nativeMessage.indexOf("[") + 1;
        int endIndexOfTime = nativeMessage.indexOf("]");
        String originalTime = nativeMessage.substring(beginIndexOfTime, endIndexOfTime);
        int time = Integer.valueOf(originalTime);
        int timeInSeconds = time / 100;
        int hours = timeInSeconds / 3600;
        int minutes = (timeInSeconds - hours * 3600) / 60;
        int seconds = (timeInSeconds - hours * 3600 - minutes * 60);
        int mSeconds = time % 100;
        @SuppressLint("DefaultLocale") String convertedTime = String.format("%d:%d:%d.%d", hours, minutes, seconds, mSeconds);
        String body = nativeMessage.substring(endIndexOfTime + 2);
        return new LogMessage(logLevel, logType, originalTime, convertedTime, body);
    }


    private void analyzeMessage(LogMessage message) {
        String logType = message.getLogType();
        if (logType.equals(LOG_TYPE_CMD)) {
            boolean isCmdOk = message.getBody().contains("OK");
            if (isCmdOk) {
                Parameter parameter = CmdAnalyzer.getParameterFromMessage(message);
                //dataManager.setConfigParameter(parameter);
                if (parameter != null) {
                    cmdObservable.onNext(parameter);
                    //listener.onSetConfigParameter(parameter.getEntity());
                }
            }
        }
    }

    @Override
    public void sendData(String data) {
        if (mConnectedThread != null) {
            mConnectedThread.write(data);
        }
    }


    private class ConnectThread extends Thread {
        BluetoothSocket mSocket;
        BluetoothDevice mDevice;

        ConnectThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;
            mDevice = device;
            try {
                tmp = device.createRfcommSocketToServiceRecord(UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mSocket = tmp;
        }

        @Override
        synchronized public void run() {
            setName("ConnectThread");
            bluetoothAdapter.cancelDiscovery();
            try {
                mSocket.connect();
                Log.d("ConnectThread", "Connected");
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    mSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
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
                // LogList.d(TAG, "Закрыли socket");
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
            mmSocket = socket;
            BufferedReader tmpReaderSerial = null;
            PrintWriter tmpWriterSerial = null;
            // Get the BluetoothSocket readerSerial and output streams
            try {
                tmpReaderSerial = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            readerSerial = tmpReaderSerial;
            try {
                // LogList.d(TAG, "Пытаемся создать OutputStream");
                tmpWriterSerial = new PrintWriter(socket.getOutputStream());
                // LogList.d(TAG, "Получили OutputStream: " + tmpWriterSerial.toString());
            } catch (IOException e) {
                Log.e(TAG, "Не удалось создать OutputStream: ", e);
            }
            writerSerial = tmpWriterSerial;
        }

        public void run() {
            setName("ConnectedThread");
            // LogList.d(TAG, "Start thread " + getName());
            // inputMessagesObservable = PublishSubject.createMessage();
            String line;
            try {
                // LogList.d(TAG, "Пытаемся прочитать из потока");
                while ((line = readerSerial.readLine()) != null) {
                    Log.v(TAG, line);
                    //inputMessagesObservable.onNext(line);
                    analyzeLine(line);
                    // messageAnalyzer.analyzeMessage(line);
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


}
