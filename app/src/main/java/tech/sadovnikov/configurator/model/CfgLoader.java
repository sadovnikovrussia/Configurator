package tech.sadovnikov.configurator.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.model.entities.Configuration;
import tech.sadovnikov.configurator.utils.ParametersEntities;
import tech.sadovnikov.configurator.utils.rx.RxTransformers;

import static tech.sadovnikov.configurator.utils.ParametersEntities.PIN;

/**
 * Класс, отвечающий за отправку списка команд (установка и считывание параметров из устройства)
 * Например:
 * считывание: id?
 * установка: id = 1
 */
public class CfgLoader implements MessageAnalyzer.OnSetCfgParameterListener {
    private static final String TAG = CfgLoader.class.getSimpleName();

    private int commandNumber;
    private int attemptNumber;
    private int period = 2500;
    private long delay = 0;

    private PublishSubject<Integer> progress;

    private List<String> commandList = new ArrayList<>();

    private Timer timer;

    private BluetoothService bluetoothService;

    private CompositeDisposable subscriptions = new CompositeDisposable();

    @Inject
    public CfgLoader() {
    }

    public void setBluetoothService(BluetoothService bluetoothService) {
        this.bluetoothService = bluetoothService;
    }

    public PublishSubject<Integer> setConfiguration(Configuration configuration) {
        commandList = configuration.getCmdListForSetting();
        Log.d(TAG, "setConfiguration: " + commandList);
        startLoading();
        return progress;
    }

    public PublishSubject<Integer> readFullConfiguration() {
        commandList.clear();
        for (ParametersEntities entity : ParametersEntities.values()) {
            commandList.add(entity.createReadingCommand());
        }
        commandList.remove(PIN.createReadingCommand());
        Log.d(TAG, "readFullConfiguration: " + commandList);
        startLoading();
        return progress;
    }

    private void startLoading() {
        subscriptions.add(bluetoothService.getCmdObservable()
                .compose(RxTransformers.applySchedulers())
                .subscribe(parameter -> onGetParameter()));
        commandNumber = 0;
        attemptNumber = 0;
        progress = PublishSubject.create();
        timer = new Timer();
        timer.schedule(new CmdTask(), delay, period);
    }

    private void onGetParameter() {
        timer.cancel();
        timer.purge();
        commandNumber++;
        attemptNumber = 0;
        int pr = commandNumber * 100 / commandList.size();
        progress.onNext(pr);
        if (commandNumber < commandList.size()) {
            timer = new Timer();
            timer.schedule(new CmdTask(), delay, period);
        } else {
            progress.onComplete();
            subscriptions.clear();
        }
    }

    @Override
    public void onSetConfigParameter(ParametersEntities parameterEntity) {
        onGetParameter();
    }


    private class CmdTask extends TimerTask {
        private final String TAG = CmdTask.class.getSimpleName();

        int attempts = 3;

        @Override
        public void run() {
            if (attemptNumber < attempts) {
                String command = commandList.get(commandNumber);
                Log.d(TAG, "commandNumber = " + String.valueOf(commandNumber) + ", " + "attemptNumber = " + attemptNumber + ", " + command);
                bluetoothService.sendData(command);
                attemptNumber++;
            } else {
                commandNumber++;
                attemptNumber = 0;
                int pr = commandNumber * 100 / commandList.size();
                progress.onNext(pr);
                if (commandNumber < commandList.size()) {
                    String command = commandList.get(commandNumber);
                    Log.d(TAG, "commandNumber = " + String.valueOf(commandNumber) + ", " + "attemptNumber = " + attemptNumber + ", " + command);
                    bluetoothService.sendData(command);
                    attemptNumber++;
                } else {
                    progress.onComplete();
                    subscriptions.clear();
                }
            }
        }

    }


}
