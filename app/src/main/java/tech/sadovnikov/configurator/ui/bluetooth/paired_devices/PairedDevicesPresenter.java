package tech.sadovnikov.configurator.ui.bluetooth.paired_devices;

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
import tech.sadovnikov.configurator.di.component.PresenterComponent;
import tech.sadovnikov.configurator.di.component.DaggerPresenterComponent;
import tech.sadovnikov.configurator.model.BluetoothService;

@InjectViewState
public class PairedDevicesPresenter extends MvpPresenter<PairedDevicesView> {
    private static final String TAG = PairedDevicesPresenter.class.getSimpleName();

    private PresenterComponent presenterComponent;
    @Inject
    BluetoothService bluetoothService;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    PairedDevicesPresenter() {
        super();
        Log.d(TAG, "onConstructor: ");
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
        Log.d(TAG, "onFirstViewAttach: ");
        getViewState().setPairedDevices(bluetoothService.getPairedDevices());
        Disposable subscribe = bluetoothService.getPairedDevicesObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bluetoothDevices -> {
                    Log.d(TAG, "onNext: " + bluetoothDevices);
                    getViewState().setPairedDevices(bluetoothDevices);
                });
        compositeDisposable.add(subscribe);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    void onDeviceClicked(BluetoothDevice device) {
        bluetoothService.connectToDevice(device);
    }
}
