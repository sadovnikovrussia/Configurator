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

    Contract.Presenter presenter;

    private String[] parametersList = new String[]{ID, FIRMWARE_VERSION};

    private HashMap<String, String> configuration;

    public Configuration(Contract.Presenter presenter) {
        this.presenter = presenter;
        this.configuration = new HashMap<>();
        for (String parameter : parametersList) {
            configuration.put(parameter, "");
        }
    }


    @Override
    public String getSetCommand(int index) {
        return parametersList[index] + "=" + configuration.get(parametersList[index]);
    }

    @Override
    public String getRequestCommand(int index) {
        String name = parametersList[index];
        if (name.equals(FIRMWARE_VERSION)) {
            return "@" + name.substring(9);
        }
        return name + "?";
    }

    @Override
    public String getParameterValue(String name) {
        Log.w(TAG, "getParameterValue(): " + name + "=" + configuration.get(name));
        return configuration.get(name);
    }

    @Override
    public void setParameter(String name, String value) {
        Log.w(TAG, "setParameter: " + name + "=" + value);
        configuration.put(name, value);
        presenter.onSetParameter(name, value);
    }

    @Override
    public int getSize() {
        return parametersList.length;
    }

    @Override
    public void setParameterWithoutCallback(String name, String value) {
        Log.w(TAG, "setParameterWithoutCallback: " + name + "=" + value);
        configuration.put(name, value);
    }


}
