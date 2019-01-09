package tech.sadovnikov.configurator.model;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.aware.PublishConfig;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.subjects.PublishSubject;
import rx.Observable;


public interface BluetoothService {

    boolean isEnabled();

    boolean enable();

    boolean disable();

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

    PublishSubject<String> getInputMessagesStream();

    void setInputStreamListener(InputStreamListener listener);

    interface InputStreamListener {

    }
}
