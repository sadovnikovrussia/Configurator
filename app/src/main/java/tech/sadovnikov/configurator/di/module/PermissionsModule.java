package tech.sadovnikov.configurator.di.module;

import android.Manifest;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import tech.sadovnikov.configurator.di.ApplicationContext;
import tech.sadovnikov.configurator.di.PresenterScope;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

@Module
public class PermissionsModule {

    @PresenterScope
    @Provides
    int checkBluetoothPermission(@ApplicationContext Context context) {
        return checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

}
