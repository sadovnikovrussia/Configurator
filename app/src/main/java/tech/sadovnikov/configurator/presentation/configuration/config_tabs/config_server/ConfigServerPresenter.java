package tech.sadovnikov.configurator.presentation.configuration.config_tabs.config_server;

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
public class ConfigServerPresenter extends MvpPresenter<ConfigServerView> {
    private static final String TAG = ConfigServerPresenter.class.getName();

    @Inject
    BluetoothService bluetoothService;

    ConfigServerPresenter() {
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

    void onCloseConnectAction() {
        bluetoothService.sendData(SpecialCommands.CLOSE_CONNECT);
    }

    void onClearArchiveAction() {
        bluetoothService.sendData(SpecialCommands.CLEAR_ARCHIVE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

}
