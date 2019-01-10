package tech.sadovnikov.configurator.model.entities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import java.util.Objects;

import tech.sadovnikov.configurator.model.exception.NoSettableParameterException;
import tech.sadovnikov.configurator.utils.ParametersEntities;

public class Parameter implements CommandCreating {

    private ParametersEntities entity;
    @Nullable
    private String value;

    private Parameter(ParametersEntities entity) {
        this.entity = entity;
    }

    private Parameter(ParametersEntities entity, @Nullable String value) {
        this.entity = entity;
        this.value = value;
    }

    public static Parameter of(ParametersEntities parametersEntity){
        return new Parameter(parametersEntity);
    }

    public static Parameter of(ParametersEntities parameterEntity, String value) {
        return new Parameter(parameterEntity, value);
    }

    @Override
    public String createReadingCommand() {
        return entity.getName() + endOfCommonReadingCommand;
    }

    @Override
    public String createSettingCommand() throws NoSettableParameterException {
        if (entity.isSettable()) return entity.getName() + eq + value;
        else throw new NoSettableParameterException(this);
    }

    @NonNull
    public String getName() {
        return entity.getName();
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
        return "Parameter{" +
                "name='" + entity.getName() + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parameter)) return false;
        Parameter parameter1 = (Parameter) o;
        return entity == parameter1.entity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity);
    }
}
