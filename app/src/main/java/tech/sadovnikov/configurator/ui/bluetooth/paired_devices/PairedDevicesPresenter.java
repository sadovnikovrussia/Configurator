package tech.sadovnikov.configurator.ui.bluetooth.paired_devices;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import tech.sadovnikov.configurator.ConfiguratorApplication;
import tech.sadovnikov.configurator.model.BluetoothService;

@InjectViewState
public class PairedDevicesPresenter extends MvpPresenter<PairedDevicesView> {
    private static final String TAG = PairedDevicesPresenter.class.getSimpleName();

    private BluetoothService bluetoothService;

    public PairedDevicesPresenter() {
        Log.d(TAG, "PairedDevicesPresenter: ");
        bluetoothService = ConfiguratorApplication.getApplicationComponent().getBluetoothService();
    }


    public void onStartView() {
        getViewState().showPairedDevices(bluetoothService.getPairedDevices());
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.d(TAG, "onFirstViewAttach: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
