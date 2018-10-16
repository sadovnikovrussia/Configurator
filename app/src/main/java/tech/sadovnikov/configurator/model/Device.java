package tech.sadovnikov.configurator.model;

import tech.sadovnikov.configurator.Contract;

/**
 * Класс , представляющий собой железку (Буй)
 */
public class Device implements Contract.Device {

    private static Device instance;


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

    @Override
    public Contract.Configuration getCurrentConfiguration() {
        return null;
    }

}
