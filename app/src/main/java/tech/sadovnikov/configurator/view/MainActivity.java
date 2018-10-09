package tech.sadovnikov.configurator.view;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import tech.sadovnikov.configurator.Contract;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.model.Configuration;
import tech.sadovnikov.configurator.model.Parameter;
import tech.sadovnikov.configurator.presenter.BluetoothBroadcastReceiver;
import tech.sadovnikov.configurator.presenter.BluetoothService;
import tech.sadovnikov.configurator.presenter.Presenter;


public class MainActivity extends AppCompatActivity implements
        Contract.View,
        BluetoothFragment.OnBluetoothFragmentInteractionListener,
        ConfigurationFragment.OnConfigurationFragmentInteractionListener,
        ConsoleFragment.OnConsoleFragmentInteractionListener {

    private static final String TAG = "MainActivity";

    Contract.Presenter presenter;

    FrameLayout container;
    BottomNavigationView navigation;

    BluetoothFragment bluetoothFragment;
    ConfigurationFragment configurationFragment;
    ConfigBuoyFragment configBuoyFragment;
    ConfigMainFragment configMainFragment;
    ConfigNavigationFragment configNavigationFragment;
    ConsoleFragment consoleFragment;
    Menu menuActionsWithConfiguration;

    BluetoothService bluetoothService;

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
        //-----------------------------------------------
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                presenter.onNavigationItemSelected(item);
                switch (item.getItemId()) {
                    case R.id.navigation_bluetooth:
                        setFragment(bluetoothFragment);
                        return true;
                    case R.id.navigation_configuration:
                        setFragment(configurationFragment);
                        return true;
                    case R.id.navigation_console:
                        setFragment(consoleFragment);
                        return true;
                }
                return false;
            }
        });
        setFragment(this.bluetoothFragment);
    }

    // Выбор фрагмента
    void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        //navigation.setSelectedItemId(fragment.getId());
        fragmentTransaction.commit();
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



    // ---------------------------------------------------------------------------------------------
    // OnBluetoothFragmentInteractionListener
    @Override
    public void onSwitchBtStateChanged(boolean state) {
        presenter.onSwitchBtStateChanged(state);
    }

    @Override
    public void onPairedDevicesRvItemClicked(BluetoothDevice bluetoothDevice) {
        presenter.onPairedDevicesRvItemClick(bluetoothDevice);
    }

    @Override
    public void onBluetoothFragmentCreateView() {
        presenter.onBluetoothFragmentCreateView();
    }

    @Override
    public void onBluetoothFragmentStart() {
        presenter.onBluetoothFragmentStart();
    }

    // ---------------------------------------------------------------------------------------------


    // ---------------------------------------------------------------------------------------------
    // OnConfigurationFragmentInteractionListener
    @Override
    public void onRvConfigTabsItemClick() {

    }
    // ---------------------------------------------------------------------------------------------


    // ---------------------------------------------------------------------------------------------
    // OnConsoleFragmentInteractionListener
    @Override
    public void onBtnSendCommandClick(String command) {
        bluetoothService.sendData(command);
    }

    @Override
    public void onConsoleFragmentCreateView() {
        presenter.onConsoleFragmentCreateView();
    }

    // ---------------------------------------------------------------------------------------------


    // ---------------------------------------------------------------------------------------------
    // Contract.View
    @Override
    public void showLog(String logsMessages) {
        consoleFragment.showLog(logsMessages);
    }

    @Override
    public void addLogsLine(String line) {
        consoleFragment.addLogsLine(line);
    }

    @Override
    public void showParameter(Parameter parameter) {

    }

    @Override
    public void showPairedDevices() {
        bluetoothFragment.showPairedDevices();
    }

    @Override
    public void hideAllDevices() {
        bluetoothFragment.hidePairedDevices();
    }

    @Override
    public void showAvailableDevices() {

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