package tech.sadovnikov.configurator.presentation.bluetooth.available_devices;

import android.bluetooth.BluetoothDevice;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.di.component.DaggerPresenterComponent;
import tech.sadovnikov.configurator.di.component.PresenterComponent;
import tech.sadovnikov.configurator.model.BluetoothService;

@InjectViewState
public class AvailableDevicesPresenter extends MvpPresenter<AvailableDevicesView> {
    private static final String TAG = AvailableDevicesPresenter.class.getSimpleName();

    private PresenterComponent presenterComponent;
    @Inject
    BluetoothService bluetoothService;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    AvailableDevicesPresenter() {
        super();
        //Log.i(TAG, "onConstructor: ");
        initDaggerComponent();
        presenterComponent.injectAvailableDevicesPresenter(this);
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
        //Log.d(TAG, "onFirstViewAttach: ");
        getViewState().setAvailableDevices(bluetoothService.getAvailableDevices());
        Disposable subscribe = bluetoothService.getAvailableDevicesObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bluetoothDevices -> getViewState().setAvailableDevices(bluetoothDevices));
        compositeDisposable.add(subscribe);
    }

    void onDeviceClicked(BluetoothDevice device) {
        bluetoothService.connectToDevice(device);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Log.i(TAG, "onDestroy: ");
        compositeDisposable.clear();
    }

}
