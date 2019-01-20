package tech.sadovnikov.configurator.presentation.configuration.config_tabs;

import com.arellomobile.mvp.MvpView;

import tech.sadovnikov.configurator.model.data.configuration.Configuration;

public interface BaseCfgView extends MvpView {

    void showConfiguration(Configuration configuration);

}
