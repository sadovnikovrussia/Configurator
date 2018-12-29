package tech.sadovnikov.configurator.ui.bluetooth.available_devices;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.model.BluetoothService;

public class AvailableDevicesPresenter extends MvpBasePresenter<AvailableDevicesMvp.View>
        implements AvailableDevicesMvp.Presenter {
    private static final String TAG = AvailableDevicesPresenter.class.getSimpleName();

    //BluetoothService bluetoothService = App.getBluetoothService();

    AvailableDevicesPresenter() {
        Log.i(TAG, "AvailableDevicesPresenter: ");
    }

    @Override
    public void onStartView() {

    }

    @Override
    public void attachView(@NonNull AvailableDevicesMvp.View view) {
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
