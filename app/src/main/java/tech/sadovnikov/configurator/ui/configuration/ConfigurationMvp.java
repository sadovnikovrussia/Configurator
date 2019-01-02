package tech.sadovnikov.configurator.ui.configuration;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface ConfigurationMvp {

    interface View extends MvpView {

    }

    interface Presenter extends MvpPresenter<View> {

    }
}
