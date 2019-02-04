package tech.sadovnikov.configurator.model.data;

import java.util.List;

import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.model.entities.Configuration;
import tech.sadovnikov.configurator.model.data.logs.LogManager;
import tech.sadovnikov.configurator.model.entities.Parameter;
import tech.sadovnikov.configurator.utils.ParametersEntities;

public interface DataManager extends LogManager {

    void clearSubscribes();

    void setConfigParameter(Parameter parameter);

    void setConfigParameter(ParametersEntities parameterEntity, String value);

    void removeConfigParameter(ParametersEntities parameterEntity);

    List<String> getCmdListForReadDeviceConfiguration();

    List<String> getCmdListForSetDeviceConfiguration();

    List<String> getCmdListForSaveDeviceConfiguration();

    PublishSubject<Configuration> getConfigurationObservable();

    Configuration getConfiguration();

    void setConfiguration(Configuration configuration);

}
