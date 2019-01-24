package tech.sadovnikov.configurator.model;

import android.bluetooth.BluetoothDevice;

import java.util.List;

import io.reactivex.subjects.PublishSubject;
import tech.sadovnikov.configurator.model.entities.LogMessage;
import tech.sadovnikov.configurator.model.entities.Parameter;


public interface BluetoothService {
    int CONNECTION_STATE_DISCONNECTED = 0;
    int CONNECTION_STATE_DISCONNECTING = 3;
    int CONNECTION_STATE_CONNECTING = 1;
    int CONNECTION_STATE_CONNECTED = 2;

    boolean isEnabled();

    void enable();

    void disable();

    void connectToDevice(BluetoothDevice device);

    void disconnectFromDevice(BluetoothDevice device);

    void closeAllConnections();

    int getState();

    int getConnectionState();

    PublishSubject<Integer> getConnectionStateObservable();

    BluetoothDevice getConnectedDevice();

    PublishSubject<Integer> getStateObservable();

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
