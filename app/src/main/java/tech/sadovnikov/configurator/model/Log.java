package tech.sadovnikov.configurator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import tech.sadovnikov.configurator.model.entities.LogMessage;

public class Log {
    private String logType;
    private List<LogMessage> logMessageList = new ArrayList<>();

    public Log(String logType) {
        this.logType = logType;
    }

    public Log() {
    }

    void addMessage(LogMessage logMessage){
        logMessageList.add(logMessage);
    }

    public List<LogMessage> getLogMessageList() {
        return logMessageList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Log)) return false;
        Log log = (Log) o;
        return Objects.equals(logType, log.logType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(logType);
    }
}
