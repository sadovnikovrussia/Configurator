package tech.sadovnikov.configurator.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.HashMap;

import static tech.sadovnikov.configurator.Contract.Configuration.parametersList;


/**
 * Класс, предназначенный для парсинга данных из лога
 */
class DataAnalyzer {
    private static final String TAG = "DataAnalyzer";

    private final static char LOG_SYMBOL = 0x7F;

    private static final String CMD = "CMD";
    private static final String OK = "OK";

    static final String PARAMETER_VALUE = "Data";
    static final String PARAMETER_NAME = "Parameter's name";

    static final int WHAT_COMMAND_DATA = 1;
    static final int WHAT_MAIN_LOG = 0;

    private Handler uiHandler;

    private String buffer = "";
    private String logType;

    DataAnalyzer(Handler handler) {
        uiHandler = handler;
    }

    void analyze(String line) {
        sendLogs(line);
        buffer = buffer + line + "\r\n";
        if (buffer.startsWith(String.valueOf(LOG_SYMBOL))) {
            int indexStartNewMessage = buffer.indexOf(LOG_SYMBOL, 1);
            if (indexStartNewMessage != -1) {
                try {
                    String message = buffer.substring(0, indexStartNewMessage);
                    buffer = buffer.substring(indexStartNewMessage);
                    String logLevel = message.substring(1, 2);
                    // TODO <Переделать определение logType>
                    logType = message.substring(2, 5);
                    if (logType.equals(CMD) & Integer.valueOf(logLevel) == 1) {
                        if (message.contains(OK)) {
                            for (String parameter : parametersList) {
                                if (message.toLowerCase().contains(parameter)) {
                                    String value = parseMessage(message);
                                    sendCommand(value, parameter);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.w(TAG, "analyze: " + logType, e);
                }
            }
        } else buffer = "";
    }

    private String parseMessage(String message) {
        if (message.contains("@version")) {
            int index_firmware_version = message.indexOf("Firmware version");
            int endIndex = message.indexOf("\r\n", index_firmware_version);
            // Log.d(TAG, message + ", " + message.substring(index_firmware_version + 17, endIndex));
            return message.substring(index_firmware_version + 17, endIndex);
        } else {
            int ravnoIndex = message.indexOf("=");
            int endIndex = message.indexOf("\r\n", ravnoIndex);
            return message.substring(ravnoIndex + 1, endIndex).replaceAll(" ", "");
        }
    }

    private void sendCommand(String value, String parameter) {
        // Log.i(TAG, "sendCommand");
        Message msg = new Message();
        msg.what = WHAT_COMMAND_DATA;
        HashMap<String, Object> msgObj = new HashMap<>();
        msgObj.put(PARAMETER_VALUE, value);
        msgObj.put(PARAMETER_NAME, parameter);
        msg.obj = msgObj;
        uiHandler.sendMessage(msg);
    }

    private void sendLogs(String line) {
        Message msg = new Message();
        msg.what = WHAT_MAIN_LOG;
        msg.obj = line;
        uiHandler.sendMessage(msg);
    }

}
