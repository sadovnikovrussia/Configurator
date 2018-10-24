package tech.sadovnikov.configurator;

import android.content.Intent;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

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

        // Показать (установить фрагмент в MainActivity)
        void showFragment(Fragment fragment);

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

        String getSpinBlinkerBrightnessValue();

        String getSpinBlinkerModeValue();
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

        void onSetParameter(String name, String value);

        void onEtIdAfterTextChanged();

        void onAddLogsLineEvent(String line);

        void onTestButtonClick();

        void onMainActivityResult(int requestCode, int resultCode, Intent data);

        void onConfigMainFragmentStart();

        void onBtnRestartClick();

        void onBtnDefaultSettingsClick();

        void onSpinBlinkerBrightnessItemSelected(int position);

        void onSpinBlinkerModeItemSelected(int position);
    }

    interface Configuration {
        String ID = "id";
        String FIRMWARE_VERSION = "firmware version";
        String BLINKER_MODE = "blinker mode";

        // TODO <Добавить параметр>
        String[] parametersList = new String[]{ID, FIRMWARE_VERSION, BLINKER_MODE};
    }

    interface Logs {
        void addLine(String line);

        String getLogsMessages();
    }

    interface RepositoryConfiguration {
        tech.sadovnikov.configurator.model.Configuration getUiConfiguration();

        void setUiConfiguration(tech.sadovnikov.configurator.model.Configuration uiConfiguration);

        tech.sadovnikov.configurator.model.Configuration getConfigurationForSetAndSave();

        String getParameterValue(String name);

        void setParameter(String name, String value);

        void setParameter(Parameter parameter);

        int getConfigurationSize();

        void setParameterWithoutCallback(String name, String value);
    }

}
