package tech.sadovnikov.configurator.presentation.console;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import tech.sadovnikov.configurator.model.entities.LogMessage;

public interface ConsoleView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void addMessageToLogScreen(LogMessage message, boolean autoScrollOn);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showMainLogs(List<LogMessage> mainLogMessages, boolean autoScrollOn);

    @StateStrategyType(SkipStrategy.class)
    void clearMainLogs();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setAutoScrollState(boolean isAutoScroll);
}
