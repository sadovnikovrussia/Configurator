package tech.sadovnikov.configurator.model;


import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.model.entities.LogMessage;

import static tech.sadovnikov.configurator.model.entities.LogMessage.LOG_TYPE_CMD;

/**
 * Класс, представляющий собой логи устройства
 */
public class DeviceLogs implements Logs {
    private static final String TAG = DeviceLogs.class.getSimpleName();

    private Map<String, List<LogMessage>> taggedLogs = new LinkedHashMap<>();
    private Log main = new Log();
    private Map<String, Log> logs = new LinkedHashMap<>();
    private PublishSubject<LogMessage> observableMain = PublishSubject.create();
    private Set<Log> logSet = new LinkedHashSet<>();

    public DeviceLogs() {
        logs.put(LOG_TYPE_CMD, new Log(LOG_TYPE_CMD));
    }

    @Override
    public List<LogMessage> getMainLogMessages() {
        return main.getLogMessageList();
    }

    @Override
    public PublishSubject<LogMessage> getObservableMainLog() {
        return observableMain;
    }


    @Override
    public void addLogMessage(LogMessage message) {
        main.addMessage(message);
        String logType = message.getLogType();
        switch (logType) {
            case LOG_TYPE_CMD:
                if (logSet.contains(LOG_TYPE_CMD)) {
                    Log log = logs.get(LOG_TYPE_CMD);
                    log.addMessage(message);
                }
                break;
            default:
                if (logs.containsKey())
        }
        main.addMessage(message);
        observableMain.onNext(message);
    }


}
