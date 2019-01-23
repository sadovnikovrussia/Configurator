package tech.sadovnikov.configurator.presentation.configuration.config_tabs.base;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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

    private CompositeDisposable compositeDisposable;

    BaseCfgPresenter() {
        super();
        Log.w(TAG, "BaseCfgPresenter: ");
        initDaggerComponent();
        presenterComponent.injectBaseCfgPresenter(this);
        compositeDisposable = new CompositeDisposable();
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
        Disposable subscribe = dataManager.getConfigurationObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(configuration -> getViewState().showConfiguration(configuration),
                        Throwable::printStackTrace,
                        () -> {
                        },
                        disposable -> getViewState().showConfiguration(dataManager.getConfiguration()));
        compositeDisposable.add(subscribe);
    }

    public void onParameterChanged(ParametersEntities parameterEntity, String value) {
        Log.d(TAG, "onParameterChanged: ");
        if (value.length() == 0) {
            dataManager.removeConfigParameter(parameterEntity);
        } else {
            switch (parameterEntity) {
                case BLINKER_MODE:
                    if (value.equals("0")) dataManager.removeConfigParameter(parameterEntity);
                    else
                        dataManager.setConfigParameter(parameterEntity, String.valueOf(Integer.valueOf(value) - 1));
                    break;
                default:
                    dataManager.setConfigParameter(parameterEntity, value);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "onDestroy: ");
        compositeDisposable.clear();
    }

    void onCreateViewBaseCfgView() {
        Log.v(TAG, "onCreateViewBaseCfgView: ");
        //getViewState().showConfiguration(dataManager.getConfiguration());
    }


}
