package tech.sadovnikov.configurator.presentation.configuration.config_tabs.base;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import tech.sadovnikov.configurator.model.data.configuration.Configuration;

@StateStrategyType(SkipStrategy.class)
public interface BaseCfgView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showConfiguration(Configuration configuration);

}
