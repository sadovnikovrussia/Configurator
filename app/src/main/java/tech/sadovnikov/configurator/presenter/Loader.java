package tech.sadovnikov.configurator.presenter;

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

    private boolean loading = false;
    private int command = 0;
    private int attempt = 1;
    private int attempts = 3;
    private int period = 5000;

    private Contract.Configuration configuration;

    private Timer timer;
    private TimerTask task;

    static final String SET = "Set";
    static final String READ = "Read";

    private BluetoothService bluetoothService;

    Loader(final BluetoothService bluetoothService) {
        this.bluetoothService = bluetoothService;
    }

    void loadConfiguration(Contract.Configuration configuration, String action) {
        this.configuration = configuration;
        Log.d(TAG, "loadConfiguration: configuration = " + this.configuration.toString() + ", size = " + configuration.getSize());
        loading = true;
        command = 0;
        attempt = 1;
        timer = new Timer();
        if (action.equals(READ)){
            task = new ReadingTask(this.configuration);
        } else if (action.equals(SET)) {
            task = new SettingTask(this.configuration);
        }
        timer.schedule(task, 0, period);
    }

    void nextCommand() {
        if (loading) {
            Log.d(TAG, "onNextCommand: " + this.configuration.toString());
            timer.cancel();
            timer.purge();
            timer = new Timer();
            if (task instanceof ReadingTask){
                task = new ReadingTask(this.configuration);
            } else if (task instanceof SettingTask) {
                task = new SettingTask(this.configuration);
            }
            command++;
            attempt = 1;
            timer.schedule(task, 0, period);
        }
    }


    private class ReadingTask extends TimerTask {
        Contract.Configuration configuration;

        ReadingTask(Contract.Configuration configuration) {
            this.configuration = configuration;
        }

        @Override
        public void run() {
            if (command < configuration.getSize()) {
                if (attempt <= attempts) {
                    String command = configuration.getReadingCommand(Loader.this.command);
                    Log.d(TAG, "run() called: command = " + String.valueOf(Loader.this.command) + ", " + "attempt = " + Loader.this.attempt + ", " + configuration.toString() + ", " + configuration.getSize());
                    bluetoothService.sendData(command);
                    attempt++;
                } else {
                    command++;
                    if (command < configuration.getSize()){
                        attempt = 1;
                        String command = configuration.getReadingCommand(Loader.this.command);
                        Log.d(TAG, "run() called: command = " + String.valueOf(Loader.this.command) + ", " + "attempt = " + Loader.this.attempt + ", " + configuration.toString() + ", " + configuration.getSize());
                        bluetoothService.sendData(command);
                    }
                }
            } else {
                command++;
                timer.cancel();
                timer.purge();
                loading = false;
            }
        }

    }

    private class SettingTask extends TimerTask {
        Contract.Configuration configuration;

        SettingTask(Contract.Configuration configuration) {
            this.configuration = configuration;
            Log.d(TAG, "SettingTask: onConstructor: " + this.configuration.toString());
        }

        @Override
        public void run() {
            if (command < configuration.getSize()) {
                if (attempt <= attempts) {
                    String command = configuration.getSettingCommand(Loader.this.command);
                    Log.d(TAG, "run() called: command = " + String.valueOf(Loader.this.command) + ", " + "attempt = " + Loader.this.attempt + ", " + configuration.toString() + ", " + configuration.getSize());
                    bluetoothService.sendData(command);
                    attempt++;
                } else {
                    command++;
                    if (command < configuration.getSize()){
                        attempt = 1;
                        String command = configuration.getSettingCommand(Loader.this.command);
                        Log.d(TAG, "run() called: command = " + String.valueOf(Loader.this.command) + ", " + "attempt = " + Loader.this.attempt + ", " + configuration.toString() + ", " + configuration.getSize());
                        bluetoothService.sendData(command);
                    }
                }
            } else {
                command++;
                timer.cancel();
                timer.purge();
                loading = false;
            }

        }

    }

    //    void setConfiguration(Contract.Configuration configuration) {
//        this.configuration = configuration;
//        loading = true;
//        command = 0;
//        attempt = 1;
//        timer = new Timer();
//        readingTask = new ReadingTask(configuration);
//        timer.schedule(readingTask, 0, period);
//    }
//
//    void readConfiguration(Contract.Configuration configuration) {
//        this.configuration = configuration;
//        loading = true;
//        command = 0;
//        attempt = 1;
//        timer = new Timer();
//        readingTask = new ReadingTask(configuration);
//        timer.schedule(readingTask, 0, period);
//    }

}
