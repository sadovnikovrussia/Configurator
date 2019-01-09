package tech.sadovnikov.configurator.model.exception;

import tech.sadovnikov.configurator.model.entities.ParameterNew;

public class NoSettableParameterException extends Exception {

    private static String msg = " не является устанавливаемым";

    public NoSettableParameterException(ParameterNew parameterNew) {
        super(parameterNew.getName() + msg);
    }
}
