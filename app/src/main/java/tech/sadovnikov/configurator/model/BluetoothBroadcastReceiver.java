package tech.sadovnikov.configurator.model;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import javax.inject.Inject;

public class BluetoothBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = BluetoothBroadcastReceiver.class.getSimpleName();

    private Listener listener;


    @Inject
    public BluetoothBroadcastReceiver(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            Log.w(TAG, action);
            switch (action) {
                case BluetoothDevice.ACTION_FOUND:
                    listener.onFoundDevice(intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
                    Log.w(TAG, "ACTION_FOUND: " + intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
                    break;
                case BluetoothDevice.ACTION_PAIRING_REQUEST:
                    break;
                case BluetoothDevice.ACTION_ACL_CONNECTED:
                    break;
                case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                    break;
                case BluetoothDevice.ACTION_BOND_STATE_CHANGED:
                    listener.onBondStateChanged();
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                    listener.onDiscoveryStarted();
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    break;
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                    Log.w(TAG, "onReceive: " + state);
                    listener.onStateChanged();
                    switch (state) {
                        case BluetoothAdapter.STATE_ON:
                            break;
                        case BluetoothAdapter.STATE_CONNECTED:
                            break;
                        case BluetoothAdapter.STATE_OFF:
                            break;
                        case BluetoothAdapter.STATE_TURNING_ON:
                            break;
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            break;
                    }
            }
        }

    }

    public interface Listener {

        void onStateChanged();

        void onBondStateChanged();

        void onFoundDevice(BluetoothDevice device);

        void onDiscoveryStarted();
    }

}
