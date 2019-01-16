package tech.sadovnikov.configurator.model;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import tech.sadovnikov.configurator.model.data.DataManager;
import tech.sadovnikov.configurator.model.entities.LogMessage;
import tech.sadovnikov.configurator.model.entities.Parameter;

import static tech.sadovnikov.configurator.model.entities.LogMessage.LOG_SYMBOL;
import static tech.sadovnikov.configurator.model.entities.LogMessage.LOG_TYPE_CMD;

public class DeviceManager {
    private static final String TAG = DeviceManager.class.getSimpleName();
    private DataManager dataManager;

    private String buffer = "";
    private MessageAnalyzer.OnSetCfgParameterListener listener;
    BluetoothService bluetoothService;
    BluetoothDevice bluetoothDevice;

    public DeviceManager(BluetoothService bluetoothService) {
        this.bluetoothService = bluetoothService;
    }

    void create(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
        this.bluetoothService = bluetoothService;
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
                    dataManager.addLogMessage(message);
                    analyzeMessage(message);
                } catch (Exception e) {
                    Log.w(TAG, "analyzeLine: ", e);
                }
            }
        } else buffer = "";
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
                dataManager.setConfigParameter(parameter);
                if (parameter != null) {
                    listener.onSetConfigParameter(parameter.getEntity());
                }
            }
        }
    }

}
