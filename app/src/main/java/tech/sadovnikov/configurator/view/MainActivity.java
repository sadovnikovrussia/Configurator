package tech.sadovnikov.configurator.view;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import tech.sadovnikov.configurator.Contract;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.model.Parameter;
import tech.sadovnikov.configurator.presenter.BluetoothBroadcastReceiver;
import tech.sadovnikov.configurator.presenter.BluetoothService;
import tech.sadovnikov.configurator.presenter.DataAnalyzer;
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

    BluetoothService bluetoothService;
    UiHandler uiHandler;
    BluetoothBroadcastReceiver bluetoothBroadcastReceiver;

    public MainActivity() {
        Log.v(TAG, "onConstructor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG, "onCreate");
        presenter = new Presenter(this);
        bluetoothBroadcastReceiver = new BluetoothBroadcastReceiver();
        uiHandler = new UiHandler(this, presenter);
        bluetoothService = new BluetoothService(uiHandler);
        registerBluetoothBroadcastReceiver();
        initUi();
//        ArrayList<BluetoothDevice> bondedDevices = BluetoothService.getBondedDevices();
//        for (BluetoothDevice device : bondedDevices) {
//            Log.i(TAG, device.getName() + ", " + device.getAddress());
//        }
    }

    // Регистрация ресиверов
    private void registerBluetoothBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(bluetoothBroadcastReceiver, intentFilter);
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

//    @Override
//    public void onSwitchBtStateChanged(boolean state) {
//        if (state) {
//            bluetoothService.enableBt();
//            bluetoothFragment.showDevices(bluetoothService.getBondedDevices());
//        } else {
//            bluetoothFragment.hideDevices();
//            bluetoothService.disableBt();
//        }
//    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSwitchBtStateChanged(boolean state) {

    }

    @Override
    public void onPairedDevicesRvItemClicked(int position) {

    }

    @Override
    public void onRvConfigTabsItemClick() {

    }

    @Override
    public void onBtnConnectClick() {
        Log.d(TAG, "onBtnConnectClick");
        if (bluetoothService.isEnabled()) {
            bluetoothService.connectTo("38:1C:4A:2C:C8:09");
        }
    }

    @Override
    public void onBtnSendCommandClick(String command) {
        bluetoothService.sendData(command);
    }

    // ---------------------------------------------------------------------------------------------
    // Contract.View
    @Override
    public void showLog(String line) {
        consoleFragment.showLog(line);
    }

    @Override
    public void showParameter(Parameter parameter) {

    }

    @Override
    public void showPairedDevices() {

    }

    @Override
    public void showAvailableDevices() {

    }
    // ---------------------------------------------------------------------------------------------


//    @Override
//    public void onLvConfigsItemClick(int i) {
//
//        switch (i) {
//            case 0:
//                configBuoyFragment = ConfigBuoyFragment.newInstance("103");
//                setFragment(configBuoyFragment);
//                break;
//            case 1:
//                configMainFragment = new ConfigMainFragment();
//                setFragment(configMainFragment);
//                break;
//            case 2:
//                configNavigationFragment = new ConfigNavigationFragment();
//                setFragment(configNavigationFragment);
//                break;
//        }
//
//    }

    public static class UiHandler extends Handler {
        WeakReference<Activity> activityWeakReference;

        Contract.Presenter presenter;

        UiHandler(Activity activity, Contract.Presenter presenter) {
            activityWeakReference = new WeakReference<>(activity);
            this.presenter = presenter;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = (MainActivity) activityWeakReference.get();
            if (activity != null) {
                presenter.onHandleMessage(msg);
//                Object obj = msg.obj;
//                switch (msg.what) {
//                    // Отправка полученных данных в консоль
//                    case DataAnalyzer.WHAT_MAIN_LOG:
//                        activity.consoleFragment.showLog((String) obj);
//                        break;
//                    // Загрузка данных в LiveData
                    //case DataAnalyzer.WHAT_COMMAND_DATA:
                    //HashMap msgData = (HashMap) obj;
                    //    String data = (String) msgData.get(DataAnalyzer.DATA);
                    //    String command = (String) msgData.get(DataAnalyzer.PARAMETER_NAME);
                    //    break;
                //}
            }
        }

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
        unregisterReceiver(bluetoothBroadcastReceiver);
        bluetoothService.closeAllConnections();
    }
    // ---------------------------------------------------------------------------------------------

}