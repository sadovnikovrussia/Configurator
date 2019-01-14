package tech.sadovnikov.configurator.model.entities;

import tech.sadovnikov.configurator.model.exception.NoSettableParameterException;

public interface CommandCreatable {
    String endOfCommonReadingCommand = "?";
    String eq = "=";

    String createReadingCommand();

    String createSettingCommand() throws NoSettableParameterException;

}
