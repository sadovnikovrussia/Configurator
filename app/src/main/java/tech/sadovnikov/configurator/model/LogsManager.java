package tech.sadovnikov.configurator.model;


import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.model.entities.LogMessage;

import static tech.sadovnikov.configurator.model.entities.LogMessage.LOG_TYPE_CMD;

/**
 * Класс, представляющий собой логи устройства
 */
public class LogsManager extends LinkedHashSet implements Logs {
    private static final String TAG = LogsManager.class.getSimpleName();

    private Log mainLog = new Log();
    private Log cmdLog = Log.of(LOG_TYPE_CMD);
    private PublishSubject<LogMessage> observableMainLog = PublishSubject.create();
    private PublishSubject<LogMessage> observableCmdLog = PublishSubject.create();
    private Map<String, Log> logsMap = new LinkedHashMap<>();

    CmdLogListener listener;
    MessageAnalyzer messageAnalyzer;

    @Override
    public List<LogMessage> getMainLog() {
        return mainLog.getLogMessageList();
    }

    @Override
    public PublishSubject<LogMessage> getObservableMainLog() {
        return observableMainLog;
    }

    @Override
    public void addLogMessage(LogMessage message) {
        mainLog.addMessage(message);
        observableMainLog.onNext(message);
        String logType = message.getLogType();
        if (logType.equals(LOG_TYPE_CMD)) {
            cmdLog.addMessage(message);

            messageAnalyzer.analyze(message);
            analyzeCmdMessage(message);
            observableCmdLog.onNext(message);
        }
        if (logsMap.containsKey(logType)) {
            Objects.requireNonNull(logsMap.get(logType)).addMessage(message);

        } else {
            Log newLog = Log.of(logType);
            newLog.addMessage(message);
            logsMap.put(logType, newLog);
        }
    }

    private void analyzeCmdMessage(LogMessage message) {
        if (message.getBody().contains("OK")){

        }
    }

    public class MessageAnalyzer {
        public void analyze(LogMessage message) {
            if (message.getBody().contains("OK")){
                parseMessage(message);
                listener.onPositiveCommandAnswer();
            }

        }

        private void parseMessage(LogMessage message) {

        }
    }


    interface CmdLogListener {

        void onPositiveCommandAnswer();
    }


}
