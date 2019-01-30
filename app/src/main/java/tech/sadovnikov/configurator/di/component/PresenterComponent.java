package tech.sadovnikov.configurator.di.component;

import dagger.Component;
import tech.sadovnikov.configurator.di.PresenterScope;
import tech.sadovnikov.configurator.di.module.PermissionsModule;
import tech.sadovnikov.configurator.model.FileManager;
import tech.sadovnikov.configurator.presentation.bluetooth.BluetoothPresenter;
import tech.sadovnikov.configurator.presentation.bluetooth.available_devices.AvailableDevicesPresenter;
import tech.sadovnikov.configurator.presentation.bluetooth.paired_devices.PairedDevicesPresenter;
import tech.sadovnikov.configurator.presentation.configuration.config_tabs.base.BaseCfgPresenter;
import tech.sadovnikov.configurator.presentation.configuration.config_tabs.config_buoy.ConfigBuoyPresenter;
import tech.sadovnikov.configurator.presentation.configuration.config_tabs.config_main.ConfigMainPresenter;
import tech.sadovnikov.configurator.presentation.console.ConsolePresenter;
import tech.sadovnikov.configurator.presentation.main.MainPresenter;

@PresenterScope
@Component(dependencies = ApplicationComponent.class, modules = PermissionsModule.class)
public interface PresenterComponent {

    void injectBluetoothPresenter(BluetoothPresenter bluetoothPresenter);

    void injectPairedDevicesPresenter(PairedDevicesPresenter pairedDevicesPresenter);

    void injectAvailableDevicesPresenter(AvailableDevicesPresenter availableDevicesPresenter);

    void injectConsolePresenter(ConsolePresenter consolePresenter);

    void injectMainPresenter(MainPresenter mainPresenter);

    void injectBaseCfgPresenter(BaseCfgPresenter baseCfgPresenter);

    void injectConfigBuoyPresenter(ConfigBuoyPresenter configBuoyPresenter);

    void injectConfigMainPresenter(ConfigMainPresenter configMainPresenter);

    FileManager getFileManager();
}
