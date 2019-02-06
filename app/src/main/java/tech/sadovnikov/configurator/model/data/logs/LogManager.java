package tech.sadovnikov.configurator.model.data.logs;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.model.entities.LogMessage;

public interface LogManager {

    void addLogMessage(LogMessage message);

    List<LogMessage> getMainLogList();

    PublishSubject<LogMessage> getObservableMainLog();

    Map<String, LogList> getLogs();

    PublishSubject<Map<String, LogList>> getObservableLogs();

    boolean getAutoScrollMode();

    PublishSubject<Boolean> getAutoScrollModeObservable();

    void setAutoScrollMode(boolean isChecked);

    List<String> getLogTabs();

    BehaviorSubject<List<String>> getObservableLogTabs();

    PublishSubject<String> getObservableNewTab();

    LogList getLogList(String logTab);
}
