package tech.sadovnikov.configurator.ui.bluetooth.paired_devices;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.model.BluetoothService;

public class PairedDevicesPresenter extends MvpBasePresenter<PairedDevicesMvp.View> implements PairedDevicesMvp.Presenter {
    private static final String TAG = PairedDevicesPresenter.class.getSimpleName();

    private BluetoothService bluetoothService = App.getBluetoothService();

    PairedDevicesPresenter() {
        Log.d(TAG, "PairedDevicesPresenter: ");
    }

    @Override
    public void onStartView() {
        if (bluetoothService.isEnabled()) {
            ifViewAttached(view -> view.showPairedDevices(bluetoothService.getPairedDevices()));
        }
    }

    @Override
    public void attachView(@NonNull PairedDevicesMvp.View view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

}
