package tech.sadovnikov.configurator.model.entities;

public class Value<T> {
    private final T value;

    private Value(T value) {
        this.value = value;
    }

    public static <T> Value<T> of(T value) {
        return new Value<>(value);
    }

    public T getValue() {
        return value;
    }
}
