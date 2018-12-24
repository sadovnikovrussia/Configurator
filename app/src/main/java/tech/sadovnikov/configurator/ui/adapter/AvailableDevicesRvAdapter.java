package tech.sadovnikov.configurator.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.ui.bluetooth.BluetoothFragment;

public class AvailableDevicesRvAdapter extends RecyclerView.Adapter<AvailableDevicesRvAdapter.BluetoothDeviceViewHolder> {
    private static final String TAG = "AvailDevicesRvAdapter";

    private BluetoothFragment.OnBluetoothFragmentInteractionListener onBluetoothFragmentInteractionListener;

    public AvailableDevicesRvAdapter(BluetoothFragment.OnBluetoothFragmentInteractionListener onBluetoothFragmentInteractionListener) {
        // Logs.w(TAG, "onConstructor");
        this.onBluetoothFragmentInteractionListener = onBluetoothFragmentInteractionListener;
    }

    @NonNull
    @Override
    public BluetoothDeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View deviceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bluetooth_device, parent, false);
        // Logs.d(TAG, "onCreateBluetoothDeviceViewHolder");
        return new BluetoothDeviceViewHolder(deviceView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BluetoothDeviceViewHolder holder, final int position) {
        // Logs.d(TAG, "onBindViewHolder");
        onBluetoothFragmentInteractionListener.onBindViewHolderOfAvailableDevicesRvAdapter(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBluetoothFragmentInteractionListener.onAvailableDevicesRvItemClicked(String.valueOf(holder.tvDeviceAddress.getText()));
            }
        });
    }

    @Override
    public int getItemCount() {
        // Logs.d(TAG, "getItemCount: " + String.valueOf(count));
        return onBluetoothFragmentInteractionListener.onGetItemCountOfAvailableDevicesRvAdapter();
    }

    public void updateAvailableBluetoothDevices() {
        Log.d(TAG, "updateAvailableBluetoothDevices");
        notifyDataSetChanged();
    }

    // ViewHolder
    public class BluetoothDeviceViewHolder extends RecyclerView.ViewHolder implements AvailableDevicesItemView {
        // private static final String TAG = "AvailDevAdaptViewHolder";
        private TextView tvDeviceName;
        private TextView tvDeviceAddress;

        BluetoothDeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            // Log.d(TAG, "onConstructor");
            tvDeviceName = itemView.findViewById(R.id.tv_device_name);
            tvDeviceAddress = itemView.findViewById(R.id.tv_device_address);
        }

        @Override
        public void setDeviceName(String name) {
            tvDeviceName.setText(name);
        }

        @Override
        public void setDeviceAddress(String address) {
            tvDeviceAddress.setText(address);
        }

    }


}
