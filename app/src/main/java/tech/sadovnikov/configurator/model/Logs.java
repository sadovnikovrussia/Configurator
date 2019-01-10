package tech.sadovnikov.configurator.model;

import java.util.List;

import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.model.entities.LogMessage;

public interface Logs {

    void addLogMessage(LogMessage message);

    List<LogMessage> getMainLogMessages();

    PublishSubject<LogMessage> getObservableMainLog();

}
