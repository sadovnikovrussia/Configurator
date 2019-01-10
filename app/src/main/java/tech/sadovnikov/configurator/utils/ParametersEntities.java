package tech.sadovnikov.configurator.utils;

import android.support.annotation.NonNull;

public enum ParametersEntities {

    ID("id", true),
    FIRMWARE_VERSION("firmware version", false),
    BLINKER_MODE("blinker mode", true);

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
