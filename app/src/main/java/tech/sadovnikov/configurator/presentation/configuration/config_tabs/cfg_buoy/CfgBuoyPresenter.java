package tech.sadovnikov.configurator.presentation.configuration.config_tabs.cfg_buoy;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.di.component.DaggerPresenterComponent;
import tech.sadovnikov.configurator.di.component.PresenterComponent;
import tech.sadovnikov.configurator.model.BluetoothService;

@InjectViewState
public class CfgBuoyPresenter extends MvpPresenter<CfgBuoyView> {
    private static final String TAG = CfgBuoyPresenter.class.getSimpleName();

    @Inject
    BluetoothService bluetoothService;

    CfgBuoyPresenter() {
        super();
        Log.d(TAG, "CfgBuoyPresenter: ");
        PresenterComponent presenterComponent = DaggerPresenterComponent.builder()
                .applicationComponent(App.getApplicationComponent())
                .build();
        presenterComponent.injectCfgBuoyPresenter(this);
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
        Log.d(TAG, "onRestartClick: ");
    }
}
