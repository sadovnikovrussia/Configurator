package tech.sadovnikov.configurator.model;

public interface Logs {

    void addLine(String line);

    void addMessage(Message message);

    String getAllMessages();

}
