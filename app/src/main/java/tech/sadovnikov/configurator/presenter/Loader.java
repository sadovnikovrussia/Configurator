package tech.sadovnikov.configurator.presenter;

import android.os.Handler;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import tech.sadovnikov.configurator.Contract;

/**
 * Класс, отвечающий за отправку списка команд (установка и считывание параметров из устройства)
 * Например:
 * считывание: id?
 * установка: id = 1
 */
class Loader {
    private static final String TAG = "Loader";

    int currentAttempt = 1;
    boolean isAnswered = false;

    private int period = 3000;

    Contract.Configuration configuration;
    private Timer timer;
    private AttemptTask task;

    private BluetoothService bluetoothService;

    Loader(final BluetoothService bluetoothService) {
        this.bluetoothService = bluetoothService;
    }

    void setConfiguration(Contract.Configuration configuration) {
        final Contract.Configuration tmpConfiguration = configuration;
        Handler handler = new Handler();
        for (int i = 0; i < configuration.getSize() + 2; i++) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String command = tmpConfiguration.getSetCommand(0);
                    bluetoothService.sendData(command);
                }
            }, period * i);
        }
    }

    void loadConfiguration(Contract.Configuration configuration) {
        task = new AttemptTask(configuration);
        timer = new Timer();
        timer.schedule(task, 0, period);
    }

    private class AttemptTask extends TimerTask {
        Contract.Configuration configuration;
        int currentCommand = 0;
        int currentAttempt = 1;
        int attempts = 3;

        AttemptTask(Contract.Configuration configuration) {
            this.configuration = configuration;
        }

        @Override
        public void run() {
            if (currentCommand < configuration.getSize()){
                if (currentAttempt <= attempts) {
                    Log.d(TAG, "run() called: currentAttempt = " + String.valueOf(currentAttempt));
                    String command = configuration.getRequestCommand(currentCommand);
                    bluetoothService.sendData(command);
                    currentAttempt++;
                } else {
                    currentCommand++;
                    currentAttempt = 1;
                }
            }
             else {
                currentCommand++;
                timer.purge();
                timer.cancel();
            }

        }
    }


}
