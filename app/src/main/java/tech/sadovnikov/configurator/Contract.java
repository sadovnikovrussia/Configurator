package tech.sadovnikov.configurator;

import android.bluetooth.BluetoothDevice;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import tech.sadovnikov.configurator.model.Configuration;
import tech.sadovnikov.configurator.model.Parameter;


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
        void showAvailableDevices();

        void setSwitchBtState(boolean state);
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
    }

    interface Log {
        void addLine(String line);

        String getLogsMessages();
    }
}
