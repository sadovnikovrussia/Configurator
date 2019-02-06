package tech.sadovnikov.configurator.presentation.console.log_messages;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.di.component.DaggerPresenterComponent;
import tech.sadovnikov.configurator.di.component.PresenterComponent;
import tech.sadovnikov.configurator.model.data.DataManager;
import tech.sadovnikov.configurator.utils.rx.RxTransformers;

@InjectViewState
public class LogMessagesPresenter extends MvpPresenter<LogMessagesView> {
    private static final String TAG = LogMessagesPresenter.class.getSimpleName();

    @Inject
    DataManager dataManager;

    private CompositeDisposable subscriptions;
    private String logTab;

    LogMessagesPresenter() {
        subscriptions = new CompositeDisposable();
        Log.i(TAG, "LogMessagesPresenter: ");
        initDaggerComponent();
    }

    private void initDaggerComponent() {
        PresenterComponent presenterComponent = DaggerPresenterComponent
                .builder()
                .applicationComponent(App.getApplicationComponent())
                .build();
        presenterComponent.injectLogMessagesPresenter(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.i(TAG, "onFirstViewAttach: ");
        Disposable subscribe;
        if (logTab.equals("MAIN")) {
            subscribe = dataManager.getObservableMainLog()
                    .compose(RxTransformers.applySchedulers())
                    .subscribe(logMessage -> getViewState().addMessageToMainLogScreen(logMessage, dataManager.getAutoScrollMode()),
                            throwable -> Log.w(TAG, "onError: ", throwable),
                            () -> Log.i(TAG, "onComplete: "),
                            disposable -> getViewState().showMainLogs(dataManager.getMainLogList(), dataManager.getAutoScrollMode()));
        } else {
            subscribe = dataManager.getObservableMainLog()
                    .compose(RxTransformers.applySchedulers())
                    .subscribe(logMessage -> {
                                if (logTab.equals(logMessage.getLogType())) {
                                    getViewState().addMessageToLogScreen(logMessage, dataManager.getAutoScrollMode());
                                }
                            },
                            throwable -> Log.w(TAG, "onError: ", throwable),
                            () -> Log.i(TAG, "onComplete: "),
                            disposable -> getViewState().showLogs(dataManager.getLogList(logTab), dataManager.getAutoScrollMode()));
        }
        subscriptions.addAll(subscribe);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
        subscriptions.clear();
    }

    public void onCreate(String tab) {
        logTab = tab;
    }
}
