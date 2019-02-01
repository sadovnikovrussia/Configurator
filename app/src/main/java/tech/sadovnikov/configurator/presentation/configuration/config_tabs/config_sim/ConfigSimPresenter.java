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

@InjectViewState
public class ConfigSimPresenter extends MvpPresenter<ConfigSimView> {
    private static final String TAG = ConfigSimPresenter.class.getName();

    @Inject
    BluetoothService bluetoothService;
    @Inject
    DataManager dataManager;

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



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    void onSetApnDefaultAction() {
        dataManager.setConfigParameter(ParametersEntities.APN, "\"Cellular operator defaults\"");
    }

    void onSetLoginDefaultAction() {
        dataManager.setConfigParameter(ParametersEntities.LOGIN, "\"Cellular operator defaults\"");
    }

    void onSetPasswordDefaultAction() {
        dataManager.setConfigParameter(ParametersEntities.PASSWORD, "\"Cellular operator defaults\"");
    }

    void onClearApnAction() {
        dataManager.setConfigParameter(ParametersEntities.APN, "''");
    }

    void onClearLoginAction() {
        dataManager.setConfigParameter(ParametersEntities.LOGIN, "''");
    }

    void onClearPasswordAction() {
        dataManager.setConfigParameter(ParametersEntities.PASSWORD, "''");
    }
}
