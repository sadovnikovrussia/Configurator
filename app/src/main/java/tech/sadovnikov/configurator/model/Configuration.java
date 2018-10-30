package tech.sadovnikov.configurator.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Класс, представляющий конфигурацию устройства
 */
public class Configuration {
    private static final String TAG = "Configuration";

    // TODO <ДОБАВИТЬ ПАРАМЕТР>
    // Parameter names
    public static final String ID = "id";
    public static final String FIRMWARE_VERSION = "firmware version";
    public static final String BLINKER_MODE = "blinker mode";
    public static final String BLINKER_BRIGHTNESS = "blinker brightness";
    public static final String BLINKER_LX = "blinker lx";
    public static final String MAX_DEVIATION = "max deviation";
    public static final String TILT_ANGLE = "tilt angle";
    public static final String IMPACT_POW = "impact pow";
    public static final String UPOWER_THLD = "upower thld";
    public static final String DEVIATION_INT = "deviation int";
    public static final String MAX_ACTIVE = "max active";
    public static final String UPOWER = "upower";
    public static final String BASE_POS = "base pos";
    public static final String LONG_DEVIATION = "long deviation";
    public static final String LAT_DEVIATION = "lat deviation";
    public static final String HDOP = "hdop";
    public static final String FIX_DELAY = "fix delay";
    public static final String SATELLITE_SYSTEM = "satellite system";
    public static final String EVENTS_MASK = "events mask";

    // TODO <ДОБАВИТЬ ПАРАМЕТР>
    public static final String[] PARAMETER_NAMES = new String[]{
            ID, FIRMWARE_VERSION, BLINKER_MODE, BLINKER_BRIGHTNESS, BLINKER_LX, MAX_DEVIATION,
            TILT_ANGLE, IMPACT_POW, UPOWER_THLD, DEVIATION_INT, MAX_ACTIVE, UPOWER, BASE_POS,
            LONG_DEVIATION, LAT_DEVIATION, HDOP, FIX_DELAY, SATELLITE_SYSTEM, EVENTS_MASK};
    public static final ArrayList<String> PARAMETER_NAMES_LIST = new ArrayList<>();

    static {
        Collections.addAll(PARAMETER_NAMES_LIST, PARAMETER_NAMES);
    }

//    // Параметры
//    private Parameter id = new Parameter(ID);
//    private Parameter firmwareVersion = new Parameter(FIRMWARE_VERSION);
//    private Parameter blinkerMode = new Parameter(BLINKER_MODE);
//    private Parameter blinkerBrightness = new Parameter(BLINKER_BRIGHTNESS);

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
        for (String name : PARAMETER_NAMES_LIST) {
            parametersList.add(new Parameter(name));
        }
    }

    private void clear() {
        parametersList.clear();
    }


    Configuration getConfigurationForSetAndSave() {
        Configuration configuration = Configuration.getEmptyConfiguration();
        for (Parameter parameter : getParametersList()) {
            if (!parameter.isEmpty()) {
                configuration.addParameter(parameter);
            }
        }
        configuration.removeParameter(new Parameter(FIRMWARE_VERSION));
        configuration.removeParameter(new Parameter(UPOWER));
        configuration.removeParameter(new Parameter(BASE_POS));
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

    String getParameterValue(String parameterName) {
        // Log.d(TAG, "getParameterValue(): " + parameterName + "=" + configurationMap.get(parameterName));
        Parameter parameter = new Parameter(parameterName);
        if (parametersList.contains(parameter)) {
            return parametersList.get(parametersList.indexOf(parameter)).getValue();
        } else {
            Log.w(TAG, "getParameterValue: Параметр " + parameterName + " не существует в конфигурации");
            return "";
        }
    }

    void setParameter(Parameter parameter) {
        if (PARAMETER_NAMES_LIST.contains(parameter.getName())) {
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
        if (PARAMETER_NAMES_LIST.contains(name)) {
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
    }

    public void addParameter(Parameter parameter) {
        if (PARAMETER_NAMES_LIST.contains(parameter.getName())) {
            if (!parametersList.contains(parameter)) {
                parametersList.add(parameter);
            } else {
                Log.w(TAG, "addParameter: " + parameter + " уже существует в конфигурации " + this);
            }
        } else {
            Log.w(TAG, "addParameter: Данного параметра не существует");
        }
    }

    public int getSize() {
        return parametersList.size();
    }

    public String toString() {
        return "Configuration{<" + getSize() + ">" + parametersList + '}';
    }

    ArrayList<String> getCommandListForReadConfiguration() {
        ArrayList<String> commandListForReadConfiguration = new ArrayList<>();
        for (String parameterName : PARAMETER_NAMES_LIST) {
            if (parameterName.equals(FIRMWARE_VERSION))
            {
                commandListForReadConfiguration.add("@version");
            } else {
                commandListForReadConfiguration.add(parameterName + "?");
            }
        }
        return commandListForReadConfiguration;
    }

    ArrayList<String> getCommandListForSetConfiguration() {
        ArrayList<String> commandListForSetConfiguration = new ArrayList<>();
        for (Parameter parameter : parametersList) {
            String value = parameter.getValue();
            String name = parameter.getName();
            if (!value.isEmpty() && !name.equals(FIRMWARE_VERSION) && !name.equals(UPOWER)) {
                commandListForSetConfiguration.add(name + "=" + value);
            }
        }
        Log.d(TAG, "getCommandListForSetConfiguration() returned: " + commandListForSetConfiguration);
        return commandListForSetConfiguration;
    }
}
