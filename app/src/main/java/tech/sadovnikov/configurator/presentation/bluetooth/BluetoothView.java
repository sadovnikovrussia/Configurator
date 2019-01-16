package tech.sadovnikov.configurator.presentation.bluetooth;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.*;

@StateStrategyType(SkipStrategy.class)
public interface BluetoothView extends MvpView {
    void displayBluetoothState(boolean state);

    void showDevices();

    void hideDevices();

    void showTurningOn();

    void hideTurningOn();

    void requestBtPermission();

    @StateStrategyType(SkipStrategy.class)
    void hideUpdateDevicesView();

    @StateStrategyType(SkipStrategy.class)
    void showUpdateDevicesView();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setSwBluetoothStateText(String text);

}
