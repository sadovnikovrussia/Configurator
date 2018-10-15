package tech.sadovnikov.configurator.model;

import tech.sadovnikov.configurator.Contract;

/**
 * Класс , представляющий собой железку (Буй)
 */
public class Device implements Contract.Device {

    private static Device instance;

    // Текущая конфигурация устройства
    private Configuration deviceConfiguration = new Configuration();

    // Получение синглтона
    public static Device getInstance() {
        if (instance == null) {
            instance = new Device();
        }
        return instance;
    }


    // Загрузить (установить) конфигурацию в устройство
    @Override
    public void setConfiguration(Contract.Configuration configuration) {

    }

    // Считать конфигурацию из устройства
    @Override
    public void loadConfiguration() {

    }

    // Сохранить конфигурацию в файл.cfg
    @Override
    public void saveConfiguration(Contract.Configuration configuration) {
    }

    // Открыть конфигурацию из файла.cfg
    @Override
    public Configuration openConfiguration() {
        return null;
    }

    // Получить текущую конфигурацию устройства (когда уже что-то считано из устройства)
    @Override
    public Configuration getCurrentConfiguration() {
        return deviceConfiguration;
    }
}
