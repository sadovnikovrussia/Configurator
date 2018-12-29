package tech.sadovnikov.configurator.ui.bluetooth;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface BluetoothMvp {

    interface View extends MvpView {
        void displayBluetoothState(boolean state);

    //    void showPairedDevices(List<BluetoothDevice> bluetoothDevices);
    //
    //    void showAvailableDevices(List<BluetoothDevice> bluetoothDevices);

        void showDevicesContainer();

        void hideDevicesContainer();

        void showTurningOn();

        void hideTurningOn();

    }

    interface Presenter extends MvpPresenter<View> {

        void onBtSwitchClick(boolean isChecked);

        void onStart();

        void onAvailableDevicesViewShown();

        void onPairedDevicesViewShown();
    }

}
