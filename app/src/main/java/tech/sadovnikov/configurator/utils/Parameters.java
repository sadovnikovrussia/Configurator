package tech.sadovnikov.configurator.utils;

import android.support.annotation.NonNull;

public enum Parameters {

    ID("id", true),
    FIRMWARE_VERSION("firmware version",false),
    BLINKER_MODE("blinker mode", true);

    private boolean settable;
    @NonNull private String name;

    Parameters(@NonNull String name, boolean settable) {
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
