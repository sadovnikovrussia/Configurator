package tech.sadovnikov.configurator.ui.bluetooth.paired_devices;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface PairedDevicesMvp {

    interface View extends MvpView {
        void showPairedDevices();

        void hidePairedDevices();
    }

    interface Presenter extends MvpPresenter<View> {

    }
}
