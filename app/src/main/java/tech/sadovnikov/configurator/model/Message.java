package tech.sadovnikov.configurator.model;

import android.support.annotation.NonNull;

public class Message {
    private String logLevel;
    private String logType;
    private String time;
    private String convertedTime;
    private String body;

    public Message(String logLevel, String logType, String time, String convertedTime, String body) {
        this.logLevel = logLevel;
        this.logType = logType;
        this.time = time;
        this.convertedTime = convertedTime;
        this.body = body;
    }

    @NonNull
    @Override
    public String toString() {
        return "Message{" +
                "logLevel='" + logLevel + '\'' +
                ", logType='" + logType + '\'' +
                ", time='" + time + '\'' +
                ", convertedTime='" + convertedTime + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
