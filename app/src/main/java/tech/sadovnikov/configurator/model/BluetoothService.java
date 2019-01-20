package tech.sadovnikov.configurator.model;

import android.bluetooth.BluetoothDevice;

import java.util.List;

import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.model.entities.LogMessage;
import tech.sadovnikov.configurator.model.entities.Parameter;


public interface BluetoothService {

    boolean isEnabled();

    void enable();

    void disable();

    void connectToDevice(BluetoothDevice device);

    void disconnectFromDevice(BluetoothDevice device);

    void closeAllConnections();

    PublishSubject<Integer> getBluetoothStateObservable();

    List<BluetoothDevice> getPairedDevices();

    PublishSubject<List<BluetoothDevice>> getPairedDevicesObservable();

    List<BluetoothDevice> getAvailableDevices();

    PublishSubject<List<BluetoothDevice>> getAvailableDevicesObservable();

    PublishSubject<LogMessage> getLogMessageObservable();

    PublishSubject<Parameter> getCmdObservable();

    void startDiscovery();

    void cancelDiscovery();

    PublishSubject<String> getInputMessagesObservable();

    void sendData(String data);


    interface InputStreamListener {

    }
}
