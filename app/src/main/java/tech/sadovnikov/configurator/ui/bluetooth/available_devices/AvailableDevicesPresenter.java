package tech.sadovnikov.configurator.ui.bluetooth.available_devices;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import tech.sadovnikov.configurator.model.BluetoothService;

public class AvailableDevicesPresenter extends MvpBasePresenter<AvailableDevicesMvp.View>
        implements AvailableDevicesMvp.Presenter {
    private static final String TAG = AvailableDevicesPresenter.class.getSimpleName();

    private BluetoothService bluetoothService;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    AvailableDevicesPresenter(BluetoothService bluetoothService) {
        super();
        Log.i(TAG, "onConstructor: ");
        this.bluetoothService = bluetoothService;
    }

    @Override
    public void onStartView() {
        ifViewAttached(view -> view.showAvailableDevices(bluetoothService.getAvailableDevices()));
        Disposable subscribe = bluetoothService.getAvailableDevicesObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        bluetoothDevices -> {
                            Log.d(TAG, "onNext: " + bluetoothDevices);
                            ifViewAttached(view -> view.showAvailableDevices(bluetoothDevices));
                        },
                        throwable -> Log.w(TAG, "onError: ", throwable),
                        () -> Log.d(TAG, "onComplete: "),
                        disposable -> Log.d(TAG, "onSubscribe: "));
        compositeDisposable.add(subscribe);

    }

    @Override
    public void onDeviceClicked(BluetoothDevice device) {
        bluetoothService.connectToDevice(device);
    }

    @Override
    public void attachView(@NonNull AvailableDevicesMvp.View view) {
        super.attachView(view);
        Log.i(TAG, "attachView: ");
    }

    @Override
    public void detachView() {
        super.detachView();
        Log.i(TAG, "detachView: ");
        compositeDisposable.dispose();
        compositeDisposable.clear();
    }

    @Override
    public void destroy() {
        super.destroy();
        Log.i(TAG, "destroy: ");
    }

}
