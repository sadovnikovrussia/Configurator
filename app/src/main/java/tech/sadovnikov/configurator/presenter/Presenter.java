package tech.sadovnikov.configurator.presenter;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.MenuItem;

import java.util.HashMap;

import tech.sadovnikov.configurator.Contract;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.model.Configuration;
import tech.sadovnikov.configurator.model.Device;
import tech.sadovnikov.configurator.model.Logs;
import tech.sadovnikov.configurator.view.BluetoothFragment;
import tech.sadovnikov.configurator.view.ConfigurationFragment;
import tech.sadovnikov.configurator.view.ConsoleFragment;
import tech.sadovnikov.configurator.view.MainActivity;
import tech.sadovnikov.configurator.view.adapter.AvailableDevicesItemView;
import tech.sadovnikov.configurator.view.adapter.PairedDevicesItemView;

public class Presenter implements Contract.Presenter, Logs.OnLogsActionsListener {
    private static final String TAG = "Presenter";

    private Contract.View mainView;
    private Contract.Repository device;
    private Contract.Log logs;

    private Bundle bundleRepository;

    private BluetoothService bluetoothService;
    private BluetoothBroadcastReceiver bluetoothBroadcastReceiver;
    private UiHandler uiHandler;

    public Presenter(Contract.View mainView) {
        Log.v(TAG, "onConstructor");
        this.mainView = mainView;
        this.device = Device.getInstance();
        this.logs = Logs.getInstance(this);
        this.uiHandler = new UiHandler((Activity) mainView, this);
        this.bluetoothService = new BluetoothService(uiHandler);
        this.bluetoothBroadcastReceiver = new BluetoothBroadcastReceiver(this);
        this.bundleRepository = new Bundle();
        bundleRepository.putSerializable("UiConfiguration", new Configuration());
        registerBluetoothBroadcastReceiver((Context) mainView);
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
        // Log.d(TAG, "onSwitchBtStateChanged" + String.valueOf(state));
        if (state) {
            bluetoothService.enableBt();
        } else {
            bluetoothService.disableBt();
        }
    }

    @Override
    public void onPairedDevicesRvItemClick(String bluetoothDeviceAddress) {
        // Logs.d(TAG, "Ща будем подключаться к " + bluetoothDevice.getName());
        bluetoothService.connectTo(bluetoothDeviceAddress);
    }

