package tech.sadovnikov.configurator.ui.bluetooth;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

public class BluetoothPresenter extends MvpBasePresenter<BluetoothMvp.View> implements MvpPresenter<BluetoothMvp.View> {

    @Override
    public void attachView(@NonNull BluetoothMvp.View view) {
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
