package tech.sadovnikov.configurator.presentation.configuration.config_tabs;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.di.component.DaggerPresenterComponent;
import tech.sadovnikov.configurator.di.component.PresenterComponent;
import tech.sadovnikov.configurator.model.data.DataManager;
import tech.sadovnikov.configurator.utils.ParametersEntities;


@InjectViewState
public class BaseCfgPresenter extends MvpPresenter<BaseCfgView> {
    private static final String TAG = BaseCfgPresenter.class.getSimpleName();

    private PresenterComponent presenterComponent;
    @Inject
    DataManager dataManager;

    BaseCfgPresenter() {
        super();
        Log.d(TAG, "BaseCfgPresenter: ");
        initDaggerComponent();
        presenterComponent.injectBaseCfgPresenter(this);
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
        Log.d(TAG, "onFirstViewAttach: ");
    }

    public void onParameterChanged(ParametersEntities parameterEntity, String value) {
        Log.d(TAG, "onParameterChanged: ");
    }

    public void onStartBaseCfgView() {
        Log.d(TAG, "onStartBaseCfgView: ");
        getViewState().showConfiguration(dataManager.getConfiguration());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }


}
