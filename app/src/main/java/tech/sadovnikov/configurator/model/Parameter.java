package tech.sadovnikov.configurator.model;

import java.util.Objects;

public class Parameter {

    private final String name;
    private String value;

    Parameter(String name) {
        this.name = name;
        this.value = "";
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
        return Objects.equals(getName(), parameter.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName());
    }
}
