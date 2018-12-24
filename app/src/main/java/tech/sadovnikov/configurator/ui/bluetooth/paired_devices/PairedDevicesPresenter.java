package tech.sadovnikov.configurator.ui.bluetooth.paired_devices;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import javax.inject.Inject;

import tech.sadovnikov.configurator.di.component.DaggerFragmentComponent;
import tech.sadovnikov.configurator.model.BluetoothService;

public class PairedDevicesPresenter extends MvpBasePresenter<PairedDevicesMvp.View> implements PairedDevicesMvp.Presenter {

    private BluetoothService bluetoothService;

    public PairedDevicesPresenter(BluetoothService bluetoothService) {
        this.bluetoothService = bluetoothService;
    }

    public PairedDevicesPresenter() {
    }

    @Override
    public void attachView(@NonNull PairedDevicesMvp.View view) {
        super.attachView(view);
    }
}
