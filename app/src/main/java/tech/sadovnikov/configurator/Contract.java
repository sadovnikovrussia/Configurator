package tech.sadovnikov.configurator;

import android.content.Intent;
import android.os.Message;
import android.view.MenuItem;

import java.util.ArrayList;

import tech.sadovnikov.configurator.model.Parameter;
import tech.sadovnikov.configurator.presenter.BluetoothBroadcastReceiver;
import tech.sadovnikov.configurator.view.adapter.AvailableDevicesItemView;
import tech.sadovnikov.configurator.view.adapter.PairedDevicesItemView;


public interface Contract {

    interface View {
        String BLUETOOTH_FRAGMENT = "BluetoothFragment";
        String CONFIGURATION_FRAGMENT = "ConfigurationFragment";
        String CONSOLE_FRAGMENT = "ConsoleFragment";
        String CONFIG_BUOY_FRAGMENT = "Буй";
        String CONFIG_MAIN_FRAGMENT = "Основные";
        String CONFIG_NAVIGATION_FRAGMENT = "Навигация";
        int FILE_MANAGER_REQUEST_CODE = 1;

        // Показать (установить фрагмент в MainActivity)
        void showFragment(String fragment);

        // Вывести сообщение лога в консоль
        void showLog(String logsMessages);

        void addLogsLine(String line);

        // Скрыть устройства
        void hideDevices();

        // Показать устройства
        void showDevices();

        // Обновить список подключенных устройств
        void updatePairedDevices();

        // Обновить список доступных устройств
        void updateAvailableDevices();

        void setSwitchBtState(boolean state);

        void unregisterBluetoothBroadcastReceiver(BluetoothBroadcastReceiver bluetoothBroadcastReceiver);

        void setNavigationPosition(String fragment);

        void showToast(String toast);

        String getCommandLineText();

        void showParameter(String name, String value);

        String getEtIdText();

        void startFileManagerActivity();

        String getEtBlinkerLxText();
    }

    interface Presenter {
        // Lifecycle -------------------------------------------------------------------------------
        void onMainActivityCreate();

        void onMainActivityDestroy();

        void onBluetoothFragmentStart();

        void onBluetoothFragmentCreateView();

        void onAvailableDevicesFragmentDestroyView();

        void onConfigurationFragmentStart();

        void onConfigBuoyFragmentStart();

        void onConsoleFragmentCreateView();

        void onConsoleFragmentStart();


        void onSwitchBtStateChanged(boolean state);

        void onPairedDevicesRvItemClick(String bluetoothDeviceAddress);

        void onHandleMessage(Message msg);


        boolean onNavigationItemSelected(MenuItem item);

        void startDiscovery();

        void onDevicesPageSelected(int position);

        void onBindViewHolderOfAvailableDevicesRvAdapter(AvailableDevicesItemView holder, int position);

        int onGetItemCountOfAvailableDevicesRvAdapter();

        void onBindViewHolderOfPairedDevicesRvAdapter(PairedDevicesItemView holder, int position);

        int onGetItemCountOfPairedDevicesRvAdapter();

        void onTvLogsLongClick();

        void onAvailableDevicesRvItemClicked(String bluetoothDeviceAddress);

        void onBtnSendCommandClick();

        void onConfigTabsRvItemClick(String tab);

        void onConfigurationOptionsItemSelected(MenuItem item);

        void onEtIdAfterTextChanged();

        void onAddLogsLineEvent(String line);

        void onTestButtonClick();

        void onMainActivityResult(int requestCode, int resultCode, Intent data);

        void onConfigMainFragmentStart();

        void onBtnRestartClick();

        void onBtnDefaultSettingsClick();

        void onSpinBlinkerBrightnessItemSelected(int position);

        void onSpinBlinkerModeItemSelected(int position);

        void afterEtBlinkerLxTextChanged();
    }

    interface Logs {
        void addLine(String line);

        String getLogsMessages();
    }

    interface RepositoryConfiguration {

        void setUiConfiguration(tech.sadovnikov.configurator.model.Configuration uiConfiguration);

        tech.sadovnikov.configurator.model.Configuration getConfigurationForSetAndSave();

        String getParameterValue(String name);

        void setParameter(Parameter parameter);

        void setParameterWithoutCallback(String name, String value);

        ArrayList<String> getCommandListForReadConfiguration();

        ArrayList<String> getCommandListForSetConfiguration();

    }

}
