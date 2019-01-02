package tech.sadovnikov.configurator.ui.bluetooth.available_devices;

import android.bluetooth.BluetoothDevice;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.*;

import java.util.List;

@StateStrategyType(SkipStrategy.class)
public interface AvailableDevicesView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setAvailableDevices(List<BluetoothDevice> availableDevices);

}
