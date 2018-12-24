package tech.sadovnikov.configurator.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import tech.sadovnikov.configurator.model.exception.NoSettableParameterException;
import tech.sadovnikov.configurator.utils.ParameterNames;
import tech.sadovnikov.configurator.utils.Parameters;

public class ParameterNew implements CommandCreating {

    private Parameters parameters;
    @NonNull
    private final String name;
    @Nullable
    private String value;
    private final boolean setable;

    public ParameterNew(Parameters parameters) {
        this.parameters = parameters;
        this.setable = parameters.isSetable();
        this.name = parameters.getName();
    }

    public ParameterNew(Parameters parameters, @Nullable String value) {
        this.value = value;
        this.setable = parameters.isSetable();
        name = parameters.getName();
    }

//    public ParameterNew(@NonNull String name) {
//        this.name = parameters.getName();
//        this.name = name;
//        setable = ParameterNames.settable.contains(name);
//    }
//
//    public ParameterNew(@NonNull String name, @Nullable String value) {
//        this.name = parameters.getName();
//        this.name = name;
//        this.value = value;
//    }
//
//    public ParameterNew(@NonNull String name, boolean setable) {
//        this.name = parameters.getName();
//        this.name = name;
//        this.setable = setable;
//    }
//
//    public ParameterNew(@NonNull String name, @Nullable String value, boolean setable) {
//        this.name = parameters.getName();
//        this.name = name;
//        this.value = value;
//        this.setable = setable;
//    }

    @Override
    public String createReadingCommand() {
        return name + endOfCommonReadingCommand;
    }

    @Override
    public String createSettingCommand() throws NoSettableParameterException {
        if (setable) return name + eq + value;
        else throw new NoSettableParameterException(this);
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

    boolean isValueNull() {
        return value == null;
    }

    @NonNull
    @Override
    public String toString() {
        return "ParameterNew{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
