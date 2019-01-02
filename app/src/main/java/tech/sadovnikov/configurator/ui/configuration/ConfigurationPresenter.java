package tech.sadovnikov.configurator.ui.configuration;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

public class ConfigurationPresenter extends MvpBasePresenter<ConfigurationMvp.View> implements ConfigurationMvp.Presenter {
    private static final String TAG = ConfigurationPresenter.class.getSimpleName();

    ConfigurationPresenter() {
        Log.v(TAG, "ConfigurationPresenter: " + this);
    }

    @Override
    public void attachView(@NonNull ConfigurationMvp.View view) {
        super.attachView(view);
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
