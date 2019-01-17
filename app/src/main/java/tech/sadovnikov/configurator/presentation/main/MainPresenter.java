package tech.sadovnikov.configurator.presentation.main;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Stack;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.di.component.DaggerPresenterComponent;
import tech.sadovnikov.configurator.di.component.PresenterComponent;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.model.CfgLoader;
import tech.sadovnikov.configurator.model.data.DataManager;
import tech.sadovnikov.configurator.presentation.bluetooth.BluetoothView;
import tech.sadovnikov.configurator.presentation.console.ConsoleView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    private static final String TAG = MainPresenter.class.getSimpleName();

    private PresenterComponent presenterComponent;
    @Inject
    CfgLoader cfgLoader;
    @Inject
    BluetoothService bluetoothService;
    @Inject
    DataManager dataManager;

    private String bluetoothView;
    private String consoleView;
    private Class currentView;
    private Stack<Class> stack;

    MainPresenter() {
        super();
        Log.w(TAG, "onConstructor: ");
        initDaggerComponent();
        presenterComponent.injectMainPresenter(this);
        cfgLoader.setBluetoothService(bluetoothService);
        cfgLoader.setDataManager(dataManager);
        stack = new Stack<>();
        bluetoothView = BluetoothView.class.getSimpleName();
        consoleView = ConsoleView.class.getSimpleName();
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
        Log.w(TAG, "onFirstViewAttach: ");
        currentView = BluetoothView.class;
        getViewState().navigateToBluetoothView();
    }

    void onBluetoothClick() {
        Log.d(TAG, "onBluetoothClick: ");
        if (!currentView.equals(BluetoothView.class)) getViewState().navigateToBluetoothView();
        currentView = BluetoothView.class;
        //getViewState().setBluetoothNavigationPosition();
    }

    void onConfigurationClick() {
    }

    void onConsoleClick() {
        Log.d(TAG, "onConsoleClick: ");
        if (!currentView.equals(ConsoleView.class)) getViewState().navigateToConsoleView();
        currentView = ConsoleView.class;
        //getViewState().setConsoleNavigationPosition();
    }


    void onSetConfiguration() {
        getViewState().showLoadingProcess();
        Disposable progressSubscription = cfgLoader.setCurrentConfiguration()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aFloat -> getViewState().updateLoadingProcess(aFloat),
                        Throwable::printStackTrace,
                        () -> getViewState().hideLoadingProgress());
    }

    void onSaveConfiguration() {

    }

    void onOpenConfiguration() {

    }

    void onReadConfiguration() {
        getViewState().showLoadingProcess();
        Disposable progressSubscription = cfgLoader.readFullConfiguration()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aFloat -> getViewState().updateLoadingProcess(aFloat),
                        Throwable::printStackTrace,
                        () -> getViewState().hideLoadingProgress());

    }

    void onCreateBluetoothView() {
        currentView = BluetoothView.class;
        getViewState().setBluetoothNavigationPosition();
        getViewState().setTitle(R.string.title_bluetooth);
    }

    void onCreateConsoleView() {
        currentView = ConsoleView.class;
        getViewState().setConsoleNavigationPosition();
        getViewState().setTitle(R.string.title_console);
    }


}
