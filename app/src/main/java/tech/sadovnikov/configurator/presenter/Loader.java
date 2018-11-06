package tech.sadovnikov.configurator.presenter;

import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Класс, отвечающий за отправку списка команд (установка и считывание параметров из устройства)
 * Например:
 * считывание: id?
 * установка: id = 1
 */
class Loader {
    private static final String TAG = "Loader";

    static final int WHAT_LOADING_END = 11;
    static final int WHAT_LOADING_START = 12;

    private boolean loading = false;
    private int commandNumber = 0;
    private int attemptNumber = 1;
    private int period = 2500;

    private ArrayList<String> commandList;

    private Timer timer;
    private TimerTask task;

    private OnLoaderEventsListener onLoaderEventsListener;
    private BluetoothService bluetoothService;
    UiHandler handler;

    Loader(OnLoaderEventsListener onLoaderEventsListener, final BluetoothService bluetoothService, UiHandler uiHandler) {
        handler = uiHandler;
        this.onLoaderEventsListener = onLoaderEventsListener;
        this.bluetoothService = bluetoothService;
    }

    void loadCommandList(ArrayList<String> commandList) {
        Log.d(TAG, "loadCommandList: " + commandList);
        this.commandList = commandList;
        loading = true;
        commandNumber = 0;
        attemptNumber = 1;
        timer = new Timer();
        task = new Task();
        timer.schedule(task, 0, period);
        onLoaderEventsListener.onStartLoading(commandList.size());
    }

    void nextCommand() {
        if (loading) {
            Log.d(TAG, "onNextCommand:");
            timer.cancel();
            timer.purge();
            timer = new Timer();
            task = new Task();
            commandNumber++;
            attemptNumber = 1;
            onLoaderEventsListener.onNextCommand(commandNumber, commandList.size());
            timer.schedule(task, 0, period);
        }
    }


    private class Task extends TimerTask {
        private static final String TAG = "Task";

        @Override
        public void run() {
            if (commandNumber < commandList.size()) {
                int attempts = 3;
                if (attemptNumber <= attempts) {
                    String command = commandList.get(commandNumber);
                    Log.d(TAG, "run() called: commandNumber = " + String.valueOf(commandNumber) + ", " + "attemptNumber = " + attemptNumber + ", " + command);
                    bluetoothService.sendData(command);
                    attemptNumber++;
                } else {
                    commandNumber++;
                    if (commandNumber < commandList.size()) {
                        attemptNumber = 1;
                        String command = commandList.get(commandNumber);
                        Log.d(TAG, "run() called: commandNumber = " + String.valueOf(commandNumber) + ", " + "attemptNumber = " + attemptNumber + ", " + command);
                        bluetoothService.sendData(command);
                    }
                }
            } else {
                commandNumber++;
                timer.cancel();
                timer.purge();
                loading = false;
                Message msg = new Message();
                msg.what = WHAT_LOADING_END;
                handler.sendMessage(msg);
            }
        }

    }

    public interface OnLoaderEventsListener {
        void onStartLoading(int size);

        void onNextCommand(int commandNumber, int size);
    }
}
