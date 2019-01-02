package tech.sadovnikov.configurator.di.component;

import dagger.Component;
import tech.sadovnikov.configurator.di.PresenterScope;
import tech.sadovnikov.configurator.di.module.PermissionsModule;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.ui.bluetooth.BluetoothPresenter;
import tech.sadovnikov.configurator.ui.bluetooth.available_devices.AvailableDevicesPresenter;
import tech.sadovnikov.configurator.ui.bluetooth.paired_devices.PairedDevicesPresenter;

@PresenterScope
@Component(dependencies = ApplicationComponent.class, modules = PermissionsModule.class)
public interface BluetoothComponent {

    void injectBluetoothPresenter(BluetoothPresenter bluetoothPresenter);

    void injectPairedDevicesPresenter(PairedDevicesPresenter pairedDevicesPresenter);

    void injectAvailableDevicesPresenter(AvailableDevicesPresenter availableDevicesPresenter);
}
