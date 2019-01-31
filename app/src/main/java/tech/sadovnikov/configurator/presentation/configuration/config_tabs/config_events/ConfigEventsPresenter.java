package tech.sadovnikov.configurator.presentation.configuration.config_tabs.config_events;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.di.component.DaggerPresenterComponent;
import tech.sadovnikov.configurator.di.component.PresenterComponent;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.model.data.DataManager;

@InjectViewState
public class ConfigEventsPresenter extends MvpPresenter<ConfigEventsView> {
    private static final String TAG = ConfigEventsPresenter.class.getName();

    @Inject
    BluetoothService bluetoothService;

    ConfigEventsPresenter() {
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

    void onCreateAlarmEventsMessage() {
        getViewState().showCreateAlarmEventsMessageView();
    }

    void onAlarmEventsDialogPositiveClick(String events) {
        getViewState().hideAlarmEventsMessageView();
        bluetoothService.sendData("ALARM EVENTS=" + events);
    }

    void onAlarmEventsDialogNegativeClick() {
        getViewState().hideAlarmEventsMessageView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

}
