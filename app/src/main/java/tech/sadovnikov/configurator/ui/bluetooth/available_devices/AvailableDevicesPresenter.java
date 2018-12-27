package tech.sadovnikov.configurator.ui.bluetooth.available_devices;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class AvailableDevicesPresenter extends MvpPresenter<AvailableDevicesView> {
    private static final String TAG = AvailableDevicesPresenter.class.getSimpleName();

    public AvailableDevicesPresenter() {
        Log.i(TAG, "AvailableDevicesPresenter: ");
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.i(TAG, "onFirstViewAttach: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
}
