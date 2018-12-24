package tech.sadovnikov.configurator.utils;

import tech.sadovnikov.configurator.model.ParameterNew;
import tech.sadovnikov.configurator.model.exception.NoSettableParameterException;

public class CommandCreator {
    private static String endOfCommonReadingCommand = "?";
    private static String eq = "=";

    public static String createReadingCommand(ParameterNew parameterNew) {
        return parameterNew.getName() + endOfCommonReadingCommand;
    }

//    public static String createSettingCommand(ParameterNew parameterNew) throws NoSettableParameterException {
//        if (parameterNew.) return name + eq + value;
//        else throw new NoSettableParameterException(this);
//    }
}
