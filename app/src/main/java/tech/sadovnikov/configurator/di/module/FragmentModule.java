package tech.sadovnikov.configurator.di.module;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import dagger.Module;
import dagger.Provides;
import tech.sadovnikov.configurator.di.ActivityContext;
import tech.sadovnikov.configurator.di.PerFragment;
import tech.sadovnikov.configurator.ui.adapter.DevicesFragmentPagerAdapter;
import tech.sadovnikov.configurator.ui.bluetooth.paired_devices.PairedDevicesMvp;
import tech.sadovnikov.configurator.ui.bluetooth.paired_devices.PairedDevicesPresenter;

@Module
public class FragmentModule {

    private MvpFragment fragment;

    public FragmentModule(MvpFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @PerFragment
    DevicesFragmentPagerAdapter provideDevicesFragmentPagerAdapter(){
        return new DevicesFragmentPagerAdapter(fragment.getChildFragmentManager());
    }

//    @Provides
//    @PerFragment
//    PairedDevicesMvp.Presenter providePairedDevicesMvpPresenter(PairedDevicesMvp.Presenter presenter){
//        return presenter;
//    }



}
