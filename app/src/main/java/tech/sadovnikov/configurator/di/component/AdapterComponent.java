package tech.sadovnikov.configurator.di.component;

import dagger.Component;
import tech.sadovnikov.configurator.di.AdapterScope;
import tech.sadovnikov.configurator.model.data.DataManager;

@AdapterScope
@Component(dependencies = ApplicationComponent.class)
public interface AdapterComponent {
    DataManager getDataManager();
}
