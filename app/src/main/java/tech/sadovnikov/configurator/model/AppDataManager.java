package tech.sadovnikov.configurator.model;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.model.entities.ConfigurationNew;

@Singleton
public class AppDataManager implements DataManager {
    private static final String TAG = AppDataManager.class.getSimpleName();

    private Logs logs;
    private ConfigurationNew configuration;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public AppDataManager(BluetoothService bluetoothService, Logs logs) {
        this.logs = logs;
        PublishSubject<String> inputMessagesStream = bluetoothService.getInputMessagesStream();
        Log.d(TAG, "AppDataManager: " + bluetoothService + ", " + inputMessagesStream + ", " + Thread.currentThread().getName());
        Disposable subscribe = inputMessagesStream
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(line -> {
                    //Log.d(TAG, "onNext: " + bluetoothService + ", " + inputMessagesStream + ", " + Thread.currentThread().getName() + ": " + line);
                    this.logs.addLine(line);
                });
        compositeDisposable.add(subscribe);
    }

    @Override
    public void addLine(String line) {
        logs.addLine(line);
    }

    @Override
    public String getAllMessages() {
        return logs.getAllMessages();
    }

    @Override
    public void clearSubscribes() {
        compositeDisposable.clear();
    }
}
