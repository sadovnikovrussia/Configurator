package tech.sadovnikov.configurator.di.component;


import dagger.Component;
import tech.sadovnikov.configurator.di.PerFragment;
import tech.sadovnikov.configurator.di.module.FragmentModule;
import tech.sadovnikov.configurator.ui.bluetooth.BluetoothFragment;
import tech.sadovnikov.configurator.ui.bluetooth.available_devices.AvailableDevicesFragment;
import tech.sadovnikov.configurator.ui.bluetooth.paired_devices.PairedDevicesFragment;
import tech.sadovnikov.configurator.ui.configuration.ConfigurationFragment;

@PerFragment
@Component(modules = FragmentModule.class)
public interface FragmentComponent {

    void injectBluetoothFragment(BluetoothFragment bluetoothFragment);

    void injectPairedDevicesFragment(PairedDevicesFragment pairedDevicesFragment);

    void injectAvailableDevicesFragment(AvailableDevicesFragment availableDevicesFragment);

    void injectConfigurationFragment(ConfigurationFragment configurationFragment);
}
