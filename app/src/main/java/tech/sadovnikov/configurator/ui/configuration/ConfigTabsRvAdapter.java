package tech.sadovnikov.configurator.ui.configuration;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;

public class ConfigTabsRvAdapter extends RecyclerView.Adapter<ConfigTabsRvAdapter.ConfigTabsViewHolder> {
    private static final String TAG = ConfigTabsRvAdapter.class.getSimpleName();

    private ConfigurationFragment.OnConfigurationFragmentInteractionListener onConfigurationFragmentInteractionListener;

    private String[] configTabs = new String[]{"Буй", "Основные", "Навигация", "События", "Сервер", "SIM карта"};

    public ConfigTabsRvAdapter() {
    }

    public ConfigTabsRvAdapter(ConfigurationFragment.OnConfigurationFragmentInteractionListener onConfigurationFragmentInteractionListener) {
        // Logs.d(TAG, "onConstructor, " 1+ bluetoothDevices.toString());
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
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        // Logs.d(TAG, "getItemCount: " + bluetoothDevices.size());
        return configTabs.length;
    }

    class ConfigTabsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_config_tab_name)
        TextView tvConfigTabName;

        ConfigTabsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void onBind(int position) {
            tvConfigTabName.setText(configTabs[position]);
        }
    }

}
