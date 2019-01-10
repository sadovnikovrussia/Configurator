package tech.sadovnikov.configurator.utils;

import tech.sadovnikov.configurator.model.entities.Parameter;

public class CommandCreator {
    private static String endOfCommonReadingCommand = "?";
    private static String eq = "=";

    public static String createReadingCommand(Parameter parameter) {
        return parameter.getName() + endOfCommonReadingCommand;
    }

//    public static String createSettingCommand(Parameter parameterNew) throws NoSettableParameterException {
//        if (parameterNew.) return name + eq + value;
//        else throw new NoSettableParameterException(this);
//    }
}
