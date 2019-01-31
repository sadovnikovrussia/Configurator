package tech.sadovnikov.configurator.di.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.di.ApplicationContext;
import tech.sadovnikov.configurator.di.module.ApplicationModule;
import tech.sadovnikov.configurator.model.BluetoothBroadcastReceiver;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.model.data.DataManager;
import tech.sadovnikov.configurator.model.data.logs.LogManager;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(App application);

    @ApplicationContext
    Context getContext();

    Application getApplication();

    DataManager getDataManager();

    BluetoothService getBluetoothService();

    BluetoothBroadcastReceiver getBluetoothBroadcastReceiver();

    LogManager getLogManager();

    //FileManager getFileManager();
}
