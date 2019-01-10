package tech.sadovnikov.configurator.model;

import java.util.List;

import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.model.entities.Message;

public interface Logs {

    void addLine(String line);

    void addMessage(Message message);

    List<Message> getMainLogMessages();

    PublishSubject<Message> getObservableMainLog();

}
