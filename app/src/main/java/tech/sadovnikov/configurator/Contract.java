package tech.sadovnikov.configurator;

import android.bluetooth.BluetoothDevice;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import java.util.ArrayList;

import tech.sadovnikov.configurator.model.Configuration;
import tech.sadovnikov.configurator.model.Parameter;
import tech.sadovnikov.configurator.presenter.BluetoothBroadcastReceiver;
import tech.sadovnikov.configurator.view.adapter.AvailableDevicesItemView;
import tech.sadovnikov.configurator.view.adapter.AvailableDevicesRvAdapter;
import tech.sadovnikov.configurator.view.adapter.PairedDevicesItemView;


public interface Contract {

    interface Repository {

        // Загрузить (установить) конфигурацию в устройство
        void setConfiguration(Configuration configuration);

        // Считать конфигурацию из устройства
        Configuration loadConfiguration();

        // Сохранить конфигурацию в файл.cfg
        void saveConfiguration(Configuration configuration);

        // Открыть конфигурацию из файла.cfg
        Configuration openConfiguration();

        // Получить текущую конфигурацию устройства (когда уже что-то считано из устройства)
        Configuration getCurrentConfiguration();
    }

    interface View {

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
    }

    interface Presenter {
        // Lifecycle -------------------------------------------------------------------------------
        void onMainActivityCreate();

        void onMainActivityDestroy();

        void onBluetoothFragmentStart();

        void onBluetoothFragmentCreateView();

        void onAvailableDevicesFragmentDestroyView();

        void OnConfigurationFragmentStart();

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

    }

    interface Log {
        void addLine(String line);

        String getLogsMessages();
    }
}
