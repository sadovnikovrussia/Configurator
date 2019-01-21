package tech.sadovnikov.configurator.presentation.bluetooth;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.*;

import tech.sadovnikov.configurator.presentation.AddToEndSingleByTagStateStrategy;

@StateStrategyType(SkipStrategy.class)
public interface BluetoothView extends MvpView {
    String TAG = BluetoothView.class.getSimpleName();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void displayBluetoothState(boolean state);

    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "devices")
    void showDevices();

    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "devices")
    void hideDevices();

    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "turningOn")
    void showTurningOn();

    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "turningOn")
    void hideTurningOn();

    void requestBtPermission();

    @StateStrategyType(value = SkipStrategy.class)
    void hideUpdateDevicesView();

    @StateStrategyType(value = SkipStrategy.class)
    void showUpdateDevicesView();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setSwBluetoothStateText(String text);

    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "devicePage")
    void setPairedDevicesPage();

    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "devicePage")
    void setAvailableDevicesPage();
}
