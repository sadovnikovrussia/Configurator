package tech.sadovnikov.configurator.model;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.model.entities.ConfigurationNew;

import static tech.sadovnikov.configurator.entities.Configuration.PARAMETER_NAMES;

@Singleton
public class AppDataManager implements DataManager {
    private static final String TAG = AppDataManager.class.getSimpleName();

    private BluetoothService bluetoothService;

    private Logs logs;
    private ConfigurationNew configuration;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public AppDataManager(BluetoothService bluetoothService, Logs logs) {
        this.bluetoothService = bluetoothService;
        this.logs = logs;
        subscribeOnBluetoothInputStream();
    }

    private void subscribeOnBluetoothInputStream() {
        PublishSubject<String> inputMessagesStream = bluetoothService.getInputMessagesStream();
        Log.d(TAG, "AppDataManager: " + bluetoothService + ", " + inputMessagesStream + ", " + Thread.currentThread().getName());
        Disposable subscribe = inputMessagesStream
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(line -> {
                    Log.d(TAG, "onNext: " + bluetoothService + ", " + inputMessagesStream + ", " + Thread.currentThread().getName() + ": " + line);
                });
        compositeDisposable.add(subscribe);
    }

    @Override
    public void addLine(String line) {
        logs.addLine(line);
    }

    @Override
    public void addMessage(Message message) {
        logs.addMessage(message);
    }

    @Override
    public String getAllMessages() {
        return logs.getAllMessages();
    }

    @Override
    public void clearSubscribes() {
        compositeDisposable.clear();
    }


//    public class StreamAnalyzer {
//        private static final String TAG = "StreamAnalyzer";
//
//        private final static char LOG_SYMBOL = 0x7F;
//        private final static int LOG_LEVEL_1 = 1;
//        private static final String CMD = "CMD";
//        private static final String OK = "OK";
//
//        static final String PARAMETER_VALUE = "Data";
//        static final String PARAMETER_NAME = "ParameterNew's name";
//
//        static final int WHAT_COMMAND_DATA = 1;
//        static final int WHAT_MAIN_LOG = 0;
//
//        //private UiHandler uiHandler;
//
//        private String buffer = "";
//
//        private DataParser dataParser = new DataParser();
//
//        private PublishSubject<Message> observableMessages = PublishSubject.create();
//
////    public StreamAnalyzer(UiHandler handler) {
////        uiHandler = handler;
////    }
//
//        public void analyze(String line) {
//            buffer = buffer + line + "\r\n";
//            if (buffer.startsWith(String.valueOf(LOG_SYMBOL))) {
//                int indexStartNewMessage = buffer.indexOf(LOG_SYMBOL, 1);
//                if (indexStartNewMessage != -1) {
//                    try {
//                        String message = buffer.substring(0, indexStartNewMessage);
//                        buffer = buffer.substring(indexStartNewMessage);
//                        String logLevel = message.substring(1, 2);
//                        String logType = message.substring(2, 5);
//                        //
//                        observableMessages.onNext(new Message(message, logLevel, logType, "1", "1", message));
//
//                        // TODO <Переделать определение logType>
//                        if (logType.equals(CMD) & Integer.valueOf(logLevel) == LOG_LEVEL_1) {
//                            Log.w(TAG, "analyze: message = " + message);
//                            if (message.contains(OK)) {
//                                for (String parameter : PARAMETER_NAMES) {
//                                    if (message.toLowerCase().contains(parameter)) {
//                                        String value = dataParser.parseMessage(message, parameter);
//                                        if (value != null) sendCommand(value, parameter);
//                                    }
//                                }
//                            }
//                        }
//                    } catch (Exception e) {
//                        // Log.w(TAG, "analyze: " + logType, e);
//                    }
//                }
//            } else buffer = "";
//        }
//
//        private void sendCommand(String value, String parameter) {
//            // Log.i(TAG, "sendCommand");
////        Message msg = new Message();
////        msg.what = WHAT_COMMAND_DATA;
////        HashMap<String, Object> msgObj = new HashMap<>();
////        msgObj.put(PARAMETER_VALUE, value);
////        msgObj.put(PARAMETER_NAME, parameter);
////        msg.obj = msgObj;
//            // Log.d(TAG, "sendCommand: " + ((HashMap)msg.obj).get(StreamAnalyzer.PARAMETER_VALUE).toString());
////        uiHandler.sendMessage(msg);
//        }
//
////        Message msg = new Message();
////        msg.what = WHAT_MAIN_LOG;
////        msg.obj = line;
//        //uiHandler.sendMessage(msg);
//    }


}
