package tech.sadovnikov.configurator.model;

import tech.sadovnikov.configurator.model.entities.Parameter;
import tech.sadovnikov.configurator.utils.ParametersEntities;

public class CmdCreator {
    private static String endOfCommonReadingCommand = "?";
    private static String equals = "=";

    public static String forSetting(Parameter parameter) {
        ParametersEntities entity = parameter.getEntity();
        String value = parameter.getValue();
        String name = parameter.getName();
        switch (entity) {
            case APN:
                return getAlpCommand(value, name);
            case LOGIN:
                return getAlpCommand(value, name);
            case PASSWORD:
                return getAlpCommand(value, name);
            default:
                return name + equals + value;
        }
    }

    private static String getAlpCommand(String value, String name) {
        switch (value) {
            case "\"Cellular operator defaults\"":
                return name + equals + "\"\"";
            default:
                return name + equals + value;
        }
    }
}
