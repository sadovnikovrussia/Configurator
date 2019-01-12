package tech.sadovnikov.configurator.di.component;

import dagger.Component;
import tech.sadovnikov.configurator.di.PresenterScope;
import tech.sadovnikov.configurator.di.module.PermissionsModule;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.ui.bluetooth.BluetoothPresenter;
import tech.sadovnikov.configurator.ui.bluetooth.available_devices.AvailableDevicesPresenter;
import tech.sadovnikov.configurator.ui.bluetooth.paired_devices.PairedDevicesPresenter;
import tech.sadovnikov.configurator.ui.console.ConsolePresenter;
import tech.sadovnikov.configurator.ui.main.MainPresenter;

@PresenterScope
@Component(dependencies = ApplicationComponent.class, modules = PermissionsModule.class)
public interface PresenterComponent {

    void injectBluetoothPresenter(BluetoothPresenter bluetoothPresenter);

    void injectPairedDevicesPresenter(PairedDevicesPresenter pairedDevicesPresenter);

    void injectAvailableDevicesPresenter(AvailableDevicesPresenter availableDevicesPresenter);

    void injectConsolePresenter(ConsolePresenter consolePresenter);

    void injectMainPresenter(MainPresenter mainPresenter);
}
