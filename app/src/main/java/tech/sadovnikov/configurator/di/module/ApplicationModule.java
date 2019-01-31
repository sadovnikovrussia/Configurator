package tech.sadovnikov.configurator.di.module;

import android.Manifest;
import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tech.sadovnikov.configurator.di.ApplicationContext;
import tech.sadovnikov.configurator.di.BluetoothPermission;
import tech.sadovnikov.configurator.model.AppBluetoothService;
import tech.sadovnikov.configurator.model.BluetoothBroadcastReceiver;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.model.data.FileManager;
import tech.sadovnikov.configurator.model.MessageAnalyzer;
import tech.sadovnikov.configurator.model.data.AppDataManager;
import tech.sadovnikov.configurator.model.data.DataManager;
import tech.sadovnikov.configurator.model.data.logs.AppLogManager;
import tech.sadovnikov.configurator.model.data.logs.LogManager;
import tech.sadovnikov.configurator.model.data.configuration.Configuration;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

@Module
public class ApplicationModule {
    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    @ApplicationContext
    Context provideAppContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    LogManager provideLogManager() {
        return new AppLogManager();
    }

    @Provides
    @Singleton
    Configuration provideConfiguration() {
        return new Configuration();
    }

    @Provides
    @Singleton
    BluetoothService provideBluetoothService() {
        return new AppBluetoothService();
    }

    @Provides
    @Singleton
    BluetoothBroadcastReceiver provideBluetoothReceiver(BluetoothService bluetoothService) {
        return new BluetoothBroadcastReceiver((BluetoothBroadcastReceiver.Listener) bluetoothService);
    }

    @Provides
    @Singleton
    MessageAnalyzer provideStreamAnalyzer(BluetoothService bluetoothService, DataManager dataManager) {
        return new MessageAnalyzer(bluetoothService, dataManager);
    }

    @Provides
    FileManager provideFileManager(FileManager fileManager) {
        return fileManager;
    }

    @BluetoothPermission
    @Provides
    int checkBluetoothPermission() {
        return checkSelfPermission(application.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
    }

}
