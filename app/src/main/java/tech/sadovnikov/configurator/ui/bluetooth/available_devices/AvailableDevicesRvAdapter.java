package tech.sadovnikov.configurator.ui.bluetooth.available_devices;

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
        Log.i(TAG, "onConstructor: ");
    }

    @NonNull
    @Override
    public BluetoothDeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View deviceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bluetooth_device, parent, false);
        return new BluetoothDeviceViewHolder(deviceView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BluetoothDeviceViewHolder holder, final int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    void setDevices(List<BluetoothDevice> devices) {
        this.devices = devices;
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
            ButterKnife.bind(this, itemView);
        }

        public void onBind(int position) {
            super.onBind(position);
            BluetoothDevice device = devices.get(position);
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
