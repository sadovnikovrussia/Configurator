package tech.sadovnikov.configurator.model;

import tech.sadovnikov.configurator.model.entities.Parameter;
import tech.sadovnikov.configurator.utils.ParametersEntities;

public class CmdCreator {
    private static String equals = "=";

    public static String forSetting(Parameter parameter) {
        ParametersEntities entity = parameter.getEntity();
        String value = parameter.getValue();
        String name = parameter.getName();
        switch (entity) {
            case APN:
                return alpForSetting(value, name);
            case LOGIN:
                return alpForSetting(value, name);
            case PASSWORD:
                return alpForSetting(value, name);
            default:
                return name + equals + value;
        }
    }

    public static String forSaving(Parameter parameter) {
        String value = parameter.getValue();
        String name = parameter.getName();
        return name + equals + value;
    }

    private static String alpForSetting(String value, String name) {
        switch (value) {
            case "\"Cellular operator defaults\"":
                return name + equals + "\"\"";
            case "":
                return name + equals + "''";
            default:
                return name + equals + value.replaceAll("\"", "");
        }
    }

}
