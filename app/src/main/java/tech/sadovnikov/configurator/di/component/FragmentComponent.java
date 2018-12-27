package tech.sadovnikov.configurator.di.component;

import dagger.Component;
import tech.sadovnikov.configurator.di.PerFragment;
import tech.sadovnikov.configurator.di.module.FragmentModule;
import tech.sadovnikov.configurator.ui.bluetooth.BluetoothFragment;

@PerFragment
@Component(modules = FragmentModule.class)
public interface FragmentComponent {

    void injectBluetoothFargment(BluetoothFragment bluetoothFragment);

}
