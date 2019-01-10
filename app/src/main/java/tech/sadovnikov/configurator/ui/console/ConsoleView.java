package tech.sadovnikov.configurator.ui.console;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import tech.sadovnikov.configurator.model.entities.LogMessage;

interface ConsoleView extends MvpView {

    void addMessageToLogScreen(LogMessage message);

    void showMainLogs(List<LogMessage> mainLogMessages);

    void clearMainLogs();
}
