package tech.sadovnikov.configurator.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import tech.sadovnikov.configurator.di.ActivityContext;

@Module
public class ActivityModule {
    AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }


    @Provides
    AppCompatActivity provideActivity() {
        return activity;
    }

    @ActivityContext
    @Provides
    Context provideActivityContext(){
        return activity;
    }
}
