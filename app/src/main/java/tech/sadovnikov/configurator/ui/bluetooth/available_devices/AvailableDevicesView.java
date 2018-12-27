package tech.sadovnikov.configurator.ui.bluetooth.available_devices;

import com.arellomobile.mvp.MvpView;

public interface AvailableDevicesView extends MvpView {

    void showDevices();

    void hideDevices();

}
