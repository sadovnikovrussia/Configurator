package tech.sadovnikov.configurator.model;

import android.content.Intent;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Класс, представляющий конфигурацию устройства
 */
public class Configuration implements Serializable {
    private static final String TAG = "Configuration";

    public static final String ID = "id";
    public static final String FIRMWARE_VERSION = "firmware version";
    public static final String BLINKER_MODE = "blinker mode";

    // TODO <Добавить параметр>
    String[] parametersList = new String[]{ID, FIRMWARE_VERSION, BLINKER_MODE};


    // Параметры
    private Parameter id = new Parameter(ID);
    private Parameter firmwareVersion = new Parameter(FIRMWARE_VERSION);
    private Parameter blinkerMode = new Parameter(BLINKER_MODE);

    private ArrayList<Parameter> parametersArrayList = new ArrayList<>();

    public Configuration() {
        init();
        // Log.d(TAG, "Configuration: " + name + " = " + configurationMap.get(name));
    }

    public static Configuration getEmptyConfiguration() {
        Configuration configuration = new Configuration();
        configuration.clear();
        return configuration;
    }

    public void init() {
        parametersArrayList.clear();
        parametersArrayList.add(id);
        parametersArrayList.add(firmwareVersion);
        parametersArrayList.add(blinkerMode);
    }

    private void clear(){
        parametersArrayList.clear();
    }

    public Configuration getConfigurationForSetAndSave() {
        Configuration configurationForSetAndSave = new Configuration();
        ArrayList<Parameter> tempParameters = new ArrayList<>();
        for (Parameter parameter : this.getParametersArrayList()) {
            if (!parameter.isEmpty()) {
                tempParameters.add(parameter);
            }
        }
        configurationForSetAndSave.setParametersArrayList(tempParameters);
        configurationForSetAndSave.removeParameter(firmwareVersion);
        Log.d(TAG, "getConfigurationForSetAndSave() returned: " + configurationForSetAndSave);
        return configurationForSetAndSave;
    }

//    private void removeParameter(String name) {
//        switch (name) {
//            case ID:
//                parametersArrayList.remove(id);
//                break;
//            case FIRMWARE_VERSION:
//                parametersArrayList.remove(firmwareVersion);
//                break;
//            case BLINKER_MODE:
//                parametersArrayList.remove(blinkerMode);
//                break;
//        }
//    }


    private void setParametersArrayList(ArrayList<Parameter> parametersArrayList) {
        this.parametersArrayList = parametersArrayList;
    }

    public ArrayList<Parameter> getParametersArrayList() {
        return parametersArrayList;
    }

    private void removeParameter(Parameter parameter) {
        parametersArrayList.remove(parameter);
    }

    public String getSettingCommand(int index) {
        return parametersArrayList.get(index).getName() + "=" + parametersArrayList.get(index).getValue();
    }

    public String getReadingCommand(int index) {
        String name = parametersArrayList.get(index).getName();
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

    public String getParameterValue(String name) {
        // Log.d(TAG, "getParameterValue(): " + name + "=" + configurationMap.get(name));
        Parameter parameter = new Parameter(name);
        int i = parametersArrayList.indexOf(parametersArrayList);
        if (i != -1) {
            return parametersArrayList.get(i).getValue();
        } else {
            return "";
        }
    }

    public void setParameter(Parameter parameter) {
        // Log.i(TAG, "setParameter: parametersArrayList.contains(" + parameter + ") = " + parametersArrayList.contains(parameter));
        if (parametersArrayList.contains(parameter)) {
            //Log.i(TAG, "setParameter: " + parameter + ".equals(" + id +") = " + parameter.equals(id));
            //Log.d(TAG, "setParameter: ДО: " + this);
            int index = parametersArrayList.indexOf(parameter);
            parametersArrayList.remove(parameter);
            parametersArrayList.add(index, parameter);
            // parametersArrayList.add(parameter);
            //Log.d(TAG, "setParameter: ПОСЛЕ: " + this);
//            if (parameter.equals(id)) {
//                // parametersArrayList.get(parametersArrayList.indexOf(parameter))
//                Log.d(TAG, "setParameter: ДО: " + this);
//                id = parameter;
//                Log.d(TAG, "setParameter: ПОСЛЕ: " + this);
//                parametersArrayList.get(0).
//            } else if (parameter.equals(firmwareVersion)) {
//                firmwareVersion = parameter;
//            } else if (parameter.equals(blinkerMode)) {
//                blinkerMode = parameter;
//            }
        } else {
            Log.d(TAG, "setParameter: В конфигурации нет параметра " + parameter + "configuration = " + this);
        }
    }

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
            default:
                Log.d(TAG, "setParameter: В конфигурации нет параметра " + name + " = " + value + ", " + "configuration = " + this);

        }
        Log.w(TAG, "setParameter: " + name + "=" + value);
    }

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

    public int getSize() {
        return parametersArrayList.size();
    }

    public String toString() {
        return "Configuration{<" + getSize() + ">" + parametersArrayList + '}';
    }

    public boolean contains(Parameter parameter) {
        return parametersArrayList.contains(parameter);
    }

    public void addParameter(Parameter parameter) {
        if (!parametersArrayList.contains(parameter)){
            parametersArrayList.add(parameter);
        }
    }
}
