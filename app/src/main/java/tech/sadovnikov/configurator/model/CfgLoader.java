package tech.sadovnikov.configurator.model;

import android.util.Log;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import tech.sadovnikov.configurator.model.data.DataManager;
import tech.sadovnikov.configurator.utils.ParametersEntities;

/**
 * Класс, отвечающий за отправку списка команд (установка и считывание параметров из устройства)
 * Например:
 * считывание: id?
 * установка: id = 1
 */
public class CfgLoader implements MessageAnalyzer.OnSetCfgParameterListener {
    private static final String TAG = CfgLoader.class.getSimpleName();

    static final int WHAT_LOADING_END = 11;
    static final int WHAT_LOADING_START = 12;

    private boolean loading = false;
    private int commandNumber = 0;
    private int attemptNumber = 1;
    private int period = 2500;

    private List<String> commandList;

    private Timer timer;
    private TimerTask task;

    private OnLoaderEventsListener onLoaderEventsListener;
    private BluetoothService bluetoothService;
    private DataManager dataManager;
    //UiHandler handler;

    @Inject
    public CfgLoader() {
        //handler = uiHandler;
        //this.onLoaderEventsListener = onLoaderEventsListener;
        //this.bluetoothService = bluetoothService;
    }

    public void setBluetoothService(BluetoothService bluetoothService) {
        this.bluetoothService = bluetoothService;
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void readFullConfiguration() {
        commandList.clear();
        for (ParametersEntities entity : ParametersEntities.values()) {
            commandList.add(entity.createReadingCommand());
        }
        Log.d(TAG, "loadCommandList: " + commandList);
        loading = true;
        commandNumber = 0;
        attemptNumber = 1;
        timer = new Timer();
        task = new Task();
        timer.schedule(task, 0, period);
    }

    public void loadCommandList(List<String> commandList) {
        Log.d(TAG, "loadCommandList: " + commandList);
        this.commandList = commandList;
        loading = true;
        commandNumber = 0;
        attemptNumber = 1;
        timer = new Timer();
        task = new Task();
        timer.schedule(task, 0, period);
        //onLoaderEventsListener.onStartLoading(commandList.size());
    }

    private void nextCommand() {
        if (loading) {
            Log.d(TAG, "onNextCommand:");
            timer.cancel();
            timer.purge();
            timer = new Timer();
            task = new Task();
            commandNumber++;
            attemptNumber = 1;
            //onLoaderEventsListener.onNextCommand(commandNumber, commandList.size());
            timer.schedule(task, 0, period);
        }
    }

    @Override
    public void onSetConfigParameter(ParametersEntities parameterEntity) {
        nextCommand();
    }


    private class Task extends TimerTask {
        private final String TAG = Task.class.getSimpleName();

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
//                Message msg = new Message();
//                msg.what = WHAT_LOADING_END;
//                handler.sendMessage(msg);
            }
        }

    }

    public interface OnLoaderEventsListener {
        void onStartLoading(int size);

        void onNextCommand(int commandNumber, int size);
    }
}
