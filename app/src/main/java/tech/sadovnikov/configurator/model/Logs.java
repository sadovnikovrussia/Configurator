package tech.sadovnikov.configurator.model;

import tech.sadovnikov.configurator.Contract;

/**
 * Класс, представляющий собой логи устройства
 */
public class Logs implements Contract.Logs {
    private static final String TAG = "Logs";

    private Contract.Presenter presenter;
    private StringBuilder logsMessages = new StringBuilder();

    public Logs(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public String getLogsMessages() {
        return logsMessages.toString();
    }

    @Override
    public void addLine(String line) {
        logsMessages.append(line).append("\r\n");
        presenter.onAddLogsLineEvent(line);
    }

}
