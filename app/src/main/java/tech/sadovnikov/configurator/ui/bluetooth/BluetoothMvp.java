package tech.sadovnikov.configurator.ui.bluetooth;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface BluetoothMvp {

    interface View extends MvpView {
        void displayBluetoothState(boolean state);

        void showDevicesContainer();

        void hideDevicesContainer();

        void showTurningOn();

        void hideTurningOn();

        void requestBtPermission();

        boolean checkBtPermission();
    }

    interface Presenter extends MvpPresenter<View> {

        void onBtSwitchClick(boolean isChecked);

        void onStart();

        void onAvailableDevicesViewShown();

        void onPairedDevicesViewShown();

        void onPositiveBtRequestResult();
    }

}
