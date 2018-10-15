package tech.sadovnikov.configurator.model;

import tech.sadovnikov.configurator.Contract;

/**
 * Класс, представляющий собой логи устройства
 */
public class Logs implements Contract.Logs {
    private static final String TAG = "Logs";

    private static OnLogsActionsListener onLogsActionsListener;

    private StringBuilder logsMessages = new StringBuilder();

    private static final Logs ourInstance = new Logs();

    public static Logs getInstance(Contract.Presenter presenter) {
        if (presenter instanceof OnLogsActionsListener) {
            onLogsActionsListener = (OnLogsActionsListener) presenter;
        } else {
            throw new RuntimeException(presenter.toString()
                    + " must implement OnLogsActionsListener");
        }
        return ourInstance;
    }

    private Logs() {
    }

    @Override
    public String getLogsMessages() {
        return logsMessages.toString();
    }

    @Override
    public void addLine(String line) {
        logsMessages.append(line).append("\r\n");
        onLogsActionsListener.onAddLogsLineEvent(line);
    }


    public interface OnLogsActionsListener {
        void onAddLogsLineEvent(String line);
    }
}
