package tech.sadovnikov.configurator.presentation.configuration.config_tabs.cfg_main;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.di.component.DaggerPresenterComponent;
import tech.sadovnikov.configurator.di.component.PresenterComponent;

@InjectViewState
public class CfgMainPresenter extends MvpPresenter<CfgMainView> {
    private static final String TAG = CfgMainPresenter.class.getName();

    public CfgMainPresenter() {
        super();
        PresenterComponent presenterComponent = DaggerPresenterComponent.builder()
                .applicationComponent(App.getApplicationComponent())
                .build();
        Log.d(TAG, "CfgMainPresenter: ");
        presenterComponent.injectCfgMainPresenter(this);
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
}
