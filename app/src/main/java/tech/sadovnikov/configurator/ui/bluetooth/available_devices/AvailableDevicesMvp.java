package tech.sadovnikov.configurator.ui.bluetooth.available_devices;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface AvailableDevicesMvp {

    interface View extends MvpView{

    }

    interface Presenter extends MvpPresenter<View> {

        void onStartView();
    }

}
