package tech.sadovnikov.configurator.model;

import java.io.Serializable;

import tech.sadovnikov.configurator.Contract;

/**
 * Класс, представляющий конфигурацию устройства
 */
public class Configuration implements Serializable {

    private Parameter id;
    private Parameter server;

    void setParameter(String name, String value) {
        switch (name) {
            case "Id":
                this.id.setValue(value);
                break;
            case "Server":
                this.server.setValue(value);
                break;
        }
    }

}
