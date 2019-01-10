package tech.sadovnikov.configurator.model.entities;

import android.support.annotation.NonNull;

public class LogMessage {
    public final static char LOG_SYMBOL = 0x7F;
    public final static String LOG_LEVEL_1 = "1";
    public final static String LOG_TYPE_MAIN = "MAIN";
    public final static String LOG_TYPE_CMD = "CMD";

    private final String logLevel;
    private final String logType;
    private final String time;
    private final String convertedTime;
    private final String body;

    public LogMessage(String logLevel, String logType, String time, String convertedTime, String body) {
        this.logLevel = logLevel;
        this.logType = logType;
        this.time = time;
        this.convertedTime = convertedTime;
        this.body = body;
    }

    public String convertToOriginal(){
        return String.format("%c%s%s [%s] %s", LOG_SYMBOL, logLevel, logType, time, body);
    }

    public String getLogLevel() {
        return logLevel;
    }

    public String getLogType() {
        return logType;
    }

    public String getTime() {
        return time;
    }

    public String getConvertedTime() {
        return convertedTime;
    }

    public String getBody() {
        return body;
    }

    @NonNull
    @Override
    public String toString() {
        return "LogMessage{" +
                "logLevel='" + logLevel + '\'' +
                ", logType='" + logType + '\'' +
                ", time='" + time + '\'' +
                ", convertedTime='" + convertedTime + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

}
