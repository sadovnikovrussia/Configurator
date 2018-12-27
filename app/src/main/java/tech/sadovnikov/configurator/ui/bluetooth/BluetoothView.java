package tech.sadovnikov.configurator.ui.bluetooth;


import com.arellomobile.mvp.MvpView;

interface BluetoothView extends MvpView {

    void displayBluetoothState(boolean state);

    //    void showPairedDevices(List<BluetoothDevice> bluetoothDevices);
    //
    //    void showAvailableDevices(List<BluetoothDevice> bluetoothDevices);

    void showDevicesContainer();

    void hideDevicesContainer();

}
