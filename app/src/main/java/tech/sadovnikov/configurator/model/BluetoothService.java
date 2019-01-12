package tech.sadovnikov.configurator.model;

import android.bluetooth.BluetoothDevice;

import java.util.List;

import io.reactivex.subjects.PublishSubject;


public interface BluetoothService {

    boolean isEnabled();

    void enable();

    void disable();

    void connectToDevice(BluetoothDevice device);

    void closeAllConnections();

    List<BluetoothDevice> getPairedDevices();

    PublishSubject<Integer> getBluetoothStateObservable();

    void setBluetoothState(Integer state);

    void setPairedDevices(List<BluetoothDevice> pairedDevices);

    PublishSubject<List<BluetoothDevice>> getPairedDevicesObservable();

    List<BluetoothDevice> getAvailableDevices();

    PublishSubject<List<BluetoothDevice>> getAvailableDevicesObservable();

    void startDiscovery();

    void cancelDiscovery();

    PublishSubject<String> getInputMessagesObservable();

    void setInputStreamListener(InputStreamListener listener);

    void sendData(String data);

    interface InputStreamListener {

    }
}
