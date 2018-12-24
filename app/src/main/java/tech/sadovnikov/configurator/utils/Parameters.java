package tech.sadovnikov.configurator.utils;

import android.support.annotation.NonNull;

public enum Parameters {

    ID("id", true),
    FIRMWARE_VERSION("firmware version",false),
    BLINKER_MODE("blinker mode", true);

    private boolean setable;
    @NonNull private String name;

    Parameters(@NonNull String name, boolean setable) {
        this.name = name;
        this.setable = setable;
    }

    public boolean isSetable() {
        return setable;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
