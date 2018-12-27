package tech.sadovnikov.configurator.ui.bluetooth;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.model.BluetoothService;

public class BluetoothPresenter extends MvpBasePresenter<BluetoothMvp.View> implements MvpPresenter<BluetoothMvp.View> {
    private static final String TAG = BluetoothPresenter.class.getSimpleName();

    private BluetoothService bluetoothService = App.getBluetoothService();

    @Override
    public void attachView(@NonNull BluetoothMvp.View view) {
        super.attachView(view);
        ifViewAttached(view1 -> view1.displayBluetoothState(bluetoothService.isEnabled()));
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    void onBtSwitchClick(boolean isChecked) {
        if (isChecked) {
            bluetoothService.enable();
        } else {
            bluetoothService.disable();
        }
    }
}
