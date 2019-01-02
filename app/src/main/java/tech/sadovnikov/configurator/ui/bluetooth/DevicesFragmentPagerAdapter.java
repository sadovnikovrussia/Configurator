package tech.sadovnikov.configurator.ui.bluetooth;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import tech.sadovnikov.configurator.ui.bluetooth.available_devices.AvailableDevicesFragment;
import tech.sadovnikov.configurator.ui.bluetooth.paired_devices.PairedDevicesFragment;

public class DevicesFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "DevicesFragPagerAdapter";

    private String[] tabNames = new String[]{"Подключенные", "Доступные"};

    public DevicesFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        Log.v(TAG, "onConstructor: ");
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return PairedDevicesFragment.newInstance();
        } else {
            return AvailableDevicesFragment.newInstance();
        }
    }

}
