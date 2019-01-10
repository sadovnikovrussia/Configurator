package tech.sadovnikov.configurator.ui.console;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import tech.sadovnikov.configurator.model.entities.Message;

interface ConsoleView extends MvpView {

    void addMessageToLogScreen(Message message);

    void showMainLogs(List<Message> mainLogMessages);

    void clearMainLogs();
}
