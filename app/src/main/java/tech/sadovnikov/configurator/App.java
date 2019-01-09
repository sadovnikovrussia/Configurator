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
import tech.sadovnikov.configurator.model.DataManager;

public class App extends Application {
    private static final String TAG = App.class.getSimpleName();

    @Inject
    DataManager dataManager;
    @Inject
    BluetoothBroadcastReceiver receiver;
    @Inject
    BluetoothService bluetoothService;

    private static ApplicationComponent applicationComponent;

    public App() {
        super();
        Log.d(TAG, "ConfiguratorApplication: ");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initDaggerComponent();
        applicationComponent.inject(this);
        Log.d(TAG, "onCreate: " + bluetoothService + ", " + bluetoothService.getInputMessagesStream());
        receiver.setListener((BluetoothBroadcastReceiver.Listener) bluetoothService);
        registerBluetoothReceiver(receiver);
    }

    private void initDaggerComponent() {
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
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

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate: ");
        unregisterReceiver(receiver);
        dataManager.clearSubscribes();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: ");
    }

}
