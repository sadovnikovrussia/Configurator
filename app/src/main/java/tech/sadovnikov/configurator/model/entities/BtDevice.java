package tech.sadovnikov.configurator.model.entities;

import android.bluetooth.BluetoothDevice;

import java.util.Objects;

public class BtDevice {
    private static final String TAG = BtDevice.class.getSimpleName();

    public static final int STATE_CONNECTED = 102;
    public static final int STATE_CONNECTING = 101;
    public static final int STATE_DISCONNECTED = 100;

    private int state;
    private final BluetoothDevice bluetoothDeviceCore;

    public BtDevice(BluetoothDevice bluetoothDevice) {
        bluetoothDeviceCore = bluetoothDevice;
        state = STATE_DISCONNECTED;
    }

    public String getName() {
        return bluetoothDeviceCore.getName();
    }

    public String getAddress() {
        return bluetoothDeviceCore.getAddress();
    }

    public int getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BtDevice)) return false;
        BtDevice btDevice = (BtDevice) o;
        return Objects.equals(bluetoothDeviceCore, btDevice.bluetoothDeviceCore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bluetoothDeviceCore);
    }
}
