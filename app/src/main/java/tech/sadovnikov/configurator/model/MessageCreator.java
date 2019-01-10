package tech.sadovnikov.configurator.model;

import android.annotation.SuppressLint;

public class MessageCreator {

    public static Message create(String nativeMessage) {
        String logLevel = nativeMessage.substring(1, 2);
        String logType = nativeMessage.substring(2, nativeMessage.indexOf(" "));
        int beginIndexOfTime = nativeMessage.indexOf("[") + 1;
        int endIndexOfTime = nativeMessage.indexOf("]");
        String originalTime = nativeMessage.substring(beginIndexOfTime, endIndexOfTime);
        int time = Integer.valueOf(originalTime);
        int timeInSeconds = time / 100;
        int hours = timeInSeconds / 3600;
        int minutes = (timeInSeconds - hours * 3600) / 60;
        int seconds = (timeInSeconds - hours * 3600 - minutes * 60);
        int mSeconds = time % 100;
        @SuppressLint("DefaultLocale") String convertedTime = String.format("%d:%d:%d.%d", hours, minutes, seconds, mSeconds);
        String body = nativeMessage.substring(endIndexOfTime + 2);
        return new Message(logLevel, logType, originalTime, convertedTime, body);
    }
}
