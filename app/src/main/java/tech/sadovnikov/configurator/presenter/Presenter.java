package tech.sadovnikov.configurator.presenter;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Objects;

import tech.sadovnikov.configurator.Contract;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.model.Logs;
import tech.sadovnikov.configurator.model.Parameter;
import tech.sadovnikov.configurator.model.RepositoryConfiguration;
import tech.sadovnikov.configurator.view.MainActivity;
import tech.sadovnikov.configurator.view.adapter.AvailableDevicesItemView;
import tech.sadovnikov.configurator.view.adapter.PairedDevicesItemView;

import static tech.sadovnikov.configurator.model.Configuration.BASE_POS;
import static tech.sadovnikov.configurator.model.Configuration.BLINKER_BRIGHTNESS;
import static tech.sadovnikov.configurator.model.Configuration.BLINKER_LX;
import static tech.sadovnikov.configurator.model.Configuration.BLINKER_MODE;
import static tech.sadovnikov.configurator.model.Configuration.DEVIATION_INT;
import static tech.sadovnikov.configurator.model.Configuration.EVENTS_MASK;
import static tech.sadovnikov.configurator.model.Configuration.FIRMWARE_VERSION;
import static tech.sadovnikov.configurator.model.Configuration.FIX_DELAY;
import static tech.sadovnikov.configurator.model.Configuration.HDOP;
import static tech.sadovnikov.configurator.model.Configuration.ID;
import static tech.sadovnikov.configurator.model.Configuration.IMPACT_POW;
import static tech.sadovnikov.configurator.model.Configuration.LAT_DEVIATION;
import static tech.sadovnikov.configurator.model.Configuration.LONG_DEVIATION;
import static tech.sadovnikov.configurator.model.Configuration.MAX_ACTIVE;
import static tech.sadovnikov.configurator.model.Configuration.MAX_DEVIATION;
import static tech.sadovnikov.configurator.model.Configuration.SATELLITE_SYSTEM;
import static tech.sadovnikov.configurator.model.Configuration.TILT_ANGLE;
import static tech.sadovnikov.configurator.model.Configuration.UPOWER;
import static tech.sadovnikov.configurator.model.Configuration.UPOWER_THLD;
import static tech.sadovnikov.configurator.presenter.DataAnalyzer.WHAT_COMMAND_DATA;
import static tech.sadovnikov.configurator.presenter.DataAnalyzer.WHAT_MAIN_LOG;
import static tech.sadovnikov.configurator.presenter.Loader.WHAT_LOADING_END;

