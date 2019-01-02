package tech.sadovnikov.configurator.ui.bluetooth.available_devices;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.di.component.BluetoothComponent;
import tech.sadovnikov.configurator.di.component.DaggerBluetoothComponent;
import tech.sadovnikov.configurator.model.BluetoothService;

@InjectViewState
public class AvailableDevicesPresenter extends MvpPresenter<AvailableDevicesView> {
    private static final String TAG = AvailableDevicesPresenter.class.getSimpleName();

    private BluetoothComponent bluetoothComponent;
    @Inject
    BluetoothService bluetoothService;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    AvailableDevicesPresenter() {
        super();
        Log.i(TAG, "onConstructor: ");
        initDaggerComponent();
        bluetoothComponent.injectAvailableDevicesPresenter(this);
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
        Log.d(TAG, "onFirstViewAttach: ");
        getViewState().setAvailableDevices(bluetoothService.getAvailableDevices());
        Disposable subscribe = bluetoothService.getAvailableDevicesObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        bluetoothDevices -> {
                            Log.d(TAG, "onNext: " + bluetoothDevices);
                            getViewState().setAvailableDevices(bluetoothDevices);
                        },
                        throwable -> Log.w(TAG, "onError: ", throwable),
                        () -> Log.d(TAG, "onComplete: "),
                        disposable -> Log.d(TAG, "onSubscribe: "));
        compositeDisposable.add(subscribe);
    }
    
    void onDeviceClicked(BluetoothDevice device) {
        bluetoothService.connectToDevice(device);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
        compositeDisposable.dispose();
        compositeDisposable.clear();
    }

}
