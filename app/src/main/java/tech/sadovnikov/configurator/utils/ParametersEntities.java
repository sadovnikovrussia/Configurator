package tech.sadovnikov.configurator.utils;

import android.support.annotation.NonNull;


public enum ParametersEntities {
    ID("ID", true, Integer.class),
    FIRMWARE_VERSION("FIRMWARE VERSION", false, String.class) {
        @Override
        public String createReadingCommand() {
            return "@VERSION";
        }
    },

    BLINKER_MODE("BLINKER MODE", true, Integer.class),
    BLINKER_BRIGHTNESS("BLINKER BRIGHTNESS", true, Integer.class),
    BLINKER_LX("BLINKER LX", true, Integer.class),
    MAX_DEVIATION("MAX DEVIATION", true, Integer.class),
    TILT_ANGLE("TILT ANGLE", true, Integer.class),
    IMPACT_POW("IMPACT POW", true, Integer.class),
    UPOWER_THLD("UPOWER THLD", true, Float.class),
    DEVIATION_INT("DEVIATION INT", true, Integer.class),
    MAX_ACTIVE("MAX ACTIVE", true, Integer.class),
    UPOWER("UPOWER", false, Float.class),

    CURRENT_POS("CURRENT POS", false, String.class),
    TRUE_POS("TRUE POS", true, Integer.class), // Логично сделать boolean?
    BASE_POS("BASE POS", true, String.class), // List?
    LAT_DEVIATION("LAT DEVIATION", true, Integer.class),
    LONG_DEVIATION("LONG DEVIATION", true, Integer.class),
    HDOP("HDOP", true, Integer.class),
    FIX_DELAY("FIX DELAY", true, Integer.class),
    SATELLITE_SYSTEM("SATELLITE SYSTEM", true, Integer.class);

    String endOfCommonReadingCommand = "?";

    @NonNull
    private String name;
    private boolean settable;
    @NonNull
    private Class valueType;

    ParametersEntities(@NonNull String name, boolean settable, @NonNull Class valueType) {
        this.name = name;
        this.settable = settable;
        this.valueType = valueType;
    }

    public boolean isSettable() {
        return settable;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public Class getValueType() {
        return valueType;
    }

    public String createReadingCommand() {
        return getName() + endOfCommonReadingCommand;
    }

}
