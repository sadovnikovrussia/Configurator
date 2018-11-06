package tech.sadovnikov.configurator.presenter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import static tech.sadovnikov.configurator.presenter.UiHandler.WHAT_CONNECTING_ERROR;

/**
 * Класс, прденазначенный для работы с Bluetooth соединением
 */
public class BluetoothService {
    private static final String TAG = "BluetoothService";
    private static final java.util.UUID UUID = java.util.UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private static BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private ArrayList<BluetoothDevice> availableDevices = new ArrayList<>();

    private OnBluetoothServiceEventsListener listener;

    // Потоки
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;

    private UiHandler handler;
    private DataAnalyzer dataAnalyzer;

    BluetoothService(OnBluetoothServiceEventsListener onBluetoothServiceEventsListener, UiHandler handler) {
        // Log.v(TAG, "OnConstructor");
        listener = onBluetoothServiceEventsListener;
        this.handler = handler;
        dataAnalyzer = new DataAnalyzer(handler);
    }

    void enableBt() {
        // Logs.d(TAG, "enableBt");
        bluetoothAdapter.enable();
    }

    void disableBt() {
        // Logs.d(TAG, "disableBt");
        bluetoothAdapter.disable();
    }

    public boolean isEnabled() {
        return bluetoothAdapter.isEnabled();
    }

    private void clearAvailableDevices() {
        this.availableDevices.clear();
    }

    void addAvailableDevice(BluetoothDevice bluetoothDevice) {
        this.availableDevices.add(bluetoothDevice);
    }

    ArrayList<BluetoothDevice> getAvailableDevices() {
        //Logs.d(TAG, "getAvailableDevices: " + availableDevices.toString());
        return availableDevices;
    }

    // Получение списка спаренных устройств
    ArrayList<BluetoothDevice> getBondedDevices() {
        //Logs.d(TAG, "getBondedDevices: " + pairedDevices.toString());
        return new ArrayList<>(bluetoothAdapter.getBondedDevices());
    }

    void connectTo(String address) {
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
        listener.onConnectingTo(device.getName());
        onConnecting(device);

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

    void closeAllConnections() {
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

    void startDiscovery() {
        // Log.d(TAG, "onTestButtonClick");
        clearAvailableDevices();
        bluetoothAdapter.startDiscovery();
    }

    void cancelDiscovery() {
        // Log.d(TAG, "cancelDiscovery");
        bluetoothAdapter.cancelDiscovery();
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
                // Log.d(TAG, "");
            } catch (IOException e) {
                Log.w(TAG, "run: ", e);
                Message message = new Message();
                message.what = WHAT_CONNECTING_ERROR;
                handler.sendMessage(message);
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

            synchronized (BluetoothService.this) {
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
            String line;
            try {
                // Log.d(TAG, "Пытаемся прочитать из потока");
                while ((line = readerSerial.readLine()) != null) {
                    Log.v(TAG, line);
                    dataAnalyzer.analyze(line);
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

    void sendData(String data) {
        if (mConnectedThread != null) {
            mConnectedThread.write(data);
        }
    }

    interface OnBluetoothServiceEventsListener {

        void onConnectingTo(String name);

        void onErrorToConnect();
    }
}
