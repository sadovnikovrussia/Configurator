package tech.sadovnikov.configurator.model;

import android.util.Log;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import static tech.sadovnikov.configurator.entities.Configuration.PARAMETER_NAMES;


/**
 * Класс, предназначенный для парсинга данных из лога
 */
public class StreamAnalyzer {
    private static final String TAG = "StreamAnalyzer";

    private final static char LOG_SYMBOL = 0x7F;
    private final static int LOG_LEVEL_1 = 1;
    private static final String CMD = "CMD";
    private static final String OK = "OK";

    static final String PARAMETER_VALUE = "Data";
    static final String PARAMETER_NAME = "ParameterNew's name";

    static final int WHAT_COMMAND_DATA = 1;
    static final int WHAT_MAIN_LOG = 0;

    DataManager dataManager;
    BluetoothService bluetoothService;

    //private UiHandler uiHandler;

    private String buffer = "";

    private DataParser dataParser = new DataParser();

    //private PublishSubject<Message> observableMessages = PublishSubject.create();

    @Inject
    public StreamAnalyzer(BluetoothService bluetoothService, DataManager dataManager) {
        this.bluetoothService = bluetoothService;
        this.dataManager = dataManager;
        Disposable subscribe = bluetoothService.getInputMessagesStream()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(this::analyze);
    }


    private void analyze(String line) {
        buffer = buffer + line + "\r\n";
        if (buffer.startsWith(String.valueOf(LOG_SYMBOL))) {
            int indexStartNewMessage = buffer.indexOf(LOG_SYMBOL, 1);
            if (indexStartNewMessage != -1) {
                try {
                    String nativeMessage = buffer.substring(0, indexStartNewMessage);
                    buffer = buffer.substring(indexStartNewMessage);
                    Message message = MessageCreator.create(nativeMessage);
                    dataManager.addMessage(message);
                    // TODO <Переделать определение logType>
//                    if (logType.equals(CMD) & Integer.valueOf(logLevel) == LOG_LEVEL_1) {
//                        Log.w(TAG, "analyze: message = " + nativeMessage);
//                        if (nativeMessage.contains(OK)) {
//                            for (String parameter : PARAMETER_NAMES) {
//                                if (nativeMessage.toLowerCase().contains(parameter)) {
//                                    String value = dataParser.parseMessage(nativeMessage, parameter);
//                                    if (value != null) sendCommand(value, parameter);
//                                }
//                            }
//                        }
//                    }
                } catch (Exception e) {
                    // Log.w(TAG, "analyze: " + logType, e);
                }
            }
        } else buffer = "";
    }

//    private void sendCommand(String value, String parameter) {
//        // Log.i(TAG, "sendCommand");
////        Message msg = new Message();
////        msg.what = WHAT_COMMAND_DATA;
////        HashMap<String, Object> msgObj = new HashMap<>();
////        msgObj.put(PARAMETER_VALUE, value);
////        msgObj.put(PARAMETER_NAME, parameter);
////        msg.obj = msgObj;
//        // Log.d(TAG, "sendCommand: " + ((HashMap)msg.obj).get(StreamAnalyzer.PARAMETER_VALUE).toString());
////        uiHandler.sendMessage(msg);
//    }
//
////        Message msg = new Message();
////        msg.what = WHAT_MAIN_LOG;
////        msg.obj = line;
//    //uiHandler.sendMessage(msg);
}


