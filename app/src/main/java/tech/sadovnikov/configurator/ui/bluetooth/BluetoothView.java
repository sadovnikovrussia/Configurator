package tech.sadovnikov.configurator.ui.bluetooth;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.*;

@StateStrategyType(SkipStrategy.class)
public interface BluetoothView extends MvpView {
    void displayBluetoothState(boolean state);

    void showDevicesContainer();

    void hideDevicesContainer();

    void showTurningOn();

    void hideTurningOn();

    void requestBtPermission();

    boolean checkBtPermission();

}
