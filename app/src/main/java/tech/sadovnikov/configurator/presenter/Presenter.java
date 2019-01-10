//package tech.sadovnikov.configurator.presenter;
//
//import android.app.Activity;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.content.ClipData;
//import android.content.ClipboardManager;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Message;
//import android.support.annotation.NonNull;
//import android.util.Log;
//import android.view.MenuItem;
//import android.widget.EditText;
//
//import java.util.HashMap;
//import java.util.Objects;
//
//import tech.sadovnikov.configurator.Contract;
//import tech.sadovnikov.configurator.R;
//import tech.sadovnikov.configurator.model.BluetoothBroadcastReceiver;
//import tech.sadovnikov.configurator.model.BluetoothService;
//import tech.sadovnikov.configurator.model.DeviceLogs;
//import tech.sadovnikov.configurator.model.RepositoryConfiguration;
//import tech.sadovnikov.configurator.ui.MainActivity;
//import tech.sadovnikov.configurator.ui.adapter.AvailableDevicesItemView;
//import tech.sadovnikov.configurator.ui.adapter.PairedDevicesItemView;
//
//import static tech.sadovnikov.configurator.entities.Configuration.ALARM_INT;
//import static tech.sadovnikov.configurator.entities.Configuration.ANSW_NUMBER;
//import static tech.sadovnikov.configurator.entities.Configuration.APN;
//import static tech.sadovnikov.configurator.entities.Configuration.BASE_POS;
//import static tech.sadovnikov.configurator.entities.Configuration.BLINKER_BRIGHTNESS;
//import static tech.sadovnikov.configurator.entities.Configuration.BLINKER_LX;
//import static tech.sadovnikov.configurator.entities.Configuration.BLINKER_MODE;
//import static tech.sadovnikov.configurator.entities.Configuration.CMD_NUMBER;
//import static tech.sadovnikov.configurator.entities.Configuration.CONNECT_ATTEMPTS;
//import static tech.sadovnikov.configurator.entities.Configuration.CURRENT_POS;
//import static tech.sadovnikov.configurator.entities.Configuration.DELIV_TIMEOUT;
//import static tech.sadovnikov.configurator.entities.Configuration.DEVIATION_INT;
//import static tech.sadovnikov.configurator.entities.Configuration.EVENTS_MASK;
//import static tech.sadovnikov.configurator.entities.Configuration.FIRMWARE_VERSION;
//import static tech.sadovnikov.configurator.entities.Configuration.FIX_DELAY;
//import static tech.sadovnikov.configurator.entities.Configuration.HDOP;
//import static tech.sadovnikov.configurator.entities.Configuration.ID;
//import static tech.sadovnikov.configurator.entities.Configuration.IMPACT_POW;
//import static tech.sadovnikov.configurator.entities.Configuration.LAT_DEVIATION;
//import static tech.sadovnikov.configurator.entities.Configuration.LOGIN;
//import static tech.sadovnikov.configurator.entities.Configuration.LONG_DEVIATION;
//import static tech.sadovnikov.configurator.entities.Configuration.MAX_ACTIVE;
//import static tech.sadovnikov.configurator.entities.Configuration.MAX_DEVIATION;
//import static tech.sadovnikov.configurator.entities.Configuration.NORMAL_INT;
//import static tech.sadovnikov.configurator.entities.Configuration.PACKETS;
//import static tech.sadovnikov.configurator.entities.Configuration.PACKET_TOUT;
//import static tech.sadovnikov.configurator.entities.Configuration.PASSWORD;
//import static tech.sadovnikov.configurator.entities.Configuration.PRIORITY_CHNL;
//import static tech.sadovnikov.configurator.entities.Configuration.SATELLITE_SYSTEM;
//import static tech.sadovnikov.configurator.entities.Configuration.SERVER;
//import static tech.sadovnikov.configurator.entities.Configuration.SESSION_TIME;
//import static tech.sadovnikov.configurator.entities.Configuration.SIM_ATTEMPTS;
//import static tech.sadovnikov.configurator.entities.Configuration.SMS_CENTER;
//import static tech.sadovnikov.configurator.entities.Configuration.TILT_ANGLE;
//import static tech.sadovnikov.configurator.entities.Configuration.TRUE_POS;
//import static tech.sadovnikov.configurator.entities.Configuration.UPOWER;
//import static tech.sadovnikov.configurator.entities.Configuration.UPOWER_THLD;
//import static tech.sadovnikov.configurator.model.StreamAnalyzer.WHAT_COMMAND_DATA;
//import static tech.sadovnikov.configurator.model.StreamAnalyzer.WHAT_MAIN_LOG;
//import static tech.sadovnikov.configurator.presenter.Loader.WHAT_LOADING_END;
//import static tech.sadovnikov.configurator.presenter.UiHandler.WHAT_CONNECTING_ERROR;
//
//public class Presenter implements Contract.Presenter, RepositoryConfiguration.OnRepositoryConfigurationEventsListener, Loader.OnLoaderEventsListener,
//        BluetoothBroadcastReceiver.OnBluetoothBroadcastReceiverEventsListener, BluetoothService.OnBluetoothServiceEventsListener,
//        FileManager.FileManagerListener {
//    private static final String TAG = "Presenter";
//
//    private Contract.View mainView;
//    private Contract.DeviceLogs logs;
//    private Contract.RepositoryConfiguration repositoryConfiguration;
//
//    private BluetoothService bluetoothService;
//    private BluetoothBroadcastReceiver bluetoothBroadcastReceiver;
//    private Loader loader;
//    private FileManager fileManager;
//
//    public Presenter(Contract.View mainView) {
//        Log.v(TAG, "onConstructor");
//        this.mainView = mainView;
//        logs = new DeviceLogs(this);
//        UiHandler uiHandler = new UiHandler((Activity) mainView, this);
//        bluetoothService = new BluetoothService(this, uiHandler);
//        bluetoothBroadcastReceiver = new BluetoothBroadcastReceiver(this);
//        loader = new Loader(this, bluetoothService, uiHandler);
//        fileManager = new FileManager(this);
//        repositoryConfiguration = new RepositoryConfiguration(this);
//        registerBluetoothBroadcastReceiver((Context) mainView);
//    }
//
//
//    // Регистрация ресиверов
//    private void registerBluetoothBroadcastReceiver(Context getContext) {
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
//        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
//        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
//        intentFilter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
//        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
//        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
//        getContext.registerReceiver(bluetoothBroadcastReceiver, intentFilter);
//    }
//
//    @Override
//    public void onHandleMessage(Message msg) {
//        Object obj = msg.obj;
//        switch (msg.what) {
//            // Отправка полученных данных в logs
//            case WHAT_MAIN_LOG:
//                String message = (String) obj;
//                logs.addLine(message);
//                break;
//            //
//            case WHAT_COMMAND_DATA:
//                HashMap msgData = (HashMap) obj;
//                String value = (String) msgData.get(StreamAnalyzer.PARAMETER_VALUE);
//                String name = (String) msgData.get(StreamAnalyzer.PARAMETER_NAME);
//                repositoryConfiguration.setParameter(name, value);
//                onReceiveCommand();
//                break;
//            case WHAT_LOADING_END:
//                mainView.hideLoadingProgress();
//                break;
//            case WHAT_CONNECTING_ERROR:
//                mainView.showToast("Не удалось выполнить подключение к устройству");
//                break;
//        }
//    }
//
//    private void onReceiveCommand() {
//        loader.nextCommand();
//    }
//
//
//    // ---------------------------------------------------------------------------------------------
//    // MainView events
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.navigation_bluetooth:
//                mainView.showFragment(Contract.View.BLUETOOTH_FRAGMENT);
//                return true;
//            case R.id.navigation_configuration:
//                mainView.showFragment(Contract.View.CONFIGURATION_FRAGMENT);
//                return true;
//            case R.id.navigation_console:
//                mainView.showFragment(Contract.View.CONSOLE_FRAGMENT);
//                return true;
//        }
//        return false;
//
//    }
//
//    @Override
//    public void onMainActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case MainActivity.OPEN_FILE_MANAGER_REQUEST_CODE:
//                if (resultCode == Activity.RESULT_OK) {
//                    repositoryConfiguration.setUiConfiguration(fileManager.openConfiguration(Objects.requireNonNull(data.getData()).getPath()));
//                }
//            case MainActivity.SAVE_FILE_MANAGER_REQUEST_CODE:
//                if (resultCode == Activity.RESULT_OK) {
//                    // repositoryConfiguration.setUiConfiguration(fileManager.openConfiguration(Objects.requireNonNull(data.getData()).getPath()));
//                    fileManager.saveConfiguration(repositoryConfiguration.getConfigurationForSave(),
//                            Objects.requireNonNull(data.getData()).getPath());
//
//                }
//        }
//    }
//
//    @Override
//    public void onCreateOptionsMenu() {
//        if (mainView.isBluetoothFragmentResumed()) {
//            mainView.hideConfigActionsMenuGroup();
//        } else {
//            mainView.showConfigActionsMenuGroup();
//        }
//        if (mainView.isAvailableDevicesFragmentResumed()) {
//            int page = mainView.getSelectedPageOfViewPager();
//            if (page == 1) mainView.showItemUpdateAvailableDevices();
//        } else {
//            mainView.hideItemUpdateAvailableDevices();
//        }
//    }
//
//    @Override
//    public void onPositiveRequestReadExternalStoragePermissionRequestResult() {
//        mainView.startOpenFileManagerActivity();
//    }
//
//    @Override
//    public void onNegativeRequestReadExternalStoragePermissionRequestResult() {
//
//    }
//
//    @Override
//    public void onPositiveRequestWriteExternalStoragePermissionRequestResult() {
//        mainView.showSaveFileDialog();
//        // fileManager.saveConfiguration(repositoryConfiguration.getConfigurationForSave());
//    }
//
//    @Override
//    public void onSaveFileDialogPositiveClick(String fileName) {
//        fileManager.saveConfiguration(repositoryConfiguration.getConfigurationForSave(), fileName);
//
//    }
//
//    @Override
//    public void onNegativeRequestWriteExternalStoragePermissionRequestResult() {
//
//    }
//
//    @Override
//    public void onPositiveRequestAccessCoarseLocationPermissionRequestResult() {
//        bluetoothService.startDiscovery();
//    }
//
//    @Override
//    public void onNegativeRequestAccessCoarseLocationPermissionRequestResult() {
//
//    }
//
//    // Lifecycle
//    @Override
//    public void onMainActivityCreate() {
//        mainView.showFragment(Contract.View.BLUETOOTH_FRAGMENT);
//    }
//
//    @Override
//    public void onMainActivityDestroy() {
//        mainView.unregisterBluetoothBroadcastReceiver(bluetoothBroadcastReceiver);
//        if (bluetoothService != null) bluetoothService.closeAllConnections();
//    }
//
//
//    // ---------------------------------------------------------------------------------------------
//    // BluetoothFragment events
//    @Override
//    public void onSwitchBtStateChanged(boolean state) {
//        // DeviceLogs.d(TAG, "onSwitchBtStateChanged" + String.valueOf(state));
//        if (state) {
//            bluetoothService.enableBt();
//        } else {
//            bluetoothService.disableBt();
//        }
//    }
//
//    @Override
//    public void onDevicesPageSelected(int position) {
//        if (position == 0) {
//            bluetoothService.cancelDiscovery();
//            mainView.updatePairedDevices();
//            mainView.hideItemUpdateAvailableDevices();
//        } else if (position == 1) {
//            mainView.startBluetoothDiscoveryWithRequestPermission();
//            mainView.showItemUpdateAvailableDevices();
//        }
//    }
//
//    @Override
//    public void onPairedDevicesRvItemClick(String bluetoothDeviceAddress) {
//        // DeviceLogs.d(TAG, "Ща будем подключаться к " + bluetoothDevice.getName());
//        bluetoothService.connectTo(bluetoothDeviceAddress);
//    }
//
//    @Override
//    public void onAvailableDevicesRvItemClicked(String bluetoothDeviceAddress) {
//        bluetoothService.connectTo(bluetoothDeviceAddress);
//    }
//
//    // Lifecycle
//    @Override
//    public void onBluetoothFragmentCreateView() {
//    }
//
//    @Override
//    public void onBluetoothFragmentStart() {
//        mainView.setTitle("Bluetooth");
//        mainView.hideConfigActionsMenuGroup();
//        mainView.setSwitchBtState(bluetoothService.isEnabled());
//        mainView.setNavigationPosition(Contract.View.BLUETOOTH_FRAGMENT);
//        if (bluetoothService.isEnabled()) {
//            mainView.setSwitchBtText("Включено");
//            // mainView.showDevices();
//            mainView.openDevices();
//        } else {
//            mainView.setSwitchBtText("Выключено");
//            // mainView.hideDevices();
//            mainView.closeDevices();
//        }
//    }
//
//    @Override
//    public void onBluetoothFragmentDestroyView() {
//        mainView.showConfigActionsMenuGroup();
//    }
//
//    @Override
//    public void onBluetoothFragmentDestroy() {
//
//    }
//
//    @Override
//    public void onAvailableDevicesFragmentStart() {
//    }
//
//    @Override
//    public void onAvailableDevicesFragmentPause() {
//        mainView.hideItemUpdateAvailableDevices();
//    }
//
//    @Override
//    public void onAvailableDevicesFragmentResume() {
//        int page = mainView.getSelectedPageOfViewPager();
//        if (page == 1) {
//            mainView.showItemUpdateAvailableDevices();
//        }
//    }
//
//    @Override
//    public void onItemUpdateAvailableDevicesClick() {
//        bluetoothService.cancelDiscovery();
//        bluetoothService.startDiscovery();
//    }
//
//    @Override
//    public void onAvailableDevicesFragmentDestroyView() {
//        bluetoothService.cancelDiscovery();
//    }
//
//    // Recycler bluetooth devices events
//    @Override
//    public void onBindViewHolderOfPairedDevicesRvAdapter(PairedDevicesItemView holder, int position) {
//        holder.setDeviceName(bluetoothService.getBondedDevices().get(position).getName());
//        holder.setDeviceAddress(bluetoothService.getBondedDevices().get(position).getAddress());
//    }
//
//    @Override
//    public int onGetItemCountOfPairedDevicesRvAdapter() {
//        return bluetoothService.getBondedDevices().size();
//    }
//
//    @Override
//    public void onBindViewHolderOfAvailableDevicesRvAdapter(AvailableDevicesItemView holder, int position) {
//        holder.setDeviceName(bluetoothService.getAvailableDevices().get(position).getName());
//        holder.setDeviceAddress(bluetoothService.getAvailableDevices().get(position).getAddress());
//    }
//
//    @Override
//    public int onGetItemCountOfAvailableDevicesRvAdapter() {
//        return bluetoothService.getAvailableDevices().size();
//    }
//
//    // ---------------------------------------------------------------------------------------------
//    // ConfigurationFragment events
//    @Override
//    public void onConfigTabsRvItemClick(String tab) {
//        mainView.showFragment(tab);
//    }
//
//    @Override
//    public void onConfigurationOptionsItemSelected(MenuItem item) {
//        int itemId = item.getItemId();
//        if (itemId == R.id.item_set) {
//            // TODO? <Что делать со спинерами, когда эти параметр не трогали?>
//            // TODO <Поставить условие на подключение к устройству>
//            // TODO <Показать бегунок загрузки>
//            loader.loadCommandList(repositoryConfiguration.getCommandListForSetConfiguration());
//        } else if (itemId == R.id.item_load) {
//            // TODO <Поставить условие на подключение к устройству>
//            // TODO <Показать бегунок загрузки>
//            loader.loadCommandList(repositoryConfiguration.getCommandListForReadConfiguration());
//        } else if (itemId == R.id.item_open) {
//            mainView.startOpenFileManagerActivityWithRequestPermission();
//        } else if (itemId == R.id.item_save) {
//            mainView.startSaveFileActivityWithRequestPermission();
//        } else if (itemId == R.id.item_update_available_devices){
//            mainView.updateAvailableDevices();
//        }
//    }
//
//    // Lifecycle
//    @Override
//    public void onConfigurationFragmentStart() {
//        mainView.setNavigationPosition(MainActivity.CONFIGURATION_FRAGMENT);
//        mainView.setTitle("Конфигурация");
//    }
//
//
//    // ---------------------------------------------------------------------------------------------
//    // ConfigBuoyFragment events
//    @Override
//    public void onBtnRestartClick() {
//        bluetoothService.sendData("@restart");
//    }
//
//    @Override
//    public void onBtnDefaultSettingsClick() {
//        bluetoothService.sendData("@reset settings");
//    }
//
//    @Override
//    public void onEtIdFocusChange(boolean hasFocus) {
//        if (!hasFocus) repositoryConfiguration.setParameterFromUi(ID, mainView.getEtIdText());
//    }
//
//    // Lifecycle
//    @Override
//    public void onConfigBuoyFragmentStart() {
//        mainView.setTitle("Конфигурация");
//        mainView.setNavigationPosition(MainActivity.CONFIGURATION_FRAGMENT);
//        mainView.showParameter(ID, repositoryConfiguration.getParameterValue(ID));
//        mainView.showParameter(FIRMWARE_VERSION, repositoryConfiguration.getParameterValue(FIRMWARE_VERSION));
//    }
//
//
//    // ---------------------------------------------------------------------------------------------
//    // ConfigMainFragment events
//    @Override
//    public void onSpinBlinkerModeItemSelected(int position) {
//        switch (position) {
//            case 0:
//                repositoryConfiguration.setParameterFromUi(BLINKER_MODE, "");
//                break;
//            case 1:
//                repositoryConfiguration.setParameterFromUi(BLINKER_MODE, String.valueOf(0));
//                break;
//            case 2:
//                repositoryConfiguration.setParameterFromUi(BLINKER_MODE, String.valueOf(1));
//                break;
//            case 3:
//                repositoryConfiguration.setParameterFromUi(BLINKER_MODE, String.valueOf(2));
//                break;
//            case 4:
//                repositoryConfiguration.setParameterFromUi(BLINKER_MODE, String.valueOf(3));
//                break;
//            case 5:
//                repositoryConfiguration.setParameterFromUi(BLINKER_MODE, String.valueOf(6));
//                break;
//            case 6:
//                repositoryConfiguration.setParameterFromUi(BLINKER_MODE, String.valueOf(5));
//                break;
//            case 7:
//                repositoryConfiguration.setParameterFromUi(BLINKER_MODE, String.valueOf(4));
//                break;
//            case 8:
//                repositoryConfiguration.setParameterFromUi(BLINKER_MODE, String.valueOf(7));
//                break;
//        }
//    }
//
//    @Override
//    public void onSpinBlinkerBrightnessItemSelected(int position) {
//        switch (position) {
//            case 0:
//                repositoryConfiguration.setParameterFromUi(BLINKER_BRIGHTNESS, "");
//                break;
//            case 1:
//                repositoryConfiguration.setParameterFromUi(BLINKER_BRIGHTNESS, String.valueOf(0));
//                break;
//            case 2:
//                repositoryConfiguration.setParameterFromUi(BLINKER_BRIGHTNESS, String.valueOf(1));
//                break;
//        }
//    }
//
//    @Override
//    public void onEtBlinkerLxFocusChange(boolean hasFocus) {
//        repositoryConfiguration.setParameterFromUi(BLINKER_LX, mainView.getEtBlinkerLxText());
//    }
//
//    @Override
//    public void onBtnClearArchiveClick() {
//        bluetoothService.sendData("@clear archive");
//    }
//
//    @Override
//    public void onBtnCloseConnectClick() {
//        bluetoothService.sendData("close connect");
//    }
//
//    @Override
//    public void onEtMaxDeviationFocusChange(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(MAX_DEVIATION, mainView.getEtMaxDeviationText());
//    }
//
//    @Override
//    public void onEtTiltAngleFocusChange(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(TILT_ANGLE, mainView.getEtTiltDeviationText());
//    }
//
//    @Override
//    public void onEtImpactPowFocusChange(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(IMPACT_POW, mainView.getEtImpactPowText());
//    }
//
//    @Override
//    public void onEtUpowerThldFocusChange(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(UPOWER_THLD, mainView.getEtUpowerThldText());
//    }
//
//    @Override
//    public void onEtDeviationIntFocusChange(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(DEVIATION_INT, mainView.getEtDeviationIntText());
//    }
//
//    @Override
//    public void onEtMaxActiveFocusChange(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(MAX_ACTIVE, mainView.getEtMaxActiveText());
//    }
//
//    // Lifecycle
//    @Override
//    public void onConfigMainFragmentStart() {
//        mainView.setTitle("Конфигурация");
//        mainView.setNavigationPosition(MainActivity.CONFIGURATION_FRAGMENT);
//        mainView.showParameter(BLINKER_MODE, repositoryConfiguration.getParameterValue(BLINKER_MODE));
//        mainView.showParameter(BLINKER_BRIGHTNESS, repositoryConfiguration.getParameterValue(BLINKER_BRIGHTNESS));
//        mainView.showParameter(BLINKER_LX, repositoryConfiguration.getParameterValue(BLINKER_LX));
//        mainView.showParameter(MAX_DEVIATION, repositoryConfiguration.getParameterValue(MAX_DEVIATION));
//        mainView.showParameter(TILT_ANGLE, repositoryConfiguration.getParameterValue(TILT_ANGLE));
//        mainView.showParameter(IMPACT_POW, repositoryConfiguration.getParameterValue(IMPACT_POW));
//        mainView.showParameter(UPOWER_THLD, repositoryConfiguration.getParameterValue(UPOWER_THLD));
//        mainView.showParameter(DEVIATION_INT, repositoryConfiguration.getParameterValue(DEVIATION_INT));
//        mainView.showParameter(MAX_ACTIVE, repositoryConfiguration.getParameterValue(MAX_ACTIVE));
//        mainView.showParameter(UPOWER, repositoryConfiguration.getParameterValue(UPOWER));
//        mainView.showParameter(BASE_POS, repositoryConfiguration.getParameterValue(BASE_POS));
//    }
//
//    // ---------------------------------------------------------------------------------------------
//    // ConfigNavigationFragment events
//    @Override
//    public void onCbTruePosClick() {
//        repositoryConfiguration.setParameterFromUi(TRUE_POS, mainView.getStateCbTruePos());
//    }
//
//    @Override
//    public void onEtBaseLongitudeFocusChange(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(BASE_POS, mainView.getBasePos());
//    }
//
//    @Override
//    public void onEtBaseLatitudeFocusChange(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(BASE_POS, mainView.getBasePos());
//    }
//
//    @Override
//    public void onLlParameterClick(EditText editText) {
//        mainView.setFocusOnEt(editText);
//    }
//
//
//    @Override
//    public void onBtnRcvColdStartClick() {
//        bluetoothService.sendData("@rcv coldstart");
//    }
//
//    @Override
//    public void onEtLongDeviationFocusChanged(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(LONG_DEVIATION, mainView.getEtLongDeviationText());
//    }
//
//    @Override
//    public void onEtLatDeviationFocusChanged(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(LAT_DEVIATION, mainView.getEtLatDeviationText());
//    }
//
//    @Override
//    public void onEtHdopFocusChanged(boolean hasFocus) {
//        if (!hasFocus) repositoryConfiguration.setParameterFromUi(HDOP, mainView.getEtHdopText());
//    }
//
//    @Override
//    public void onEtFixDelayFocusChanged(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(FIX_DELAY, mainView.getEtFixDelayText());
//    }
//
//    @Override
//    public void onSpinSatelliteSystemItemSelected(int position) {
//        switch (position) {
//            case 0:
//                repositoryConfiguration.setParameterFromUi(SATELLITE_SYSTEM, "");
//                break;
//            case 1:
//                repositoryConfiguration.setParameterFromUi(SATELLITE_SYSTEM, String.valueOf(0));
//                break;
//            case 2:
//                repositoryConfiguration.setParameterFromUi(SATELLITE_SYSTEM, String.valueOf(1));
//                break;
//            case 3:
//                repositoryConfiguration.setParameterFromUi(SATELLITE_SYSTEM, String.valueOf(2));
//                break;
//        }
//    }
//
//    @Override
//    public void onBtnRequestBasePosClick() {
//        bluetoothService.sendData("base pos?");
//    }
//
//    @Override
//    public void onBtnShowMapCurrentPosClick() {
//        mainView.startMapActivity(mainView.getEtLongitude(), mainView.getEtLatitude());
//    }
//
//    @Override
//    public void onBtnShowMapBasePosClick() {
//        mainView.startMapActivity(mainView.getEtBaseLongitude(), mainView.getEtBaseLatitude());
//    }
//
//    // Lifecycle
//    @Override
//    public void onConfigNavigationFragmentStart() {
//        mainView.setTitle("Конфигурация");
//        mainView.showParameter(CURRENT_POS, repositoryConfiguration.getParameterValue(CURRENT_POS));
//        mainView.showParameter(TRUE_POS, repositoryConfiguration.getParameterValue(TRUE_POS));
//        mainView.showParameter(BASE_POS, repositoryConfiguration.getParameterValue(BASE_POS));
//        mainView.showParameter(LONG_DEVIATION, repositoryConfiguration.getParameterValue(LONG_DEVIATION));
//        mainView.showParameter(LAT_DEVIATION, repositoryConfiguration.getParameterValue(LAT_DEVIATION));
//        mainView.showParameter(HDOP, repositoryConfiguration.getParameterValue(HDOP));
//        mainView.showParameter(FIX_DELAY, repositoryConfiguration.getParameterValue(FIX_DELAY));
//        mainView.showParameter(SATELLITE_SYSTEM, repositoryConfiguration.getParameterValue(SATELLITE_SYSTEM));
//    }
//
//
//    // ---------------------------------------------------------------------------------------------
//    // ConfigEventsFragment events
//    @Override
//    public void onBtnAlarmEventsClick() {
//        String events = mainView.getCheckedAlarmEvents();
//        if (!events.isEmpty()) bluetoothService.sendData("alarm events=" + events);
//    }
//
//    @Override
//    public void onEventsMaskCbClick() {
//        repositoryConfiguration.setParameterFromUi(EVENTS_MASK, mainView.getCheckedEventsMask());
//    }
//
//    // Lifecycle
//    @Override
//    public void OnConfigEventsFragmentStart() {
//        mainView.setTitle("Конфигурация");
//        mainView.showParameter(EVENTS_MASK, repositoryConfiguration.getParameterValue(EVENTS_MASK));
//    }
//
//
//    // ---------------------------------------------------------------------------------------------
//    // ConfigServerFragment events
//    @Override
//    public void onEtServerFocusChange(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(SERVER, mainView.getEtServerText());
//    }
//
//    @Override
//    public void onEtConnectAttemptsFocusChange(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(CONNECT_ATTEMPTS, mainView.getEtConnectAttemptsText());
//    }
//
//    @Override
//    public void onEtSessionTimeFocusChange(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(SESSION_TIME, mainView.getEtSessionTimeText());
//    }
//
//    @Override
//    public void onEtPacketToutFocusChange(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(PACKET_TOUT, mainView.getEtPacketToutText());
//    }
//
//    @Override
//    public void onSpinPriorityChnlItemClick(int position) {
//        switch (position) {
//            case 0:
//                repositoryConfiguration.setParameterFromUi(PRIORITY_CHNL, "");
//                break;
//            case 1:
//                repositoryConfiguration.setParameterFromUi(PRIORITY_CHNL, String.valueOf(0));
//                break;
//            case 2:
//                repositoryConfiguration.setParameterFromUi(PRIORITY_CHNL, String.valueOf(1));
//                break;
//        }
//    }
//
//    @Override
//    public void onEtNormalIntFocusChange(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(NORMAL_INT, mainView.getEtNormalIntText());
//    }
//
//    @Override
//    public void onEtAlarmIntFocusChange(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(ALARM_INT, mainView.getEtAlarmIntText());
//    }
//
//    @Override
//    public void onEtSmsCenterFocusChange(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(SMS_CENTER, mainView.getEtSmsCenterText());
//    }
//
//    @Override
//    public void onEtCmdNumberFocusChange(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(CMD_NUMBER, mainView.getEtCmdNumberText());
//    }
//
//    @Override
//    public void onEtAnswNumberFocusChange(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(ANSW_NUMBER, mainView.getEtAnswNumberText());
//    }
//
//    // Lifecycle
//    @Override
//    public void onConfigServerFragmentStart() {
//        mainView.setTitle("Конфигурация");
//        mainView.showParameter(SERVER, repositoryConfiguration.getParameterValue(SERVER));
//        mainView.showParameter(CONNECT_ATTEMPTS, repositoryConfiguration.getParameterValue(CONNECT_ATTEMPTS));
//        mainView.showParameter(SESSION_TIME, repositoryConfiguration.getParameterValue(SESSION_TIME));
//        mainView.showParameter(PACKET_TOUT, repositoryConfiguration.getParameterValue(PACKET_TOUT));
//        mainView.showParameter(PRIORITY_CHNL, repositoryConfiguration.getParameterValue(PRIORITY_CHNL));
//        mainView.showParameter(NORMAL_INT, repositoryConfiguration.getParameterValue(NORMAL_INT));
//        mainView.showParameter(ALARM_INT, repositoryConfiguration.getParameterValue(ALARM_INT));
//        mainView.showParameter(SMS_CENTER, repositoryConfiguration.getParameterValue(SMS_CENTER));
//        mainView.showParameter(CMD_NUMBER, repositoryConfiguration.getParameterValue(CMD_NUMBER));
//        mainView.showParameter(ANSW_NUMBER, repositoryConfiguration.getParameterValue(ANSW_NUMBER));
//        mainView.showParameter(PACKETS, repositoryConfiguration.getParameterValue(PACKETS));
//    }
//
//
//    // ---------------------------------------------------------------------------------------------
//    // ConfigSimCardFragment events
//    @Override
//    public void onEtApnFocusChange(boolean hasFocus) {
//        if (!hasFocus) repositoryConfiguration.setParameterFromUi(APN, mainView.getEtApnText());
//    }
//
//    @Override
//    public void onEtLoginFocusChange(boolean hasFocus) {
//        if (!hasFocus) repositoryConfiguration.setParameterFromUi(LOGIN, mainView.getEtLoginText());
//    }
//
//    @Override
//    public void onEtPasswordFocusChange(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(PASSWORD, mainView.getEtPasswordText());
//    }
//
//    @Override
//    public void onBtnDefaultApnClick() {
//        bluetoothService.sendData("apn=\"\"");
//    }
//
//    @Override
//    public void onBtnDefaultLoginClick() {
//        bluetoothService.sendData("login=\"\"");
//    }
//
//    @Override
//    public void onBtnDefaultPasswordClick() {
//        bluetoothService.sendData("password=\"\"");
//    }
//
//    @Override
//    public void onBtnClearApnClick() {
//        bluetoothService.sendData("apn=\'\'");
//    }
//
//    @Override
//    public void onBtnClearLoginClick() {
//        bluetoothService.sendData("login=\'\'");
//    }
//
//    @Override
//    public void onBtnClearPasswordClick() {
//        bluetoothService.sendData("password=\'\'");
//    }
//
//    @Override
//    public void onBtnEnterPinClick() {
//        bluetoothService.sendData("pin=" + mainView.getEtPinText());
//    }
//
//    @Override
//    public void onBtnClearPinClick() {
//        bluetoothService.sendData("clear pin=" + mainView.getEtPinText());
//    }
//
//    @Override
//    public void onEtSimAttemptsFocusChange(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(SIM_ATTEMPTS, mainView.getEtSimAttemptsText());
//    }
//
//    @Override
//    public void onEtDelivTimeoutFocusChange(boolean hasFocus) {
//        if (!hasFocus)
//            repositoryConfiguration.setParameterFromUi(DELIV_TIMEOUT, mainView.getEtDelivTimeoutText());
//    }
//
//    // Lifecycle
//    @Override
//    public void onConfigSimCardFragmentStart() {
//        mainView.setTitle("Конфигурация");
//        mainView.showParameter(APN, repositoryConfiguration.getParameterValue(APN));
//        mainView.showParameter(LOGIN, repositoryConfiguration.getParameterValue(LOGIN));
//        mainView.showParameter(PASSWORD, repositoryConfiguration.getParameterValue(PASSWORD));
//        mainView.showParameter(SIM_ATTEMPTS, repositoryConfiguration.getParameterValue(SIM_ATTEMPTS));
//        mainView.showParameter(DELIV_TIMEOUT, repositoryConfiguration.getParameterValue(DELIV_TIMEOUT));
//    }
//
//
//    // ---------------------------------------------------------------------------------------------
//    // ConsoleFragment events
//    @Override
//    public void onBtnSendCommandClick() {
//        String commandLineText = mainView.getCommandLineText();
//        bluetoothService.sendData(commandLineText);
//    }
//
//    // Lifecycle
//    @Override
//    public void onConsoleFragmentStart() {
//        mainView.setTitle("Консоль");
//        mainView.setNavigationPosition(MainActivity.CONSOLE_FRAGMENT);
//    }
//
//    @Override
//    public void onConsoleFragmentCreateView() {
//        mainView.showLog(logs.getAllMessages());
//    }
//
//    // DeviceLogs events ---------------------------------------------------------------------------------
//    @Override
//    public void onAddLogsLineEvent(String line) {
//        mainView.addLogsLine(line);
//    }
//
//    @Override
//    public void onTvLogsLongClick() {
//        String logsMessages = logs.getAllMessages();
//        ClipboardManager clipboard = (ClipboardManager) ((Activity) mainView).getSystemService(Context.CLIPBOARD_SERVICE);
//        ClipData clip = ClipData.newPlainText("", logsMessages);
//        if (clipboard != null) {
//            clipboard.setPrimaryClip(clip);
//            mainView.showToast("Логи скопированы в буфер обмена");
//        }
//    }
//
//    // ---------------------------------------------------------------------------------------------
//    // BT events
//    @Override
//    public void onBluetoothServiceStateOn() {
//        mainView.setSwitchBtText("Включено");
//        mainView.setSwitchBtState(bluetoothService.isEnabled());
//        // mainView.showDevices();
//        mainView.openDevices();
//        mainView.updatePairedDevices();
//        mainView.updateAvailableDevices();
//
//    }
//
//    @Override
//    public void onBluetoothServiceStateOff() {
//        mainView.setSwitchBtText("Выключено");
//        mainView.setSwitchBtState(bluetoothService.isEnabled());
//        bluetoothService.closeAllConnections();
//        //mainView.hideDevices();
//        mainView.closeDevices();
//    }
//
//    @Override
//    public void onStateConnected(BluetoothDevice device) {
//        mainView.showToast("Подключено устройство " + device.getName());
//    }
//
//    @Override
//    public void onBluetoothServiceActionFound(BluetoothDevice bluetoothDevice) {
//        bluetoothService.addAvailableDevice(bluetoothDevice);
//        mainView.updateAvailableDevices();
//    }
//
//
//    @Override
//    public void onSetParameter(String name, String value) {
//        mainView.showParameter(name, value);
//
//    }
//
//    @NonNull
//    @Override
//    public String toString() {
//        return "Presenter";
//    }
//
//    @Override
//    public void onStartLoading(int size) {
//        mainView.showLoadingProgress(size);
//    }
//
//    @Override
//    public void onNextCommand(int commandNumber, int size) {
//        mainView.setLoadingProgress(commandNumber, size);
//    }
//
//    @Override
//    public void onConnectingTo(String name) {
//        mainView.showToast("Подключение к " + name);
//    }
//
//    @Override
//    public void onErrorToConnect() {
//        mainView.showToast("Не удалось подключиться");
//    }
//
//    @Override
//    public void onSaveConfigurationSuccess(String fileName) {
//        mainView.showToast("Конфигурация " + fileName + "\r\nсохранена в папку \"Загрузки\"(Downloads)");
//    }
//}
