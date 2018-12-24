package tech.sadovnikov.configurator.model;

import android.bluetooth.BluetoothDevice;

import java.io.IOException;
import java.util.List;

import rx.Observable;


public interface BluetoothService {

    boolean isEnabled();

    boolean enable();

    boolean disable();

    Observable<String> outputStream();

    void connectToDevice(BluetoothDevice device) throws IOException;

    void closeAllConnections();

    List<BluetoothDevice> getPairedDevices();

}
