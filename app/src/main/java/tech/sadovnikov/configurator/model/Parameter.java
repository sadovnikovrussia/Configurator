package tech.sadovnikov.configurator.model;

/**
 * Класс, представляющий параметр, например Id, Server и тд
 */
public class Parameter {

    private String name;
    private String value;
    private String type;

    public Parameter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }
}
