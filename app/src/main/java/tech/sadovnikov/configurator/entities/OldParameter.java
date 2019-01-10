package tech.sadovnikov.configurator.entities;

import android.support.annotation.NonNull;

import java.util.Objects;

public class OldParameter {

    private final String name;
    private String value;

    public OldParameter(String name) {
        this.name = name;
        this.value = "";
    }

    public OldParameter(String name, String value) {
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
        if (!(o instanceof OldParameter)) return false;
        OldParameter oldParameter = (OldParameter) o;
        return Objects.equals(getName().toLowerCase(), oldParameter.getName().toLowerCase());
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
