package tech.sadovnikov.configurator.presenter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BluetoothBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "BluetoothBrReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case BluetoothDevice.ACTION_FOUND:
                    Log.d(TAG, "ACTION_FOUND");
                    BluetoothDevice d = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    String name = d.getName();
                    String address = d.getAddress();
                    Log.d(TAG, "Founded device: name = " + name + ", address = " + address);
                    break;
                case BluetoothDevice.ACTION_PAIRING_REQUEST:
                    Log.d(TAG, "BluetoothDevice.ACTION_PAIRING_REQUEST");
                    break;
                case BluetoothDevice.ACTION_BOND_STATE_CHANGED:
                    Log.d(TAG, "BluetoothDevice.ACTION_BOND_STATE_CHANGED");
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                    Log.d(TAG, "BluetoothAdapter.ACTION_DISCOVERY_STARTED");
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    Log.d(TAG, "BluetoothAdapter.ACTION_DISCOVERY_FINISHED");
                    break;
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                    switch (state) {
                        // BT включился
                        case BluetoothAdapter.STATE_ON:
                            Log.d(TAG, "BluetoothAdapter.STATE_ON");
//                            bluetoothFragment.mSwitchBt.setChecked(true);
//                            bluetoothFragment.showDevices(mBluetoothService.getBondedDevices());
                            break;
                        // BT выключился
                        case BluetoothAdapter.STATE_OFF:
                            Log.d(TAG, "BluetoothAdapter.STATE_OFF");
//                            bluetoothFragment.mSwitchBt.setChecked(false);
//                            bluetoothFragment.hideDevices();
                            break;
                        // BT включается
                        case BluetoothAdapter.STATE_TURNING_ON:
                            Log.d(TAG, "BluetoothAdapter.STATE_TURNING_ON");
                            break;
                        // BT выключается
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            Log.d(TAG, "BluetoothAdapter.STATE_TURNING_OFF");
                            break;
                    }
            }
        }

    }
}
