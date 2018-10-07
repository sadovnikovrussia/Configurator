package tech.sadovnikov.configurator.model;

import tech.sadovnikov.configurator.Contract;

/**
 * Класс , представляющий собой железку (Буй)
 */
public class Device implements Contract.Repository {

    private static Device instance = new Device();

    // Текущая конфигурация устройства
    private Configuration configuration;

    // Получение синглтона
    public static Device getInstance() {
        return instance;
    }

    // Загрузить (установить) конфигурацию в устройство
    @Override
    public void setConfiguration(Configuration configuration) {
    }

    // Считать конфигурацию из устройства
    @Override
    public Configuration loadConfiguration() {
        return null;
    }

    // Сохранить конфигурацию в файл.cfg
    @Override
    public void saveConfiguration(Configuration configuration) {
    }

    // Открыть конфигурацию из файла.cfg
    @Override
    public Configuration openConfiguration() {
        return null;
    }

    // Получить текущую конфигурацию устройства (когда уже что-то считано из устройства)
    @Override
    public Configuration getCurrentConfiguration() {
        return configuration;
    }
}
