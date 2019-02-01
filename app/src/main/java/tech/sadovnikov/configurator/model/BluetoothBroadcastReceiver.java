package tech.sadovnikov.configurator.model;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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
            Log.w(TAG, "ACTION = " + action);
            switch (action) {
                case BluetoothDevice.ACTION_FOUND:
                    listener.onFoundDevice(intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
                    Log.d(TAG, "ACTION_FOUND: " + intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
                    break;
                case BluetoothDevice.ACTION_PAIRING_REQUEST:
                    Log.d(TAG, "ACTION_PAIRING_REQUEST: " + ((BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)).getBondState());
                    break;
                case BluetoothDevice.ACTION_ACL_CONNECTED:
                    Log.d(TAG, "ACTION_ACL_CONNECTED: " + (intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)));
                    Log.d(TAG, "ACTION_ACL_CONNECTED: " + (intent.getParcelableExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE)));
                    break;
                case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                    Log.d(TAG, "ACTION_ACL_DISCONNECTED: ");
                    listener.onAclDisconnected();
                    break;
                case BluetoothDevice.ACTION_BOND_STATE_CHANGED:
                    Log.d(TAG, "ACTION_BOND_STATE_CHANGED: " + ((BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)).getBondState());
                    listener.onBondStateChanged();
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                    Log.d(TAG, "ACTION_DISCOVERY_STARTED: ");
                    listener.onDiscoveryStarted();
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    Log.d(TAG, "ACTION_DISCOVERY_FINISHED: ");
                    break;
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    Log.d(TAG, "ACTION_STATE_CHANGED: ");
                    int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                    switch (state) {
                        case BluetoothAdapter.STATE_ON:
                            Log.d(TAG, "STATE_ON: ");
                            break;
                        case BluetoothAdapter.STATE_OFF:
                            Log.d(TAG, "STATE_OFF: ");
                            break;
                        case BluetoothAdapter.STATE_TURNING_ON:
                            Log.d(TAG, "STATE_TURNING_ON: ");
                            break;
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            Log.d(TAG, "STATE_TURNING_OFF: ");
                            break;
                    }
                    listener.onStateChanged();
                    break;
                case BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED:
                    Log.d(TAG, "ACTION_CONNECTION_STATE_CHANGED: ");
                    int connectionState = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, -1);
                    switch (connectionState) {
                        case BluetoothAdapter.STATE_CONNECTING:
                            Log.d(TAG, "STATE_CONNECTING: ");
                            listener.onStateConnecting(intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
                            break;
                        case BluetoothAdapter.STATE_CONNECTED:
                            Log.d(TAG, "STATE_CONNECTED: ");
                            listener.onStateConnected(intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
                            break;
                        case BluetoothAdapter.STATE_DISCONNECTING:
                            Log.d(TAG, "STATE_DISCONNECTING: ");
                            break;
                        case BluetoothAdapter.STATE_DISCONNECTED:
                            Log.d(TAG, "STATE_DISCONNECTED: ");
                            listener.onStateDisconnected(intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
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

        void onStateConnected(BluetoothDevice device);

        void onStateConnecting(BluetoothDevice device);

        void onStateDisconnected(BluetoothDevice device);

        void onAclDisconnected();
    }

}
