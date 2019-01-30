package tech.sadovnikov.configurator.presentation.configuration.config_tabs.config_main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.di.component.DaggerPresenterComponent;
import tech.sadovnikov.configurator.di.component.PresenterComponent;
import tech.sadovnikov.configurator.model.data.DataManager;

@InjectViewState
public class ConfigMainPresenter extends MvpPresenter<ConfigMainView> {
    private static final String TAG = ConfigMainPresenter.class.getName();

    @Inject
    DataManager dataManager;

    ConfigMainPresenter() {
        super();
//        Log.d(TAG, "ConfigMainPresenter: ");
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
//        Log.d(TAG, "onFirstViewAttach: ");
//        PublishSubject<Configuration> configurationObservable = dataManager.getConfigurationObservable();
//        Log.e(TAG, "onFirstViewAttach: " + configurationObservable);
//        Disposable subscribe = configurationObservable
//                .compose(RxTransformers.applySchedulers())
//                .subscribe(configuration -> {
//                            Log.d(TAG, "onNext: onConfigurationChanged");
//                            getViewState().showConfiguration(configuration);
//                        },
//                        Throwable::printStackTrace,
//                        () -> {
//                        },
//                        disposable -> getViewState().showConfiguration(dataManager.getConfiguration()));
//        compositeDisposable.add(subscribe);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Log.d(TAG, "onDestroy: ");
    }
}
