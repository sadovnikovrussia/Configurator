package tech.sadovnikov.configurator.presentation.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.os.Build;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.di.component.DaggerPresenterComponent;
import tech.sadovnikov.configurator.di.component.PresenterComponent;
import tech.sadovnikov.configurator.model.BluetoothService;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

@InjectViewState
public class BluetoothPresenter extends MvpPresenter<BluetoothView> {
    private static final String TAG = BluetoothPresenter.class.getSimpleName();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private PresenterComponent presenterComponent;

    @Inject
    int bluetoothPermission;
    @Inject
    BluetoothService bluetoothService;


    BluetoothPresenter() {
        Log.w(TAG, "onConstructor");
        initDaggerComponent();
        presenterComponent.injectBluetoothPresenter(this);
    }

    private void initDaggerComponent() {
        presenterComponent = DaggerPresenterComponent
                .builder()
                .applicationComponent(App.getApplicationComponent())
                .build();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.w(TAG, "onFirstViewAttach: ");
        Disposable subscription = bluetoothService.getBluetoothStateObservable()
                .subscribe(integer -> {
                            switch (integer) {
                                case BluetoothAdapter.STATE_TURNING_ON:
                                    getViewState().showTurningOn();
                                    break;
                                case BluetoothAdapter.STATE_ON:
                                    getViewState().setSwBluetoothStateText("Включено");
                                    getViewState().displayBluetoothState(true);
                                    getViewState().showDevices();
                                    getViewState().hideTurningOn();
                                    getViewState().showUpdateDevicesView();
                                    break;
                                case BluetoothAdapter.STATE_OFF:
                                    getViewState().setSwBluetoothStateText("Выключено");
                                    getViewState().displayBluetoothState(false);
                                    getViewState().hideDevices();
                                    getViewState().hideUpdateDevicesView();
                                    break;
                            }
                        },
                        throwable -> Log.w(TAG, "onError: ", throwable),
                        () -> Log.i(TAG, "onComplete: Усе"),
                        disposable -> {
                            getViewState().displayBluetoothState(bluetoothService.isEnabled());
                            if (bluetoothService.isEnabled()) {
                                getViewState().setSwBluetoothStateText("Включено");
                                getViewState().showDevices();
                            } else {
                                getViewState().setSwBluetoothStateText("Выключено");
                                getViewState().hideDevices();
                            }
                        });
        compositeDisposable.add(subscription);
    }

    @Override
    public void onDestroy() {
        Log.w(TAG, "onDestroy: ");
        super.onDestroy();
        compositeDisposable.clear();
    }


    void onAvailableDevicesViewShown() {
        getViewState().setAvailableDevicesPage();
    }

    void onPairedDevicesViewShown() {
        getViewState().setPairedDevicesPage();
    }

    void onPositiveBtRequestResult() {
        bluetoothPermission = PERMISSION_GRANTED;
        bluetoothService.startDiscovery();
    }

    void onBtSwitchClick(boolean isChecked) {
        if (isChecked) {
            bluetoothService.enable();
        } else {
            bluetoothService.disable();
        }
    }

    void onUpdateDevicesClick() {
        bluetoothService.cancelDiscovery();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Log.e(TAG, "updateDevices: " + (bluetoothPermission == PERMISSION_GRANTED));
            if (bluetoothPermission == PERMISSION_GRANTED) {
                bluetoothService.startDiscovery();
            } else getViewState().requestBtPermission();
        } else bluetoothService.startDiscovery();
    }

    void onPrepareOptionsMenu() {
        //Log.d(TAG, "onPrepareOptionsMenu: ");
        if (bluetoothService.isEnabled()) getViewState().showUpdateDevicesView();
        else getViewState().hideUpdateDevicesView();
    }
}
