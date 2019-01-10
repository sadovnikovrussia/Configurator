package tech.sadovnikov.configurator.model.exception;

import tech.sadovnikov.configurator.model.entities.Parameter;

public class NoSettableParameterException extends Exception {

    private static String msg = " не является устанавливаемым";

    public NoSettableParameterException(Parameter parameter) {
        super(parameter.getName() + msg);
    }
}
