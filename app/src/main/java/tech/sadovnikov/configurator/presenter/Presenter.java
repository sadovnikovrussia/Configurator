package tech.sadovnikov.configurator.presenter;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.MenuItem;

import java.util.HashMap;

import tech.sadovnikov.configurator.Contract;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.model.Configuration;
import tech.sadovnikov.configurator.model.Logs;
import tech.sadovnikov.configurator.view.MainActivity;
import tech.sadovnikov.configurator.view.adapter.AvailableDevicesItemView;
import tech.sadovnikov.configurator.view.adapter.PairedDevicesItemView;

import static tech.sadovnikov.configurator.Contract.Configuration.BLINKER_MODE;
import static tech.sadovnikov.configurator.Contract.Configuration.FIRMWARE_VERSION;
import static tech.sadovnikov.configurator.Contract.Configuration.ID;
import static tech.sadovnikov.configurator.presenter.DataAnalyzer.WHAT_COMMAND_DATA;
import static tech.sadovnikov.configurator.presenter.DataAnalyzer.WHAT_MAIN_LOG;

public class Presenter implements Contract.Presenter, Configuration.OnConfigurationInteractionListener {
    private static final String TAG = "Presenter";

    private Contract.View mainView;
    private Contract.Device device;
    private Contract.Logs logs;
    private Contract.Configuration uiConfiguration;

    private BluetoothService bluetoothService;
    private BluetoothBroadcastReceiver bluetoothBroadcastReceiver;
    private UiHandler uiHandler;
    private Loader loader;

    public Presenter(Contract.View mainView) {
        Log.v(TAG, "onConstructor");
        this.mainView = mainView;
        uiConfiguration = new Configuration(this);
        device = tech.sadovnikov.configurator.model.Device.getInstance();
        logs = new Logs(this);
        uiHandler = new UiHandler((Activity) mainView, this);
        bluetoothService = new BluetoothService(uiHandler);
        bluetoothBroadcastReceiver = new BluetoothBroadcastReceiver(this);
        loader = new Loader(bluetoothService);
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
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        context.registerReceiver(bluetoothBroadcastReceiver, intentFilter);
    }

    @Override
    public void onSetParameter(String name, String value) {
        mainView.showParameter(name, value);
    }

    @Override
    public void onHandleMessage(Message msg) {
        Object obj = msg.obj;
        switch (msg.what) {
            // Отправка полученных данных в logs
            case WHAT_MAIN_LOG:
                String message = (String) obj;
                logs.addLine(message);
                break;
            //
            case WHAT_COMMAND_DATA:
                HashMap msgData = (HashMap) obj;
                String value = (String) msgData.get(DataAnalyzer.PARAMETER_VALUE);
                String name = (String) msgData.get(DataAnalyzer.PARAMETER_NAME);
                uiConfiguration.setParameter(name, value);
                onReceiveCommand();
                break;
        }
    }

    private void onReceiveCommand() {
        loader.nextCommand();
    }


    // BluetoothFragment events --------------------------------------------------------------------
    @Override
    public void onBtnSendCommandClick() {
        String commandLineText = mainView.getCommandLineText();
        bluetoothService.sendData(commandLineText);
    }

    @Override
    public void onSwitchBtStateChanged(boolean state) {
        // Logs.d(TAG, "onSwitchBtStateChanged" + String.valueOf(state));
        if (state) {
            bluetoothService.enableBt();
        } else {
            bluetoothService.disableBt();
        }
    }

    @Override
    public void onDevicesPageSelected(int position) {
        if (position == 0) {
            bluetoothService.cancelDiscovery();
            mainView.updatePairedDevices();
            //mainView.showPairedDevices();
        } else if (position == 1) {
            startDiscovery();
            //mainView.showAvailableDevices();
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

    // Lifecycle
    @Override
    public void onBluetoothFragmentCreateView() {
    }

    @Override
    public void onBluetoothFragmentStart() {
        mainView.setSwitchBtState(bluetoothService.isEnabled());
        mainView.setNavigationPosition(Contract.View.BLUETOOTH_FRAGMENT);
        if (bluetoothService.isEnabled()) {
            mainView.showDevices();
        } else {
            mainView.hideDevices();
        }
    }

    @Override
    public void onAvailableDevicesFragmentDestroyView() {
        bluetoothService.cancelDiscovery();
    }

    @Override
    public void onBindViewHolderOfPairedDevicesRvAdapter(PairedDevicesItemView holder, int position) {
        holder.setDeviceName(bluetoothService.getBondedDevices().get(position).getName());
        holder.setDeviceAddress(bluetoothService.getBondedDevices().get(position).getAddress());
    }

    @Override
    public int onGetItemCountOfPairedDevicesRvAdapter() {
        return bluetoothService.getBondedDevices().size();
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


    // ConfigurationFragment events ----------------------------------------------------------------
    @Override
    public void onConfigTabsRvItemClick(String tab) {
        mainView.showFragment(tab);
    }

    @Override
    public void onConfigurationOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.item_set) {
            loader.loadConfiguration(uiConfiguration.getConfigurationForSet(), Loader.SET);
            // loader.setConfiguration(uiConfiguration);
        } else if (itemId == R.id.item_load) {
            loader.loadConfiguration(uiConfiguration, Loader.READ);
            // loader.readConfiguration(uiConfiguration);
        }
    }

    // Lifecycle
    @Override
    public void onConfigurationFragmentStart() {
        mainView.setNavigationPosition(MainActivity.CONFIGURATION_FRAGMENT);
    }

    // ConfigBuoyFragment events -------------------------------------------------------------------
    @Override
    public void onEtIdAfterTextChanged() {
        uiConfiguration.setParameterWithoutCallback("id", mainView.getEtIdText());
    }

    // Lifecycle
    @Override
    public void onConfigBuoyFragmentStart() {
        mainView.setNavigationPosition(MainActivity.CONFIGURATION_FRAGMENT);
        // TODO <Добавить параметр>
        mainView.showParameter(ID, uiConfiguration.getParameterValue(ID));
        mainView.showParameter(FIRMWARE_VERSION, uiConfiguration.getParameterValue(FIRMWARE_VERSION));
        mainView.showParameter(BLINKER_MODE, uiConfiguration.getParameterValue(BLINKER_MODE));
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

    // MainActivity events -------------------------------------------------------------------------
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_bluetooth:
                mainView.showFragment(Contract.View.BLUETOOTH_FRAGMENT);
                return true;
            case R.id.navigation_configuration:
                mainView.showFragment(Contract.View.CONFIGURATION_FRAGMENT);
                return true;
            case R.id.navigation_console:
                mainView.showFragment(Contract.View.CONSOLE_FRAGMENT);
                return true;
        }
        return false;

    }

    // Lifecycle
    @Override
    public void onMainActivityCreate() {
        mainView.showFragment(Contract.View.BLUETOOTH_FRAGMENT);
    }

    @Override
    public void onMainActivityDestroy() {
        mainView.unregisterBluetoothBroadcastReceiver(bluetoothBroadcastReceiver);
        bluetoothService.closeAllConnections();
    }

    // Logs events ---------------------------------------------------------------------------------
    @Override
    public void onAddLogsLineEvent(String line) {
        mainView.addLogsLine(line);
    }

    @Override
    public void onTvLogsLongClick() {
        String logsMessages = logs.getLogsMessages();
        ClipboardManager clipboard = (ClipboardManager) ((Activity) mainView).getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", logsMessages);
        clipboard.setPrimaryClip(clip);
        mainView.showToast("Логи скопированы в буфер обмена");
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

}
