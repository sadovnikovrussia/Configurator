package tech.sadovnikov.configurator.utils;

import tech.sadovnikov.configurator.model.entities.Configuration;

public class ConfigurationCreator {

    public static Configuration mainConfiguration() {
        Configuration main = new Configuration();
        for (ParametersEntities parametersEntity : ParametersEntities.values()) {
            main.addParameter(parametersEntity);
        }
        return main;
    }
}
