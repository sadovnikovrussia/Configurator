package tech.sadovnikov.configurator.presentation.console;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.di.component.DaggerPresenterComponent;
import tech.sadovnikov.configurator.di.component.PresenterComponent;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.model.data.DataManager;
import tech.sadovnikov.configurator.model.entities.LogMessage;

@InjectViewState
public class ConsolePresenter extends MvpPresenter<ConsoleView> {
    private static final String TAG = ConsolePresenter.class.getSimpleName();

    private PresenterComponent presenterComponent;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    BluetoothService bluetoothService;
    @Inject
    DataManager dataManager;

    ConsolePresenter() {
        Log.i(TAG, "ConsolePresenter: ");
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
        Log.i(TAG, "onFirstViewAttach: ");
        getViewState().setAutoScrollState(dataManager.getAutoScrollState());
        Disposable subscribe = dataManager.getObservableMainLog()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(message -> getViewState().addMessageToLogScreen(message, dataManager.getAutoScrollState()),
                        Throwable::printStackTrace,
                        () -> {},
                        disposable -> {
                            Log.i(TAG, "onSubscribe: ");
                            getViewState().showMainLogs(dataManager.getMainLogList(), dataManager.getAutoScrollState());
                        });
        compositeDisposable.add(subscribe);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
        compositeDisposable.clear();
    }

    void onSendCommand(String command) {
        bluetoothService.sendData(command);
    }

    void onSaveLogMessages() {

    }

    void onChangeAutoScrollClick(boolean isChecked) {
        dataManager.setAutoScrollMode(isChecked);
        getViewState().setAutoScrollState(dataManager.getAutoScrollState());
    }

}
