package tech.sadovnikov.configurator.ui.bluetooth.available_devices;

import android.bluetooth.BluetoothDevice;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

public interface AvailableDevicesMvp {

    interface View extends MvpView{

        void showAvailableDevices(List<BluetoothDevice> availableDevices);
    }

    interface Presenter extends MvpPresenter<View> {

        void onStartView();
    }

}
