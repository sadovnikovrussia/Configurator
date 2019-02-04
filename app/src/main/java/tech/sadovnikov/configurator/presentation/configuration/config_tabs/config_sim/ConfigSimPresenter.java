package tech.sadovnikov.configurator.presentation.configuration.config_tabs.config_sim;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.di.component.DaggerPresenterComponent;
import tech.sadovnikov.configurator.di.component.PresenterComponent;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.model.data.DataManager;
import tech.sadovnikov.configurator.utils.ParametersEntities;
import tech.sadovnikov.configurator.utils.SpecialCommands;

import static tech.sadovnikov.configurator.utils.AppConstants.SIM_DEFAULTS;
import static tech.sadovnikov.configurator.utils.ParametersEntities.*;

@InjectViewState
public class ConfigSimPresenter extends MvpPresenter<ConfigSimView> {
    private static final String TAG = ConfigSimPresenter.class.getName();

    @Inject
    BluetoothService bluetoothService;

    ConfigSimPresenter() {
        super();
        Log.d(TAG, "ConfigSimPresenter: ");
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

    void onSetApnDefaultAction() {
        bluetoothService.sendData(APN.getName() + "=\"\"");
    }

    void onSetLoginDefaultAction() {
        bluetoothService.sendData(LOGIN.getName() + "=\"\"");
    }

    void onSetPasswordDefaultAction() {
        bluetoothService.sendData(PASSWORD.getName() + "=\"\"");
    }

    void onClearApnAction() {
        bluetoothService.sendData(APN.getName() + "=''");
    }

    void onClearLoginAction() {
        bluetoothService.sendData(LOGIN.getName() + "=''");
    }

    void onClearPasswordAction() {
        bluetoothService.sendData(PASSWORD.getName() + "=''");
    }

    void onEnterPinAction(String pin) {
        bluetoothService.sendData("PIN=" + pin);
    }

    void onClearPinAction(String pin) {
        bluetoothService.sendData("CLEAR PIN=" + pin);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

}
