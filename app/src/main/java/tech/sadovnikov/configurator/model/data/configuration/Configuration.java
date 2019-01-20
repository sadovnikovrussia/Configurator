package tech.sadovnikov.configurator.model.data.configuration;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import tech.sadovnikov.configurator.model.entities.Parameter;
import tech.sadovnikov.configurator.utils.ParametersEntities;

public class Configuration {
    private static final String TAG = Configuration.class.getSimpleName();

    private Map<ParametersEntities, Parameter> parametersMap = new LinkedHashMap<>();

    public void setParameter(ParametersEntities parameterEntity, String value) {
        Parameter parameter = Parameter.of(parameterEntity, value);
        //parameters.remove(parameter);
        //parameters.add(parameter);
        parametersMap.put(parameterEntity, Parameter.of(parameterEntity, value));
        Log.d(TAG, "setParameter: " + parameter);
        Log.d(TAG, "setParameter: " + this);
    }

    public void setParameter(Parameter parameter) {
        //parameters.remove(parameter);
        //parameters.add(parameter);
        parametersMap.put(parameter.getEntity(), parameter);
        //Log.d(TAG, "setParameter: " + parameter);
        //Log.d(TAG, "setParameter: " + this);
    }

    @Nullable
    public Parameter getParameter(ParametersEntities parameterEntity) {
        return parametersMap.get(parameterEntity);
    }

    public Parameter removeParameter(ParametersEntities parametersEntity) {
        //return parameters.remove(Parameter.of(parametersEntity));
        return parametersMap.remove(parametersEntity);
    }

    public List<String> getCmdListForReadDeviceConfiguration() {
        List<String> commandList = new ArrayList<>();
        for (ParametersEntities entity : ParametersEntities.values()) {
            commandList.add(entity.createReadingCommand());
        }
        return commandList;
    }

    public List<String> getCmdListForSetOrSave() {
        List<String> cmdList = new ArrayList<>();
        for (Map.Entry<ParametersEntities, Parameter> parameterEntry : parametersMap.entrySet()) {
            ParametersEntities parameterEntity = parameterEntry.getKey();
            if (parameterEntity.isSettable()){
                cmdList.add(parameterEntry.getValue().createSettingCommand());
            }
        }
        return cmdList;
    }


    public void clear() {
        //parameters.clear();
        parametersMap.clear();
    }

    @NonNull
    @Override
    public String toString() {
        return "Configuration{" + parametersMap +
                '}';
    }

    public int getSize() {
        return parametersMap.size();
    }

}
