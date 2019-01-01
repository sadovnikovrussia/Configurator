package tech.sadovnikov.configurator.ui.bluetooth.paired_devices;

import android.bluetooth.BluetoothDevice;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

public interface PairedDevicesMvp {

    interface View extends MvpView {

        void showPairedDevices(List<BluetoothDevice> pairedDevices);

    }

    interface Presenter extends MvpPresenter<View> {

        void onStartView();

    }
}
