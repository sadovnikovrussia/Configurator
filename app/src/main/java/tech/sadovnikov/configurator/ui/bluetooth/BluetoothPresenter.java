package tech.sadovnikov.configurator.ui.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.os.Build;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.model.BluetoothService;

@InjectViewState
public class BluetoothPresenter extends MvpPresenter<BluetoothView> {
    private static final String TAG = BluetoothPresenter.class.getSimpleName();
    private BluetoothService bluetoothService;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Listener listener;

    BluetoothPresenter(BluetoothService bluetoothService) {
        Log.v(TAG, "onConstructor");
        this.bluetoothService = bluetoothService;
    }

    public void onStart() {
        getViewState().displayBluetoothState(bluetoothService.isEnabled());
        if (bluetoothService.isEnabled()) getViewState().showDevicesContainer();
        else getViewState().hideDevicesContainer();
        PublishSubject<Integer> bluetoothStateObservable = bluetoothService.getBluetoothStateObservable();
        Disposable disposable = bluetoothStateObservable
                .subscribe(integer -> {
                            Log.d(TAG, "onNext: " + integer + bluetoothService);
                            switch (integer) {
                                case BluetoothAdapter.STATE_TURNING_ON:
                                    getViewState().showTurningOn();
                                    break;
                                case BluetoothAdapter.STATE_ON:
                                    getViewState().displayBluetoothState(true);
                                    getViewState().showDevicesContainer();
                                    getViewState().hideTurningOn();
                                    break;
                                case BluetoothAdapter.STATE_OFF:
                                    getViewState().displayBluetoothState(false);
                                    getViewState().hideDevicesContainer();
                                    break;
                            }
                        },
                        throwable -> Log.w(TAG, "onStart: ", throwable),
                        () -> Log.i(TAG, "onStart: Усе"));
        compositeDisposable.add(disposable);
    }

    void onAvailableDevicesViewShown() {
        // Todo Обработать permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getViewState().checkBtPermission()) {
                listener.onAvailableDevicesViewShown();
                bluetoothService.startDiscovery();
            } else getViewState().requestBtPermission();
        }
    }

    
    void onPairedDevicesViewShown() {
        bluetoothService.cancelDiscovery();
    }

    
    void onPositiveBtRequestResult() {
        bluetoothService.startDiscovery();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    void onBtSwitchClick(boolean isChecked) {
        if (isChecked) {
            bluetoothService.enable();
        } else {
            bluetoothService.disable();
        }
    }

    interface Listener {
        void onAvailableDevicesViewShown();
    }
}
