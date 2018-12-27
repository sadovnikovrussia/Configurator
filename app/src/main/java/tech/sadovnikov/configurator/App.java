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

public class App extends Application {
    private static final String TAG = "ConfiguratorApplication";

    @Inject
    DataManager dataManager;

    private BluetoothBroadcastReceiver receiver;
    private static BluetoothService bluetoothService;

    ApplicationComponent applicationComponent;

    public App() {
        super();
        Log.d(TAG, "ConfiguratorApplication: ");
        initializeInjection();
        bluetoothService = applicationComponent.getBluetoothService();
        receiver = applicationComponent.getBluetoothBroadcastReceiver();
        applicationComponent.inject(this);
    }

    private void registerBluetoothReceiver(BluetoothBroadcastReceiver receiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        getApplicationContext().registerReceiver(receiver, intentFilter);
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
        Log.d(TAG, "App: " + this + "," + bluetoothService + "," + receiver);
        registerBluetoothReceiver(receiver);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate: ");
        unregisterReceiver(receiver);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: ");
    }


}
