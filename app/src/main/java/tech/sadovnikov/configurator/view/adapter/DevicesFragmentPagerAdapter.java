package tech.sadovnikov.configurator.view.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;

import tech.sadovnikov.configurator.view.AvailableDevicesFragment;
import tech.sadovnikov.configurator.view.MainActivity;
import tech.sadovnikov.configurator.view.PairedDevicesFragment;

public class DevicesFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "DevicesFragPagerAdapter";

    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Подключенные", "Доступные"};
    private Context context;
    public PairedDevicesFragment pairedDevicesFragment;
    public AvailableDevicesFragment availableDevicesFragment;

    public DevicesFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        pairedDevicesFragment = PairedDevicesFragment.newInstance();
        availableDevicesFragment = AvailableDevicesFragment.newInstance();
        Log.d(TAG, "onConstructor");
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "onGetItem: position = " + String.valueOf(position));
        if (position == 0) {
            return pairedDevicesFragment;
        } else {
            return availableDevicesFragment;
        }
        //return PairedDevicesFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Генерируем заголовок в зависимости от позиции
        return tabTitles[position];
    }

    public void showAvailableDevices() {
        Log.d(TAG,"showAvailableDevices()");
        availableDevicesFragment.showAvailableDevices();
    }

    public void showPairedDevices() {
        Log.d(TAG, "showPairedDevices()");
        pairedDevicesFragment.showPairedDevices();
    }
}
