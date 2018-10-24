package tech.sadovnikov.configurator.model;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Класс, представляющий конфигурацию устройства
 */
public class Configuration implements Serializable {
    private static final String TAG = "Configuration";

    public static final String ID = "id";
    public static final String FIRMWARE_VERSION = "firmware version";
    public static final String BLINKER_MODE = "blinker mode";

    public static final String[] PARAMETERS_NAMES = new String[]{ID, FIRMWARE_VERSION, BLINKER_MODE};
    public static final ArrayList<String> PARAMETERS_NAMES_LIST = new ArrayList<>();
    static {
        Collections.addAll(PARAMETERS_NAMES_LIST, PARAMETERS_NAMES);
    }

    // Параметры
    private Parameter id = new Parameter(ID);
    private Parameter firmwareVersion = new Parameter(FIRMWARE_VERSION);
    private Parameter blinkerMode = new Parameter(BLINKER_MODE);

    private ArrayList<Parameter> parametersList = new ArrayList<>();

    Configuration() {
        init();
        // Log.d(TAG, "Configuration: " + name + " = " + configurationMap.get(name));
    }

    public static Configuration getEmptyConfiguration() {
        Configuration configuration = new Configuration();
        configuration.clear();
        return configuration;
    }

    private void init() {
        parametersList.clear();
        parametersList.add(id);
        parametersList.add(firmwareVersion);
        parametersList.add(blinkerMode);
    }

    void clear(){
        parametersList.clear();
    }


    Configuration getConfigurationForSetAndSave() {
        Configuration configuration = Configuration.getEmptyConfiguration();
        for (Parameter parameter : getParametersList()) {
            if (!parameter.isEmpty()) {
                configuration.addParameter(parameter);
            }
        }
        configuration.removeParameter(firmwareVersion);
        Log.i(TAG, "getConfigurationForSetAndSave() returned: " + configuration);
        return configuration;
    }

    ArrayList<Parameter> getParametersList() {
        return parametersList;
    }

    private void removeParameter(Parameter parameter) {
        parametersList.remove(parameter);
    }

    public String getSettingCommand(int index) {
        return parametersList.get(index).getName() + "=" + parametersList.get(index).getValue();
    }

    public String getReadingCommand(int index) {
        String name = parametersList.get(index).getName();
        if (name.equals(FIRMWARE_VERSION)) {
            return "@" + name.substring(9);
        }
        return name + "?";
    }

    public String getReadingCommand(Parameter parameter) {
        String name = parameter.getName();
        switch (name) {
            case FIRMWARE_VERSION:
                return "@" + name.substring(9);
            default:
                return name + "?";
        }
    }

    String getParameterValue(String parameterName) {
        // Log.d(TAG, "getParameterValue(): " + parameterName + "=" + configurationMap.get(parameterName));
        Parameter parameter = new Parameter(parameterName);
        if (parametersList.contains(parameter)) {
            return parametersList.get(parametersList.indexOf(parameter)).getValue();
        }
        else {
            Log.w(TAG, "getParameterValue: Параметр " + parameterName + " не существует в конфигурации" );
            return "";
        }
    }

    void setParameter(Parameter parameter) {
        if (PARAMETERS_NAMES_LIST.contains(parameter.getName())){
            if (parametersList.contains(parameter)) {
                int index = parametersList.indexOf(parameter);
                parametersList.get(index).setValue(parameter.getValue());
                //parametersList.remove(parameter);
                //parametersList.add(index, parameter);
            } else {
                Log.w(TAG, "setParameter: В конфигурации нет параметра " + parameter + "configuration = " + this);
            }
        }

    }

    void setParameter(String name, String value) {
        Parameter parameter = new Parameter(name, value);
        if (parametersList.contains(parameter)) {
            int index = parametersList.indexOf(parameter);
            parametersList.get(index).setValue(parameter.getValue());
            //parametersList.remove(parameter);
            //parametersList.add(index, parameter);
        } else {
            Log.w(TAG, "setParameter: В конфигурации нет параметра " + parameter + "configuration = " + this);
        }
    }

    public void addParameter(Parameter parameter) {
        if (PARAMETERS_NAMES_LIST.contains(parameter.getName())){
            if (!parametersList.contains(parameter)){
                parametersList.add(parameter);
                //Log.d(TAG, "addParameter: " + parameter + ", " + this);
            }
        }
    }



    public int getSize() {
        return parametersList.size();
    }

    public String toString() {
        return "Configuration{<" + getSize() + ">" + parametersList + '}';
    }

    public boolean contains(Parameter parameter) {
        return parametersList.contains(parameter);
    }

    public boolean contains(String parameterName) {
        return parametersList.contains(new Parameter(parameterName));
    }

}
