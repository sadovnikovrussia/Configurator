package tech.sadovnikov.configurator.presentation.configuration.config_tabs.base;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.di.component.DaggerPresenterComponent;
import tech.sadovnikov.configurator.di.component.PresenterComponent;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.model.data.DataManager;
import tech.sadovnikov.configurator.model.data.configuration.Configuration;
import tech.sadovnikov.configurator.utils.ParametersEntities;
import tech.sadovnikov.configurator.utils.rx.RxTransformers;


@InjectViewState
public class BaseCfgPresenter extends MvpPresenter<BaseCfgView> {
    private static final String TAG = BaseCfgPresenter.class.getSimpleName();

    private PresenterComponent presenterComponent;
    @Inject
    DataManager dataManager;
    @Inject
    BluetoothService bluetoothService;

    private CompositeDisposable compositeDisposable;

    BaseCfgPresenter() {
        super();
        Log.w(TAG, "BaseCfgPresenter: ");
        initDaggerAndInject();
        compositeDisposable = new CompositeDisposable();
    }

    private void initDaggerAndInject() {
        presenterComponent = DaggerPresenterComponent
                .builder()
                .applicationComponent(App.getApplicationComponent())
                .build();
        presenterComponent.injectBaseCfgPresenter(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        PublishSubject<Configuration> configurationObservable = dataManager.getConfigurationObservable();
        Log.w(TAG, "onFirstViewAttach: " + configurationObservable);
        Disposable subscribe = configurationObservable
                .compose(RxTransformers.applySchedulers())
                .subscribe(configuration -> {
                            Log.d(TAG, "onNext: onConfigurationChanged" + getAttachedViews());
                            getViewState().showConfiguration(configuration);
                        },
                        Throwable::printStackTrace,
                        () -> {
                        },
                        disposable -> {
                            Log.d(TAG, "onSubscribe: ToConfiguration: ");
                            getViewState().showConfiguration(dataManager.getConfiguration());
                        });
        compositeDisposable.add(subscribe);
    }

    // todo выключать подписку на dataManager при изменении параметра из UI
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
                case BLINKER_BRIGHTNESS:
                    if (value.equals("0")) dataManager.removeConfigParameter(parameterEntity);
                    else
                        dataManager.setConfigParameter(parameterEntity, String.valueOf(Integer.valueOf(value) - 1));
                    break;
                case SATELLITE_SYSTEM:
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

}
