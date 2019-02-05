package tech.sadovnikov.configurator.model.data.logs;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import rx.internal.operators.OnSubscribeRedo;
import tech.sadovnikov.configurator.model.entities.LogMessage;

/**
 * Класс, представляющий собой логи устройства
 */
public class AppLogManager implements LogManager {
    private static final String TAG = AppLogManager.class.getSimpleName();

    private LogList mainLogList = new LogList();
    private LogList cmdLogList = LogList.of("CMD");
    private PublishSubject<LogMessage> observableMainLog = PublishSubject.create();
    private Map<String, LogList> logs = new LinkedHashMap<>();
    private PublishSubject<Map<String, LogList>> observableLogs;


    private PublishSubject<String> observableNewTab;

    private BehaviorSubject<List<String>> observableTabs;

    private Analyzer analyzer;

    private boolean autoScrollMode;
    private PublishSubject<Boolean> autoScrollModeObservable;


    @Inject
    public AppLogManager() {
        analyzer = new Analyzer();
        autoScrollMode = true;
        autoScrollModeObservable = PublishSubject.create();
        observableLogs = PublishSubject.create();
        logs.put("MAIN", mainLogList);
        logs.put("CMD", cmdLogList);
        observableTabs = BehaviorSubject.createDefault(new ArrayList<>(logs.keySet()));
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
    public Map<String, LogList> getLogs() {
        return logs;
    }

    @Override
    public PublishSubject<Map<String, LogList>> getObservableLogs() {
        return observableLogs;
    }

    @Override
    public boolean getAutoScrollMode() {
        return autoScrollMode;
    }

    @Override
    public PublishSubject<Boolean> getAutoScrollModeObservable() {
        return autoScrollModeObservable;
    }

    @Override
    public void setAutoScrollMode(boolean isChecked) {
        autoScrollMode = isChecked;
    }

    @Override
    public List<String> getLogTabs() {
        return new ArrayList<>(logs.keySet());
    }

    @Override
    public BehaviorSubject<List<String>> getObservableLogTabs() {
        return observableTabs;
    }

    @Override
    public PublishSubject<String> getObservableNewTab() {
        return observableNewTab;
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
            if (logs.containsKey(logType)) {
                logs.get(logType).addMessage(message);
            } else {
                LogList newLogList = LogList.of(logType);
                newLogList.addMessage(message);
                logs.put(logType, newLogList);
                observableNewTab.onNext(logType);
                observableTabs.onNext(new ArrayList<>(logs.keySet()));
            }
        }
    }


}