public class Presenter implements Contract.Presenter, RepositoryConfiguration.OnRepositoryConfigurationEventsListener, Loader.OnLoaderEventsListener,
        BluetoothBroadcastReceiver.OnBluetoothBroadcastReceiverEventsListener, BluetoothService.OnBluetoothServiceEventsListener {
    private static final String TAG = "Presenter";

    private Contract.View mainView;
    private Contract.Logs logs;
    private Contract.RepositoryConfiguration repositoryConfiguration;

    private BluetoothService bluetoothService;
    private BluetoothBroadcastReceiver bluetoothBroadcastReceiver;
    private Loader loader;
    private FileManager fileManager;

    public Presenter(Contract.View mainView) {
        Log.v(TAG, "onConstructor");
        this.mainView = mainView;
        logs = new Logs(this);
        UiHandler uiHandler = new UiHandler((Activity) mainView, this);
        bluetoothService = new BluetoothService(this, uiHandler);
        bluetoothBroadcastReceiver = new BluetoothBroadcastReceiver(this);
        loader = new Loader(this, bluetoothService, uiHandler);
        fileManager = new FileManager();
        repositoryConfiguration = new RepositoryConfiguration(this);
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
                Parameter parameter = new Parameter(name, value);
                repositoryConfiguration.setParameter(parameter);
                onReceiveCommand();
                break;
            case WHAT_LOADING_END:
                mainView.hideLoadingProgress();
                break;
        }
    }

    private void onReceiveCommand() {
        loader.nextCommand();
    }

    // ---------------------------------------------------------------------------------------------
    // BluetoothFragment events
    @Override
    public void onTestButtonClick() {
        int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;
        ActivityCompat.requestPermissions((MainActivity) mainView,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        // fileManager.saveConfiguration(repositoryConfiguration.getConfigurationForSetAndSave());
        mainView.startFileManagerActivity();
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
        } else if (position == 1) {
            startDiscovery();
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

    // Recycler bluetooth devices events
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

    // ---------------------------------------------------------------------------------------------
    // ConfigurationFragment events
    @Override
    public void onConfigTabsRvItemClick(String tab) {
        mainView.showFragment(tab);
    }

    @Override
    public void onConfigurationOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.item_set) {
            // TODO? <Что делать со спинерами, когда эти параметр не трогали?>
            // TODO <Поставить условие на подключение к устройству>
            // TODO <Показать бегунок загрузки>
            //loader.loadConfiguration(repositoryConfiguration.getConfigurationForSetAndSave(), Loader.SET);
            loader.loadCommandList(repositoryConfiguration.getCommandListForSetConfiguration());
        } else if (itemId == R.id.item_load) {
            // TODO <Поставить условие на подключение к устройству>
            // TODO <Показать бегунок загрузки>
            //loader.loadConfiguration(repositoryConfiguration.getUiConfiguration(), Loader.READ);
            loader.loadCommandList(repositoryConfiguration.getCommandListForReadConfiguration());
        } else if (itemId == R.id.item_open) {
            mainView.startFileManagerActivity();
        } else if (itemId == R.id.item_save) {
            mainView.requestWritePermission();
            fileManager.saveConfiguration(repositoryConfiguration.getConfigurationForSetAndSave());
        }
    }

    // Lifecycle
    @Override
    public void onConfigurationFragmentStart() {
        mainView.setNavigationPosition(MainActivity.CONFIGURATION_FRAGMENT);
    }


    // ---------------------------------------------------------------------------------------------
    // ConfigBuoyFragment events
    @Override
    public void onBtnRestartClick() {
        bluetoothService.sendData("@restart");
    }

    @Override
    public void onBtnDefaultSettingsClick() {
        bluetoothService.sendData("@reset settings");
    }

    @Override
    public void onEtIdAfterTextChanged() {
        repositoryConfiguration.setParameterWithoutCallback(ID, mainView.getEtIdText());
    }

    // Lifecycle
    @Override
    public void onConfigBuoyFragmentStart() {
        mainView.setNavigationPosition(MainActivity.CONFIGURATION_FRAGMENT);
        mainView.showParameter(ID, repositoryConfiguration.getParameterValue(ID));
        mainView.showParameter(FIRMWARE_VERSION, repositoryConfiguration.getParameterValue(FIRMWARE_VERSION));
    }


    // ---------------------------------------------------------------------------------------------
    // ConfigMainFragment events
    @Override
    public void onSpinBlinkerModeItemSelected(int position) {
        Log.d(TAG, "onSpinBlinkerModeItemSelected: position = " + position);
        repositoryConfiguration.setParameterWithoutCallback(BLINKER_MODE, String.valueOf(position));
    }

    @Override
    public void afterEtBlinkerLxTextChanged() {
        repositoryConfiguration.setParameterWithoutCallback(BLINKER_LX, mainView.getEtBlinkerLxText());
    }

    @Override
    public void onEtMaxDeviationFocusChange(boolean hasFocus) {
        if (!hasFocus) repositoryConfiguration.setParameterWithoutCallback(MAX_DEVIATION, mainView.getEtMaxDeviationText());
    }

    @Override
    public void onEtTiltAngleFocusChange(boolean hasFocus) {
        if (!hasFocus) repositoryConfiguration.setParameterWithoutCallback(TILT_ANGLE, mainView.getEtTiltDeviationText());
    }

    @Override
    public void onEtImpactPowFocusChange(boolean hasFocus) {
        if (!hasFocus) repositoryConfiguration.setParameterWithoutCallback(IMPACT_POW, mainView.getEtImpactPowText());
    }

    @Override
    public void onEtUpowerThldFocusChange(boolean hasFocus) {
        if (!hasFocus) repositoryConfiguration.setParameterWithoutCallback(UPOWER_THLD, mainView.getEtUpowerThldText());
    }

    @Override
    public void onEtDeviationIntFocusChange(boolean hasFocus) {
        if (!hasFocus) repositoryConfiguration.setParameterWithoutCallback(DEVIATION_INT, mainView.getEtDeviationIntText());
    }

    @Override
    public void onEtMaxActiveFocusChange(boolean hasFocus) {
        if (!hasFocus) repositoryConfiguration.setParameterWithoutCallback(MAX_ACTIVE, mainView.getEtMaxActiveText());
    }

    @Override
    public void onSpinBlinkerBrightnessItemSelected(int position) {
        Log.d(TAG, "onSpinBlinkerBrightnessItemSelected: position = " + position);
        repositoryConfiguration.setParameterWithoutCallback(BLINKER_BRIGHTNESS, String.valueOf(position));
    }

    // Lifecycle
    @Override
    public void onConfigMainFragmentStart() {
        mainView.setNavigationPosition(MainActivity.CONFIGURATION_FRAGMENT);
        mainView.showParameter(BLINKER_MODE, repositoryConfiguration.getParameterValue(BLINKER_MODE));
        mainView.showParameter(BLINKER_BRIGHTNESS, repositoryConfiguration.getParameterValue(BLINKER_BRIGHTNESS));
        mainView.showParameter(BLINKER_LX, repositoryConfiguration.getParameterValue(BLINKER_LX));
        mainView.showParameter(MAX_DEVIATION, repositoryConfiguration.getParameterValue(MAX_DEVIATION));
        mainView.showParameter(TILT_ANGLE, repositoryConfiguration.getParameterValue(TILT_ANGLE));
        mainView.showParameter(IMPACT_POW, repositoryConfiguration.getParameterValue(IMPACT_POW));
        mainView.showParameter(UPOWER_THLD, repositoryConfiguration.getParameterValue(UPOWER_THLD));
        mainView.showParameter(DEVIATION_INT, repositoryConfiguration.getParameterValue(DEVIATION_INT));
        mainView.showParameter(MAX_ACTIVE, repositoryConfiguration.getParameterValue(MAX_ACTIVE));
        mainView.showParameter(UPOWER, repositoryConfiguration.getParameterValue(UPOWER));
        mainView.showParameter(BASE_POS, repositoryConfiguration.getParameterValue(BASE_POS));
    }

    // ---------------------------------------------------------------------------------------------
    // ConfigMainFragment events
    @Override
    public void onBtnRcvColdStartClick() {
        bluetoothService.sendData("@rcv coldstart");
    }

    @Override
    public void onEtLongDeviationFocusChanged(boolean hasFocus) {
        if (!hasFocus) repositoryConfiguration.setParameterWithoutCallback(LONG_DEVIATION, mainView.getEtLongDeviationText());
    }

    @Override
    public void onEtLatDeviationFocusChanged(boolean hasFocus) {
        if (!hasFocus) repositoryConfiguration.setParameterWithoutCallback(LAT_DEVIATION, mainView.getEtLatDeviationText());
    }

    @Override
    public void onEtHdopFocusChanged(boolean hasFocus) {
        if (!hasFocus) repositoryConfiguration.setParameterWithoutCallback(HDOP, mainView.getEtHdopText());
    }

    @Override
    public void onEtFixDelayFocusChanged(boolean hasFocus) {
        if (!hasFocus) repositoryConfiguration.setParameterWithoutCallback(FIX_DELAY, mainView.getEtFixDelayText());
    }

    @Override
    public void onSpinSatelliteSystemItemSelected(int position) {
        repositoryConfiguration.setParameterWithoutCallback(SATELLITE_SYSTEM, String.valueOf(position));
    }

    @Override
    public void onBtnRequestBasePosClick() {
        bluetoothService.sendData("base pos?");
    }

    // Lifecycle
    @Override
    public void onConfigNavigationFragmentStart() {
        mainView.showParameter(LONG_DEVIATION, repositoryConfiguration.getParameterValue(LONG_DEVIATION));
        mainView.showParameter(LAT_DEVIATION, repositoryConfiguration.getParameterValue(LAT_DEVIATION));
        mainView.showParameter(HDOP, repositoryConfiguration.getParameterValue(HDOP));
        mainView.showParameter(FIX_DELAY, repositoryConfiguration.getParameterValue(FIX_DELAY));
        mainView.showParameter(SATELLITE_SYSTEM, repositoryConfiguration.getParameterValue(SATELLITE_SYSTEM));
    }


    // ---------------------------------------------------------------------------------------------
    // ConfigMainFragment events
    @Override
    public void onBtnAlarmEventsClick() {
        String events = mainView.getCheckedAlarmEvents();
        if (!events.isEmpty()) bluetoothService.sendData("alarm events=" + events);
    }

    @Override
    public void onEventsMaskCbClick() {
        repositoryConfiguration.setParameterWithoutCallback(EVENTS_MASK, mainView.getCheckedEventsMask());
    }

    // Lifecycle
    // TODO <ДОБАВИТЬ ПАРАМЕТР>
    @Override
    public void OnConfigEventsFragmentStart() {
        mainView.showParameter(EVENTS_MASK, repositoryConfiguration.getParameterValue(EVENTS_MASK));
    }



    // ---------------------------------------------------------------------------------------------
    // ConsoleFragment events
    @Override
    public void onBtnSendCommandClick() {
        String commandLineText = mainView.getCommandLineText();
        bluetoothService.sendData(commandLineText);
    }

    // Lifecycle
    @Override
    public void onConsoleFragmentStart() {
        mainView.setNavigationPosition(MainActivity.CONSOLE_FRAGMENT);
    }

    @Override
    public void onConsoleFragmentCreateView() {
        mainView.showLog(logs.getLogsMessages());
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

    @Override
    public void onMainActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MainActivity.FILE_MANAGER_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    repositoryConfiguration.setUiConfiguration(fileManager.openConfiguration(Objects.requireNonNull(data.getData()).getPath()));
                }
        }
    }

    // Lifecycle
    @Override
    public void onMainActivityCreate() {
        mainView.showFragment(Contract.View.BLUETOOTH_FRAGMENT);
    }

    @Override
    public void onMainActivityDestroy() {
        mainView.unregisterBluetoothBroadcastReceiver(bluetoothBroadcastReceiver);
        if (bluetoothService != null) {
            bluetoothService.closeAllConnections();
        }
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
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            mainView.showToast("Логи скопированы в буфер обмена");
        }
    }

    // ---------------------------------------------------------------------------------------------
    // BT events
    @Override
    public void onBluetoothServiceStateOn() {
        mainView.setSwitchBtState(bluetoothService.isEnabled());
        // mainView.showPairedDevices();
        mainView.showDevices();
        mainView.updatePairedDevices();
        mainView.updateAvailableDevices();

    }

    @Override
    public void onBluetoothServiceStateOff() {
        mainView.setSwitchBtState(bluetoothService.isEnabled());
        mainView.hideDevices();
        bluetoothService.closeAllConnections();
    }

    @Override
    public void onStateConnected(BluetoothDevice device) {
        mainView.showToast("Подключено устройство " + device.getName());
    }

    @Override
    public void onBluetoothServiceActionFound(BluetoothDevice bluetoothDevice) {
        bluetoothService.addAvailableDevice(bluetoothDevice);
        String name = bluetoothDevice.getName();
        String address = bluetoothDevice.getAddress();
        Log.d(TAG, "onBluetoothServiceActionFound: name = " + name + ", address = " + address);
        mainView.updateAvailableDevices();
    }

    @Override
    public void onSetParameter(Parameter parameter) {
        mainView.showParameter(parameter.getName(), parameter.getValue());
    }

    @Override
    public String toString() {
        return "Presenter";
    }

    @Override
    public void onStartLoading(int size) {
        mainView.showLoadingProgress(size);
    }

    @Override
    public void onNextCommand(int commandNumber) {
        mainView.setLoadingProgress(commandNumber);
    }

//    @Override
//    public void onEndOfLoading() {
//        mainView.hideLoadingProgress();
//    }

    @Override
    public void onConnectingTo(String name) {
        mainView.showToast("Подключение к " + name);
    }

    @Override
    public void onErrorToConnect() {
        mainView.showToast("Не удалось подключиться");
    }
}
