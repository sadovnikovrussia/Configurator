package tech.sadovnikov.configurator.di.component;


import dagger.Component;
import tech.sadovnikov.configurator.di.PerFragment;
import tech.sadovnikov.configurator.di.module.FragmentModule;
import tech.sadovnikov.configurator.presentation.bluetooth.BluetoothFragment;
import tech.sadovnikov.configurator.presentation.bluetooth.available_devices.AvailableDevicesFragment;
import tech.sadovnikov.configurator.presentation.bluetooth.paired_devices.PairedDevicesFragment;
import tech.sadovnikov.configurator.presentation.configuration.ConfigurationFragment;

@PerFragment
@Component(modules = FragmentModule.class)
public interface FragmentComponent {

    void injectBluetoothFragment(BluetoothFragment bluetoothFragment);

    void injectPairedDevicesFragment(PairedDevicesFragment pairedDevicesFragment);

    void injectAvailableDevicesFragment(AvailableDevicesFragment availableDevicesFragment);

    void injectConfigurationFragment(ConfigurationFragment configurationFragment);
}
