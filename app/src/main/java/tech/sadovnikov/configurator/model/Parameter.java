package tech.sadovnikov.configurator.model;

import android.support.annotation.NonNull;

import java.util.Objects;

public class Parameter {

    private final String name;
    private String value;

    Parameter(String name) {
        this.name = name;
        this.value = "";
    }

    public Parameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parameter)) return false;
        Parameter parameter = (Parameter) o;
        return Objects.equals(getName().toLowerCase(), parameter.getName().toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @NonNull
    @Override
    public String toString() {
        return "Parameter{" + name + "=" + value + "}";
    }

    boolean isEmpty() {
        return value.isEmpty();
    }
}
