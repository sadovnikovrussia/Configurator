package tech.sadovnikov.configurator.view.adapter;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tech.sadovnikov.configurator.R;

public class BluetoothDevicesAdapter extends RecyclerView.Adapter<BluetoothDevicesAdapter.BluetoothDeviceViewHolder> {
    private static final String TAG = "BluetoothDevicesAdapter";
    private ArrayList<BluetoothDevice> bluetoothDevices;

    public BluetoothDevicesAdapter(ArrayList<BluetoothDevice> bluetoothDevices) {
        Log.d(TAG, "onConstructor, " + bluetoothDevices.toString());
        this.bluetoothDevices = bluetoothDevices;
    }

    @NonNull
    @Override
    public BluetoothDeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewDevice = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bluetooth_device, parent, false);
        Log.d(TAG, "onCreateViewHolder");
        return new BluetoothDeviceViewHolder(viewDevice);
    }

    @Override
    public void onBindViewHolder(@NonNull BluetoothDeviceViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + bluetoothDevices.size());
        return bluetoothDevices.size();
    }
    class BluetoothDeviceViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDeviceName;
        private TextView tvDeviceAddress;

        BluetoothDeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDeviceName = itemView.findViewById(R.id.tv_device_name);
            tvDeviceAddress = itemView.findViewById(R.id.tv_device_address);
        }

        void bind(int position) {
            tvDeviceName.setText(bluetoothDevices.get(position).getName());
            tvDeviceAddress.setText(bluetoothDevices.get(position).getAddress());
        }
    }

}
