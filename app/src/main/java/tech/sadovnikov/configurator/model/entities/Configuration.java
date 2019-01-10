package tech.sadovnikov.configurator.model.entities;

import java.util.HashSet;
import java.util.Set;

import tech.sadovnikov.configurator.utils.ParametersEntities;

public class Configuration {
    private static final String TAG = Configuration.class.getSimpleName();

    private Set<Parameter> parameters = new HashSet<>();

    public static Configuration createMainConfiguration() {
        Configuration main = new Configuration();
        for (ParametersEntities parametersEntity : ParametersEntities.values()) {
            main.addParameter(parametersEntity);
        }
        return main;
    }
    public boolean addParameter(ParametersEntities parameterEntity){
        return parameters.add(Parameter.of(parameterEntity));
    }

    public void setParameter(ParametersEntities parameterEntity, String value){
        Parameter p = Parameter.of(parameterEntity, value);
        parameters.remove(p);
        parameters.add(p);
    }

    public boolean removeParameter(ParametersEntities parametersEntity){
        return parameters.remove(Parameter.of(parametersEntity));
    }

    public void clear(){
        parameters.clear();
    }
}
