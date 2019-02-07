package tech.sadovnikov.configurator.presentation.console;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import tech.sadovnikov.configurator.model.entities.LogMessage;
import tech.sadovnikov.configurator.presentation.AddToEndSingleByTagStateStrategy;

@StateStrategyType(SkipStrategy.class)
public interface ConsoleView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setAutoScrollState(boolean isAutoScroll);

    @StateStrategyType(SkipStrategy.class)
    void showSaveLogDialog();
    @StateStrategyType(SkipStrategy.class)
    void hideSaveLogDialog();

    void requestWritePermission();

    void showMessage(String message);

    void showSuccessSaveLogMessage(String fileName);
    void showErrorSaveLogMessage(Exception e);

}
