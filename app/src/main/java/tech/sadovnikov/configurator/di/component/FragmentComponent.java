package tech.sadovnikov.configurator.di.component;

import javax.inject.Inject;

import dagger.Component;
import tech.sadovnikov.configurator.di.PerFragment;
import tech.sadovnikov.configurator.di.module.BluetoothFragmentModule;
import tech.sadovnikov.configurator.ui.bluetooth.BluetoothFragment;

@PerFragment
@Component(modules = BluetoothFragmentModule.class, dependencies = ApplicationComponent.class)
public interface FragmentComponent {

    void injectBluetoothFargment(BluetoothFragment bluetoothFragment);

}
