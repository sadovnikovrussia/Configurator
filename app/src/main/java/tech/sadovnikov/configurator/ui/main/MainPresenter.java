package tech.sadovnikov.configurator.ui.main;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

public class MainPresenter extends MvpBasePresenter<MainMvp.MainView> implements MainMvp.MainPresenter {
    private static final String TAG = MainPresenter.class.getSimpleName();

    @Override
    public void attachView(@NonNull MainMvp.MainView view) {
        super.attachView(view);
        ifViewAttached(MainMvp.MainView::showBluetoothView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
