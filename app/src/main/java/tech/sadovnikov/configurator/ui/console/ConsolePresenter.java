package tech.sadovnikov.configurator.ui.console;

import android.util.Log;

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
public class ConsolePresenter extends MvpPresenter<ConsoleView> {
    private static final String TAG = ConsolePresenter.class.getSimpleName();

    private PresenterComponent presenterComponent;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    BluetoothService bluetoothService;

    ConsolePresenter() {
        Log.v(TAG, "ConsolePresenter: ");
        initDaggerComponent();
        presenterComponent.injectConsolePresenter(this);
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
        Log.v(TAG, "onFirstViewAttach: ");
        Disposable subscribe = bluetoothService.getInputMessagesStream()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(message -> {
                    Log.d(TAG, "onNext: " + message);
                    getViewState().addMessageToLog(message);
                });
        compositeDisposable.add(subscribe);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy: ");
        compositeDisposable.clear();
    }

    void onSendCommandClick(String command) {

    }

    void onLogsLongClick() {

    }

    void onChangeAutoScrollClick() {

    }
}
