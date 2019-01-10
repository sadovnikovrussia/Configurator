package tech.sadovnikov.configurator.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, представляющий собой логи устройства
 */
public class DeviceLogs implements Logs {
    private static final String TAG = DeviceLogs.class.getSimpleName();

    private StringBuilder allMessages = new StringBuilder();
    private Map<String, List<Message>> taggedLogs = new LinkedHashMap<>();
    private List<Message> main = new ArrayList<>();


    @Override
    public String getAllMessages() {
        return allMessages.toString();
    }

    @Override
    public void addLine(String line) {
        Log.d(TAG, "addLine: " + line);
        allMessages.append(line).append("\r\n");
    }

    @Override
    public void addMessage(Message message) {
        Log.d(TAG, "addMessage: " + message);
        main.add(message);
    }


}
