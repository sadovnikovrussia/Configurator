package tech.sadovnikov.configurator.model.data.logs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import tech.sadovnikov.configurator.model.entities.LogMessage;

public class LogList {
    private String logType;
    private List<LogMessage> logMessageList = new ArrayList<>();

    LogList() {
    }

    private LogList(String logType) {
        this.logType = logType;
    }

    public static LogList of(String logType) {
        return new LogList(logType);
    }

    void addMessage(LogMessage logMessage){
        logMessageList.add(logMessage);
    }

    List<LogMessage> getLogMessageList() {
        return logMessageList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogList)) return false;
        LogList logList = (LogList) o;
        return Objects.equals(logType, logList.logType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(logType);
    }
}
