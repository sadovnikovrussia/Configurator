package tech.sadovnikov.configurator.di.module;

import android.Manifest;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import tech.sadovnikov.configurator.di.ApplicationContext;
import tech.sadovnikov.configurator.di.BluetoothPermission;
import tech.sadovnikov.configurator.di.ReadPermission;
import tech.sadovnikov.configurator.di.WritePermission;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

@Module
public class PermissionsModule {

    @Provides
    @BluetoothPermission
    int checkBluetoothPermission(@ApplicationContext Context context) {
        return checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    @Provides
    @WritePermission
    int checkWriteExternalStoragePermission(@ApplicationContext Context context) {
        return checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

//    @Provides
//    @ReadPermission
//    int checkReadExternalStoragePermission(@ApplicationContext Context context) {
//        return checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
//    }

}
