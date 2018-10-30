package tech.sadovnikov.configurator.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.view.ConfigurationFragment;

public class ConfigTabsRvAdapter extends RecyclerView.Adapter<ConfigTabsRvAdapter.ConfigTabsViewHolder> {
    // private static final String TAG = "RvBtDevicesAdapter";

    private ConfigurationFragment.OnConfigurationFragmentInteractionListener onConfigurationFragmentInteractionListener;

    private String[] configTabs = new String[]{"Буй", "Основные", "Навигация", "События", "Сервер"};


    public ConfigTabsRvAdapter(ConfigurationFragment.OnConfigurationFragmentInteractionListener onConfigurationFragmentInteractionListener) {
        // Logs.d(TAG, "onConstructor, " + bluetoothDevices.toString());
        this.onConfigurationFragmentInteractionListener = onConfigurationFragmentInteractionListener;
        }

    @NonNull
    @Override
    public ConfigTabsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View configTabView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_config_tab, parent, false);
        // Logs.d(TAG, "onCreateBluetoothDeviceViewHolder");
        return new ConfigTabsViewHolder(configTabView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ConfigTabsViewHolder holder, int position) {
        // Logs.d(TAG, "onBindViewHolder");
        holder.bind(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onConfigurationFragmentInteractionListener.onConfigTabsRvItemClick((String) holder.tvConfigTabName.getText());
            }
        });
    }

    @Override
    public int getItemCount() {
        // Logs.d(TAG, "getItemCount: " + bluetoothDevices.size());
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
