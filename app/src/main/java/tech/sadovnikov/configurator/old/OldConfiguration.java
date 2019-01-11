package tech.sadovnikov.configurator.old;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Класс, представляющий конфигурацию устройства
 */
public class OldConfiguration {
    private static final String TAG = "OldConfiguration";

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
    public static final String CURRENT_POS = "current pos";
    public static final String TRUE_POS = "true pos";
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
            TILT_ANGLE, IMPACT_POW, UPOWER_THLD, DEVIATION_INT, MAX_ACTIVE, UPOWER,  TRUE_POS,
            CURRENT_POS, BASE_POS,
            LONG_DEVIATION, LAT_DEVIATION, HDOP, FIX_DELAY, SATELLITE_SYSTEM, EVENTS_MASK, SERVER,
            CONNECT_ATTEMPTS, SESSION_TIME, PACKET_TOUT, PRIORITY_CHNL, NORMAL_INT, ALARM_INT,
            SMS_CENTER, CMD_NUMBER, ANSW_NUMBER, PACKETS, APN, LOGIN, PASSWORD, SIM_ATTEMPTS,
            DELIV_TIMEOUT};
    public static final ArrayList<String> PARAMETER_NAMES_LIST = new ArrayList<>();

    static {
        Collections.addAll(PARAMETER_NAMES_LIST, PARAMETER_NAMES);
    }

    private ArrayList<OldParameter> parametersList = new ArrayList<>();

    public OldConfiguration() {
        init();
        // LogList.d(TAG, "OldConfiguration: " + name + " = " + configurationMap.get(name));
    }

    public static OldConfiguration getEmptyConfiguration() {
        OldConfiguration oldConfiguration = new OldConfiguration();
        oldConfiguration.clear();
        return oldConfiguration;
    }

    private void init() {
        parametersList.clear();
        for (String name : PARAMETER_NAMES_LIST) {
            parametersList.add(new OldParameter(name));
        }
    }

    private void clear() {
        parametersList.clear();
    }


    public OldConfiguration getConfigurationForSave() {
        OldConfiguration oldConfiguration = OldConfiguration.getEmptyConfiguration();
        for (OldParameter oldParameter : getParametersList()) {
            if (!oldParameter.isEmpty()) {
                oldConfiguration.addParameter(oldParameter);
            }
        }
        oldConfiguration.removeParameter(new OldParameter(FIRMWARE_VERSION));
        oldConfiguration.removeParameter(new OldParameter(UPOWER));
        oldConfiguration.removeParameter(new OldParameter(CURRENT_POS));
        oldConfiguration.removeParameter(new OldParameter(PACKETS));
        Log.i(TAG, "getConfigurationForSave() returned: " + oldConfiguration);
        return oldConfiguration;
    }

    public ArrayList<OldParameter> getParametersList() {
        return parametersList;
    }

    private void removeParameter(OldParameter oldParameter) {
        parametersList.remove(oldParameter);
    }

    public String getSettingCommand(int index) {
        return parametersList.get(index).getName() + "=" + parametersList.get(index).getValue();
    }

    public String getParameterValue(String parameterName) {
        // LogList.d(TAG, "getParameterValue(): " + parameterName + "=" + configurationMap.get(parameterName));
        OldParameter oldParameter = new OldParameter(parameterName);
        if (parametersList.contains(oldParameter)) {
            return parametersList.get(parametersList.indexOf(oldParameter)).getValue();
        } else {
            Log.w(TAG, "getParameterValue: Параметр " + parameterName + " не существует в конфигурации");
            return "";
        }
    }

    public void setParameter(OldParameter oldParameter) {
        if (PARAMETER_NAMES_LIST.contains(oldParameter.getName())) {
            if (parametersList.contains(oldParameter)) {
                int index = parametersList.indexOf(oldParameter);
                parametersList.get(index).setValue(oldParameter.getValue());
            } else {
                Log.w(TAG, "setParameter: В конфигурации нет параметра " + oldParameter + "configuration = " + this);
            }
        }
    }

    public void addParameter(OldParameter oldParameter) {
        if (PARAMETER_NAMES_LIST.contains(oldParameter.getName())) {
            if (!parametersList.contains(oldParameter)) {
                parametersList.add(oldParameter);
            } else {
                Log.w(TAG, "addParameter: " + oldParameter + " уже существует в конфигурации " + this);
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
        return "OldConfiguration{<" + getSize() + ">" + parametersList + '}';
    }

    public ArrayList<String> getCommandListForReadConfiguration() {
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

    public ArrayList<String> getCommandListForSetConfiguration() {
        ArrayList<String> commandListForSetConfiguration = new ArrayList<>();
        for (OldParameter oldParameter : parametersList) {
            String value = oldParameter.getValue();
            String name = oldParameter.getName();
            boolean isAlp = name.equals(APN) || name.equals(LOGIN) || name.equals(PASSWORD);
            boolean containsDefault = value.toLowerCase().contains("cellular operator defaults");
            boolean nonEmpty = !value.isEmpty();
            boolean changeable = !name.equals(FIRMWARE_VERSION) && !name.equals(UPOWER) && !name.equals(CURRENT_POS) && !name.equals(PACKETS);
            if (changeable && nonEmpty) {
                if (isAlp && containsDefault) value = "\"\"";
                commandListForSetConfiguration.add(name + "=" + value);
            }
        }
        Log.d(TAG, "getCommandListForSetConfiguration() returned: " + commandListForSetConfiguration);
        return commandListForSetConfiguration;
    }
}
