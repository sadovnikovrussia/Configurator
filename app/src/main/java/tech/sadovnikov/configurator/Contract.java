package tech.sadovnikov.configurator;

import android.bluetooth.BluetoothDevice;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import java.util.ArrayList;

import tech.sadovnikov.configurator.model.Configuration;
import tech.sadovnikov.configurator.model.Parameter;
import tech.sadovnikov.configurator.presenter.BluetoothBroadcastReceiver;
import tech.sadovnikov.configurator.view.adapter.AvailableDevicesRvAdapter;


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
        void setFragment(Fragment fragment);

        // Вывести сообщение лога в консоль
        void showLog(String logsMessages);

        void addLogsLine(String line);

        // Вывести значение параметра на экран в соответствующий компонент
        void showParameter(Parameter parameter);

        // Показать спаренные устройства
        void showPairedDevices();

        // Скрыть все устройства устройства
        void hideAllDevices();

        // Показать доступные устройства
        void showAvailableDevices(ArrayList<BluetoothDevice> availableBluetoothDevices);

        void setSwitchBtState(boolean state);

        void unregisterBluetoothBroadcastReceiver(BluetoothBroadcastReceiver bluetoothBroadcastReceiver);
    }

    interface Presenter {
        void onSwitchBtStateChanged(boolean state);

        void onPairedDevicesRvItemClick(BluetoothDevice bluetoothDevice);

        void onHandleMessage(Message msg);

        void onBluetoothFragmentCreateView();

        void onConsoleFragmentCreateView();

        void onMainActivityCreate();

        void onMainActivityDestroy();

        void onBluetoothFragmentStart();

        boolean onNavigationItemSelected(MenuItem item);

        void startDiscovery();

        void onDevicesPageSelected(int position);

        void onBindViewHolderOfAvailableDevicesRvAdapter(AvailableDevicesRvAdapter.BluetoothDeviceViewHolder holder, int position);

        int onGetItemCountOfAvailableDevicesRvAdapter();

    }

    interface Log {
        void addLine(String line);

        String getLogsMessages();
    }
}
