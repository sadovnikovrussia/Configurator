package tech.sadovnikov.configurator;

import android.bluetooth.BluetoothDevice;
import android.os.Message;

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
        // Вывести сообщение лога в консоль
        void showLog(String line);

        // Вывести значение параметра на экран в соответствующий компонент
        void showParameter(Parameter parameter);

        // Показать спаренные устройства
        void showPairedDevices();

        // Показать доступные устройства
        void showAvailableDevices();
    }

    interface Presenter {
        void onSwitchBtStateChanged();

        void onPairedDevicesRvItemClick(BluetoothDevice bluetoothDevice);

        void onHandleMessage(Message msg);
    }
}
