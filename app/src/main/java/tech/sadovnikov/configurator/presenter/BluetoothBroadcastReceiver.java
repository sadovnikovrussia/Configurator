package tech.sadovnikov.configurator.presenter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BluetoothBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "BluetoothBroadReceiver";

    Presenter presenter;

    public BluetoothBroadcastReceiver(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            // Logs.w(TAG, action);
            switch (action) {
                case BluetoothDevice.ACTION_FOUND:
                    Log.w(TAG, "ACTION_FOUND");
                    BluetoothDevice d = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    presenter.onBluetoothServiceActionFound(d);
                    break;
                case BluetoothDevice.ACTION_PAIRING_REQUEST:
                    Log.w(TAG, "BluetoothDevice.ACTION_PAIRING_REQUEST");
                    break;
                case BluetoothDevice.ACTION_ACL_CONNECTED:
                    Log.w(TAG, "BluetoothDevice.ACTION_ACL_CONNECTED");
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
                            presenter.onBluetoothServiceStateOn();
                            break;
                        // BT выключился
                        case BluetoothAdapter.STATE_OFF:
                            Log.w(TAG, "BluetoothAdapter.STATE_OFF");
                            presenter.onBluetoothServiceStateOff();
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
}
