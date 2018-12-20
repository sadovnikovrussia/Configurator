package tech.sadovnikov.configurator.model;

import android.bluetooth.BluetoothDevice;

import java.io.IOException;

import io.reactivex.Observable;

public interface BluetoothService {

    Observable<String> outputStream();

    void connectToDevice(BluetoothDevice device) throws IOException;

    void closeAllConnections();

}
