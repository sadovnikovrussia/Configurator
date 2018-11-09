package tech.sadovnikov.configurator.model;

import android.support.annotation.NonNull;
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
    public static final String SERVER = "server";
    public static final String CONNECT_ATTEMPTS = "connect attempts";
    public static final String SESSION_TIME = "session time";
    public static final String PACKET_TOUT = "packet tout";
    public static final String PRIORITY_CHNL = "priority chnl";
    public static final String NORMAL_INT = "normal int";
    public static final String ALARM_INT = "alarm int";
    public static final String SMS_CENTER = "sms center";
    public static final String CMD_NUMBER = "cmd number";
    public static final String ANSW_NUMBER = "answ number";
    public static final String PACKETS = "packets";
    public static final String APN = "apn";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String SIM_ATTEMPTS = "sim attempts";
    public static final String DELIV_TIMEOUT = "deliv timeout";

    // TODO <ДОБАВИТЬ ПАРАМЕТР>
    public static final String[] PARAMETER_NAMES = new String[]{
            ID, FIRMWARE_VERSION, BLINKER_MODE, BLINKER_BRIGHTNESS, BLINKER_LX, MAX_DEVIATION,
            TILT_ANGLE, IMPACT_POW, UPOWER_THLD, DEVIATION_INT, MAX_ACTIVE, UPOWER, BASE_POS,
            LONG_DEVIATION, LAT_DEVIATION, HDOP, FIX_DELAY, SATELLITE_SYSTEM, EVENTS_MASK, SERVER,
            CONNECT_ATTEMPTS, SESSION_TIME, PACKET_TOUT, PRIORITY_CHNL, NORMAL_INT, ALARM_INT,
            SMS_CENTER, CMD_NUMBER, ANSW_NUMBER, PACKETS, APN, LOGIN, PASSWORD, SIM_ATTEMPTS,
            DELIV_TIMEOUT};
    public static final ArrayList<String> PARAMETER_NAMES_LIST = new ArrayList<>();

    static {
        Collections.addAll(PARAMETER_NAMES_LIST, PARAMETER_NAMES);
    }

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


    Configuration getConfigurationForSave() {
        Configuration configuration = Configuration.getEmptyConfiguration();
        for (Parameter parameter : getParametersList()) {
            if (!parameter.isEmpty()) {
                configuration.addParameter(parameter);
            }
        }
        configuration.removeParameter(new Parameter(FIRMWARE_VERSION));
        configuration.removeParameter(new Parameter(UPOWER));
        configuration.removeParameter(new Parameter(BASE_POS));
        configuration.removeParameter(new Parameter(PACKETS));
        Log.i(TAG, "getConfigurationForSave() returned: " + configuration);
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

    @NonNull
    public String toString() {
        return "Configuration{<" + getSize() + ">" + parametersList + '}';
    }

    ArrayList<String> getCommandListForReadConfiguration() {
        ArrayList<String> commandListForReadConfiguration = new ArrayList<>();
        for (String parameterName : PARAMETER_NAMES_LIST) {
            switch (parameterName) {
                case FIRMWARE_VERSION:
                    commandListForReadConfiguration.add("@version");
                    break;
                case PACKETS:
                    commandListForReadConfiguration.add("@packets?");
                    break;
                default:
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
            boolean isAlp = name.equals(APN) || name.equals(LOGIN) || name.equals(PASSWORD);
            boolean containsDefault = value.toLowerCase().contains("cellular operator defaults");
            boolean nonEmpty = !value.isEmpty();
            boolean changeable = !name.equals(FIRMWARE_VERSION) && !name.equals(UPOWER) && !name.equals(BASE_POS) && !name.equals(PACKETS);
            if (changeable && nonEmpty) {
                if (isAlp && containsDefault) value = "\"\"";
                commandListForSetConfiguration.add(name + "=" + value);
            }
        }
        Log.d(TAG, "getCommandListForSetConfiguration() returned: " + commandListForSetConfiguration);
        return commandListForSetConfiguration;
    }
}
