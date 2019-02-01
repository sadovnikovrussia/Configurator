package tech.sadovnikov.configurator.model.entities;

import android.support.annotation.NonNull;


import java.util.Objects;

import tech.sadovnikov.configurator.model.exception.NoSettableParameterException;
import tech.sadovnikov.configurator.utils.ParametersEntities;

public class Parameter implements CommandCreatable {

    @NonNull
    private ParametersEntities entity;
    @NonNull
    private String value;

//    private Parameter(@NonNull ParametersEntities entity) {
//        this.entity = entity;
//    }

    private Parameter(@NonNull ParametersEntities entity, @NonNull String value) {
        this.entity = entity;
        this.value = value;
    }

//    public static Parameter of(ParametersEntities parametersEntity){
//        return new Parameter(parametersEntity);
//    }

    public static Parameter of(ParametersEntities parameterEntity, String value) {
        return new Parameter(parameterEntity, value);
    }


    @Override
    public String createSettingCommand() {
        if (entity.isSettable()) return entity.getName() + eq + value;
        return "";
    }

    @NonNull
    public ParametersEntities getEntity() {
        return entity;
    }

    @NonNull
    public String getName() {
        return entity.getName();
    }

    @NonNull
    public String getValue() {
        return value;
    }

    public void setValue(@NonNull String value) {
        this.value = value;
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