    @Override
    public void onAvailableDevicesRvItemClicked(String bluetoothDeviceAddress) {
        bluetoothService.connectTo(bluetoothDeviceAddress);
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

    // BluetoothFragment Lifecycle -----------------------------------------------------------------
    @Override
    public void onBluetoothFragmentCreateView() {
    }

    @Override
    public void onBluetoothFragmentStart() {
        mainView.setSwitchBtState(bluetoothService.isEnabled());
        mainView.setNavigationPosition(MainActivity.BLUETOOTH_FRAGMENT);
        if (bluetoothService.isEnabled()) {
            mainView.showDevices();
        } else {
            mainView.hideDevices();
        }
    }

    // ConfigurationFragment Lifecycle -------------------------------------------------------------
    @Override
    public void OnConfigurationFragmentStart() {
        mainView.setNavigationPosition(MainActivity.CONFIGURATION_FRAGMENT);
    }

    // ConsoleFragment lifecycle -------------------------------------------------------------------
    @Override
    public void onConsoleFragmentStart() {
        mainView.setNavigationPosition(MainActivity.CONSOLE_FRAGMENT);
    }

    @Override
    public void onConsoleFragmentCreateView() {
        mainView.showLog(logs.getLogsMessages());
    }


    @Override
    public void startDiscovery() {
        bluetoothService.clearAvailableDevices();
        // TODO <Проверить, при каких условиях требуется данный permission>
        int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
        ActivityCompat.requestPermissions((MainActivity) mainView,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        bluetoothService.startDiscovery();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_bluetooth:
                BluetoothFragment bluetoothFragment = new BluetoothFragment();
                bluetoothFragment.setArguments(bundleRepository);
                ((MainActivity) mainView).bluetoothFragment = bluetoothFragment;
                mainView.showFragment(bluetoothFragment);
                return true;
            case R.id.navigation_configuration:
                ConfigurationFragment configurationFragment = new ConfigurationFragment();
                configurationFragment.setArguments(bundleRepository);
                mainView.showFragment(configurationFragment);
                return true;
            case R.id.navigation_console:
                ConsoleFragment consoleFragment = new ConsoleFragment();
                mainView.showFragment(consoleFragment);
                return true;
        }
        return false;

    }

    @Override
    public void onMainActivityCreate() {
        //((MainActivity) mainView).bluetoothFragment = new BluetoothFragment();
        //((MainActivity) mainView).bluetoothFragment.setArguments(bundleRepository);
        //mainView.showFragment(((MainActivity) mainView).bluetoothFragment);
        mainView.showFragment(MainActivity.BLUETOOTH_FRAGMENT);
    }

    @Override
    public void onMainActivityDestroy() {
        mainView.unregisterBluetoothBroadcastReceiver(bluetoothBroadcastReceiver);
    }

    @Override
    public void onAddLogsLineEvent(String line) {
        mainView.addLogsLine(line);
    }

    @Override
    public void onDevicesPageSelected(int position) {
        if (position == 0){
          bluetoothService.cancelDiscovery();
          mainView.updatePairedDevices();
          //mainView.showPairedDevices();
        } else if (position == 1) {
            startDiscovery();
            //mainView.showAvailableDevices();
        }
    }

    @Override
    public void onAvailableDevicesFragmentDestroyView() {
        bluetoothService.cancelDiscovery();
    }

    @Override
    public void onBindViewHolderOfPairedDevicesRvAdapter(PairedDevicesItemView holder, int position) {
        holder.setDeviceName(bluetoothService.getPairedDevices().get(position).getName());
        holder.setDeviceAddress(bluetoothService.getPairedDevices().get(position).getAddress());
    }

    @Override
    public int onGetItemCountOfPairedDevicesRvAdapter() {
        return bluetoothService.getPairedDevices().size();
    }

    @Override
    public void onBindViewHolderOfAvailableDevicesRvAdapter(AvailableDevicesItemView holder, int position) {
        holder.setDeviceName(bluetoothService.getAvailableDevices().get(position).getName());
        holder.setDeviceAddress(bluetoothService.getAvailableDevices().get(position).getAddress());
    }

    @Override
    public int onGetItemCountOfAvailableDevicesRvAdapter() {
        return bluetoothService.getAvailableDevices().size();
    }

    void onBluetoothServiceStateOn() {
        mainView.setSwitchBtState(bluetoothService.isEnabled());
        // mainView.showPairedDevices();
        mainView.showDevices();
        mainView.updatePairedDevices();
        mainView.updateAvailableDevices();
    }

    void onBluetoothServiceStateOff() {
        mainView.setSwitchBtState(bluetoothService.isEnabled());
        mainView.hideDevices();
    }

    void onBluetoothServiceActionFound(BluetoothDevice bluetoothDevice) {
        bluetoothService.addAvailableDevice(bluetoothDevice);
        String name = bluetoothDevice.getName();
        String address = bluetoothDevice.getAddress();
        Log.d(TAG, "onBluetoothServiceActionFound: name = " + name + ", address = " + address);
        //mainView.showAvailableDevices();
        mainView.updateAvailableDevices();
    }

    void onBluetoothServiceActionDiscoveryStarted() {

    }

    @Override
    public void onTvLogsLongClick() {
        String logsMessages = logs.getLogsMessages();
        ClipboardManager clipboard = (ClipboardManager)((Activity)mainView).getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", logsMessages);
        clipboard.setPrimaryClip(clip);
        mainView.showToast("Логи скопированы в буфер обмена");
    }
}
