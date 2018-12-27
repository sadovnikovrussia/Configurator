package tech.sadovnikov.configurator.ui.bluetooth;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

interface BluetoothView extends MvpView {

    void displayBluetoothState(boolean state);

    //    void showPairedDevices(List<BluetoothDevice> bluetoothDevices);
    //
    //    void showAvailableDevices(List<BluetoothDevice> bluetoothDevices);

    void showDevicesContainer();

    void hideDevicesContainer();

}
