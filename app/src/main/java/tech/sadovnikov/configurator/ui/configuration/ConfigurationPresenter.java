package tech.sadovnikov.configurator.ui.configuration;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class ConfigurationPresenter extends MvpPresenter<ConfigurationView> {
    private static final String TAG = ConfigurationPresenter.class.getSimpleName();

    ConfigurationPresenter() {
        Log.v(TAG, "ConfigurationPresenter: " + this);
    }

}
