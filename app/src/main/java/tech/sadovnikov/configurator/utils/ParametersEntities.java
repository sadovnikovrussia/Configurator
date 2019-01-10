package tech.sadovnikov.configurator.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public enum ParametersEntities {

    ID("id", true),
    FIRMWARE_VERSION("firmware version", false),
    BLINKER_MODE("blinker mode", true);

    @NonNull
    private String name;
    @Nullable
    private String value;
    private boolean settable;

    ParametersEntities(@NonNull String name, boolean settable) {
        this.name = name;
        this.settable = settable;
    }

    ParametersEntities(@NonNull String name, @Nullable String value, boolean settable) {
        this.settable = settable;
        this.name = name;
        this.value = value;

    }

    public boolean isSettable() {
        return settable;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getValue() {
        return value;
    }

    public void setValue(@Nullable String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ParametersEntities{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
