package tech.sadovnikov.configurator.di.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import tech.sadovnikov.configurator.di.ActivityContext;
import tech.sadovnikov.configurator.di.ApplicationContext;
import tech.sadovnikov.configurator.di.module.ApplicationModule;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.model.data.DataManager;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(Application application);

    @ApplicationContext
    Context context();

    Application getApplication();

    DataManager getDataManager();

    BluetoothService getBluetoothService();

}
