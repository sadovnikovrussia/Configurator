package tech.sadovnikov.configurator.model.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.model.data.logs.LogManager;
import tech.sadovnikov.configurator.model.entities.Configuration;
import tech.sadovnikov.configurator.model.entities.LogMessage;
import tech.sadovnikov.configurator.model.entities.Parameter;

@Singleton
public class AppDataManager implements DataManager {
    private static final String TAG = AppDataManager.class.getSimpleName();

    private LogManager logManager;
    private Configuration configuration;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public AppDataManager(LogManager logManager, Configuration configuration) {
        this.logManager = logManager;
        this.configuration = configuration;
    }

    @Override
    public void addLogMessage(LogMessage message) {
        logManager.addLogMessage(message);
    }

    @Override
    public List<LogMessage> getMainLogList() {
        return logManager.getMainLogList();
    }

    @Override
    public PublishSubject<LogMessage> getObservableMainLog() {
        return logManager.getObservableMainLog();
    }

    @Override
    public void setConfigParameter(Parameter parameter) {
        configuration.setParameter(parameter);
    }

    @Override
    public List<String> getCmdListForReadDeviceConfiguration() {
        return configuration.getCmdListForReadDeviceConfiguration();
    }

    @Override
    public void clearSubscribes() {
        compositeDisposable.clear();
    }


}
