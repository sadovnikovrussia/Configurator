package tech.sadovnikov.configurator.model;

import java.util.ArrayList;
import java.util.Collections;

public class ConfigurationNew {
    private static final String TAG = "ConfigurationNew";

    // Parameter names
    public static final String ID = "id";
    public static final String FIRMWARE_VERSION = "firmware version";
    public static final String BLINKER_MODE = "blinker mode";
    public static final String BLINKER_BRIGHTNESS = "blinker brightness";

    public static final String[] PARAMETER_NAMES = new String[]{ID, FIRMWARE_VERSION, BLINKER_MODE, BLINKER_BRIGHTNESS};
    public static final ArrayList<String> PARAMETER_NAMES_LIST = new ArrayList<>();
    static {
        Collections.addAll(PARAMETER_NAMES_LIST, PARAMETER_NAMES);
    }



}
