package tech.sadovnikov.configurator.presentation.bluetooth.paired_devices;

import android.bluetooth.BluetoothDevice;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.*;

import java.util.List;

@StateStrategyType(SkipStrategy.class)
public interface PairedDevicesView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setPairedDevices(List<BluetoothDevice> pairedDevices);

}
