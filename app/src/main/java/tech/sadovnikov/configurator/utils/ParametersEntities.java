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
    BLINKER_MODE("BLINKER MODE", true, Integer.class);

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
