package tech.sadovnikov.configurator.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import tech.sadovnikov.configurator.Contract;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.model.Configuration;
import tech.sadovnikov.configurator.presenter.BluetoothBroadcastReceiver;
import tech.sadovnikov.configurator.presenter.Presenter;
import tech.sadovnikov.configurator.view.adapter.AvailableDevicesItemView;
import tech.sadovnikov.configurator.view.adapter.PairedDevicesItemView;


public class MainActivity extends AppCompatActivity implements Contract.View,
        BluetoothFragment.OnBluetoothFragmentInteractionListener,
        ConfigurationFragment.OnConfigurationFragmentInteractionListener,
        ConsoleFragment.OnConsoleFragmentInteractionListener,
        ConfigBuoyFragment.OnConfigBuoyFragmentInteractionListener {

    private static final String TAG = "MainActivity";

    public static final String BLUETOOTH_FRAGMENT = "BluetoothFragment";
    public static final String CONFIGURATION_FRAGMENT = "ConfigurationFragment";
    public static final String CONSOLE_FRAGMENT = "ConsoleFragment";
    public static final String CONFIG_BUOY_FRAGMENT = "Буй";
    public static final String CONFIG_MAIN_FRAGMENT = "Основные";
    public static final String CONFIG_NAVIGATION_FRAGMENT = "Навигация";

    Contract.Presenter presenter;

    // UI
    FrameLayout container;
    BottomNavigationView navigation;
    Menu menuActionsWithConfiguration;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG, "onCreate");
        presenter = new Presenter(this);
        initUi();
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

    // Показать фрагмент
    @Override
    public void showFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (fragment instanceof BluetoothFragment) {
            bluetoothFragment = (BluetoothFragment) fragment;
            fragmentTransaction.replace(R.id.container, bluetoothFragment);
        } else if (fragment instanceof ConfigurationFragment) {
            configurationFragment = (ConfigurationFragment) fragment;
            fragmentTransaction.replace(R.id.container, configurationFragment);
        } else if (fragment instanceof ConsoleFragment) {
            consoleFragment = (ConsoleFragment) fragment;
            fragmentTransaction.replace(R.id.container, consoleFragment);
        }
        fragmentTransaction.addToBackStack(null);
        //navigation.setSelectedItemId(fragment.getId());
        fragmentTransaction.commit();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_configuration_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void showParameter(String name, String value) {
        switch (name) {
            case Configuration.ID:
                EditText etID = findViewById(R.id.et_id);
                if (etID != null) {
                    etID.setText(value);
                }
                break;
            case Configuration.FIRMWARE_VERSION:
                EditText etVersion = findViewById(R.id.et_version);
                if (etVersion != null) {
                    etVersion.setText(value);
                }
                break;
        }
    }

    @Override
    public String getEtIdText() {
        return configBuoyFragment.getEtIdText();
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
    public void startDiscovery() {
        presenter.startDiscovery();
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
    public void onConfigBuoyFragmentStart() {
        presenter.onConfigBuoyFragmentStart();
    }

    @Override
    public void onEtIdAfterTextChanged() {
        presenter.onEtIdAfterTextChanged();
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
    // Contract.View
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

    // ---------------------------------------------------------------------------------------------


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
        // unregisterReceiver(bluetoothBroadcastReceiver);
    }
    // ---------------------------------------------------------------------------------------------

}