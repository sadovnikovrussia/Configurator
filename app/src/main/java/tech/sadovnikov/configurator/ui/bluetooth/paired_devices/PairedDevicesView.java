package tech.sadovnikov.configurator.ui.bluetooth.paired_devices;

import android.bluetooth.BluetoothDevice;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

interface PairedDevicesView extends MvpView {

    void showPairedDevices(List<BluetoothDevice> devices);

    void hidePairedDevices();
}
