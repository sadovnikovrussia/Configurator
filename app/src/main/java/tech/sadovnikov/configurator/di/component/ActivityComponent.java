package tech.sadovnikov.configurator.di.component;

import android.content.Context;

import dagger.Component;
import tech.sadovnikov.configurator.di.ActivityContext;
import tech.sadovnikov.configurator.di.module.ActivityModule;

@Component(modules = ActivityModule.class)
public interface ActivityComponent {


}
