package tech.sadovnikov.configurator.presenter;

import android.os.Message;
import android.util.Log;

import java.util.HashMap;

import static tech.sadovnikov.configurator.entities.Configuration.PARAMETER_NAMES;


/**
 * Класс, предназначенный для парсинга данных из лога
 */
public class DataAnalyzer {
    private static final String TAG = "DataAnalyzer";

    private final static char LOG_SYMBOL = 0x7F;
    private final static int LOG_LEVEL_1 = 1;
    private static final String CMD = "CMD";
    private static final String OK = "OK";

    static final String PARAMETER_VALUE = "Data";
    static final String PARAMETER_NAME = "ParameterNew's name";

    static final int WHAT_COMMAND_DATA = 1;
    static final int WHAT_MAIN_LOG = 0;

    private UiHandler uiHandler;

    private String buffer = "";
    private String logType;

    private DataParser dataParser = new DataParser();

    public DataAnalyzer(UiHandler handler) {
        uiHandler = handler;
    }

    public void analyze(String line) {
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
                    if (logType.equals(CMD) & Integer.valueOf(logLevel) == LOG_LEVEL_1) {
                        Log.w(TAG, "analyze: message = " + message);
                        if (message.contains(OK)) {
                            for (String parameter : PARAMETER_NAMES) {
                                if (message.toLowerCase().contains(parameter)) {
                                    String value = dataParser.parseMessage(message, parameter);
                                    if (value != null) sendCommand(value, parameter);
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

    private void sendCommand(String value, String parameter) {
        // Log.i(TAG, "sendCommand");
        Message msg = new Message();
        msg.what = WHAT_COMMAND_DATA;
        HashMap<String, Object> msgObj = new HashMap<>();
        msgObj.put(PARAMETER_VALUE, value);
        msgObj.put(PARAMETER_NAME, parameter);
        msg.obj = msgObj;
        // Log.d(TAG, "sendCommand: " + ((HashMap)msg.obj).get(DataAnalyzer.PARAMETER_VALUE).toString());
        uiHandler.sendMessage(msg);
    }

    private void sendLogs(String line) {
        Message msg = new Message();
        msg.what = WHAT_MAIN_LOG;
        msg.obj = line;
        uiHandler.sendMessage(msg);
    }

}
