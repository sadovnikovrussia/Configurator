package tech.sadovnikov.configurator.presentation.main;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.di.component.DaggerPresenterComponent;
import tech.sadovnikov.configurator.di.component.PresenterComponent;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.model.CfgLoader;
import tech.sadovnikov.configurator.model.data.DataManager;

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

    MainPresenter() {
        super();
        Log.w(TAG, "onConstructor: ");
        initDaggerComponent();
        presenterComponent.injectMainPresenter(this);
        cfgLoader.setBluetoothService(bluetoothService);
        cfgLoader.setDataManager(dataManager);
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
        getViewState().navigateToBluetoothView();
    }

    void onBluetoothClick(boolean isFragmentOpened) {
        if (!isFragmentOpened) getViewState().navigateToBluetoothView();
    }

    void onConfigurationClick(boolean isFragmentOpened) {
    }

    void onConsoleClick(boolean isFragmentOpened) {
        if (!isFragmentOpened) getViewState().navigateToConsoleView();
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


    void onCreateConsoleView() {
        getViewState().setConsoleNavigationPosition();
    }

    void onCreateBluetoothView() {
        getViewState().setBluetoothNavigationPosition();
    }
}
