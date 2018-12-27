package tech.sadovnikov.configurator.ui.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.model.BluetoothService;

public class BluetoothPresenter extends MvpBasePresenter<BluetoothMvp.View> implements BluetoothMvp.Presenter {
    private static final String TAG = BluetoothPresenter.class.getSimpleName();

    private BluetoothService bluetoothService = App.getBluetoothService();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void attachView(@NonNull BluetoothMvp.View view) {
        super.attachView(view);

    }

    @Override
    public void onStart() {
        Log.e(TAG, "onStart:");
        ifViewAttached(view -> view.displayBluetoothState(bluetoothService.isEnabled()));
        if (bluetoothService.isEnabled()) ifViewAttached(BluetoothMvp.View::showDevicesContainer);
        else ifViewAttached(BluetoothMvp.View::hideDevicesContainer);
        PublishSubject<Integer> bluetoothStateObservable = bluetoothService.getBluetoothStateObservable();
        Log.d(TAG, "onStart: " + bluetoothStateObservable);
        Disposable disposable = bluetoothStateObservable
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                            Log.d(TAG, "ПРИЛЕТЕЛО: " + integer);
                            switch (integer) {
                                case BluetoothAdapter.STATE_ON:
                                    ifViewAttached(view -> view.displayBluetoothState(true));
                                    ifViewAttached(BluetoothMvp.View::showDevicesContainer);
                                    break;
                                case BluetoothAdapter.STATE_OFF:
                                    ifViewAttached(view -> view.displayBluetoothState(false));
                                    ifViewAttached(BluetoothMvp.View::hideDevicesContainer);
                                    break;
                            }
                        },
                        throwable -> Log.w(TAG, "onStart: ", throwable),
                        () -> Log.i(TAG, "onStart: Усе"));
        compositeDisposable.add(disposable);
    }

    @Override
    public void detachView() {
        super.detachView();
        compositeDisposable.dispose();
        compositeDisposable.clear();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void onBtSwitchClick(boolean isChecked) {
        if (isChecked) {
            bluetoothService.enable();
        } else {
            bluetoothService.disable();
        }
    }

}
