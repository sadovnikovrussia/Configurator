package tech.sadovnikov.configurator.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tech.sadovnikov.configurator.di.ApplicationContext;
import tech.sadovnikov.configurator.di.PreferenceInfo;
import tech.sadovnikov.configurator.model.AppBluetoothService;
import tech.sadovnikov.configurator.model.AppDataManager;
import tech.sadovnikov.configurator.model.BluetoothBroadcastReceiver;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.model.DataManager;
import tech.sadovnikov.configurator.model.LogsManager;
import tech.sadovnikov.configurator.model.Logs;
import tech.sadovnikov.configurator.model.StreamAnalyzer;
import tech.sadovnikov.configurator.model.data.prefs.AppPreferencesHelper;
import tech.sadovnikov.configurator.model.data.prefs.PreferencesHelper;
import tech.sadovnikov.configurator.model.entities.Configuration;
import tech.sadovnikov.configurator.utils.AppConstants;

@Module
public class ApplicationModule {
    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    @ApplicationContext
    Context provideAppContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    Logs provideLogs() {
        return new LogsManager();
    }

    @Provides
    @Singleton
    Configuration provideConfiguration() {
        return new Configuration();
    }

    @Provides
    @Singleton
    BluetoothService provideBluetoothService() {
        return new AppBluetoothService();
    }

    @Provides
    @Singleton
    BluetoothBroadcastReceiver provideBluetoothReceiver() {
        return new BluetoothBroadcastReceiver();
    }

    @Provides
    @Singleton
    StreamAnalyzer provideStreamAnalyzer(BluetoothService bluetoothService, DataManager dataManager) {
        return new StreamAnalyzer(bluetoothService, dataManager);
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
