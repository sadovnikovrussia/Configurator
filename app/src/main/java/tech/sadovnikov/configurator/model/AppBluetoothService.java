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
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    private String buffer = "";

    private PublishSubject<String> inputMessagesObservable = PublishSubject.create();

    private PublishSubject<Integer> bluetoothStateObservable = PublishSubject.create();

    private PublishSubject<List<BluetoothDevice>> pairedDevicesObservable = PublishSubject.create();

    private PublishSubject<List<BluetoothDevice>> availableDevicesObservable = PublishSubject.create();
    private List<BluetoothDevice> availableDevices = new ArrayList<>();

    private PublishSubject<LogMessage> logMessageObservable = PublishSubject.create();

    private PublishSubject<Parameter> cmdObservable = PublishSubject.create();

    private BluetoothDevice connectedDevice;

    // Потоки
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;

    // todo Немного криво работает при подключении к устройству, когда уже запущен режим подключения к другому устройству
    private int connectionState;
    private PublishSubject<Integer> connectionStateObservable;


//    public AppBluetoothService(OnBluetoothServiceEventsListener onBluetoothServiceEventsListener, UiHandler handler) {
//        // LogList.v(TAG, "OnConstructor");
//        listener = onBluetoothServiceEventsListener;
//        this.handler = handler;
//        messageAnalyzer = new MessageAnalyzer(handler);
//    }
//

    public AppBluetoothService() {
        connectionStateObservable = PublishSubject.create();
    }

    @Override
    public void enable() {
        bluetoothAdapter.enable();
    }

    @Override
    public void disable() {
        closeAllConnections();
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
        if (!device.equals(connectedDevice)){
            cancelDiscovery();
            onConnecting(device);
        }
    }

    @Override
    public void disconnectFromDevice(BluetoothDevice device) {
        Log.d(TAG, "disconnectFromDevice: ");
        //todo Переделать в будущем
        closeAllConnections();
    }

    @Override
    public void closeAllConnections() {
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        updateStatus(null, CONNECTION_STATE_DISCONNECTED);
    }

    @Override
    public int getState() {
        return bluetoothAdapter.getState();
    }

    @Override
    public int getConnectionState() {
        return connectionState;
    }

    @Override
    public PublishSubject<Integer> getConnectionStateObservable() {
        return connectionStateObservable;
    }

    @Override
    public BluetoothDevice getConnectedDevice() {
        return connectedDevice;
    }

    @Override
    public PublishSubject<Integer> getStateObservable() {
        return bluetoothStateObservable;
    }

    @Override
    public List<BluetoothDevice> getPairedDevices() {
        Log.d(TAG, "getPairedDevices: " + bluetoothAdapter.getBondedDevices());
        return new ArrayList<>(bluetoothAdapter.getBondedDevices());
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
    public void onStateChanged() {
        bluetoothStateObservable.onNext(bluetoothAdapter.getState());
    }

    @Override
    public void onBondStateChanged() {
        Log.d(TAG, "onBondStateChanged: ");
        pairedDevicesObservable.onNext(new ArrayList<>(bluetoothAdapter.getBondedDevices()));
    }

    @Override
    public void onStateConnecting(BluetoothDevice device) {
        Log.d(TAG, "onStateConnecting: " + device);
    }

    @Override
    public void onStateConnected(BluetoothDevice device) {
        Log.d(TAG, "onStateConnected: " + device + ", " + getConnectionState());
    }

    @Override
    public void onStateDisconnected(BluetoothDevice device) {
        Log.d(TAG, "onStateDisconnected: " + device);
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
                if (parameter != null) {
                    cmdObservable.onNext(parameter);
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

    private void updateStatus(BluetoothDevice bluetoothDevice, int state) {
        connectionState = state;
        connectedDevice = bluetoothDevice;
        connectionStateObservable.onNext(connectionState);
    }

    private synchronized void onConnecting(BluetoothDevice device) {
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        updateStatus(device, CONNECTION_STATE_CONNECTING);
        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
    }

    private class ConnectThread extends Thread {
        private final String TAG = ConnectThread.class.getSimpleName();
        private BluetoothSocket mSocket;
        private BluetoothDevice bluetoothDevice;

        ConnectThread(BluetoothDevice device) {
            bluetoothDevice = device;
            setName(TAG);
            BluetoothSocket tmp = null;
            try {
                tmp = device.createRfcommSocketToServiceRecord(UUID);
            } catch (IOException e) {
                e.printStackTrace();
                updateStatus(null, CONNECTION_STATE_DISCONNECTED);
            }
            mSocket = tmp;
        }

        @Override
        synchronized public void run() {
            bluetoothAdapter.cancelDiscovery();
            try {
                mSocket.connect();
            } catch (IOException e) {
                updateStatus(null, CONNECTION_STATE_DISCONNECTED);
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
            onConnected(mSocket, bluetoothDevice);
        }

        void cancel() {
            try {
                mSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "cancel: Не удалось закрыть socket в connectThread", e);
            }
        }
    }

    private synchronized void onConnected(BluetoothSocket socket, BluetoothDevice bluetoothDevice) {
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        mConnectedThread = new ConnectedThread(socket, bluetoothDevice);
        mConnectedThread.start();
    }

    private class ConnectedThread extends Thread {
        private final String TAG = ConnectedThread.class.getSimpleName();
        private BluetoothSocket mmSocket;
        private final BufferedReader readerSerial;
        private final PrintWriter writerSerial;
        private final BluetoothDevice bluetoothDevice;

        ConnectedThread(BluetoothSocket socket, BluetoothDevice bluetoothDevice) {
            this.bluetoothDevice = bluetoothDevice;
            setName(TAG);
            mmSocket = socket;
            BufferedReader tmpReaderSerial = null;
            PrintWriter tmpWriterSerial = null;
            try {
                tmpReaderSerial = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            readerSerial = tmpReaderSerial;
            try {
                tmpWriterSerial = new PrintWriter(socket.getOutputStream());
            } catch (IOException e) {
                Log.e(TAG, "Не удалось создать OutputStream: ", e);
            }
            writerSerial = tmpWriterSerial;
            Log.d(TAG, "ConnectedThread: " + "Connected");
        }

        public void run() {
            updateStatus(bluetoothDevice, CONNECTION_STATE_CONNECTED);
            String line;
            try {
                while ((line = readerSerial.readLine()) != null) {
                    Log.v(TAG, line);
                    analyzeLine(line);
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
