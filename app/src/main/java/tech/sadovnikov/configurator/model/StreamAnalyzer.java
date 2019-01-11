package tech.sadovnikov.configurator.model;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import tech.sadovnikov.configurator.model.entities.LogMessage;

import static tech.sadovnikov.configurator.model.entities.LogMessage.LOG_LEVEL_1;
import static tech.sadovnikov.configurator.model.entities.LogMessage.LOG_TYPE_CMD;
import static tech.sadovnikov.configurator.model.entities.LogMessage.LOG_SYMBOL;


/**
 * Класс, предназначенный для парсинга данных из лога
 */
public class StreamAnalyzer {
    private static final String TAG = "StreamAnalyzer";

    private DataManager dataManager;
    private BluetoothService bluetoothService;

    private String buffer = "";

    private MessageAnalyzer messageAnalyzer;
    private CommandAnalyzer commandAnalyzer;

    @Inject
    public StreamAnalyzer(BluetoothService bluetoothService, DataManager dataManager) {
        this.bluetoothService = bluetoothService;
        this.dataManager = dataManager;
//        this.messageAnalyzer = messageAnalyzer;
//        this.commandAnalyzer = commandAnalyzer;
        Disposable subscribe = bluetoothService.getInputMessagesStream()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::analyzeLine);
    }


    private void analyzeLine(String line) {
        buffer = buffer + line + "\r\n";
        if (buffer.startsWith(String.valueOf(LOG_SYMBOL))) {
            int indexStartNewMessage = buffer.indexOf(LOG_SYMBOL, 1);
            if (indexStartNewMessage != -1) {
                try {
                    String nativeMessage = buffer.substring(0, indexStartNewMessage);
                    buffer = buffer.substring(indexStartNewMessage);
                    LogMessage message = MessageCreator.create(nativeMessage);
                    dataManager.addLogMessage(message);


                    // TODO <Переделать определение logType>
//                    if (logType.equals(LOG_TYPE_CMD) & Integer.valueOf(logLevel) == LOG_LEVEL_1) {
//                        Log.w(TAG, "analyzeLine: message = " + nativeMessage);
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
                    // Log.w(TAG, "analyzeLine: " + logType, e);
                }
            }
        } else buffer = "";
    }




//    private void sendCommand(String value, String parameter) {
//        // Log.i(TAG, "sendCommand");
////        LogMessage msg = new LogMessage();
////        msg.what = WHAT_COMMAND_DATA;
////        HashMap<String, Object> msgObj = new HashMap<>();
////        msgObj.put(PARAMETER_VALUE, value);
////        msgObj.put(PARAMETER_NAME, parameter);
////        msg.obj = msgObj;
//        // Log.d(TAG, "sendCommand: " + ((HashMap)msg.obj).get(StreamAnalyzer.PARAMETER_VALUE).toString());
////        uiHandler.sendMessage(msg);
//    }
//
////        LogMessage msg = new LogMessage();
////        msg.what = WHAT_MAIN_LOG;
////        msg.obj = line;
//    //uiHandler.sendMessage(msg);
}


