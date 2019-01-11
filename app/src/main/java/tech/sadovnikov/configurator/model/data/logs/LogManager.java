package tech.sadovnikov.configurator.model.data.logs;

import java.util.List;

import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.model.entities.LogMessage;

public interface LogManager {

    void addLogMessage(LogMessage message);

    List<LogMessage> getMainLogList();

    PublishSubject<LogMessage> getObservableMainLog();

}
