package tech.sadovnikov.configurator.model.data.logs;

import android.support.annotation.NonNull;

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

    void addMessage(@NonNull LogMessage logMessage){
        logMessageList.add(logMessage);
    }

    public List<LogMessage> getLogMessageList() {
        return logMessageList;
    }

    public String getLogType() {
        return logType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogList)) return false;
        LogList logList = (LogList) o;
        return Objects.equals(logType, logList.getLogType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(logType);
    }

    @NonNull
    @Override
    public String toString() {
        return logType + logMessageList;
    }

}
