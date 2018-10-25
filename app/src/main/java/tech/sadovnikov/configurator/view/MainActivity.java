package tech.sadovnikov.configurator.view;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import tech.sadovnikov.configurator.Contract;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.presenter.BluetoothBroadcastReceiver;
import tech.sadovnikov.configurator.presenter.Presenter;
import tech.sadovnikov.configurator.view.adapter.AvailableDevicesItemView;
import tech.sadovnikov.configurator.view.adapter.PairedDevicesItemView;

import static tech.sadovnikov.configurator.model.Configuration.BLINKER_BRIGHTNESS;
import static tech.sadovnikov.configurator.model.Configuration.BLINKER_LX;
import static tech.sadovnikov.configurator.model.Configuration.BLINKER_MODE;
import static tech.sadovnikov.configurator.model.Configuration.FIRMWARE_VERSION;
import static tech.sadovnikov.configurator.model.Configuration.ID;


public class MainActivity extends AppCompatActivity implements Contract.View,
        BluetoothFragment.OnBluetoothFragmentInteractionListener,
        ConfigurationFragment.OnConfigurationFragmentInteractionListener,
        ConsoleFragment.OnConsoleFragmentInteractionListener,
        ConfigBuoyFragment.OnConfigBuoyFragmentInteractionListener,
        ConfigMainFragment.OnConfigMainFragmentInteractionListener {

    private static final String TAG = "MainActivity";

    Contract.Presenter presenter;

    // UI
    FrameLayout container;
    BottomNavigationView navigation;
    // Menu menuActionsWithConfiguration;

    // Fragments
    BluetoothFragment bluetoothFragment;
    ConfigurationFragment configurationFragment;
    ConfigBuoyFragment configBuoyFragment;
    ConfigMainFragment configMainFragment;
    ConfigNavigationFragment configNavigationFragment;
    ConsoleFragment consoleFragment;

    public MainActivity() {
        Log.v(TAG, "onConstructor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        presenter = new Presenter(this);
        presenter.onMainActivityCreate();
    }

    private void initUi() {
        container = findViewById(R.id.container);
        bluetoothFragment = new BluetoothFragment();
        configurationFragment = new ConfigurationFragment();
        consoleFragment = new ConsoleFragment();
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return presenter.onNavigationItemSelected(item);
            }
        });

    }

    // ---------------------------------------------------------------------------------------------
    // Contract.View
    // TODO <Добавить параметр>
    @Override
    public void showParameter(String name, String value) {
        Log.d(TAG, "showParameter: " + name + "=" + value);
        switch (name) {
            case ID:
                if (configBuoyFragment != null && configBuoyFragment.etId != null)
                    configBuoyFragment.etId.setText(value);
                break;
            case FIRMWARE_VERSION:
                if (configBuoyFragment != null && configBuoyFragment.etVersion != null)
                    configBuoyFragment.etVersion.setText(value);
                break;
            case BLINKER_MODE:
                if (!value.isEmpty()) {
                    if (configMainFragment != null && configMainFragment.spinBlinkerMode != null) {
                        configMainFragment.spinBlinkerMode.setSelection(Integer.valueOf(value));
                    }
                }
                break;
            case BLINKER_BRIGHTNESS:
                // Log.d(TAG, "showParameter: case BLINKER_BRIGHTNESS: value = " + value + ".isEmpty() = " + value.isEmpty());
                if (!value.isEmpty()) {
                    if (configMainFragment != null && configMainFragment.spinBlinkerBrightness != null) {
                        configMainFragment.spinBlinkerBrightness.setSelection(Integer.valueOf(value));
                    }
                }
                break;
            case BLINKER_LX:
                if (configMainFragment != null && configMainFragment.etBlinkerLx != null) {
                    configMainFragment.etBlinkerLx.setText(value);
                }
                break;
        }
    }


    // Показать фрагмент
    @Override
    public void showFragment(String fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (fragment) {
            case BLUETOOTH_FRAGMENT:
                //bluetoothFragment = new BluetoothFragment();
                fragmentTransaction.replace(R.id.container, bluetoothFragment);
                navigation.setSelectedItemId(bluetoothFragment.getId());
                break;
            case CONFIGURATION_FRAGMENT:
                //configurationFragment = new ConfigurationFragment();
                fragmentTransaction.replace(R.id.container, configurationFragment);
                navigation.setSelectedItemId(configurationFragment.getId());
                break;
            case CONSOLE_FRAGMENT:
                //consoleFragment = new ConsoleFragment();
                fragmentTransaction.replace(R.id.container, consoleFragment);
                navigation.setSelectedItemId(consoleFragment.getId());
                break;
            case CONFIG_BUOY_FRAGMENT:
                configBuoyFragment = new ConfigBuoyFragment();
                fragmentTransaction.replace(R.id.container, configBuoyFragment);
                navigation.setSelectedItemId(configurationFragment.getId());
                break;
            case CONFIG_MAIN_FRAGMENT:
                configMainFragment = new ConfigMainFragment();
                fragmentTransaction.replace(R.id.container, configMainFragment);
                navigation.setSelectedItemId(configurationFragment.getId());
                break;
            case CONFIG_NAVIGATION_FRAGMENT:
                configNavigationFragment = new ConfigNavigationFragment();
                fragmentTransaction.replace(R.id.container, configNavigationFragment);
                navigation.setSelectedItemId(configurationFragment.getId());
                break;
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    // TODO <Добавить фрагмент для параметра>
    // Поставить активную позицию на bottomNavigation в зависимости от фрагмента
    @Override
    public void setNavigationPosition(String fragment) {
        switch (fragment) {
            case BLUETOOTH_FRAGMENT:
                navigation.getMenu().getItem(0).setChecked(true);
                break;
            case CONFIGURATION_FRAGMENT:
                navigation.getMenu().getItem(1).setChecked(true);
                break;
            case CONSOLE_FRAGMENT:
                navigation.getMenu().getItem(2).setChecked(true);
                break;
            case CONFIG_BUOY_FRAGMENT:
                navigation.getMenu().getItem(1).setChecked(true);
                break;
            case CONFIG_MAIN_FRAGMENT:
                navigation.getMenu().getItem(1).setChecked(true);
                break;
            case CONFIG_NAVIGATION_FRAGMENT:
                navigation.getMenu().getItem(1).setChecked(true);
                break;
        }
    }

    @Override
    public void setSwitchBtState(boolean state) {
        bluetoothFragment.setSwitchBtState(state);
    }

    @Override
    public void showToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLog(String logsMessages) {
        consoleFragment.showLog(logsMessages);
    }

    @Override
    public void addLogsLine(String line) {
        consoleFragment.addLogsLine(line);
    }

    @Override
    public void showDevices() {
        bluetoothFragment.showDevices();
    }

    @Override
    public void hideDevices() {
        bluetoothFragment.hideDevices();
    }

    @Override
    public void unregisterBluetoothBroadcastReceiver(BluetoothBroadcastReceiver bluetoothBroadcastReceiver) {
        unregisterReceiver(bluetoothBroadcastReceiver);
    }

    @Override
    public void updatePairedDevices() {
        bluetoothFragment.updatePairedDevices();
    }

    @Override
    public void updateAvailableDevices() {
        // Logs.d(TAG, "updateAvailableDevices()");
        bluetoothFragment.updateAvailableDevices();
    }

    @Override
    public String getCommandLineText() {
        return consoleFragment.getCommandLineText();
    }

    // TODO <Нужно ли здесь делать принудительный запрос permission>
    @Override
    public void startFileManagerActivity() {
        int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 3;
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, FILE_MANAGER_REQUEST_CODE);
    }

    @Override
    public String getEtBlinkerLxText() {
        return configMainFragment.etBlinkerLx.getText().toString();
    }

    @Override
    public String getEtIdText() {
        return configBuoyFragment.getEtIdText();
    }


    // ---------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_configuration_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Log.d(TAG, "onActivityResult: " + "requestCode = " + requestCode + ", " + "resultCode = " + resultCode + ", " + "data = " + data.getData());
        // data.getData();
        presenter.onMainActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        presenter.onConfigurationOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }


    // ---------------------------------------------------------------------------------------------
    // OnBluetoothFragmentInteractionListener
    @Override
    public void onSwitchBtStateChanged(boolean state) {
        presenter.onSwitchBtStateChanged(state);
    }

    @Override
    public void onDevicesPageSelected(int position) {
        presenter.onDevicesPageSelected(position);
    }

    @Override
    public void onPairedDevicesRvItemClicked(String bluetoothDeviceAddress) {
        presenter.onPairedDevicesRvItemClick(bluetoothDeviceAddress);
    }

    @Override
    public void onAvailableDevicesRvItemClicked(String bluetoothDeviceAddress) {
        presenter.onAvailableDevicesRvItemClicked(bluetoothDeviceAddress);
    }

    @Override
    public void onBluetoothFragmentCreateView() {
        presenter.onBluetoothFragmentCreateView();
    }

    @Override
    public void onBluetoothFragmentStart() {
        presenter.onBluetoothFragmentStart();
    }

    @Override
    public void onAvailableDevicesFragmentDestroyView() {
        presenter.onAvailableDevicesFragmentDestroyView();
    }

    @Override
    public void onBindViewHolderOfPairedDevicesRvAdapter(PairedDevicesItemView holder, int position) {
        presenter.onBindViewHolderOfPairedDevicesRvAdapter(holder, position);
    }

    @Override
    public int onGetItemCountOfPairedDevicesRvAdapter() {
        return presenter.onGetItemCountOfPairedDevicesRvAdapter();
    }

    @Override
    public void onBindViewHolderOfAvailableDevicesRvAdapter(AvailableDevicesItemView holder, int position) {
        presenter.onBindViewHolderOfAvailableDevicesRvAdapter(holder, position);
    }

    @Override
    public int onGetItemCountOfAvailableDevicesRvAdapter() {
        return presenter.onGetItemCountOfAvailableDevicesRvAdapter();
    }

    @Override
    public void onTestButtonClick() {
        presenter.onTestButtonClick();
    }


    // ---------------------------------------------------------------------------------------------
    // OnConfigurationFragmentInteractionListener
    @Override
    public void onConfigTabsRvItemClick(String tab) {
        presenter.onConfigTabsRvItemClick(tab);
    }

    @Override
    public void OnConfigurationFragmentStart() {
        presenter.onConfigurationFragmentStart();
    }


    // ---------------------------------------------------------------------------------------------
    // OnConfigBuoyFragmentInteractionListener
    @Override
    public void onEtIdAfterTextChanged() {
        presenter.onEtIdAfterTextChanged();
    }

    @Override
    public void onBtnRestartClick() {
        presenter.onBtnRestartClick();
    }

    @Override
    public void onBtnDefaultSettingsClick() {
        presenter.onBtnDefaultSettingsClick();
    }

    @Override
    public void onConfigBuoyFragmentStart() {
        presenter.onConfigBuoyFragmentStart();
    }


    // ---------------------------------------------------------------------------------------------
    // OnConfigMainFragmentInteractionListener
    @Override
    public void onSpinBlinkerModeItemSelected(int position) {
        presenter.onSpinBlinkerModeItemSelected(position);
    }

    @Override
    public void onSpinBlinkerBrightnessItemSelected(int position) {
        presenter.onSpinBlinkerBrightnessItemSelected(position);
    }

    @Override
    public void afterEtBlinkerLxTextChanged() {
        presenter.afterEtBlinkerLxTextChanged();
    }

    // Lifecycle
    @Override
    public void onConfigMainFragmentStart() {
        presenter.onConfigMainFragmentStart();
    }


    // ---------------------------------------------------------------------------------------------
    // OnConsoleFragmentInteractionListener
    @Override
    public void onBtnSendCommandClick(String command) {
        presenter.onBtnSendCommandClick();
    }

    @Override
    public void onConsoleFragmentCreateView() {
        presenter.onConsoleFragmentCreateView();
    }

    @Override
    public void onConsoleFragmentStart() {
        presenter.onConsoleFragmentStart();
    }

    @Override
    public void onTvLogsLongClick() {
        presenter.onTvLogsLongClick();
    }


    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
        presenter.onMainActivityDestroy();
    }
    // ---------------------------------------------------------------------------------------------

}