package tech.sadovnikov.configurator.presenter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;

/**
 * Класс, прденазначенный для работы с Bluetooth соединением
 */
public class BluetoothService {

    private static final String TAG = "BluetoothService";
    private static final java.util.UUID UUID = java.util.UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final int WHAT_LOG = 10;

    private static BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private ArrayList<BluetoothDevice> availableDevices = new ArrayList<>();

    // Потоки
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private Thread analyzeThread;

    private Handler mHandler;
    DataAnalyzer dataAnalyzer;

    BluetoothService(Handler handler) {
        Log.v(TAG, "OnConstructor");
        mHandler = handler;
        dataAnalyzer = new DataAnalyzer(mHandler);
    }

    void enableBt() {
        // Log.d(TAG, "enableBt");
        bluetoothAdapter.enable();
    }

    void disableBt() {
        // Log.d(TAG, "disableBt");
        bluetoothAdapter.disable();
    }

    public boolean isEnabled() {
        return bluetoothAdapter.isEnabled();
    }

    // Получение списка спаренных устройств
    static public ArrayList<BluetoothDevice> getBondedDevices() {
        Set<BluetoothDevice> mBondedDevices = bluetoothAdapter.getBondedDevices();
        return new ArrayList<>(mBondedDevices);
    }

    void clearAvailableDevices() {
        this.availableDevices.clear();
    }

    void addAvailableDevice (BluetoothDevice bluetoothDevice) {
        this.availableDevices.add(bluetoothDevice);
    }

    void setAvailableDevices(ArrayList<BluetoothDevice> availableDevices) {
        this.availableDevices = availableDevices;
    }

    ArrayList<BluetoothDevice> getAvailableDevices() {
        //Log.d(TAG, "getAvailableDevices: " + availableDevices.toString());
        return availableDevices;
    }

    ArrayList<BluetoothDevice> getPairedDevices() {
        ArrayList<BluetoothDevice> pairedDevices = new ArrayList<>(bluetoothAdapter.getBondedDevices());
        //Log.d(TAG, "getPairedDevices: " + pairedDevices.toString());
        return pairedDevices;
    }

    void connectTo(String address) {
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
        Log.d(TAG, "onConnecting to: " + device.getName());
        onConnecting(device);
    }

    void connectTo(BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "onConnecting to: " + bluetoothDevice.getName());
        onConnecting(bluetoothDevice);
    }


    private synchronized void onConnecting(BluetoothDevice device) {
        Log.d(TAG, "Connecting to: " + device);

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
        Log.d(TAG, "onConnected to Socket: " + socket.toString());
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
        try {
            mConnectThread.mSocket.close();
            mConnectedThread.mmSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void startDiscovery() {
        Log.d(TAG, "startDiscovery");
        bluetoothAdapter.startDiscovery();
    }

    void cancelDiscovery() {
        bluetoothAdapter.cancelDiscovery();
    }

    private class ConnectThread extends Thread {
        BluetoothSocket mSocket;
        BluetoothDevice mDevice;

        ConnectThread(BluetoothDevice device) {
            Log.d(TAG, "Create ConnectThread");
            BluetoothSocket tmp = null;
            mDevice = device;
            try {
                tmp = device.createRfcommSocketToServiceRecord(UUID);
                Log.d(TAG, "Получаем socket c помощью createRfcommSocketToServiceRecord(UUID): " + "BluetoothSocket = " + tmp.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Socket's createRfcommSocketToServiceRecord(UUID) method failed", e);
            }
            mSocket = tmp;
        }

        @Override
        synchronized public void run() {
            setName("ConnectThread");
            Log.d(TAG, "Started thread " + "\"" + getName() + "\"");
            bluetoothAdapter.cancelDiscovery();
            Log.d(TAG, "Выключили поиск: " + "bluetoothAdapter.isDiscovering() = " + bluetoothAdapter.isDiscovering());
            try {
                Log.d(TAG, "Пробуем mSocket.onConnecting()");
                mSocket.connect();
                Message message = new Message();
                message.obj = mDevice.getName();
                mHandler.sendMessage(message);
                Log.d(TAG, "mSocket is onConnected? " + String.valueOf(mSocket.isConnected()));
            } catch (IOException e) {
                Log.d(TAG, "Не получилось. mSocket is onConnected? " + String.valueOf(mSocket.isConnected()) + ", " + e.getMessage());
                e.printStackTrace();
                try {
                    Log.d(TAG, "Закрываем socket");
                    mSocket.close();
                    Log.d(TAG, "Закрыли socket");
                } catch (IOException e1) {
                    e1.printStackTrace();
                    Log.d(TAG, "Не удалось закрыть socket: " + e1.getMessage());
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
            } catch (IOException e) {
                Log.d(TAG, "close() of onConnecting " + " socket failed", e);
            }
        }
    }

    private class ConnectedThread extends Thread {
        final BluetoothSocket mmSocket;
        private final BufferedReader readerSerial;
        private final PrintWriter writerSerial;

        ConnectedThread(BluetoothSocket socket) {
            Log.d(TAG, "Create ConnectedThread");
            mmSocket = socket;
            BufferedReader tmpReaderSerial = null;
            PrintWriter tmpWriterSerial = null;

            // Get the BluetoothSocket readerSerial and output streams
            try {
                Log.d(TAG, "Пытаемся получить InputStream");
                tmpReaderSerial = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Log.d(TAG, "Получили InputStream: " + tmpReaderSerial.toString());
            } catch (IOException e) {
                Log.d(TAG, "Не удалось получить InputStream: " + e.getMessage());
            }
            readerSerial = tmpReaderSerial;
            try {
                Log.d(TAG, "Пытаемся создать OutputStream");
                tmpWriterSerial = new PrintWriter(socket.getOutputStream());
                Log.d(TAG, "Получили OutputStream: " + tmpWriterSerial.toString());
            } catch (IOException e) {
                Log.d(TAG, "Не удалось создать OutputStream: ", e);
            }
            writerSerial = tmpWriterSerial;
        }

        public void run() {
            setName("ConnectedThread");
            Log.d(TAG, "Start thread " + getName());
            String line;
            try {
                Log.d(TAG, "Пытаемся прочитать из потока");
                while ((line = readerSerial.readLine()) != null) {
                    Log.i(TAG, line);
                    dataAnalyzer.analyze(line);
                    //Message message = new Message();
                    //message.obj = line;
                    //message.what = WHAT_LOG;
                    //mUiHandler.sendMessage(message);
                }
            } catch (IOException e) {
                Log.d(TAG, "Не удалось прочитать из потока", e);
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

    //String readFromDevice(String line){
    //    return line;
    //}


}
