package tech.sadovnikov.configurator.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.HashMap;

/**
 * Класс, предназначенный для парсинга данных из лога
 */
public class DataAnalyzer {

    private static final String TAG = "DataAnalyzer";

    private final static char LOG_SYMBOL = 0x7F;

    private static final String CMD = "CMD";
    private static final String OK = "OK";

    public static final String DATA = "Data";
    public static final String PARAMETER_NAME = "Parameter's name";

    public static final int WHAT_COMMAND_DATA = 1;
    public static final int WHAT_MAIN_LOG = 0;

    private static final String[] COMMANDS = new String[]{"Id", "Firmware version"};

    private Handler uiHandler;

    private String buffer = "";

    DataAnalyzer(Handler handler) {
        uiHandler = handler;
    }

    void analyze(String line) {
        sendMainLog(line);
        buffer = buffer + line + "\r\n";
        if (buffer.startsWith(String.valueOf(LOG_SYMBOL))) {
            int indexStartNewMessage = buffer.indexOf(LOG_SYMBOL, 1);
            if (indexStartNewMessage != -1) {
                String message = buffer.substring(0, indexStartNewMessage);
                buffer = buffer.substring(indexStartNewMessage);
                String logLevel = message.substring(1, 2);
                String logType = message.substring(2, 5);
                if (logType.equals(CMD) & Integer.valueOf(logLevel) == 1) {
                    if (message.contains(OK)) {
                        for (String command : COMMANDS) {
                            if (message.contains(command.toUpperCase())) {
                                String data = parseMessage(message);
                                sendDataToUi(data, command);
                            }
                        }
                    }
                }
            }
        } else buffer = "";
    }

    private String parseMessage(String message) {
        int ravnoIndex = message.indexOf("=");
        int endIndex = message.indexOf("\r\n", ravnoIndex);
        return message.substring(ravnoIndex + 1, endIndex).replaceAll(" ", "");
    }

    private void sendDataToUi(String data, String command) {
        Log.i(TAG, "sendDataToUi");
        Message msg = new Message();
        msg.what = WHAT_COMMAND_DATA;
        HashMap<String, Object> msgObj = new HashMap<>();
        msgObj.put(DATA, data);
        msgObj.put(PARAMETER_NAME, command);
        msg.obj = msgObj;
        uiHandler.sendMessage(msg);
    }

    private void sendMainLog(String line){
        Message msg = new Message();
        msg.what = WHAT_MAIN_LOG;
        msg.obj = line;
        uiHandler.sendMessage(msg);
    }

}
