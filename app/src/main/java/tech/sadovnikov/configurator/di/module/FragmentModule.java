package tech.sadovnikov.configurator.di.module;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import dagger.Module;
import dagger.Provides;
import tech.sadovnikov.configurator.di.PerFragment;
import tech.sadovnikov.configurator.ui.bluetooth.available_devices.AvailableDevicesRvAdapter;
import tech.sadovnikov.configurator.ui.configuration.ConfigTabsRvAdapter;
import tech.sadovnikov.configurator.ui.bluetooth.DevicesFragmentPagerAdapter;
import tech.sadovnikov.configurator.ui.bluetooth.paired_devices.PairedDevicesRvAdapter;

@Module
public class FragmentModule {

    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @PerFragment
    DevicesFragmentPagerAdapter provideDevicesFragmentPagerAdapter(){
        return new DevicesFragmentPagerAdapter(fragment.getChildFragmentManager());
    }

    @Provides
    @PerFragment
    LinearLayoutManager provideLinearLayoutManager(){
        return new LinearLayoutManager(fragment.requireContext());
    }

    @Provides
    @PerFragment
    PairedDevicesRvAdapter providePairedDevicesRvAdapter(){
        return new PairedDevicesRvAdapter();
    }

    @Provides
    @PerFragment
    AvailableDevicesRvAdapter provideAvailableDevicesRvAdapter(){
        return new AvailableDevicesRvAdapter();
    }

    @Provides
    @PerFragment
    ConfigTabsRvAdapter provideConfigTabsRvAdapter(){
        return new ConfigTabsRvAdapter();
    }


}
