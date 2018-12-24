package tech.sadovnikov.configurator.model.data;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import tech.sadovnikov.configurator.di.ApplicationContext;
import tech.sadovnikov.configurator.model.data.prefs.PreferencesHelper;

@Singleton
public class AppDataManager implements DataManager{

    private static final String TAG = "AppDataManager";

    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          PreferencesHelper preferencesHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
    }

}
