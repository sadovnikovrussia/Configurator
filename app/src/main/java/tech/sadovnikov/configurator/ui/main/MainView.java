package tech.sadovnikov.configurator.ui.main;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.*;

@StateStrategyType(SkipStrategy.class)
public interface MainView extends MvpView {

    void showBluetoothView();

    void showConsoleView();

    void showConfigurationView();


}
