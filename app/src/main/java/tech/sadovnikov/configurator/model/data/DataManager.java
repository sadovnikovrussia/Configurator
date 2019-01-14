package tech.sadovnikov.configurator.model.data;

import java.util.List;

import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.model.data.configuration.Configuration;
import tech.sadovnikov.configurator.model.data.logs.LogManager;
import tech.sadovnikov.configurator.model.entities.Parameter;

public interface DataManager extends LogManager {

    void clearSubscribes();

    void setConfigParameter(Parameter parameter);

    List<String> getCmdListForReadDeviceConfiguration();

    PublishSubject<Configuration> getConfigurationObservable();

    interface DataManagerListener {
        void onGetParameterFromDevice();
    }

}
