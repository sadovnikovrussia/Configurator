package tech.sadovnikov.configurator.presentation.configuration.config_tabs.config_navigation;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.di.component.DaggerPresenterComponent;
import tech.sadovnikov.configurator.di.component.PresenterComponent;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.model.data.DataManager;
import tech.sadovnikov.configurator.utils.SpecialCommands;

import static tech.sadovnikov.configurator.utils.ParametersEntities.BASE_POS;
import static tech.sadovnikov.configurator.utils.ParametersEntities.CURRENT_POS;

@InjectViewState
public class ConfigNavigationPresenter extends MvpPresenter<ConfigNavigationView> {
    private static final String TAG = ConfigNavigationPresenter.class.getName();

    @Inject
    DataManager dataManager;
    @Inject
    BluetoothService bluetoothService;

    ConfigNavigationPresenter() {
        super();
        initDaggerAndInject();
    }

    private void initDaggerAndInject() {
        PresenterComponent presenterComponent = DaggerPresenterComponent.builder()
                .applicationComponent(App.getApplicationComponent())
                .build();
        presenterComponent.injectConfigMainPresenter(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.d(TAG, "onFirstViewAttach: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    void onRequestBasePos() {
        bluetoothService.sendData(BASE_POS.createReadingCommand());
    }

    void onShowBasePosInMaps(String latitude, String longitude) {
        getViewState().showPositionInMaps(latitude, longitude);
    }

    void onRequestCurrentPos() {
        bluetoothService.sendData(CURRENT_POS.createReadingCommand());
    }

    void onShowCurrentPosInMaps(String latitude, String longitude) {
        getViewState().showPositionInMaps(latitude, longitude);
    }

    void onRcvColdStartSend() {
        bluetoothService.sendData(SpecialCommands.RCV_COLD_START);
    }
}
