package tech.sadovnikov.configurator.presentation.configuration.config_tabs.config_events;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import tech.sadovnikov.configurator.presentation.configuration.config_tabs.base.BaseCfgView;

@StateStrategyType(SkipStrategy.class)
public interface ConfigEventsView extends BaseCfgView {
    String name = "События";

    void showCreateAlarmEventsMessageView();

    void hideAlarmEventsMessageView();
}
