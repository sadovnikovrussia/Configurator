package tech.sadovnikov.configurator.presenter;

import android.os.Handler;

import tech.sadovnikov.configurator.Contract;

/**
 * Класс, отвечающий за отправку списка команд (установка и считывание параметров из устройства)
 * Например:
 * считывание: id?
 * установка: id = 1
 */
class Loader {
    private static final String TAG = "Loader";

    private int delay = 1500;

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
            }, delay * i);
        }
    }

    void loadConfiguration(final Contract.Configuration configuration) {
        Handler handler = new Handler();
        for (int i = 0; i < configuration.getSize(); i++) {
            final int finalI = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String command = configuration.getRequestCommand(finalI);
                    bluetoothService.sendData(command);
                }
            }, delay * i);
        }
    }


}
