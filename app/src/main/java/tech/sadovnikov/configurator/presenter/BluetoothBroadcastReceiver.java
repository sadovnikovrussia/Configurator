package tech.sadovnikov.configurator.presenter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BluetoothBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "BluetoothBroadReceiver";

    OnBluetoothBroadcastReceiverEventsListener listener;

    public BluetoothBroadcastReceiver(OnBluetoothBroadcastReceiverEventsListener onBluetoothBroadcastReceiverEventsListener) {
        listener = onBluetoothBroadcastReceiverEventsListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            // Logs.w(TAG, action);
            switch (action) {
                case BluetoothDevice.ACTION_FOUND:
                    Log.w(TAG, "ACTION_FOUND");
                    Log.w(TAG, "onReceive: " + intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE) );
                    listener.onBluetoothServiceActionFound((BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
                    break;
                case BluetoothDevice.ACTION_PAIRING_REQUEST:
                    Log.w(TAG, "BluetoothDevice.ACTION_PAIRING_REQUEST");
                    break;
                case BluetoothDevice.ACTION_ACL_CONNECTED:
                    Log.w(TAG, "BluetoothDevice.ACTION_ACL_CONNECTED");
                    listener.onStateConnected((BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
                    break;
                case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                    Log.w(TAG, "BluetoothDevice.ACTION_ACL_DISCONNECTED");
                    break;
                case BluetoothDevice.ACTION_BOND_STATE_CHANGED:
                    Log.w(TAG, "BluetoothDevice.ACTION_BOND_STATE_CHANGED");
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                    Log.w(TAG, "BluetoothAdapter.ACTION_DISCOVERY_STARTED");
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    Log.w(TAG, "BluetoothAdapter.ACTION_DISCOVERY_FINISHED");
                    break;
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                    switch (state) {
                        // BT включился
                        case BluetoothAdapter.STATE_ON:
                            Log.w(TAG, "BluetoothAdapter.STATE_ON");
                            listener.onBluetoothServiceStateOn();
                            break;
                        case BluetoothAdapter.STATE_CONNECTED:
                            Log.w(TAG, "BluetoothAdapter.STATE_CONNECTED");
                            listener.onStateConnected((BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
                            break;
                        // BT выключился
                        case BluetoothAdapter.STATE_OFF:
                            Log.w(TAG, "BluetoothAdapter.STATE_OFF");
                            listener.onBluetoothServiceStateOff();
                            break;
                        // BT включается
                        case BluetoothAdapter.STATE_TURNING_ON:
                            Log.w(TAG, "BluetoothAdapter.STATE_TURNING_ON");
                            break;
                        // BT выключается
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            Log.w(TAG, "BluetoothAdapter.STATE_TURNING_OFF");
                            break;
                    }
            }
        }

    }

    interface OnBluetoothBroadcastReceiverEventsListener{
        void onStateConnected(BluetoothDevice device);

        void onBluetoothServiceActionFound(BluetoothDevice device);

        void onBluetoothServiceStateOn();

        void onBluetoothServiceStateOff();
    }
}
