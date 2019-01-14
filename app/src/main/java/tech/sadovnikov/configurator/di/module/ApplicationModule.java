package tech.sadovnikov.configurator.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tech.sadovnikov.configurator.di.ApplicationContext;
import tech.sadovnikov.configurator.di.MainConfiguration;
import tech.sadovnikov.configurator.model.AppBluetoothService;
import tech.sadovnikov.configurator.model.BluetoothBroadcastReceiver;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.model.StreamAnalyzer;
import tech.sadovnikov.configurator.model.data.AppDataManager;
import tech.sadovnikov.configurator.model.data.DataManager;
import tech.sadovnikov.configurator.model.data.logs.AppLogManager;
import tech.sadovnikov.configurator.model.data.logs.LogManager;
import tech.sadovnikov.configurator.model.data.configuration.Configuration;

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
    LogManager provideLogManager() {
        return new AppLogManager();
    }

    @Provides
    @Singleton
    @MainConfiguration
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


}
