package tech.sadovnikov.configurator.di.module;

import dagger.Module;
import dagger.Provides;
import tech.sadovnikov.configurator.model.MessageAnalyzer;

@Module
public class AnalyzersModule {

    @Provides
    MessageAnalyzer provideMessageAnalyzer(){
        return new MessageAnalyzer();
    }
}
