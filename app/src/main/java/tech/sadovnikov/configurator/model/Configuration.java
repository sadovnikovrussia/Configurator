package tech.sadovnikov.configurator.model;

import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;

import tech.sadovnikov.configurator.Contract;

/**
 * Класс, представляющий конфигурацию устройства
 */
public class Configuration implements Serializable, Contract.Configuration {
    private static final String TAG = "Configuration";

    public static final String ID = "id";
    public static final String FIRMWARE_VERSION = "firmware version";

    private String[] parametersList = new String[]{ID, FIRMWARE_VERSION};

    private HashMap<String, String> configuration;

    public Configuration() {
        this.configuration = new HashMap<>();
        for (String parameter : parametersList) {
            configuration.put(parameter, "");
        }
    }

    @Override
    public String getParameter(String name) {
        return configuration.get(name);
    }

    @Override
    public void setParameter(String name, String value) {
        Log.w(TAG, "setParameter: " + name + "=" + value);
        configuration.put(name, value);
    }


}
