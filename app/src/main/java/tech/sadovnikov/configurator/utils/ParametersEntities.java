package tech.sadovnikov.configurator.utils;

import android.support.annotation.NonNull;

public enum ParametersEntities {

    ID("ID", true),
    FIRMWARE_VERSION("FIRMWARE VERSION", false),
    BLINKER_MODE("BLINKER MODE", true);

    @NonNull
    private String name;
    private boolean settable;

    ParametersEntities(@NonNull String name, boolean settable) {
        this.name = name;
        this.settable = settable;
    }

    public boolean isSettable() {
        return settable;
    }

    @NonNull
    public String getName() {
        return name;
    }

}
