package tech.sadovnikov.configurator.presentation.bluetooth;

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
        //Log.v(TAG, "onConstructor");
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
        getViewState().displayBluetoothState(bluetoothService.isEnabled());
        if (bluetoothService.isEnabled()) {
            getViewState().setSwBluetoothStateText("Включено");
            getViewState().showDevices();
        } else {
            getViewState().setSwBluetoothStateText("Выключено");
            getViewState().hideDevices();
        }
        PublishSubject<Integer> bluetoothStateObservable = bluetoothService.getBluetoothStateObservable();
        Disposable disposable = bluetoothStateObservable
                .subscribe(integer -> {
                            Log.d(TAG, "onNext: " + integer + bluetoothService);
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
                        () -> Log.i(TAG, "onComplete: Усе"));
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void updateDevices() {
        bluetoothService.cancelDiscovery();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.e(TAG, "updateDevices: " + (bluetoothPermission == PERMISSION_GRANTED));
            if (bluetoothPermission == PERMISSION_GRANTED) {
                bluetoothService.startDiscovery();
            } else getViewState().requestBtPermission();
        } else bluetoothService.startDiscovery();
    }

    void onAvailableDevicesViewShown() {

    }

    void onPairedDevicesViewShown() {

    }

    void onPositiveBtRequestResult() {
        Log.d(TAG, "onPositiveBtRequestResult: ");
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

    void onCreateOptionsMenu() {
        if (bluetoothService.isEnabled()) getViewState().showUpdateDevicesView();
        else getViewState().hideUpdateDevicesView();
    }

    void onUpdateDevicesClick() {
        updateDevices();
    }

    void onPrepareOptionsMenu() {
        Log.d(TAG, "onPrepareOptionsMenu: ");
        if (bluetoothService.isEnabled()) getViewState().showUpdateDevicesView();
        else getViewState().hideUpdateDevicesView();
    }
}