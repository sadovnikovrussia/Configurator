package tech.sadovnikov.configurator.ui.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import tech.sadovnikov.configurator.ConfiguratorApplication;
import tech.sadovnikov.configurator.model.BluetoothService;

@InjectViewState
public class BluetoothPresenter extends MvpPresenter<BluetoothView> {
    private static final String TAG = BluetoothPresenter.class.getSimpleName();

    private BluetoothService bluetoothService;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BluetoothPresenter() {
        Log.w(TAG, "BluetoothPresenter: " + this);
        bluetoothService = ConfiguratorApplication.getApplicationComponent().getBluetoothService();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.v(TAG, "onFirstViewAttach: ");
        Disposable disposable = bluetoothService.getBluetoothStateObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    switch (integer) {
                        case BluetoothAdapter.STATE_ON:
                            getViewState().displayBluetoothState(true);
                            getViewState().showDevicesContainer();
                            break;
                        case BluetoothAdapter.STATE_OFF:
                            getViewState().displayBluetoothState(false);
                            getViewState().hideDevicesContainer();
                            break;
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void onStart() {
        getViewState().displayBluetoothState(bluetoothService.isEnabled());
        if (bluetoothService.isEnabled()) {
            getViewState().showDevicesContainer();
        } else {
            getViewState().hideDevicesContainer();
        }
    }

    @Override
    public void onDestroy() {
        Log.w(TAG, "onDestroy: " + this);
        compositeDisposable.dispose();
        super.onDestroy();
    }

    void onBtSwitchClick(boolean isChecked) {
        if (isChecked) bluetoothService.enable();
        else bluetoothService.disable();
    }
}
