package tech.sadovnikov.configurator.view.adapter;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tech.sadovnikov.configurator.R;

public class ConfigTabsRvAdapter extends RecyclerView.Adapter<ConfigTabsRvAdapter.ConfigTabsViewHolder> {
    private static final String TAG = "RvBtDevicesAdapter";
    private String[] configTabs;


    public ConfigTabsRvAdapter(String[] configTabs) {
        // Log.d(TAG, "onConstructor, " + bluetoothDevices.toString());
        this.configTabs = configTabs;
    }

    @NonNull
    @Override
    public ConfigTabsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View deviceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_config_tab, parent, false);
        // Log.d(TAG, "onCreateBluetoothDeviceViewHolder");
        return new ConfigTabsViewHolder(deviceView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfigTabsViewHolder holder, int position) {
        // Log.d(TAG, "onBindViewHolder");
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        // Log.d(TAG, "getItemCount: " + bluetoothDevices.size());
        return configTabs.length;
    }

    class ConfigTabsViewHolder extends RecyclerView.ViewHolder {
        private TextView tvConfigTabName;

        ConfigTabsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvConfigTabName = itemView.findViewById(R.id.tv_config_tab_name);
        }

        void bind(int position) {
            tvConfigTabName.setText(configTabs[position]);
        }
    }

}
