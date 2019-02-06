package tech.sadovnikov.configurator.presentation.console.log_messages;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import tech.sadovnikov.configurator.model.data.logs.LogList;
import tech.sadovnikov.configurator.model.entities.LogMessage;

@StateStrategyType(SkipStrategy.class)
public interface LogMessagesView extends MvpView {
    void addMessageToLogScreen(LogMessage logMessage, boolean autoScrollMode);
    void addMessageToMainLogScreen(LogMessage logMessage, boolean autoScrollMode);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showLogs(LogList logList, boolean autoScrollMode);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showMainLogs(List<LogMessage> mainLogList, boolean autoScrollMode);
}
