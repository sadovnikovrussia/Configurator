package tech.sadovnikov.configurator.ui.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.model.BluetoothService;

public class BluetoothPresenter extends MvpBasePresenter<BluetoothMvp.View> implements BluetoothMvp.Presenter {
    private static final String TAG = BluetoothPresenter.class.getSimpleName();
    private BluetoothService bluetoothService;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Listener listener;

    BluetoothPresenter(BluetoothService bluetoothService) {
        Log.v(TAG, "onConstructor");
        this.bluetoothService = bluetoothService;
    }

    @Override
    public void attachView(@NonNull BluetoothMvp.View view) {
        super.attachView(view);
        Log.v(TAG, "attachView: ");
    }

    @Override
    public void onStart() {
        ifViewAttached(view -> view.displayBluetoothState(bluetoothService.isEnabled()));
        if (bluetoothService.isEnabled()) ifViewAttached(BluetoothMvp.View::showDevicesContainer);
        else ifViewAttached(BluetoothMvp.View::hideDevicesContainer);
        PublishSubject<Integer> bluetoothStateObservable = bluetoothService.getBluetoothStateObservable();
        Disposable disposable = bluetoothStateObservable
                .subscribe(integer -> {
                            Log.d(TAG, "onNext: " + integer + bluetoothService);
                            switch (integer) {
                                case BluetoothAdapter.STATE_TURNING_ON:
                                    ifViewAttached(BluetoothMvp.View::showTurningOn);
                                    break;
                                case BluetoothAdapter.STATE_ON:
                                    ifViewAttached(view -> view.displayBluetoothState(true));
                                    ifViewAttached(BluetoothMvp.View::showDevicesContainer);
                                    ifViewAttached(BluetoothMvp.View::hideTurningOn);
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
    public void onAvailableDevicesViewShown() {
        // Todo Обработать permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ifViewAttached(view1 -> {
                if (view1.checkBtPermission()) {
                    listener.onAvailableDevicesViewShown();
                    bluetoothService.startDiscovery();
                }
                else view1.requestBtPermission();
            });

        }

    }

    @Override
    public void onPairedDevicesViewShown() {
        bluetoothService.cancelDiscovery();
    }

    @Override
    public void onPositiveBtRequestResult() {
        bluetoothService.startDiscovery();
    }

    public void setBluetoothPresenterListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void detachView() {
        super.detachView();
        Log.v(TAG, "detachView: ");
        compositeDisposable.dispose();
        compositeDisposable.clear();
    }

    @Override
    public void destroy() {
        super.destroy();
        Log.v(TAG, "destroy: ");
    }

    @Override
    public void onBtSwitchClick(boolean isChecked) {
        if (isChecked) {
            bluetoothService.enable();
        } else {
            bluetoothService.disable();
        }
    }

    interface Listener {
        void onAvailableDevicesViewShown();
    }
}
