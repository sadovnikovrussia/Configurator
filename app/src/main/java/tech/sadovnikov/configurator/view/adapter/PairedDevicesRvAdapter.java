package tech.sadovnikov.configurator.view.adapter;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.view.BluetoothFragment;

public class PairedDevicesRvAdapter extends RecyclerView.Adapter<PairedDevicesRvAdapter.BluetoothDeviceViewHolder> {
    private static final String TAG = "RvBtDevicesAdapter";

    private ArrayList<BluetoothDevice> bluetoothDevices;

    private OnPairedDevicesRvItemClickListener onPairedDevicesRvItemClickListener;
    BluetoothFragment.OnBluetoothFragmentInteractionListener onBluetoothFragmentInteractionListener;

    public PairedDevicesRvAdapter(ArrayList<BluetoothDevice> bluetoothDevices,
                                  BluetoothFragment.OnBluetoothFragmentInteractionListener onBluetoothFragmentInteractionListener) {
        // Log.d(TAG, "onConstructor, " + bluetoothDevices.toString());
        this.bluetoothDevices = bluetoothDevices;
        this.onBluetoothFragmentInteractionListener = onBluetoothFragmentInteractionListener;
        //this.onPairedDevicesRvItemClickListener = onPairedDevicesRvItemClickListener;
    }

    @NonNull
    @Override
    public BluetoothDeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View deviceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bluetooth_device, parent, false);
        // Log.d(TAG, "onCreateBluetoothDeviceViewHolder");
        return new BluetoothDeviceViewHolder(deviceView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BluetoothDeviceViewHolder holder, final int position) {
        // Log.d(TAG, "onBindViewHolder");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onBindViewHolder.onClick, position = " + String.valueOf(position) + ", " + bluetoothDevices.get(position).getName());
                onBluetoothFragmentInteractionListener.onPairedDevicesRvItemClicked(bluetoothDevices.get(position));
                // onPairedDevicesRvItemClickListener.OnPairedDevicesRvItemClick(bluetoothDevices.get(position));
            }
        });
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        // Log.d(TAG, "getItemCount: " + bluetoothDevices.size());
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

    public interface OnPairedDevicesRvItemClickListener {

        void OnPairedDevicesRvItemClick(BluetoothDevice bluetoothDevice);

    }

}
