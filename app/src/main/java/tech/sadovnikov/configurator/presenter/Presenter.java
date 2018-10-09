package tech.sadovnikov.configurator.presenter;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;

import java.util.HashMap;

import tech.sadovnikov.configurator.Contract;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.model.Device;
import tech.sadovnikov.configurator.model.Logs;

public class Presenter implements Contract.Presenter, Logs.OnLogsActionsListener {
    private static final String TAG = "Presenter";

    private Contract.View mainActivity;
    private Contract.Repository device;
    private Contract.Log logs;

    private BluetoothService bluetoothService;
    private BluetoothBroadcastReceiver bluetoothBroadcastReceiver;

    public Presenter(Contract.View mainActivity) {
        Log.v(TAG, "onConstructor");
        this.mainActivity = mainActivity;
        this.device = Device.getInstance();
        this.logs = Logs.getInstance(this);
        UiHandler uiHandler = new UiHandler((Activity) mainActivity, this);
        this.bluetoothService = new BluetoothService(uiHandler);
        this.bluetoothBroadcastReceiver = new BluetoothBroadcastReceiver(this);
        registerBluetoothBroadcastReceiver((Context) mainActivity);
    }

    // Регистрация ресиверов
    private void registerBluetoothBroadcastReceiver(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        context.registerReceiver(bluetoothBroadcastReceiver, intentFilter);
    }

    @Override
    public void onSwitchBtStateChanged(boolean state) {
        if (state) {
            bluetoothService.enableBt();
        } else {
            bluetoothService.disableBt();
        }
    }

    @Override
    public void onPairedDevicesRvItemClick(BluetoothDevice bluetoothDevice) {
        // Logs.d(TAG, "Ща будем подключаться к " + bluetoothDevice.getName());
        bluetoothService.connectTo(bluetoothDevice);
    }

    @Override
    public void onHandleMessage(Message msg) {
        Object obj = msg.obj;
        switch (msg.what) {
            // Отправка полученных данных в консоль
            case DataAnalyzer.WHAT_MAIN_LOG:
                String message = (String) obj;
                logs.addLine(message);
                break;
            // Загрузка данных в LiveData
            case DataAnalyzer.WHAT_COMMAND_DATA:
                HashMap msgData = (HashMap) obj;
                String data = (String) msgData.get(DataAnalyzer.DATA);
                String command = (String) msgData.get(DataAnalyzer.PARAMETER_NAME);
                break;
        }
    }

    @Override
    public void onBluetoothFragmentCreateView() {
    }

    @Override
    public void onBluetoothFragmentStart() {
        mainActivity.setSwitchBtState(bluetoothService.isEnabled());
        if (bluetoothService.isEnabled()) {
            Log.w(TAG, "mainActivity.showPairedDevices()");
            mainActivity.showPairedDevices();
        } else {
            mainActivity.hideAllDevices();
        }
    }

    @Override
    public void onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_bluetooth:
                break;
            case R.id.navigation_configuration:
                break;
            case R.id.navigation_console:
                break;
        }

    }

    @Override
    public void onMainActivityCreate() {
    }

    @Override
    public void onMainActivityDestroy() {
        ((Context) mainActivity).unregisterReceiver(bluetoothBroadcastReceiver);
    }

    @Override
    public void onConsoleFragmentCreateView() {
        mainActivity.showLog(logs.getLogsMessages());
    }

    @Override
    public void onAddLogsLineEvent(String line) {
        mainActivity.addLogsLine(line);
    }

    void onBluetoothServiceStateOn() {
        mainActivity.setSwitchBtState(bluetoothService.isEnabled());
        mainActivity.showPairedDevices();
    }

    void onBluetoothServiceStateOff() {
        mainActivity.setSwitchBtState(bluetoothService.isEnabled());
        mainActivity.hideAllDevices();
    }
}
