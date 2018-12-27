package tech.sadovnikov.configurator.ui.bluetooth;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.model.BluetoothService;

public class BluetoothPresenter extends MvpBasePresenter<BluetoothMvp.View> implements BluetoothMvp.Presenter {
    private static final String TAG = BluetoothPresenter.class.getSimpleName();

    private BluetoothService bluetoothService = App.getBluetoothService();

    @Override
    public void attachView(@NonNull BluetoothMvp.View view) {
        super.attachView(view);

    }

    @Override
    public void onStart() {
        ifViewAttached(view -> view.displayBluetoothState(bluetoothService.isEnabled()));
        if (bluetoothService.isEnabled()) ifViewAttached(BluetoothMvp.View::showDevicesContainer);
        else ifViewAttached(BluetoothMvp.View::hideDevicesContainer);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void onBtSwitchClick(boolean isChecked) {
        if (isChecked) {
            bluetoothService.enable();
        } else {
            bluetoothService.disable();
        }
    }

}
