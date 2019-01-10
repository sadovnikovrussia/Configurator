package tech.sadovnikov.configurator.model;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.model.entities.LogMessage;
import tech.sadovnikov.configurator.model.entities.Configuration;

@Singleton
public class AppDataManager implements DataManager {
    private static final String TAG = AppDataManager.class.getSimpleName();

    private Logs logs;
    private Configuration configuration = Configuration.createMainConfiguration();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public AppDataManager(Logs logs) {
        this.logs = logs;
    }


    @Override
    public void addLine(String line) {
        logs.addLine(line);
    }

    @Override
    public void addLogMessage(LogMessage message) {
        logs.addLogMessage(message);
    }

    @Override
    public List<LogMessage> getMainLogMessages() {
        return logs.getMainLogMessages();
    }

    @Override
    public PublishSubject<LogMessage> getObservableMainLog() {
        return logs.getObservableMainLog();
    }

    @Override
    public void clearSubscribes() {
        compositeDisposable.clear();
    }

}
