package tech.sadovnikov.configurator.model;

import tech.sadovnikov.configurator.model.exception.NoSettableParameterException;

interface CommandCreating {
    String endOfCommonReadingCommand = "?";
    String eq = "=";

    String createReadingCommand();

    String createSettingCommand() throws NoSettableParameterException;

}
