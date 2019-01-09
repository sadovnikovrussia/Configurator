package tech.sadovnikov.configurator.model;

public class Message {
    private String originalMessage;

    private final char startSymbol = 0x7F;
    private String logLevel;
    private String logType;
    private String time;
    private String convertedTime;
    private String body;

    public Message(String originalMessage, String logLevel, String logType, String time, String convertedTime, String body) {
        this.originalMessage = originalMessage;
        this.logLevel = logLevel;
        this.logType = logType;
        this.time = time;
        this.convertedTime = convertedTime;
        this.body = body;
    }
}
