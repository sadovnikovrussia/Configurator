package tech.sadovnikov.configurator.model.data.logs;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.model.entities.LogMessage;

import static tech.sadovnikov.configurator.model.entities.LogMessage.LOG_TYPE_CMD;

/**
 * Класс, представляющий собой логи устройства
 */
public class AppLogManager implements LogManager {
    private static final String TAG = AppLogManager.class.getSimpleName();

    private LogList mainLogList = new LogList();
    private LogList cmdLogList = LogList.of(LOG_TYPE_CMD);
    private PublishSubject<LogMessage> observableMainLog = PublishSubject.create();
    // private PublishSubject<LogMessage> observableCmdLog = PublishSubject.createMessage();
    private Map<String, LogList> logs = new LinkedHashMap<>();

    private Analyzer analyzer;

    @Inject
    public AppLogManager() {
        this.analyzer = new Analyzer();
    }

    @Override
    public List<LogMessage> getMainLogList() {
        return mainLogList.getLogMessageList();
    }

    @Override
    public PublishSubject<LogMessage> getObservableMainLog() {
        return observableMainLog;
    }

    @Override
    public void addLogMessage(LogMessage message) {
        mainLogList.addMessage(message);
        observableMainLog.onNext(message);
        analyzer.analyze(message);
    }


    private final class Analyzer {
        void analyze(LogMessage message) {
            String logType = message.getLogType();
            if (logType.equals(LOG_TYPE_CMD)) {
                boolean isCmdOk = message.getBody().contains("OK");
                if (isCmdOk) {
                    cmdLogList.addMessage(message);
                }
            } else {
                if (logs.containsKey(logType)) {
                    Objects.requireNonNull(logs.get(logType)).addMessage(message);
                } else {
                    LogList newLogList = LogList.of(logType);
                    newLogList.addMessage(message);
                    logs.put(logType, newLogList);
                }
            }
        }
    }


}
