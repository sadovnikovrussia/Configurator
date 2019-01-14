package tech.sadovnikov.configurator.ui.main;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

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
        getViewState().showBluetoothView();
    }

    void onBluetoothClick(boolean isFragmentOpened) {
        if (!isFragmentOpened) getViewState().showBluetoothView();
    }

    void onConfigurationClick(boolean isFragmentOpened) {
    }

    void onConsoleClick(boolean isFragmentOpened) {
        if (!isFragmentOpened) getViewState().showConsoleView();
    }

    void onSetConfiguration() {

    }

    void onSaveConfiguration() {

    }

    void onOpenConfiguration() {

    }

    void onReadConfiguration() {
        cfgLoader.readFullConfiguration();
        getViewState().showLoadingProcess();
    }
}
