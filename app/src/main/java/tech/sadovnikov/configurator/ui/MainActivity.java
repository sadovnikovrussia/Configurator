//package tech.sadovnikov.configurator.ui;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.design.widget.BottomNavigationView;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Objects;
//
//import tech.sadovnikov.configurator.Contract;
//import tech.sadovnikov.configurator.R;
////import tech.sadovnikov.configurator.presenter.Presenter;
//import tech.sadovnikov.configurator.model.BluetoothBroadcastReceiver;
//import tech.sadovnikov.configurator.ui.bluetooth.BluetoothFragment;
//import tech.sadovnikov.configurator.ui.config_tabs.ConfigBuoyFragment;
//import tech.sadovnikov.configurator.ui.config_tabs.ConfigEventsFragment;
//import tech.sadovnikov.configurator.ui.config_tabs.ConfigMainFragment;
//import tech.sadovnikov.configurator.ui.config_tabs.ConfigNavigationFragment;
//import tech.sadovnikov.configurator.ui.config_tabs.ConfigServerFragment;
//import tech.sadovnikov.configurator.ui.config_tabs.ConfigSimCardFragment;
//import tech.sadovnikov.configurator.ui.configuration.ConfigurationFragment;
//import tech.sadovnikov.configurator.ui.console.ConsoleFragment;
//
//import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
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
//
//
//public abstract class MainActivity extends AppCompatActivity implements Contract.View,
//        BluetoothFragment.OnBluetoothFragmentInteractionListener,
//        ConfigurationFragment.OnConfigurationFragmentInteractionListener,
//        ConsoleFragment.OnConsoleFragmentInteractionListener,
//        ConfigBuoyFragment.OnConfigBuoyFragmentInteractionListener,
//        ConfigMainFragment.OnConfigMainFragmentInteractionListener,
//        ConfigNavigationFragment.OnConfigNavigationFragmentInteractionListener,
//        ConfigEventsFragment.OnConfigEventsFragmentInteractionListener,
//        ConfigServerFragment.OnConfigServerFragmentInteractionListener,
//        ConfigSimCardFragment.OnConfigSimCardFragmentInteractionListener,
//        SaveFileDialogFragment.OnSaveFileDialogFragmentInteractionListener {
//
//    private static final String TAG = MainActivity.class.getSimpleName();
//
//    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
//    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;
//    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 3;
//
//    Contract.Presenter presenter;
//
//    // TODO <Добавить кнопку "Обновить устройства">
//    // UI
//    FrameLayout container;
//    BottomNavigationView navigation;
//    ProgressBar progressBar;
//    Menu actionBarMenu;
//    MenuItem itemUpdateAvailableDevices;
//
//    // Fragments
//    BluetoothFragment bluetoothFragment;
//    ConfigurationFragment configurationFragment;
//    ConfigBuoyFragment configBuoyFragment;
//    ConfigMainFragment configMainFragment;
//    ConfigNavigationFragment configNavigationFragment;
//    ConfigEventsFragment configEventsFragment;
//    ConfigServerFragment configServerFragment;
//    ConfigSimCardFragment configSimCardFragment;
//    ConsoleFragment consoleFragment;
//
//    public MainActivity() {
//        Log.v(TAG, "onConstructor");
//    }
//
//
//    private void initUi() {
//        container = findViewById(R.id.container);
//        progressBar = findViewById(R.id.progressBar);
//        bluetoothFragment = new BluetoothFragment();
//        configurationFragment = new ConfigurationFragment();
//        consoleFragment = new ConsoleFragment();
//        navigation = findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                return presenter.onNavigationItemSelected(item);
//            }
//        });
//
//    }
//
//    // ---------------------------------------------------------------------------------------------
//    // Contract.View
//    @Override
//    public void showParameter(String name, String value) {
//        Log.d(TAG, "showParameter: " + name + "=" + value);
//        switch (name) {
//            case ID:
//                if (configBuoyFragment != null && configBuoyFragment.etId != null)
//                    configBuoyFragment.etId.setText(value);
//                break;
//            case FIRMWARE_VERSION:
//                if (configBuoyFragment != null && configBuoyFragment.etVersion != null)
//                    configBuoyFragment.etVersion.setText(value);
//                break;
//            case BLINKER_MODE:
//                if (configMainFragment != null && configMainFragment.spinBlinkerMode != null) {
//                    if (value.isEmpty()) {
//                        configMainFragment.spinBlinkerMode.setSelection(0);
//                    } else {
//                        switch (value) {
//                            case "0":
//                                configMainFragment.spinBlinkerMode.setSelection(1);
//                                break;
//                            case "1":
//                                configMainFragment.spinBlinkerMode.setSelection(2);
//                                break;
//                            case "2":
//                                configMainFragment.spinBlinkerMode.setSelection(3);
//                                break;
//                            case "3":
//                                configMainFragment.spinBlinkerMode.setSelection(4);
//                                break;
//                            case "4":
//                                configMainFragment.spinBlinkerMode.setSelection(7);
//                                break;
//                            case "5":
//                                configMainFragment.spinBlinkerMode.setSelection(6);
//                                break;
//                            case "6":
//                                configMainFragment.spinBlinkerMode.setSelection(5);
//                                break;
//                            case "7":
//                                configMainFragment.spinBlinkerMode.setSelection(8);
//                                break;
//                        }
//                    }
//                }
//                break;
//            case BLINKER_BRIGHTNESS:
//                if (configMainFragment != null && configMainFragment.spinBlinkerBrightness != null) {
//                    if (value.isEmpty()) {
//                        configMainFragment.spinBlinkerBrightness.setSelection(0);
//                    } else {
//                        switch (value) {
//                            case "0":
//                                configMainFragment.spinBlinkerBrightness.setSelection(1);
//                                break;
//                            case "1":
//                                configMainFragment.spinBlinkerBrightness.setSelection(2);
//                                break;
//                        }
//                    }
//                }
//                break;
//            case BLINKER_LX:
//                if (configMainFragment != null && configMainFragment.etBlinkerLx != null) {
//                    configMainFragment.etBlinkerLx.setText(value);
//                }
//                break;
//            case MAX_DEVIATION:
//                if (configMainFragment != null && configMainFragment.etMaxDeviation != null)
//                    configMainFragment.etMaxDeviation.setText(value);
//                break;
//            case TILT_ANGLE:
//                if (configMainFragment != null && configMainFragment.etTiltAngle != null)
//                    configMainFragment.etTiltAngle.setText(value);
//                break;
//            case IMPACT_POW:
//                if (configMainFragment != null && configMainFragment.etImpactPow != null)
//                    configMainFragment.etImpactPow.setText(value);
//                break;
//            case UPOWER_THLD:
//                if (configMainFragment != null && configMainFragment.etUpowerThld != null)
//                    configMainFragment.etUpowerThld.setText(value);
//                break;
//            case DEVIATION_INT:
//                if (configMainFragment != null && configMainFragment.etDeviationInt != null)
//                    configMainFragment.etDeviationInt.setText(value);
//                break;
//            case MAX_ACTIVE:
//                if (configMainFragment != null && configMainFragment.etMaxActive != null)
//                    configMainFragment.etMaxActive.setText(value);
//                break;
//            case UPOWER:
//                if (configMainFragment != null && configMainFragment.etUpower != null)
//                    configMainFragment.etUpower.setText(value);
//                break;
//            case CURRENT_POS:
//                if (configNavigationFragment != null && configNavigationFragment.etLatitude != null && configNavigationFragment.etLongitude != null) {
//                    if (value.isEmpty()) {
//                        configNavigationFragment.etLatitude.setText("");
//                        configNavigationFragment.etLongitude.setText("");
//                    } else {
//                        String[] strings = value.split(",");
//                        configNavigationFragment.etLatitude.setText(strings[0].trim());
//                        configNavigationFragment.etLongitude.setText(strings[1].trim());
//                    }
//                }
//                break;
//            case TRUE_POS:
//                if (configNavigationFragment != null && configNavigationFragment.cbTruePos != null) {
//                    if (value.equals("1")) {
//                        configNavigationFragment.cbTruePos.setChecked(true);
//                    } else {
//                        configNavigationFragment.cbTruePos.setChecked(false);
//                    }
//                }
//                break;
//            case BASE_POS:
//                if (configNavigationFragment != null && configNavigationFragment.etBaseLongitude != null && configNavigationFragment.etBaseLatitude != null) {
//                    if (value.isEmpty()) {
//                        configNavigationFragment.etBaseLongitude.setText("");
//                        configNavigationFragment.etBaseLatitude.setText("");
//                    } else {
//                        String[] strings = value.split(",");
//                        configNavigationFragment.etBaseLatitude.setText(strings[0].trim());
//                        configNavigationFragment.etBaseLongitude.setText(strings[1].trim());
//                    }
//                }
//                break;
//            case LONG_DEVIATION:
//                if (configNavigationFragment != null && configNavigationFragment.etLongDeviation != null)
//                    configNavigationFragment.etLongDeviation.setText(value);
//                break;
//            case LAT_DEVIATION:
//                if (configNavigationFragment != null && configNavigationFragment.etLatDeviation != null)
//                    configNavigationFragment.etLatDeviation.setText(value);
//                break;
//            case HDOP:
//                if (configNavigationFragment != null && configNavigationFragment.etHdop != null)
//                    configNavigationFragment.etHdop.setText(value);
//                break;
//            case FIX_DELAY:
//                if (configNavigationFragment != null && configNavigationFragment.etFixDelay != null)
//                    configNavigationFragment.etFixDelay.setText(value);
//                break;
//            case SATELLITE_SYSTEM:
//                if (configNavigationFragment != null && configNavigationFragment.spinSatelliteSystem != null) {
//                    if (value.isEmpty()) {
//                        configNavigationFragment.spinSatelliteSystem.setSelection(0);
//                    } else {
//                        switch (value) {
//                            case "0":
//                                configNavigationFragment.spinSatelliteSystem.setSelection(1);
//                                break;
//                            case "1":
//                                configNavigationFragment.spinSatelliteSystem.setSelection(2);
//                                break;
//                            case "2":
//                                configNavigationFragment.spinSatelliteSystem.setSelection(2);
//                                break;
//                        }
//                    }
//                }
//                break;
//            case EVENTS_MASK:
//                // Можно дописать в условие все cb, но если cb9 существует, то и остальные тоже
//                if (configEventsFragment != null && configEventsFragment.cb9 != null) {
//                    configEventsFragment.setCheckedEventsMaskCb(value);
//                }
//                break;
//            case SERVER:
//                if (configServerFragment != null && configServerFragment.etServer != null)
//                    configServerFragment.etServer.setText(value);
//                break;
//            case CONNECT_ATTEMPTS:
//                if (configServerFragment != null && configServerFragment.etConnectAttempts != null)
//                    configServerFragment.etConnectAttempts.setText(value);
//                break;
//            case SESSION_TIME:
//                if (configServerFragment != null && configServerFragment.etSessionTime != null)
//                    configServerFragment.etSessionTime.setText(value);
//                break;
//            case PACKET_TOUT:
//                if (configServerFragment != null && configServerFragment.etPacketTout != null)
//                    configServerFragment.etPacketTout.setText(value);
//                break;
//            case PRIORITY_CHNL:
//                if (configServerFragment != null && configServerFragment.spinPriorityChnl != null) {
//                    if (value.isEmpty()) {
//                        configServerFragment.spinPriorityChnl.setSelection(0);
//                    } else {
//                        switch (value) {
//                            case "0":
//                                configServerFragment.spinPriorityChnl.setSelection(1);
//                                break;
//                            case "1":
//                                configServerFragment.spinPriorityChnl.setSelection(2);
//                                break;
//                        }
//                    }
//                }
//                break;
//            case NORMAL_INT:
//                if (configServerFragment != null && configServerFragment.etNormalInt != null)
//                    configServerFragment.etNormalInt.setText(value);
//                break;
//            case ALARM_INT:
//                if (configServerFragment != null && configServerFragment.etAlarmInt != null)
//                    configServerFragment.etAlarmInt.setText(value);
//                break;
//            case SMS_CENTER:
//                if (configServerFragment != null && configServerFragment.etSmsCenter != null)
//                    configServerFragment.etSmsCenter.setText(value);
//                break;
//            case CMD_NUMBER:
//                if (configServerFragment != null && configServerFragment.etCmdNumber != null)
//                    configServerFragment.etCmdNumber.setText(value);
//                break;
//            case ANSW_NUMBER:
//                if (configServerFragment != null && configServerFragment.etAnswNumber != null)
//                    configServerFragment.etAnswNumber.setText(value);
//                break;
//            case PACKETS:
//                if (!value.isEmpty() && configServerFragment != null && configServerFragment.etPackets != null) {
//                    configServerFragment.etPackets.setText(value.split(",")[0]);
//                    configServerFragment.etPacketsPercents.setText(value.split(",")[1]);
//                }
//                break;
//            case APN:
//                if (configSimCardFragment != null && configSimCardFragment.etApn != null) {
//                    configSimCardFragment.etApn.setText(value.replaceAll("\"", ""));
//                }
//                break;
//            case LOGIN:
//                if (configSimCardFragment != null && configSimCardFragment.etLogin != null) {
//                    configSimCardFragment.etLogin.setText(value.replaceAll("\"", ""));
//                }
//                break;
//            case PASSWORD:
//                if (configSimCardFragment != null && configSimCardFragment.etPassword != null) {
//                    configSimCardFragment.etPassword.setText(value.replaceAll("\"", ""));
//                }
//                break;
//            case SIM_ATTEMPTS:
//                if (configSimCardFragment != null && configSimCardFragment.etSimAttempts != null) {
//                    configSimCardFragment.etSimAttempts.setText(value);
//                }
//                break;
//            case DELIV_TIMEOUT:
//                if (configSimCardFragment != null && configSimCardFragment.etDelivTimeOut != null) {
//                    configSimCardFragment.etDelivTimeOut.setText(value);
//                }
//                break;
//        }
//    }
//
//    // Показать фрагмент
//    @Override
//    public void showFragment(String fragment) {
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        switch (fragment) {
//            case BLUETOOTH_FRAGMENT:
//                //bluetoothFragment = new BluetoothFragment();
//                fragmentTransaction.replace(R.id.container, bluetoothFragment);
//                navigation.setSelectedItemId(bluetoothFragment.getId());
//                break;
//            case CONFIGURATION_FRAGMENT:
//                //configurationFragment = new ConfigurationFragment();
//                fragmentTransaction.replace(R.id.container, configurationFragment);
//                navigation.setSelectedItemId(configurationFragment.getId());
//                break;
//            case CONSOLE_FRAGMENT:
//                //consoleFragment = new ConsoleFragment();
//                fragmentTransaction.replace(R.id.container, consoleFragment);
//                navigation.setSelectedItemId(consoleFragment.getId());
//                break;
//            case CONFIG_BUOY_FRAGMENT:
//                configBuoyFragment = new ConfigBuoyFragment();
//                fragmentTransaction.replace(R.id.container, configBuoyFragment);
//                navigation.setSelectedItemId(configurationFragment.getId());
//                break;
//            case CONFIG_MAIN_FRAGMENT:
//                configMainFragment = new ConfigMainFragment();
//                fragmentTransaction.replace(R.id.container, configMainFragment);
//                navigation.setSelectedItemId(configurationFragment.getId());
//                break;
//            case CONFIG_NAVIGATION_FRAGMENT:
//                configNavigationFragment = new ConfigNavigationFragment();
//                fragmentTransaction.replace(R.id.container, configNavigationFragment);
//                navigation.setSelectedItemId(configurationFragment.getId());
//                break;
//            case CONFIG_EVENTS_FRAGMENT:
//                configEventsFragment = new ConfigEventsFragment();
//                fragmentTransaction.replace(R.id.container, configEventsFragment);
//                navigation.setSelectedItemId(configurationFragment.getId());
//                break;
//            case CONFIG_SERVER_FRAGMENT:
//                configServerFragment = new ConfigServerFragment();
//                fragmentTransaction.replace(R.id.container, configServerFragment);
//                navigation.setSelectedItemId(configurationFragment.getId());
//                break;
//            case CONFIG_SIM_CARD_FRAGMENT:
//                configSimCardFragment = new ConfigSimCardFragment();
//                fragmentTransaction.replace(R.id.container, configSimCardFragment);
//                navigation.setSelectedItemId(configurationFragment.getId());
//                break;
//        }
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }
//
//    // Поставить активную позицию на bottomNavigation в зависимости от фрагмента
//    @Override
//    public void setNavigationPosition(String fragment) {
//        switch (fragment) {
//            case BLUETOOTH_FRAGMENT:
//                navigation.getMenu().getItem(0).setChecked(true);
//                break;
//            case CONFIGURATION_FRAGMENT:
//                navigation.getMenu().getItem(1).setChecked(true);
//                break;
//            case CONSOLE_FRAGMENT:
//                navigation.getMenu().getItem(2).setChecked(true);
//                break;
//            case CONFIG_BUOY_FRAGMENT:
//                navigation.getMenu().getItem(1).setChecked(true);
//                break;
//            case CONFIG_MAIN_FRAGMENT:
//                navigation.getMenu().getItem(1).setChecked(true);
//                break;
//            case CONFIG_NAVIGATION_FRAGMENT:
//                navigation.getMenu().getItem(1).setChecked(true);
//                break;
//            case CONFIG_EVENTS_FRAGMENT:
//                navigation.getMenu().getItem(1).setChecked(true);
//                break;
//            case CONFIG_SERVER_FRAGMENT:
//                navigation.getMenu().getItem(1).setChecked(true);
//                break;
//            case CONFIG_SIM_CARD_FRAGMENT:
//                navigation.getMenu().getItem(1).setChecked(true);
//                break;
//        }
//    }
//
//    @Override
//    public void setSwitchBtState(boolean state) {
//        bluetoothFragment.setSwitchBtState(state);
//    }
//
//    @Override
//    public void showToast(String toast) {
//        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void showLog(String logsMessages) {
//        consoleFragment.showLog(logsMessages);
//    }
//
//    @Override
//    public void addLogsLine(String line) {
//        consoleFragment.addLogsLine(line);
//    }
//
//    @Override
//    public void showDevices() {
//        bluetoothFragment.showDevices();
//    }
//
//    @Override
//    public void hideDevices() {
//        bluetoothFragment.hideDevices();
//    }
//
//
//    public void unregisterBluetoothBroadcastReceiver(BluetoothBroadcastReceiver bluetoothBroadcastReceiver) {
//        unregisterReceiver(bluetoothBroadcastReceiver);
//    }
//
//    @Override
//    public void updatePairedDevices() {
//        bluetoothFragment.updatePairedDevices();
//    }
//
//    @Override
//    public void updateAvailableDevices() {
//        // DeviceLogs.d(TAG, "updateAvailableDevices()");
//        bluetoothFragment.updateAvailableDevices();
//    }
//
//
//    @Override
//    public void showLoadingProgress(int size) {
//        Log.d(TAG, "showLoadingProgress: ");
//        Objects.requireNonNull(getSupportActionBar()).setSubtitle("0%");
//    }
//
//    @Override
//    public void setLoadingProgress(int commandNumber, int size) {
//        float pers = (float) commandNumber / (float) size * (float) 100;
//        String subtitle = String.valueOf((int) pers) + "%";
//        Log.d(TAG, "setLoadingProgress: " + commandNumber + ", " + size + ", " + subtitle);
//        Objects.requireNonNull(getSupportActionBar()).setSubtitle(subtitle);
//    }
//
//    @Override
//    public void hideLoadingProgress() {
//        Objects.requireNonNull(getSupportActionBar()).setSubtitle("");
//        showToast("Загрузка завершена");
//    }
//
//    @Override
//    public String getCommandLineText() {
//        return consoleFragment.getCommandLineText();
//    }
//
//    @Override
//    public String getEtBlinkerLxText() {
//        return configMainFragment.etBlinkerLx.getText().toString();
//    }
//
//    @Override
//    public String getEtIdText() {
//        return configBuoyFragment.getEtIdText();
//    }
//
//    @Override
//    public String getEtMaxDeviationText() {
//        return configMainFragment.etMaxDeviation.getText().toString();
//    }
//
//    @Override
//    public String getEtTiltDeviationText() {
//        return configMainFragment.etTiltAngle.getText().toString();
//    }
//
//    @Override
//    public String getEtImpactPowText() {
//        return configMainFragment.etImpactPow.getText().toString();
//    }
//
//    @Override
//    public String getEtUpowerThldText() {
//        return configMainFragment.etUpowerThld.getText().toString();
//    }
//
//    @Override
//    public String getEtDeviationIntText() {
//        return configMainFragment.etDeviationInt.getText().toString();
//    }
//
//    @Override
//    public String getEtMaxActiveText() {
//        return configMainFragment.etMaxActive.getText().toString();
//    }
//
//    @Override
//    public String getEtLongitude() {
//        return configNavigationFragment.etLongitude.getText().toString();
//    }
//
//    @Override
//    public String getEtLatitude() {
//        return configNavigationFragment.etLatitude.getText().toString();
//    }
//
//    @Override
//    public String getEtBaseLongitude() {
//        return configNavigationFragment.etBaseLongitude.getText().toString();
//    }
//
//    @Override
//    public String getEtBaseLatitude() {
//        return configNavigationFragment.etBaseLatitude.getText().toString();
//    }
//
//    @Override
//    public String getStateCbTruePos() {
//        String state;
//        if (configNavigationFragment.cbTruePos.isChecked()) {
//            state = "1";
//        } else state = "0";
//        return state;
//    }
//
//    @Override
//    public String getBasePos() {
//        String lat = configNavigationFragment.etBaseLatitude.getText().toString();
//        String lon = configNavigationFragment.etBaseLongitude.getText().toString();
//        if (lat.isEmpty() && lon.isEmpty()) {
//            return "";
//        } else return lat + "," + lon;
//    }
//
//    @Override
//    public void setFocusOnEt(final EditText editText) {
//        editText.requestFocus();
//        editText.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (inputMethodManager != null) {
//                    inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
//                }
//            }
//        }, 300);
//        editText.setSelection(editText.getText().length());
//    }
//
//    @Override
//    public void showItemUpdateAvailableDevices() {
//        Log.d(TAG, "showItemUpdateAvailableDevices: ");
//        if (actionBarMenu != null)
//            actionBarMenu.setGroupVisible(R.id.group_update_available_devices, true);
//    }
//
//    @Override
//    public void hideItemUpdateAvailableDevices() {
//        Log.d(TAG, "hideItemUpdateAvailableDevices: ");
//        if (actionBarMenu != null)
//            actionBarMenu.setGroupVisible(R.id.group_update_available_devices, false);
//    }
//
//    @Override
//    public boolean isAvailableDevicesFragmentResumed() {
//        return true;
//    }
//
//    @Override
//    public void openDevices() {
//        bluetoothFragment.openDevices();
//    }
//
//    @Override
//    public void closeDevices() {
//        bluetoothFragment.closeDevices();
//    }
//
//    @Override
//    public int getSelectedPageOfViewPager() {
//        return 0;
//    }
//
//    @Override
//    public void requestReadPermission() {
//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
//    }
//
//    @Override
//    public void requestWritePermission() {
//        ActivityCompat.requestPermissions(
//                this,
//                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
//    }
//
//    @Override
//    public void requestBluetoothPermission() {
//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
//    }
//
//    @Override
//    public String getEtLongDeviationText() {
//        return configNavigationFragment.etLongDeviation.getText().toString();
//    }
//
//    @Override
//    public String getEtLatDeviationText() {
//        return configNavigationFragment.etLatDeviation.getText().toString();
//    }
//
//    @Override
//    public String getEtHdopText() {
//        return configNavigationFragment.etHdop.getText().toString();
//    }
//
//    @Override
//    public String getEtFixDelayText() {
//        return configNavigationFragment.etFixDelay.getText().toString();
//    }
//
//    @Override
//    public String getCheckedAlarmEvents() {
//        String events = "";
//        if (configEventsFragment.cb1.isChecked()) events += "1,";
//        if (configEventsFragment.cb2.isChecked()) events += "2,";
//        if (configEventsFragment.cb3.isChecked()) events += "3,";
//        if (configEventsFragment.cb4.isChecked()) events += "4,";
//        if (configEventsFragment.cb5.isChecked()) events += "5,";
//        if (configEventsFragment.cb6.isChecked()) events += "6,";
//        if (configEventsFragment.cb7.isChecked()) events += "7,";
//        if (configEventsFragment.cb8.isChecked()) events += "8,";
//        if (!events.isEmpty()) events = events.substring(0, events.length() - 1);
//        return events;
//    }
//
//    @Override
//    public String getCheckedEventsMask() {
//        String events = "";
//        if (configEventsFragment.cb9.isChecked()) events += "1,";
//        if (configEventsFragment.cb10.isChecked()) events += "2,";
//        if (configEventsFragment.cb11.isChecked()) events += "3,";
//        if (configEventsFragment.cb12.isChecked()) events += "4,";
//        if (configEventsFragment.cb13.isChecked()) events += "5,";
//        if (configEventsFragment.cb14.isChecked()) events += "6,";
//        if (configEventsFragment.cb15.isChecked()) events += "7,";
//        if (configEventsFragment.cb16.isChecked()) events += "8,";
//        if (!events.isEmpty()) events = events.substring(0, events.length() - 1);
//        return events;
//    }
//
//    @Override
//    public String getEtServerText() {
//        return configServerFragment.etServer.getText().toString();
//    }
//
//    @Override
//    public String getEtConnectAttemptsText() {
//        return configServerFragment.etConnectAttempts.getText().toString();
//    }
//
//    @Override
//    public String getEtSessionTimeText() {
//        return configServerFragment.etSessionTime.getText().toString();
//    }
//
//    @Override
//    public String getEtPacketToutText() {
//        return configServerFragment.etPacketTout.getText().toString();
//    }
//
//    @Override
//    public String getEtNormalIntText() {
//        return configServerFragment.etNormalInt.getText().toString();
//    }
//
//    @Override
//    public String getEtAlarmIntText() {
//        return configServerFragment.etAlarmInt.getText().toString();
//    }
//
//    @Override
//    public String getEtSmsCenterText() {
//        return configServerFragment.etSmsCenter.getText().toString();
//    }
//
//    @Override
//    public String getEtCmdNumberText() {
//        return configServerFragment.etCmdNumber.getText().toString();
//    }
//
//    @Override
//    public String getEtAnswNumberText() {
//        return configServerFragment.etAnswNumber.getText().toString();
//    }
//
//    @Override
//    public String getEtApnText() {
//        return configSimCardFragment.etApn.getText().toString();
//    }
//
//    @Override
//    public String getEtPasswordText() {
//        return configSimCardFragment.etPassword.getText().toString();
//    }
//
//    @Override
//    public String getEtLoginText() {
//        return configSimCardFragment.etLogin.getText().toString();
//    }
//
//    @Override
//    public String getEtPinText() {
//        return configSimCardFragment.etPin.getText().toString();
//    }
//
//    @Override
//    public String getEtSimAttemptsText() {
//        return configSimCardFragment.etSimAttempts.getText().toString();
//    }
//
//    @Override
//    public String getEtDelivTimeoutText() {
//        return configSimCardFragment.etDelivTimeOut.getText().toString();
//    }
//
//    @Override
//    public void setTitle(String title) {
//        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
//    }
//
//    @Override
//    public void setSwitchBtText(String text) {
////        if (bluetoothFragment != null && bluetoothFragment.switchBt != null) {
////            bluetoothFragment.switchBt.setText(text);
////        }
//    }
//
//    @Override
//    public void showConfigActionsMenuGroup() {
//        if (actionBarMenu != null)
//            actionBarMenu.setGroupVisible(R.id.group_configuration_actions_menu, true);
//
//    }
//
//    @Override
//    public void hideConfigActionsMenuGroup() {
//        if (actionBarMenu != null)
//            actionBarMenu.setGroupVisible(R.id.group_configuration_actions_menu, false);
//    }
//
//
//    // ---------------------------------------------------------------------------------------------
//    // OnBluetoothFragmentInteractionListener
//    @Override
//    public void onSwitchBtStateChanged(boolean state) {
//        presenter.onSwitchBtStateChanged(state);
//    }
//
//    @Override
//    public void onDevicesPageSelected(int position) {
//        presenter.onDevicesPageSelected(position);
//    }
//
//    @Override
//    public void onPairedDevicesRvItemClicked(String bluetoothDeviceAddress) {
//        presenter.onPairedDevicesRvItemClick(bluetoothDeviceAddress);
//    }
//
//    @Override
//    public void onAvailableDevicesRvItemClicked(String bluetoothDeviceAddress) {
//        presenter.onAvailableDevicesRvItemClicked(bluetoothDeviceAddress);
//    }
//
//    @Override
//    public boolean isBluetoothFragmentResumed() {
//        return bluetoothFragment.isResumed();
//    }
//
//    @Override
//    public void onBluetoothFragmentCreateView() {
//        presenter.onBluetoothFragmentCreateView();
//    }
//
//    @Override
//    public void onBluetoothFragmentStart() {
//        presenter.onBluetoothFragmentStart();
//    }
//
//    @Override
//    public void onBluetoothFragmentDestroyView() {
//        presenter.onBluetoothFragmentDestroyView();
//    }
//
//    @Override
//    public void onBluetoothFragmentDestroy() {
//        presenter.onBluetoothFragmentDestroy();
//    }
//
//    @Override
//    public void onAvailableDevicesFragmentDestroyView() {
//        presenter.onAvailableDevicesFragmentDestroyView();
//    }
//
//    @Override
//    public void onBindViewHolderOfPairedDevicesRvAdapter(PairedDevicesItemView holder, int position) {
//        presenter.onBindViewHolderOfPairedDevicesRvAdapter(holder, position);
//    }
//
//    @Override
//    public int onGetItemCountOfPairedDevicesRvAdapter() {
//        return presenter.onGetItemCountOfPairedDevicesRvAdapter();
//    }
//
//    @Override
//    public void onBindViewHolderOfAvailableDevicesRvAdapter(AvailableDevicesItemView holder, int position) {
//        presenter.onBindViewHolderOfAvailableDevicesRvAdapter(holder, position);
//    }
//
//    @Override
//    public int onGetItemCountOfAvailableDevicesRvAdapter() {
//        return presenter.onGetItemCountOfAvailableDevicesRvAdapter();
//    }
//
//    @Override
//    public void onAvailableDevicesFragmentStart() {
//        presenter.onAvailableDevicesFragmentStart();
//    }
//
//    @Override
//    public void onAvailableDevicesFragmentPause() {
//        presenter.onAvailableDevicesFragmentPause();
//    }
//
//    @Override
//    public void onAvailableDevicesFragmentResume() {
//        presenter.onAvailableDevicesFragmentResume();
//    }
//
//    // ---------------------------------------------------------------------------------------------
//    // OnConfigurationFragmentInteractionListener
//    @Override
//    public void onConfigTabsRvItemClick(String tab) {
//        presenter.onConfigTabsRvItemClick(tab);
//    }
//
//    @Override
//    public void OnConfigurationFragmentStart() {
//        presenter.onConfigurationFragmentStart();
//    }
//
//
//    // ---------------------------------------------------------------------------------------------
//    // OnConfigBuoyFragmentInteractionListener
//    @Override
//    public void onEtIdFocusChange(boolean hasFocus) {
//        presenter.onEtIdFocusChange(hasFocus);
//
//    }
//
//    @Override
//    public void onBtnRestartClick() {
//        presenter.onBtnRestartClick();
//    }
//
//    @Override
//    public void onBtnDefaultSettingsClick() {
//        presenter.onBtnDefaultSettingsClick();
//    }
//
//    @Override
//    public void onLlBuoyParameterClick(EditText editText) {
//        presenter.onLlParameterClick(editText);
//    }
//
//    @Override
//    public void onConfigBuoyFragmentStart() {
//        presenter.onConfigBuoyFragmentStart();
//    }
//
//
//    // ---------------------------------------------------------------------------------------------
//    // OnConfigMainFragmentInteractionListener
//    @Override
//    public void onSpinBlinkerModeItemSelected(int position) {
//        presenter.onSpinBlinkerModeItemSelected(position);
//    }
//
//    @Override
//    public void onSpinBlinkerBrightnessItemSelected(int position) {
//        presenter.onSpinBlinkerBrightnessItemSelected(position);
//    }
//
//    @Override
//    public void onEtMaxDeviationFocusChange(boolean hasFocus) {
//        presenter.onEtMaxDeviationFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onEtTiltAngleFocusChange(boolean hasFocus) {
//        presenter.onEtTiltAngleFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onEtImpactPowFocusChange(boolean hasFocus) {
//        presenter.onEtImpactPowFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onEtUpowerThldFocusChange(boolean hasFocus) {
//        presenter.onEtUpowerThldFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onEtDeviationIntFocusChange(boolean hasFocus) {
//        presenter.onEtDeviationIntFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onEtMaxActiveFocusChange(boolean hasFocus) {
//        presenter.onEtMaxActiveFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onEtBlinkerLxFocusChange(boolean hasFocus) {
//        presenter.onEtBlinkerLxFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onLlMainParameterClick(EditText editText) {
//        presenter.onLlParameterClick(editText);
//    }
//
//    // Lifecycle
//    @Override
//    public void onConfigMainFragmentStart() {
//        presenter.onConfigMainFragmentStart();
//    }
//
//
//    // ---------------------------------------------------------------------------------------------
//    // OnConfigNavigationFragmentInteractionListener
//    @Override
//    public void onBtnRcvColdStartClick() {
//        presenter.onBtnRcvColdStartClick();
//    }
//
//    @Override
//    public void onEtLongDeviationFocusChanged(boolean hasFocus) {
//        presenter.onEtLongDeviationFocusChanged(hasFocus);
//    }
//
//    @Override
//    public void onEtLatDeviationFocusChanged(boolean hasFocus) {
//        presenter.onEtLatDeviationFocusChanged(hasFocus);
//    }
//
//    @Override
//    public void onEtHdopFocusChanged(boolean hasFocus) {
//        presenter.onEtHdopFocusChanged(hasFocus);
//    }
//
//    @Override
//    public void onEtFixDelayFocusChanged(boolean hasFocus) {
//        presenter.onEtFixDelayFocusChanged(hasFocus);
//    }
//
//    @Override
//    public void onSpinSatelliteSystemItemSelected(int position) {
//        presenter.onSpinSatelliteSystemItemSelected(position);
//    }
//
//    @Override
//    public void onBtnRequestBasePosClick() {
//        presenter.onBtnRequestBasePosClick();
//    }
//
//    @Override
//    public void onBtnShowMapCurrentPosClick() {
//        presenter.onBtnShowMapCurrentPosClick();
//    }
//
//    @Override
//    public void onBtnShowMapBasePosClick() {
//        presenter.onBtnShowMapBasePosClick();
//    }
//
//    @Override
//    public void onCbTruePosClick() {
//        presenter.onCbTruePosClick();
//    }
//
//    @Override
//    public void onEtBaseLongitudeFocusChange(boolean hasFocus) {
//        presenter.onEtBaseLongitudeFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onEtBaseLatitudeFocusChange(boolean hasFocus) {
//        presenter.onEtBaseLatitudeFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onLlNavigationParameterClick(EditText editText) {
//        presenter.onLlParameterClick(editText);
//    }
//
//    // Lifecycle
//    @Override
//    public void onConfigNavigationFragmentStart() {
//        presenter.onConfigNavigationFragmentStart();
//    }
//
//
//    // ---------------------------------------------------------------------------------------------
//    // OnConfigEventsFragmentInteractionListener
//    @Override
//    public void onBtnAlarmEventsClick() {
//        presenter.onBtnAlarmEventsClick();
//    }
//
//    @Override
//    public void onEventsMaskCbClick() {
//        presenter.onEventsMaskCbClick();
//    }
//
//    // Lifecycle
//    @Override
//    public void onConfigEventsFragmentStart() {
//        presenter.OnConfigEventsFragmentStart();
//    }
//
//
//    // ---------------------------------------------------------------------------------------------
//    // OnConfigServerFragmentInteractionListener
//    @Override
//    public void onEtServerFocusChange(boolean hasFocus) {
//        presenter.onEtServerFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onEtConnectAttemptsFocusChange(boolean hasFocus) {
//        presenter.onEtConnectAttemptsFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onEtSessionTimeFocusChange(boolean hasFocus) {
//        presenter.onEtSessionTimeFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onEtPacketToutFocusChange(boolean hasFocus) {
//        presenter.onEtPacketToutFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onSpinPriorityChnlItemClick(int position) {
//        presenter.onSpinPriorityChnlItemClick(position);
//    }
//
//    @Override
//    public void onEtNormalIntFocusChange(boolean hasFocus) {
//        presenter.onEtNormalIntFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onEtAlarmIntFocusChange(boolean hasFocus) {
//        presenter.onEtAlarmIntFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onEtSmsCenterFocusChange(boolean hasFocus) {
//        presenter.onEtSmsCenterFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onEtCmdNumberFocusChange(boolean hasFocus) {
//        presenter.onEtCmdNumberFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onEtAnswNumberFocusChange(boolean hasFocus) {
//        presenter.onEtAnswNumberFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onBtnClearArchiveClick() {
//        presenter.onBtnClearArchiveClick();
//    }
//
//    @Override
//    public void onBtnCloseConnectClick() {
//        presenter.onBtnCloseConnectClick();
//    }
//
//    // Lifecycle
//    @Override
//    public void onConfigServerFragmentStart() {
//        presenter.onConfigServerFragmentStart();
//    }
//
//
//    // ---------------------------------------------------------------------------------------------
//    // OnConfigSimCardFragmentInteractionListener
//    @Override
//    public void onEtApnFocusChange(boolean hasFocus) {
//        presenter.onEtApnFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onEtLoginFocusChange(boolean hasFocus) {
//        presenter.onEtLoginFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onEtPasswordFocusChange(boolean hasFocus) {
//        presenter.onEtPasswordFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onBtnDefaultApnClick() {
//        presenter.onBtnDefaultApnClick();
//    }
//
//    @Override
//    public void onBtnDefaultLoginClick() {
//        presenter.onBtnDefaultLoginClick();
//    }
//
//    @Override
//    public void onBtnDefaultPasswordClick() {
//        presenter.onBtnDefaultPasswordClick();
//    }
//
//    @Override
//    public void onBtnClearApnClick() {
//        presenter.onBtnClearApnClick();
//    }
//
//    @Override
//    public void onBtnClearLoginClick() {
//        presenter.onBtnClearLoginClick();
//    }
//
//    @Override
//    public void onBtnClearPasswordClick() {
//        presenter.onBtnClearPasswordClick();
//    }
//
//    @Override
//    public void onBtnEnterPinClick() {
//        presenter.onBtnEnterPinClick();
//    }
//
//    @Override
//    public void onBtnClearPinClick() {
//        presenter.onBtnClearPinClick();
//    }
//
//    @Override
//    public void onEtSimAttemptsFocusChange(boolean hasFocus) {
//        presenter.onEtSimAttemptsFocusChange(hasFocus);
//    }
//
//    @Override
//    public void onEtDelivTimeoutFocusChange(boolean hasFocus) {
//        presenter.onEtDelivTimeoutFocusChange(hasFocus);
//    }
//
//    // Lifecycle
//    @Override
//    public void onConfigSimCardFragmentStart() {
//        presenter.onConfigSimCardFragmentStart();
//    }
//
//
//    // ---------------------------------------------------------------------------------------------
//    // OnConsoleFragmentInteractionListener
//    @Override
//    public void onBtnSendCommandClick(String command) {
//        presenter.onBtnSendCommandClick();
//    }
//
//    @Override
//    public void onConsoleFragmentCreateView() {
//        presenter.onConsoleFragmentCreateView();
//    }
//
//    @Override
//    public void onConsoleFragmentStart() {
//        presenter.onConsoleFragmentStart();
//    }
//
//    @Override
//    public void onTvLogsLongClick() {
//        presenter.onTvLogsLongClick();
//    }
//
//
//    // ---------------------------------------------------------------------------------------------
//    // States
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        Log.v(TAG, "onCreate");
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        //presenter = new Presenter(this);
//        initUi();
//        presenter.onMainActivityCreate();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.v(TAG, "onStart");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.v(TAG, "onResume");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.v(TAG, "onPause");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.v(TAG, "onStop");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.v(TAG, "onDestroy");
//        presenter.onMainActivityDestroy();
//    }
//
//
//    // ---------------------------------------------------------------------------------------------
//    // Activity
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        Log.v(TAG, "onCreateOptionsMenu: ");
//        actionBarMenu = menu;
//        getMenuInflater().inflate(R.menu.menu_configuration_options, actionBarMenu);
//        itemUpdateAvailableDevices = actionBarMenu.findItem(R.id.item_update_available_devices);
//        itemUpdateAvailableDevices.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                presenter.onItemUpdateAvailableDevicesClick();
//                return false;
//            }
//        });
//        presenter.onCreateOptionsMenu();
//        return super.onCreateOptionsMenu(actionBarMenu);
//    }
//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        Log.v(TAG, "onPrepareOptionsMenu: ");
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        presenter.onConfigurationOptionsItemSelected(item);
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // Log.d(TAG, "onActivityResult: " + "requestCode = " + requestCode + ", " + "resultCode = " + resultCode + ", " + "data = " + data.getData());
//        // data.getData();
//        presenter.onMainActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public void startMapActivity(String latitude, String longitude) {
//        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude);
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        startActivity(mapIntent);
//    }
//
//    @Override
//    public void startOpenFileManagerActivityWithRequestPermission() {
//        requestReadPermission();
//    }
//
//    @Override
//    public void startSaveFileActivityWithRequestPermission() {
//        requestWritePermission();
//    }
//
//    @Override
//    public void startBluetoothDiscoveryWithRequestPermission() {
//        requestBluetoothPermission();
//    }
//
//    @Override
//    public void startOpenFileManagerActivity() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("*/*");
//        startActivityForResult(intent, OPEN_FILE_MANAGER_REQUEST_CODE);
//    }
//
//    @Override
//    public void onSaveFileDialogPositiveClick(String fileName) {
//        presenter.onSaveFileDialogPositiveClick(fileName);
//    }
//
//    @Override
//    public void showSaveFileDialog() {
//        SaveFileDialogFragment dialog = new SaveFileDialogFragment();
//        dialog.show(getSupportFragmentManager(), "SaveFileDialogFragment");
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        ArrayList<String> pers = new ArrayList<>();
//        ArrayList<Integer> grants = new ArrayList<>();
//        Collections.addAll(pers, permissions);
//        for (int i : grantResults) {
//            grants.add(i);
//        }
//        Log.d(TAG, "onRequestPermissionsResult() returned: " + requestCode + ", " + pers + ", " + grants);
//        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
//            if (grants.contains(PERMISSION_GRANTED)) {
//                Log.i(TAG, "onRequestPermissionsResult: Подверждено =)");
//                presenter.onPositiveRequestReadExternalStoragePermissionRequestResult();
//            } else {
//                Log.e(TAG, "onRequestPermissionsResult: Отклонено =(");
//                presenter.onNegativeRequestReadExternalStoragePermissionRequestResult();
//            }
//        } else if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
//            if (grants.contains(PERMISSION_GRANTED)) {
//                Log.i(TAG, "onRequestPermissionsResult: Подверждено =)");
//                presenter.onPositiveRequestWriteExternalStoragePermissionRequestResult();
//            } else {
//                Log.e(TAG, "onRequestPermissionsResult: Отклонено =(");
//                presenter.onNegativeRequestWriteExternalStoragePermissionRequestResult();
//            }
//        } else if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION) {
//            if (grants.contains(PERMISSION_GRANTED)) {
//                Log.i(TAG, "onRequestPermissionsResult: Подверждено =)");
//                presenter.onPositiveRequestAccessCoarseLocationPermissionRequestResult();
//            } else {
//                Log.e(TAG, "onRequestPermissionsResult: Отклонено =(");
//                presenter.onNegativeRequestAccessCoarseLocationPermissionRequestResult();
//            }
//        }
//
//    }
//}