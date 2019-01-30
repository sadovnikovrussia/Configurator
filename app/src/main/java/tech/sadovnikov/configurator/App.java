package tech.sadovnikov.configurator;

import android.Manifest;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;

import javax.inject.Inject;

import tech.sadovnikov.configurator.di.component.ApplicationComponent;
import tech.sadovnikov.configurator.di.component.DaggerApplicationComponent;
import tech.sadovnikov.configurator.di.module.ApplicationModule;
import tech.sadovnikov.configurator.model.BluetoothBroadcastReceiver;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.model.MessageAnalyzer;
import tech.sadovnikov.configurator.model.data.DataManager;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

public class App extends Application {
    private static final String TAG = App.class.getSimpleName();

    @Inject
    DataManager dataManager;
    @Inject
    BluetoothBroadcastReceiver receiver;
    @Inject
    BluetoothService bluetoothService;
    @Inject
    MessageAnalyzer messageAnalyzer;

    private static ApplicationComponent applicationComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        initDaggerComponent();
        applicationComponent.inject(this);
        Log.d(TAG, "onCreate: " + bluetoothService);
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
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_REQUEST_ENABLE);

        getApplicationContext().registerReceiver(receiver, intentFilter);
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }


    @Override
    public void onTerminate() {
        Log.d(TAG, "onTerminate: ");
        unregisterReceiver(receiver);
        dataManager.clearSubscribes();
        bluetoothService.closeAllConnections();
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: ");
    }

}
