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
import java.util.HashMap;

import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.presenter.BluetoothBroadcastReceiver;
import tech.sadovnikov.configurator.presenter.BluetoothService;
import tech.sadovnikov.configurator.presenter.DataAnalyzer;

public class MainActivity extends AppCompatActivity implements BluetoothFragment.OnBluetoothFragmentInteractionListener {

    private static final String TAG = "MainActivity";

    FrameLayout container;
    BottomNavigationView navigation;

    BluetoothFragment bluetoothFragment;
    ConfigurationFragment configurationFragment;
    ConfigBuoyFragment configBuoyFragment;
    ConfigMainFragment configMainFragment;
    ConfigNavigationFragment configNavigationFragment;
    ConsoleFragment consoleFragment;

    BluetoothService mBluetoothService;
    Handler mUiHandler;
    BluetoothBroadcastReceiver bluetoothBroadcastReceiver;

    public MainActivity() {
        Log.v(TAG, "onConstructor");
    }

    // Приемник изменения состояния Bluetooth от системы

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG, "onCreate");
        bluetoothBroadcastReceiver = new BluetoothBroadcastReceiver();
        mUiHandler = new UiHandler(this);
        mBluetoothService = new BluetoothService(mUiHandler);
        registerBluetoothBroadcastReceiver();
        initUi();
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
//            mBluetoothService.enableBt();
//            bluetoothFragment.showDevices(mBluetoothService.getBondedDevices());
//        } else {
//            bluetoothFragment.hideDevices();
//            mBluetoothService.disableBt();
//        }
//    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSwitchBtStateChanged(boolean state) {

    }

    @Override
    public void onRvBtItemClicked(int position) {

    }


//    public void onLvBtItemClicked(int i) {
//        mBluetoothService.connectTo(i);
//    }

//    @Override
//    public void onClickBtnSendCommand(String line) {
//        mBluetoothService.sendData(line);
//    }

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

    static class UiHandler extends Handler {

        WeakReference<Activity> activityWeakReference;

        UiHandler(Activity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "Получили в handle");
            MainActivity activity = (MainActivity) activityWeakReference.get();
            if (activity != null) {
                Object obj = msg.obj;
                switch (msg.what) {
                    // Отправка полученных данных в консоль
                    case DataAnalyzer.WHAT_MAIN_LOG:
                        activity.consoleFragment.showLog((String) obj);
                        break;
                    // Загрузка данных в LiveData
                    case DataAnalyzer.WHAT_COMMAND_DATA:
                        HashMap msgData = (HashMap) obj;
                        String data = (String) msgData.get(DataAnalyzer.DATA);
                        String command = (String) msgData.get(DataAnalyzer.PARAMETER_NAME);
                        break;
                }
            }
        }
    }


    // Lifecycle -----------------------------------------------------------------------------------
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
    }
    // ---------------------------------------------------------------------------------------------

}
