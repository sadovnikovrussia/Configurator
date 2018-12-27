package tech.sadovnikov.configurator;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.util.Log;

import javax.inject.Inject;

import tech.sadovnikov.configurator.di.component.ApplicationComponent;
import tech.sadovnikov.configurator.di.component.DaggerApplicationComponent;
import tech.sadovnikov.configurator.di.module.ApplicationModule;
import tech.sadovnikov.configurator.model.BluetoothBroadcastReceiver;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.model.data.DataManager;

public class ConfiguratorApplication extends Application {
    private static final String TAG = ConfiguratorApplication.class.getSimpleName();

    static private ApplicationComponent applicationComponent;

    @Inject
    DataManager dataManager;

    @Inject
    BluetoothService bluetoothService;

    @Inject
    BluetoothBroadcastReceiver bluetoothReceiver;


    public ConfiguratorApplication() {
        super();
        Log.v(TAG, "ConfiguratorApplication: ");
    }

    private void initializeInjection() {
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "onCreate: ");
        initializeInjection();
        bluetoothService = applicationComponent.getBluetoothService();
        bluetoothReceiver = applicationComponent.getBluetoothBroadcastReceiver();
        Log.d(TAG, "onCreate: " + bluetoothService);
        //applicationComponent.inject(this);
        registerReceiver();
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        registerReceiver(bluetoothReceiver, intentFilter);
    }

    @Override
    public void onTerminate() {
        unregisterReceiver(bluetoothReceiver);
        Log.v(TAG, "onTerminate: ");
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.v(TAG, "onConfigurationChanged: " + bluetoothService);

    }


}
