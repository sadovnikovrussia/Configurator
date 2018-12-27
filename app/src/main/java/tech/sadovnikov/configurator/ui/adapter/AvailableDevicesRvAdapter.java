package tech.sadovnikov.configurator.ui.adapter;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.ui.base.BaseViewHolder;

public class AvailableDevicesRvAdapter extends RecyclerView.Adapter<AvailableDevicesRvAdapter.BluetoothDeviceViewHolder> {
    private static final String TAG = AvailableDevicesRvAdapter.class.getSimpleName();

    private Listener listener;

    private List<BluetoothDevice> devices = new ArrayList<>();

    public AvailableDevicesRvAdapter() {
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
        holder.onBind(position);
//
//        onBluetoothFragmentInteractionListener.onBindViewHolderOfAvailableDevicesRvAdapter(holder, position);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBluetoothFragmentInteractionListener.onAvailableDevicesRvItemClicked(String.valueOf(holder.tvDeviceAddress.getText()));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        // Logs.d(TAG, "getItemCount: " + String.valueOf(count));
        return devices.size();
    }

    public void setDevices(List<BluetoothDevice> devices) {
        this.devices = devices;
    }

    public void updateAvailableBluetoothDevices() {
        Log.d(TAG, "updateAvailableBluetoothDevices");
        notifyDataSetChanged();
    }

    // ViewHolder
    public class BluetoothDeviceViewHolder extends BaseViewHolder {
        // private static final String TAG = "AvailDevAdaptViewHolder";
        @BindView(R.id.tv_device_name)
        TextView tvDeviceName;
        @BindView(R.id.tv_device_address)
        TextView tvDeviceAddress;

        BluetoothDeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            // Log.d(TAG, "onConstructor");
            ButterKnife.bind(this, itemView);
        }

        public void onBind(int position) {
            super.onBind(position);
            final BluetoothDevice device = devices.get(position);
            tvDeviceName.setText(device.getName());
            tvDeviceAddress.setText(device.getAddress());
            itemView.setOnClickListener(v -> listener.onDeviceClicked(device));
        }

        @Override
        protected void clear() {

        }

    }

    public interface Listener {
        void onDeviceClicked(BluetoothDevice device);
    }


}
