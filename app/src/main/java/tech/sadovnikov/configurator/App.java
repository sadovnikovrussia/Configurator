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

public class App extends Application {
    private static final String TAG = "ConfiguratorApplication";

    @Inject
    DataManager dataManager;

    private static BluetoothService bluetoothService;

    ApplicationComponent applicationComponent;

    public App() {
        super();
        Log.d(TAG, "ConfiguratorApplication: ");
        initializeInjection();
        bluetoothService = applicationComponent.getBluetoothService();
        applicationComponent.inject(this);

    }

    private void initializeInjection() {
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static BluetoothService getBluetoothService() {
        return bluetoothService;
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
