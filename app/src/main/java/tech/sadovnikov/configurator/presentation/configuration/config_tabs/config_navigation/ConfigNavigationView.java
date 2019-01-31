package tech.sadovnikov.configurator.presentation.configuration.config_tabs.config_navigation;

import tech.sadovnikov.configurator.presentation.configuration.config_tabs.base.BaseCfgView;

public interface ConfigNavigationView extends BaseCfgView {
    String name = "Навигация";

    void showPositionInMaps(String latitude, String longitude);
}
