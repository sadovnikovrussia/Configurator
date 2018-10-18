package tech.sadovnikov.configurator.model;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import tech.sadovnikov.configurator.Contract;

/**
 * Класс, представляющий конфигурацию устройства
 */
public class Configuration implements Serializable, Contract.Configuration {
    private static final String TAG = "Configuration";

    private OnConfigurationInteractionListener onConfigurationInteractionListener;
    private LinkedHashMap<String, String> configurationMap;
    private ArrayList<String> parametersArrayList = new ArrayList<>();


    public Configuration(OnConfigurationInteractionListener onConfigurationInteractionListener) {
        this.onConfigurationInteractionListener = onConfigurationInteractionListener;
        this.configurationMap = new LinkedHashMap<>();
        for (String parameter : parametersList) {
            configurationMap.put(parameter, "");
            // Log.d(TAG, "Configuration: " + parameter + " = " + configurationMap.get(parameter));
            parametersArrayList.add(parameter);
        }
    }

    @Override
    public Configuration getConfigurationForSet() {
        Configuration configurationForSet = new Configuration(onConfigurationInteractionListener);
        Log.d(TAG, "ConfigurationForSet: " + configurationForSet.hashCode() + configurationForSet.toString());
        Log.d(TAG, "Configuration: " + configurationForSet.hashCode() + this.toString());
        configurationForSet.clearConfiguration();
        for (String parameter : parametersList) {
            Log.d(TAG, "getConfigurationForSet: " + parameter + " = " + this.getParameterValue(parameter));
            if (this.getParameterValue(parameter) != null) {
                if (!this.getParameterValue(parameter).equals("")) {
                    configurationForSet.addParameter(parameter, this.getParameterValue(parameter));
                }
            }
        }
        Log.d(TAG, "getConfigurationForSet() returned1: " + configurationForSet);
        configurationForSet.removeParameter(FIRMWARE_VERSION);
        Log.d(TAG, "getConfigurationForSet() returned2: " + configurationForSet);
        return configurationForSet;
    }

    @Override
    public String getSettingCommand(int index) {
        return parametersArrayList.get(index) + "=" + configurationMap.get(parametersArrayList.get(index));
    }

    void clearConfiguration(){
        this.parametersArrayList.clear();
        this.configurationMap.clear();
    }

    private void removeParameter(String name){
        this.configurationMap.remove(name);
        this.parametersArrayList.remove(name);
    }

    void addParameter(String name, String value){
        this.parametersArrayList.add(name);
        this.configurationMap.put(name, value);
    }

    @Override
    public String getReadingCommand(int index) {
        String name = parametersList[index];
        if (name.equals(FIRMWARE_VERSION)) {
            return "@" + name.substring(9);
        }
        return name + "?";
    }

    @Override
    public String getParameterValue(String name) {
        // Log.d(TAG, "getParameterValue(): " + name + "=" + configurationMap.get(name));
        return configurationMap.get(name);
    }

    @Override
    public void setParameter(String name, String value) {
        Log.w(TAG, "setParameter: " + name + "=" + value);
        configurationMap.put(name, value);
        onConfigurationInteractionListener.onSetParameter(name, value);
    }

    @Override
    public void setParameterWithoutCallback(String name, String value) {
        Log.d(TAG, "setParameterWithoutCallback: " + name + "=" + value);
        configurationMap.put(name, value);
    }

    @Override
    public int getSize() {
        return configurationMap.size();
    }


    @Override
    public String toString() {
        return configurationMap.toString();
    }

    public interface OnConfigurationInteractionListener {

        void onSetParameter(String name, String value);
    }
}
