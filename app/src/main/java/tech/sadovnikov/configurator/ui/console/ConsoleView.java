package tech.sadovnikov.configurator.ui.console;

import com.arellomobile.mvp.MvpView;

interface ConsoleView extends MvpView {

    void addMessageToLog(String message);

    String getCommandLineText();
}
