package tech.sadovnikov.configurator.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.view.BluetoothFragment;

public class PairedDevicesRvAdapter extends RecyclerView.Adapter<PairedDevicesRvAdapter.BluetoothDeviceViewHolder> {
    private static final String TAG = "PairedDevicesRvAdapter";

    private BluetoothFragment.OnBluetoothFragmentInteractionListener onBluetoothFragmentInteractionListener;

    public PairedDevicesRvAdapter(BluetoothFragment.OnBluetoothFragmentInteractionListener onBluetoothFragmentInteractionListener) {
        Log.i(TAG, "onConstructor");
        this.onBluetoothFragmentInteractionListener = onBluetoothFragmentInteractionListener;
    }

    @NonNull
    @Override
    public BluetoothDeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View deviceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bluetooth_device, parent, false);
        Log.d(TAG, "onCreateBluetoothDeviceViewHolder");
        return new BluetoothDeviceViewHolder(deviceView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BluetoothDeviceViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder");
        onBluetoothFragmentInteractionListener.onBindViewHolderOfPairedDevicesRvAdapter(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onBindViewHolder.onClick, position = " + String.valueOf(position) +
                        ", " + holder.tvDeviceName.getText() + ": " + holder.tvDeviceAddress.getText());
                onBluetoothFragmentInteractionListener.onPairedDevicesRvItemClicked(String.valueOf(holder.tvDeviceAddress.getText()));
            }
        });
    }

    @Override
    public int getItemCount() {
        int count = onBluetoothFragmentInteractionListener.onGetItemCountOfPairedDevicesRvAdapter();
        // Log.d(TAG, "getItemCount: " + String.valueOf(count));
        return count;
    }

    public void updatePairedBluetoothDevices() {
        Log.d(TAG, "updateAvailableBluetoothDevices");
        notifyDataSetChanged();
    }

    // ViewHolder
    class BluetoothDeviceViewHolder extends RecyclerView.ViewHolder implements PairedDevicesItemView {
        private static final String TAG = "PairDevAdaptViewHolder";
        private TextView tvDeviceName;
        private TextView tvDeviceAddress;

        BluetoothDeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "onConstructor");
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
