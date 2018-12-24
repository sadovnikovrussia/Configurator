package tech.sadovnikov.configurator.utils;

import java.util.HashSet;
import java.util.Set;

public final class ParameterNames {
    public static final String FIRMWARE_VERSION = "firmware version";
    public static final String UPOWER = "upower";
    public static final String PACKETS = "packets";
    public static final String CURRENT_POS = "current pos";
    public static final String ID = "id";
    public static final String BLINKER_MODE = "blinker mode";
    public static final String BLINKER_BRIGHTNESS = "blinker brightness";
    public static final String BLINKER_LX = "blinker lx";
    public static final String MAX_DEVIATION = "max deviation";
    public static final String TILT_ANGLE = "tilt angle";
    public static final String IMPACT_POW = "impact pow";
    public static final String UPOWER_THLD = "upower thld";
    public static final String DEVIATION_INT = "deviation int";
    public static final String MAX_ACTIVE = "max active";
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
    public static final String APN = "apn";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String SIM_ATTEMPTS = "sim attempts";
    public static final String DELIV_TIMEOUT = "deliv timeout";

    public static Set<String> settable = new HashSet<>();

    static {
        settable.add(ID);
        settable.add(BLINKER_MODE);
        settable.add(BLINKER_BRIGHTNESS);
        settable.add(BLINKER_LX);
        settable.add(MAX_DEVIATION);
        settable.add(TILT_ANGLE);
        settable.add(IMPACT_POW);
        settable.add(UPOWER_THLD);
        settable.add(DEVIATION_INT);
        settable.add(MAX_ACTIVE);
        settable.add(TRUE_POS);
        settable.add(BASE_POS);
        settable.add(LONG_DEVIATION);
        settable.add(LAT_DEVIATION);
        settable.add(HDOP);
        settable.add(FIX_DELAY);
        settable.add(SATELLITE_SYSTEM);
        settable.add(EVENTS_MASK);
        settable.add(SERVER);
        settable.add(CONNECT_ATTEMPTS);
        settable.add(SESSION_TIME);
        settable.add(PACKET_TOUT);
        settable.add(PRIORITY_CHNL);
        settable.add(NORMAL_INT);
        settable.add(ALARM_INT);
        settable.add(SMS_CENTER);
        settable.add(CMD_NUMBER);
        settable.add(ANSW_NUMBER);
        settable.add(APN);
        settable.add(LOGIN);
        settable.add(PASSWORD);
        settable.add(SIM_ATTEMPTS);
        settable.add(DELIV_TIMEOUT);
    }

    public static Set<String> unsettable = new HashSet<>();
    static {
        unsettable.add(FIRMWARE_VERSION);
        unsettable.add(UPOWER);
        unsettable.add(PACKETS);
        unsettable.add(CURRENT_POS);
    }

    public static Set<String> names = new HashSet<>();
    static {
        names.addAll(unsettable);
        names.addAll(settable);
    }

    private ParameterNames() {
    }

}
