package tech.sadovnikov.configurator;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import javax.inject.Inject;

import tech.sadovnikov.configurator.di.component.ApplicationComponent;
import tech.sadovnikov.configurator.di.component.DaggerApplicationComponent;
import tech.sadovnikov.configurator.di.module.ApplicationModule;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.model.data.DataManager;

public class ConfiguratorApplication extends Application {
    private static final String TAG = "ConfiguratorApplication";

    @Inject
    DataManager dataManager;

    @Inject
    BluetoothService bluetoothService;

    public BluetoothService getBluetoothService() {
        return bluetoothService;
    }

    ApplicationComponent applicationComponent;

    public ConfiguratorApplication() {
        super();
        Log.d(TAG, "ConfiguratorApplication: ");
        initializeInjection();
        applicationComponent.inject(this);
    }

    private void initializeInjection() {
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate: ");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: ");
    }



}
