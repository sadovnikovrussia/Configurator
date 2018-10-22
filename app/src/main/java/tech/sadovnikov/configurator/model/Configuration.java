package tech.sadovnikov.configurator.model;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import tech.sadovnikov.configurator.Contract;

/**
 * Класс, представляющий конфигурацию устройства
 */
public class Configuration implements Serializable, Contract.Configuration {
    private static final String TAG = "Configuration";

    private OnConfigurationInteractionListener onConfigurationInteractionListener;


    // Параметры
    private Parameter id = new Parameter(Contract.Configuration.ID);
    private Parameter firmwareVersion = new Parameter(Contract.Configuration.FIRMWARE_VERSION);
    private Parameter blinkerMode = new Parameter(Contract.Configuration.BLINKER_MODE);

    private ArrayList<Parameter> parametersArrayList = new ArrayList<>();

    public Configuration() {
        parametersArrayList.add(id);
        parametersArrayList.add(firmwareVersion);
        parametersArrayList.add(blinkerMode);
        // Log.d(TAG, "Configuration: " + name + " = " + configurationMap.get(name));
        // parametersArrayList.add(name);
    }

    public Configuration(OnConfigurationInteractionListener onConfigurationInteractionListener) {
        this.onConfigurationInteractionListener = onConfigurationInteractionListener;
        parametersArrayList.add(id);
        parametersArrayList.add(firmwareVersion);
        parametersArrayList.add(blinkerMode);
        // Log.d(TAG, "Configuration: " + parameter + " = " + configurationMap.get(parameter));
    }

    @Override
    public Configuration getConfigurationForSetAndSave() {
        Configuration configurationForSet = new Configuration(onConfigurationInteractionListener);
        configurationForSet.removeParameter(firmwareVersion);
        Log.d(TAG, "getConfigurationForSetAndSave() returned: " + configurationForSet);
        return configurationForSet;
    }


    private void removeParameter(String name) {
        switch (name) {
            case ID:
                parametersArrayList.remove(id);
                break;
            case FIRMWARE_VERSION:
                parametersArrayList.remove(firmwareVersion);
                break;
            case BLINKER_MODE:
                parametersArrayList.remove(blinkerMode);
                break;
        }
    }

    private void removeParameter(Parameter parameter) {
        parametersArrayList.remove(parameter);
    }

    @Override
    public String getSettingCommand(int index) {
        return parametersArrayList.get(index).getName() + "=" + parametersArrayList.get(index).getValue();
    }

    @Override
    public String getReadingCommand(int index) {
        String name = parametersArrayList.get(index).getName();
        if (name.equals(FIRMWARE_VERSION)) {
            return "@" + name.substring(9);
        }
        return name + "?";
    }

    @Override
    public String getReadingCommand(Parameter parameter) {
        String name = parameter.getName();
        switch (name) {
            case FIRMWARE_VERSION:
                return "@" + name.substring(9);
            default:
                return name + "?";
        }
    }

    @Override
    public String getParameterValue(String name) {
        // Log.d(TAG, "getParameterValue(): " + name + "=" + configurationMap.get(name));
        switch (name) {
            case ID:
                return id.getValue();
            case FIRMWARE_VERSION:
                return firmwareVersion.getValue();
            case BLINKER_MODE:
                return blinkerMode.getValue();
            default:
                return "";
        }
    }

    @Override
    public void setParameter(String name, String value) {
        switch (name) {
            case ID:
                id.setValue(value);
                break;
            case FIRMWARE_VERSION:
                firmwareVersion.setValue(value);
                break;
            case BLINKER_MODE:
                blinkerMode.setValue(value);
                break;
        }
        Log.w(TAG, "setParameter: " + name + "=" + value);
        onConfigurationInteractionListener.onSetParameter(name, value);
    }

    @Override
    public void setParameterWithoutCallback(String name, String value) {
        switch (name) {
            case ID:
                id.setValue(value);
                break;
            case FIRMWARE_VERSION:
                firmwareVersion.setValue(value);
                break;
            case BLINKER_MODE:
                blinkerMode.setValue(value);
                break;
        }
        Log.d(TAG, "setParameterWithoutCallback: " + name + "=" + value);
    }

    @Override
    public int getSize() {
        return parametersArrayList.size();
    }


    @Override
    public String toString() {
        return parametersArrayList.toString();
    }

    public interface OnConfigurationInteractionListener {

        void onSetParameter(String name, String value);
    }
}
