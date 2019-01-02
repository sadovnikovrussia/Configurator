package tech.sadovnikov.configurator.di.module;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.di.ApplicationContext;
import tech.sadovnikov.configurator.di.PreferenceInfo;
import tech.sadovnikov.configurator.model.AppBluetoothService;
import tech.sadovnikov.configurator.model.BluetoothBroadcastReceiver;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.model.data.AppDataManager;
import tech.sadovnikov.configurator.model.data.DataManager;
import tech.sadovnikov.configurator.model.data.prefs.AppPreferencesHelper;
import tech.sadovnikov.configurator.model.data.prefs.PreferencesHelper;
import tech.sadovnikov.configurator.utils.AppConstants;

@Module
public class ApplicationModule {
    private static final String TAG = ApplicationModule.class.getSimpleName();
    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication(){
        return application;
    }

    @Provides
    @Singleton
    @ApplicationContext
    Context provideAppContext(){
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    BluetoothService provideBluetoothService(){
        //Log.d(TAG, "provideBluetoothService() returned: " + appBluetoothService);
        return new AppBluetoothService();
    }

    @Provides
    @Singleton
    BluetoothBroadcastReceiver provideBluetoothReceiver(){
        return new BluetoothBroadcastReceiver();
    }






    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }



}
