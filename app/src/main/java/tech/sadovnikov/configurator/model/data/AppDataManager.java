package tech.sadovnikov.configurator.model.data;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.model.data.configuration.Configuration;
import tech.sadovnikov.configurator.model.data.logs.LogManager;
import tech.sadovnikov.configurator.model.entities.LogMessage;
import tech.sadovnikov.configurator.model.entities.Parameter;

@Singleton
public class AppDataManager implements DataManager {
    private static final String TAG = AppDataManager.class.getSimpleName();

    private LogManager logManager;
    private Configuration configuration;
    private BluetoothService bluetoothService;
    private PublishSubject<Configuration> configurationObservable;
    //private DataManagerListener dataManagerListener;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public AppDataManager(LogManager logManager, Configuration configuration, BluetoothService bluetoothService) {
        this.logManager = logManager;
        this.configuration = configuration;
        this.bluetoothService = bluetoothService;
        Disposable subscribe = bluetoothService.getLogMessageObservable()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addLogMessage);
        this.configurationObservable = PublishSubject.create();
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
        Log.d(TAG, "setConfigParameter: " + parameter);
        configuration.setParameter(parameter);
    }

    @Override
    public List<String> getCmdListForReadDeviceConfiguration() {
        return configuration.getCmdListForReadDeviceConfiguration();
    }

    @Override
    public List<String> getCmdListForSetDeviceConfiguration() {
        return configuration.getCmdListForSetDeviceConfiguration();
    }

    @Override
    public PublishSubject<Configuration> getConfigurationObservable() {
        return configurationObservable;
    }

    @Override
    public void clearSubscribes() {
        compositeDisposable.clear();
    }


}