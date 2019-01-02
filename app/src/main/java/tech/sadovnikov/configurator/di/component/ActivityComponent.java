package tech.sadovnikov.configurator.di.component;

import android.content.Context;

import dagger.Component;
import tech.sadovnikov.configurator.di.ActivityContext;
import tech.sadovnikov.configurator.di.ActivityScope;
import tech.sadovnikov.configurator.di.module.ActivityModule;
import tech.sadovnikov.configurator.model.BluetoothService;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    BluetoothService getBluetoothService();
}
