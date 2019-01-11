package tech.sadovnikov.configurator.model.entities;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

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

    @Inject
    public Configuration() {
    }

    private void addParameter(ParametersEntities parameterEntity) {
        parameters.add(Parameter.of(parameterEntity));
    }

    public void setParameter(ParametersEntities parameterEntity, String value) {
        Parameter parameter = Parameter.of(parameterEntity, value);
        parameters.remove(parameter);
        parameters.add(parameter);
        Log.d(TAG, "setParameter: " + parameter);
        Log.d(TAG, "setParameter: " + this);
    }

    public void setParameter(Parameter parameter) {
        parameters.remove(parameter);
        parameters.add(parameter);
        Log.d(TAG, "setParameter: " + parameter);
        Log.d(TAG, "setParameter: " + this);
    }

    public boolean removeParameter(ParametersEntities parametersEntity) {
        return parameters.remove(Parameter.of(parametersEntity));
    }

    public void clear() {
        parameters.clear();
    }

    @NonNull
    @Override
    public String toString() {
        return "Configuration{" +
                "parameters=" + parameters +
                '}';
    }
}
