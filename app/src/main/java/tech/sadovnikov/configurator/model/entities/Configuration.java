package tech.sadovnikov.configurator.model.entities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import tech.sadovnikov.configurator.model.CmdCreator;
import tech.sadovnikov.configurator.utils.ParametersEntities;

public class Configuration {
    private static final String TAG = Configuration.class.getSimpleName();

    private Map<ParametersEntities, Parameter> parametersMap = new LinkedHashMap<>();

    public void setParameter(Parameter parameter) {
        parametersMap.put(parameter.getEntity(), parameter);
    }

    @Nullable
    public Parameter getParameter(ParametersEntities parameterEntity) {
        return parametersMap.get(parameterEntity);
    }

    public Parameter removeParameter(ParametersEntities parametersEntity) {
        return parametersMap.remove(parametersEntity);
    }

    public List<String> getCmdListForSaving() {
        List<String> cmdList = new ArrayList<>();
        for (Map.Entry<ParametersEntities, Parameter> parameterEntry : parametersMap.entrySet()) {
            ParametersEntities parameterEntity = parameterEntry.getKey();
            if (parameterEntity.isSettable()) {
                if (!parameterEntry.getValue().getValue().equals(""))
                    cmdList.add(CmdCreator.forSaving(parameterEntry.getValue()));
            }
        }
        return cmdList;
    }

    public List<String> getCmdListForSetting() {
        List<String> cmdList = new ArrayList<>();
        for (Map.Entry<ParametersEntities, Parameter> parameterEntry : parametersMap.entrySet()) {
            ParametersEntities parameterEntity = parameterEntry.getKey();
            if (parameterEntity.isSettable()) {
                cmdList.add(CmdCreator.forSetting(parameterEntry.getValue()));
            }
        }
        Log.d(TAG, "getCmdListForSetting() returned: " + cmdList);
        return cmdList;
    }


    public void clear() {
        parametersMap.clear();
    }

    @NonNull
    @Override
    public String toString() {
        return "Configuration" + parametersMap;
    }

    public int getSize() {
        return parametersMap.size();
    }

}
