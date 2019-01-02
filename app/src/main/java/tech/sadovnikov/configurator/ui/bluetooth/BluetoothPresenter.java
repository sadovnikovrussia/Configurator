package tech.sadovnikov.configurator.ui.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.os.Build;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.di.component.BluetoothComponent;
import tech.sadovnikov.configurator.di.component.DaggerBluetoothComponent;
import tech.sadovnikov.configurator.model.BluetoothService;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

@InjectViewState
public class BluetoothPresenter extends MvpPresenter<BluetoothView> {
    private static final String TAG = BluetoothPresenter.class.getSimpleName();


    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private BluetoothComponent bluetoothComponent;

    @Inject
    int bluetoothPermission;
    @Inject
    BluetoothService bluetoothService;


    BluetoothPresenter() {
        Log.v(TAG, "onConstructor");
        initDaggerComponent();
        bluetoothComponent.injectBluetoothPresenter(this);
    }

    private void initDaggerComponent() {
        bluetoothComponent = DaggerBluetoothComponent
                .builder()
                .applicationComponent(App.getApplicationComponent())
                .build();
    }


    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
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

    public void onStart() {
    }

    void onAvailableDevicesViewShown() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (bluetoothPermission == PERMISSION_GRANTED) {
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

    void onBtSwitchClick(boolean isChecked) {
        if (isChecked) {
            bluetoothService.enable();
        } else {
            bluetoothService.disable();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
