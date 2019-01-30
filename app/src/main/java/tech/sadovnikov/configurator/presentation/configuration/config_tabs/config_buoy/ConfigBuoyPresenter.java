package tech.sadovnikov.configurator.presentation.configuration.config_tabs.config_buoy;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.di.component.DaggerPresenterComponent;
import tech.sadovnikov.configurator.di.component.PresenterComponent;
import tech.sadovnikov.configurator.model.BluetoothService;

@InjectViewState
public class ConfigBuoyPresenter extends MvpPresenter<ConfigBuoyView> {
    private static final String TAG = ConfigBuoyPresenter.class.getSimpleName();

    @Inject
    BluetoothService bluetoothService;

    ConfigBuoyPresenter() {
        super();
        Log.d(TAG, "ConfigBuoyPresenter: ");
        initDaggerAndInject();
    }

    private void initDaggerAndInject() {
        PresenterComponent presenterComponent = DaggerPresenterComponent.builder()
                .applicationComponent(App.getApplicationComponent())
                .build();
        presenterComponent.injectConfigBuoyPresenter(this);
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

    void onRestartClick() {
        bluetoothService.sendData("@RESTART");
    }

    void onDefaultSettingsClick() {
        bluetoothService.sendData("@RESET SETTINGS");
    }
}
