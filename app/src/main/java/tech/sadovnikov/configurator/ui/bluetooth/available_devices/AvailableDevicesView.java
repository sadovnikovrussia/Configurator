package tech.sadovnikov.configurator.ui.bluetooth.available_devices;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface AvailableDevicesView extends MvpView {

    void showDevices();

    void hideDevices();

}
