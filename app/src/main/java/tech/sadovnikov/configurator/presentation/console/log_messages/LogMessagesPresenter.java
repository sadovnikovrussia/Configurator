package tech.sadovnikov.configurator.presentation.console.log_messages;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.di.component.DaggerPresenterComponent;
import tech.sadovnikov.configurator.di.component.PresenterComponent;
import tech.sadovnikov.configurator.model.data.DataManager;

@InjectViewState
public class LogMessagesPresenter extends MvpPresenter<LogMessagesView> {
    private static final String TAG = LogMessagesPresenter.class.getSimpleName();

    private PresenterComponent presenterComponent;
    @Inject
    DataManager dataManager;

    private CompositeDisposable compositeDisposable;

    LogMessagesPresenter() {
        compositeDisposable = new CompositeDisposable();
        Log.i(TAG, "LogMessagesPresenter: ");
        initDaggerComponent();
    }

    private void initDaggerComponent() {
        presenterComponent = DaggerPresenterComponent
                .builder()
                .applicationComponent(App.getApplicationComponent())
                .build();
        presenterComponent.injectLogMessagesPresenter(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.i(TAG, "onFirstViewAttach: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
}
