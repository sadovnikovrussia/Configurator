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
import tech.sadovnikov.configurator.model.data.RemoteDevice;
import tech.sadovnikov.configurator.model.entities.LogMessage;
import tech.sadovnikov.configurator.model.entities.Parameter;
import tech.sadovnikov.configurator.utils.ParametersEntities;

import static tech.sadovnikov.configurator.model.entities.LogMessage.LOG_SYMBOL;
import static tech.sadovnikov.configurator.model.entities.LogMessage.LOG_TYPE_CMD;


/**
 * Класс, прденазначенный для работы с Bluetooth соединением
 */
public class AppBluetoothService implements BluetoothService, BluetoothBroadcastReceiver.Listener, RemoteDevice {
    private static final String TAG = AppBluetoothService.class.getSimpleName();

    private static final java.util.UUID UUID = java.util.UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    private String buffer = "";

    private PublishSubject<String> inputMessagesObservable = PublishSubject.create();
    private PublishSubject<Integer> bluetoothState = PublishSubject.create();
    private PublishSubject<List<BluetoothDevice>> pairedDevices = PublishSubject.create();
    private PublishSubject<List<BluetoothDevice>> availableDevicesObservable = PublishSubject.create();
    private List<BluetoothDevice> availableDevices = new ArrayList<>();

    private PublishSubject<LogMessage> logMessageObservable = PublishSubject.create();
    private InputStreamListener inputStreamListener;

    // Потоки
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;

    private MessageAnalyzer messageAnalyzer;

    private AnswerCallback answerCallback;

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
    public PublishSubject<String> getInputMessagesObservable() {
        return inputMessagesObservable;
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
        //LogList.d(TAG, "Connecting to: " + device);

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
                    emmitMessage(message);
                    //dataManager.addLogMessage(message);
                    analyzeMessage(message);
                } catch (Exception e) {
                    Log.w(TAG, "analyzeLine: ", e);
                }
            }
        } else buffer = "";
    }

    private void emmitMessage(LogMessage message) {
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
                    answerCallback.onAnswerOk(parameter);
                    //listener.onSetConfigParameter(parameter.getEntity());
                }
            } else {
                answerCallback.onAnswerError(message);
            }
        }
    }

    @Override
    public void getParameter(ParametersEntities parameterEntity, AnswerCallback answerCallback) {
        this.answerCallback = answerCallback;
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
            // LogList.d(TAG, "Create ConnectThread");
            BluetoothSocket tmp = null;
            mDevice = device;
            try {
                tmp = device.createRfcommSocketToServiceRecord(UUID);
                // LogList.d(TAG, "Получаем socket c помощью createRfcommSocketToServiceRecord(UUID): " + "BluetoothSocket = " + tmp.toString());
            } catch (IOException e) {
                e.printStackTrace();
                // LogList.e(TAG, "ConnectThread: не удалось createRfcommSocketToServiceRecord", e);
                // LogList.d(TAG, "Socket's createRfcommSocketToServiceRecord(UUID) method failed", e);
            }
            mSocket = tmp;
        }

        @Override
        synchronized public void run() {
            setName("ConnectThread");
            // LogList.d(TAG, "Started thread " + "\"" + getName() + "\"");
            bluetoothAdapter.cancelDiscovery();
            // LogList.d(TAG, "Выключили поиск: " + "bluetoothAdapter.isDiscovering() = " + bluetoothAdapter.isDiscovering());
            try {
                mSocket.connect();
                Log.d("ConnectThread", "Connected");
            } catch (IOException e) {
                Log.w("ConnectThread", "run: ", e);
//                LogMessage message = new LogMessage();
//                message.what = WHAT_CONNECTING_ERROR;
//                handler.sendMessage(message);
                //listener.onErrorToConnect();
                //LogList.d(TAG, "Не получилось. mSocket is onConnected? " + String.valueOf(mSocket.isConnected()) + ", " + e.getMessage());
                //e.printStackTrace();
                try {
                    mSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    // LogList.d(TAG, "Не удалось закрыть socket: " + e1.getMessage());
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
            // LogList.d(TAG, "Create ConnectedThread");
            mmSocket = socket;
            BufferedReader tmpReaderSerial = null;
            PrintWriter tmpWriterSerial = null;
            // Get the BluetoothSocket readerSerial and output streams
            try {
                // LogList.d(TAG, "Пытаемся получить InputStream");
                tmpReaderSerial = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // LogList.d(TAG, "Получили InputStream: " + tmpReaderSerial.toString());
            } catch (IOException e) {
                // LogList.d(TAG, "Не удалось получить InputStream: " + e.getMessage());
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
