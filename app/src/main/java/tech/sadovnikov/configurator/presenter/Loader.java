package tech.sadovnikov.configurator.presenter;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import tech.sadovnikov.configurator.model.Configuration;

/**
 * Класс, отвечающий за отправку списка команд (установка и считывание параметров из устройства)
 * Например:
 * считывание: id?
 * установка: id = 1
 */
class Loader {
    private static final String TAG = "Loader";

    private boolean loading = false;
    private int commandNumber = 0;
    private int attemptNumber = 1;
    private int attempts = 3;
    private int period = 5000;

    private Configuration configuration;

    private Timer timer;
    private TimerTask task;

    static final String SET = "Set";
    static final String READ = "Read";

    private BluetoothService bluetoothService;

    Loader(final BluetoothService bluetoothService) {
        this.bluetoothService = bluetoothService;
    }

    void loadConfiguration(final Configuration configuration, String action) {
        this.configuration = configuration;
        Log.d(TAG, "loadConfiguration: configuration = " + this.configuration);
        loading = true;
        commandNumber = 0;
        attemptNumber = 1;
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
            Log.d(TAG, "onNextCommand: " + this.configuration);
            timer.cancel();
            timer.purge();
            timer = new Timer();
            if (task instanceof ReadingTask){
                task = new ReadingTask(this.configuration);
            } else if (task instanceof SettingTask) {
                task = new SettingTask(this.configuration);
            }
            commandNumber++;
            attemptNumber = 1;
            timer.schedule(task, 0, period);
        }
    }


    private class ReadingTask extends TimerTask {
        private static final String TAG = "ReadingTask";
        Configuration configuration;

        ReadingTask(Configuration configuration) {
            this.configuration = configuration;
        }

        @Override
        public void run() {
            //if (configuration.getParametersArrayList().it)
            if (commandNumber < configuration.getSize()) {
                if (attemptNumber <= attempts) {
                    String command = this.configuration.getReadingCommand(commandNumber);
                    Log.d(TAG, "run() called: commandNumber = " + String.valueOf(commandNumber) + ", " + "attemptNumber = " + attemptNumber + ", " + configuration);
                    bluetoothService.sendData(command);
                    attemptNumber++;
                } else {
                    commandNumber++;
                    if (commandNumber < configuration.getSize()){
                        attemptNumber = 1;
                        String command = configuration.getReadingCommand(commandNumber);
                        Log.d(TAG, "run() called: commandNumber = " + String.valueOf(commandNumber) + ", " + "attemptNumber = " + attemptNumber + ", " + configuration);
                        bluetoothService.sendData(command);
                    }
                }
            } else {
                commandNumber++;
                timer.cancel();
                timer.purge();
                loading = false;
            }
        }

    }

    private class SettingTask extends TimerTask {
        Configuration configuration;

        SettingTask(Configuration configuration) {
            this.configuration = configuration;
            Log.d(TAG, "SettingTask: onConstructor: " + this.configuration.toString());
        }

        @Override
        public void run() {
            if (commandNumber < configuration.getSize()) {
                if (attemptNumber <= attempts) {
                    String command = configuration.getSettingCommand(commandNumber);
                    Log.d(TAG, "run() called: commandNumber = " + String.valueOf(commandNumber) + ", " + "attemptNumber = " + attemptNumber + ", " + configuration.toString() + ", " + configuration.getSize());
                    bluetoothService.sendData(command);
                    attemptNumber++;
                } else {
                    commandNumber++;
                    if (commandNumber < configuration.getSize()){
                        attemptNumber = 1;
                        String command = configuration.getSettingCommand(commandNumber);
                        Log.d(TAG, "run() called: commandNumber = " + String.valueOf(commandNumber) + ", " + "attemptNumber = " + attemptNumber + ", " + configuration.toString() + ", " + configuration.getSize());
                        bluetoothService.sendData(command);
                    }
                }
            } else {
                commandNumber++;
                timer.cancel();
                timer.purge();
                loading = false;
            }

        }

    }

    //    void setConfiguration(Configuration configuration) {
//        this.configuration = configuration;
//        loading = true;
//        commandNumber = 0;
//        attemptNumber = 1;
//        timer = new Timer();
//        readingTask = new ReadingTask(configuration);
//        timer.schedule(readingTask, 0, period);
//    }
//
//    void readConfiguration(Configuration configuration) {
//        this.configuration = configuration;
//        loading = true;
//        commandNumber = 0;
//        attemptNumber = 1;
//        timer = new Timer();
//        readingTask = new ReadingTask(configuration);
//        timer.schedule(readingTask, 0, period);
//    }

}
