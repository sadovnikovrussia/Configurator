package tech.sadovnikov.configurator.ui.main;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

public class MainPresenter extends MvpBasePresenter<MainMvp.MainView> implements MainMvp.MainPresenter {
    private static final String TAG = MainPresenter.class.getSimpleName();
    private boolean restart;

    MainPresenter() {
        super();
        Log.w(TAG, "onConstructor: ");
    }

    @Override
    public void attachView(@NonNull MainMvp.MainView view) {
        super.attachView(view);
        Log.w(TAG, "attachView: ");
        if (!restart) {
            ifViewAttached(MainMvp.MainView::showBluetoothView);

            restart = true;
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        Log.w(TAG, "detachView: ");
    }

    @Override
    public void destroy() {
        super.destroy();
        Log.w(TAG, "destroy: ");
    }

    @Override
    public void onRestoreInstanceState(boolean restart) {
    }
}
