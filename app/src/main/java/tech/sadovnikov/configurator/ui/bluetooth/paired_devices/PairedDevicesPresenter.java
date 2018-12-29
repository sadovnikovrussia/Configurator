package tech.sadovnikov.configurator.ui.bluetooth.paired_devices;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import tech.sadovnikov.configurator.model.BluetoothService;

public class PairedDevicesPresenter extends MvpBasePresenter<PairedDevicesMvp.View> implements PairedDevicesMvp.Presenter {
    private static final String TAG = PairedDevicesPresenter.class.getSimpleName();

    private BluetoothService bluetoothService;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    PairedDevicesPresenter(BluetoothService bluetoothService) {
        Log.d(TAG, "PairedDevicesPresenter: ");
        this.bluetoothService = bluetoothService;
    }

    @Override
    public void onStartView() {
        ifViewAttached(view -> view.showPairedDevices(bluetoothService.getPairedDevices()));
        Disposable subscribe = bluetoothService.getPairedDevicesObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bluetoothDevices -> {
                    Log.d(TAG, "onStartView: " + bluetoothDevices);
                    ifViewAttached(view -> view.showPairedDevices(bluetoothDevices));
                });
        compositeDisposable.add(subscribe);
    }

    @Override
    public void attachView(@NonNull PairedDevicesMvp.View view) {
        super.attachView(view);
        Log.d(TAG, "attachView: ");
    }

    @Override
    public void detachView() {
        super.detachView();
        compositeDisposable.dispose();
        compositeDisposable.clear();
        Log.d(TAG, "detachView: ");
    }

    @Override
    public void destroy() {
        super.destroy();
        Log.d(TAG, "destroy: ");
    }

}
