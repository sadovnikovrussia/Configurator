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

    private Contract.Presenter presenter;


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
        Log.d(TAG, "getParameterValue(): " + name + "=" + configuration.get(name));
        return configuration.get(name);
    }

    @Override
    public void setParameter(String name, String value) {
        Log.w(TAG, "setParameter: " + name + "=" + value);
        configuration.put(name, value);
        presenter.onSetParameter(name, value);
    }

    @Override
    public void setParameterWithoutCallback(String name, String value) {
        Log.d(TAG, "setParameterWithoutCallback: " + name + "=" + value);
        configuration.put(name, value);
    }

    @Override
    public int getSize() {
        return parametersList.length;
    }

}
