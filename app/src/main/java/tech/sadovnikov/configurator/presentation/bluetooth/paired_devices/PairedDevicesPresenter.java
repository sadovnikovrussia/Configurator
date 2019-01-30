package tech.sadovnikov.configurator.presentation.bluetooth.paired_devices;

import android.bluetooth.BluetoothDevice;
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
import tech.sadovnikov.configurator.utils.rx.RxTransformers;

@InjectViewState
public class PairedDevicesPresenter extends MvpPresenter<PairedDevicesView> {
    private static final String TAG = PairedDevicesPresenter.class.getSimpleName();

    private PresenterComponent presenterComponent;
    @Inject
    BluetoothService bluetoothService;
    private CompositeDisposable subscriptions = new CompositeDisposable();


    PairedDevicesPresenter() {
        super();
        //Log.w(TAG, "onConstructor: ");
        initDaggerComponent();
        presenterComponent.injectPairedDevicesPresenter(this);
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
        //Log.w(TAG, "onFirstViewAttach: ");
        Disposable subscriptionState = bluetoothService.getStateObservable()
                .compose(RxTransformers.applySchedulers())
                .subscribe(state -> getViewState().setPairedDevices(bluetoothService.getPairedDevices(), bluetoothService.getConnectedDevice(), bluetoothService.getConnectionState()),
                        Throwable::printStackTrace,
                        () -> {},
                        disposable -> getViewState().setPairedDevices(bluetoothService.getPairedDevices(), bluetoothService.getConnectedDevice(), bluetoothService.getConnectionState()));
        Disposable subscriptionPairedDevices = bluetoothService.getPairedDevicesObservable()
                .compose(RxTransformers.applySchedulers())
                .subscribe(bluetoothDevices -> getViewState().setPairedDevices(bluetoothDevices, bluetoothService.getConnectedDevice(), bluetoothService.getConnectionState()));
        Disposable subscriptionConnectionState = bluetoothService.getConnectionStateObservable()
                .compose(RxTransformers.applySchedulers())
                .subscribe(integer -> getViewState().setPairedDevices(bluetoothService.getPairedDevices(), bluetoothService.getConnectedDevice(), integer));
        subscriptions.add(subscriptionState);
        subscriptions.add(subscriptionPairedDevices);
        subscriptions.add(subscriptionConnectionState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Log.w(TAG, "onDestroy: ");
        subscriptions.clear();
    }

    void onDeviceClicked(BluetoothDevice device) {
        bluetoothService.connectToDevice(device);
    }

    void onDeviceLongClick(BluetoothDevice device) {
        if (device.equals(bluetoothService.getConnectedDevice()))
            getViewState().showCloseConnectionDialog(device);
    }

    void onCloseBtConnection() {
        bluetoothService.closeAllConnections();
        getViewState().hideCloseConnectionDialog();
    }

    void onCancelCloseConnectionDialog() {
        getViewState().hideCloseConnectionDialog();
    }
}
