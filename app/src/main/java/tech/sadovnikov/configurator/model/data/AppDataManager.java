package tech.sadovnikov.configurator.model.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.model.data.logs.LogList;
import tech.sadovnikov.configurator.model.entities.Configuration;
import tech.sadovnikov.configurator.model.data.logs.LogManager;
import tech.sadovnikov.configurator.model.entities.LogMessage;
import tech.sadovnikov.configurator.model.entities.Parameter;
import tech.sadovnikov.configurator.utils.ParametersEntities;
import tech.sadovnikov.configurator.utils.rx.RxTransformers;

@Singleton
public class AppDataManager implements DataManager {
    private static final String TAG = AppDataManager.class.getSimpleName();

    private LogManager logManager;
    private Configuration configuration;
    private final PublishSubject<Configuration> configurationObservable;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    AppDataManager(LogManager logManager, Configuration configuration, BluetoothService bluetoothService) {
        this.logManager = logManager;
        this.configuration = configuration;
        Disposable subscriptionLog = bluetoothService.getLogMessageObservable()
                .compose(RxTransformers.applySchedulers())
                .subscribe(this::addLogMessage);
        Disposable subscriptionCmd = bluetoothService.getCmdObservable()
                .compose(RxTransformers.applySchedulers())
                .subscribe(this::setConfigParameter);
        compositeDisposable.addAll(subscriptionLog, subscriptionCmd);
        this.configurationObservable = PublishSubject.create();
        Log.w(TAG, "AppDataManager: " + configurationObservable);
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
    public Map<String, LogList> getLogs() {
        return logManager.getLogs();
    }

    @Override
    public PublishSubject<Map<String, LogList>> getObservableLogs() {
        return logManager.getObservableLogs();
    }

    @Override
    public PublishSubject<List<String>> getObservableLogTabs() {
        return logManager.getObservableLogTabs();
    }

    @Override
    public List<String> getLogTabs() {
        return logManager.getLogTabs();
    }

    @Override
    public PublishSubject<String> getObservableNewTab() {
        return logManager.getObservableNewTab();
    }

    @Override
    public void setConfigParameter(Parameter parameter) {
        Log.d(TAG, "setConfigParameter: " + parameter);
        configuration.setParameter(parameter);
        configurationObservable.onNext(configuration);
    }

    @Override
    public void setConfigParameter(ParametersEntities parameterEntity, String value) {
        Parameter parameter = Parameter.of(parameterEntity, value);
        Log.d(TAG, "setConfigParameter: " + parameter);
        configuration.setParameter(parameter);
        configurationObservable.onNext(configuration);
    }

    @Override
    public void removeConfigParameter(ParametersEntities parameterEntity) {
        configuration.removeParameter(parameterEntity);
        Log.d(TAG, "removeConfigParameter: " + parameterEntity + "\r\n" + configuration);
        configurationObservable.onNext(configuration);
    }

    @Override
    public List<String> getCmdListForReadDeviceConfiguration() {
        List<String> commandList = new ArrayList<>();
        for (ParametersEntities entity : ParametersEntities.values()) {
            commandList.add(entity.createReadingCommand());
        }
        return commandList;    }

    @Override
    public List<String> getCmdListForSetDeviceConfiguration() {
        return configuration.getCmdListForSetting();
    }

    @Override
    public List<String> getCmdListForSaveDeviceConfiguration() {
        return configuration.getCmdListForSaving();
    }

    @Override
    public PublishSubject<Configuration> getConfigurationObservable() {
        return configurationObservable;
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        Log.d(TAG, "setConfiguration: " + this.configuration);
        configurationObservable.onNext(this.configuration);
    }

    @Override
    public boolean getAutoScrollMode() {
        return logManager.getAutoScrollMode();
    }

    @Override
    public void setAutoScrollMode(boolean isChecked) {
        logManager.setAutoScrollMode(isChecked);
    }

    @Override
    public PublishSubject<Boolean> getAutoScrollModeObservable() {
        return logManager.getAutoScrollModeObservable();
    }

    @Override
    public void clearSubscribes() {
        compositeDisposable.clear();
    }


}
