package tech.sadovnikov.configurator.presentation.bluetooth.available_devices;

import android.bluetooth.BluetoothDevice;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.*;

import java.util.List;

public interface AvailableDevicesView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setAvailableDevices(List<BluetoothDevice> availableDevices);

}
