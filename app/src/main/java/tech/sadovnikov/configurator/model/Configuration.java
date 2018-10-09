package tech.sadovnikov.configurator.model;

import java.io.Serializable;
import java.util.HashMap;

import tech.sadovnikov.configurator.Contract;

/**
 * Класс, представляющий конфигурацию устройства
 */
public class Configuration implements Serializable {
    private static final String TAG = "Configuration";

    HashMap<String, String> configuration;

    public Configuration() {
        this.configuration = new HashMap<>();
        this.configuration.put("id", "");
        this.configuration.put("version", "");
    }

    String getParameter(String name) {
        return configuration.get(name);
    }

    void setParameter(String name, String value) {
        configuration.put(name, value);
    }


    // private Parameter id;
    // private Parameter server;

//    void setParameter(String name, String value) {
//        switch (name) {
//            case "Id":
//                this.id.setValue(value);
//                break;
//            case "Server":
//                this.server.setValue(value);
//                break;
//        }
//    }

}
