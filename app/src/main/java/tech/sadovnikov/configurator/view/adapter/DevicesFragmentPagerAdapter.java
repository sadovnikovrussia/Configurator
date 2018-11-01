package tech.sadovnikov.configurator.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import tech.sadovnikov.configurator.view.AvailableDevicesFragment;
import tech.sadovnikov.configurator.view.PairedDevicesFragment;

public class DevicesFragmentPagerAdapter extends FragmentPagerAdapter {
    // private static final String TAG = "DevicesFragPagerAdapter";

    private String tabTitles[] = new String[]{"Подключенные", "Доступные"};
    private PairedDevicesFragment pairedDevicesFragment;
    private AvailableDevicesFragment availableDevicesFragment;

    public DevicesFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        // Log.d(TAG, "onConstructor");
        // TODO <Надо ли здесь получать фрагменты?>
        pairedDevicesFragment = PairedDevicesFragment.getInstance();
        availableDevicesFragment = AvailableDevicesFragment.getInstance();
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        // Log.d(TAG, "onGetItem: position = " + String.valueOf(position));
        if (position == 0) {
            return pairedDevicesFragment;
        } else {
            return availableDevicesFragment;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Генерируем заголовок в зависимости от позиции
        return tabTitles[position];
    }

    public void updateAvailableDevices() {
        availableDevicesFragment.updateAvailableDevices();
    }

    public void updatePairedDevices() {
        pairedDevicesFragment.updatePairedDevices();
    }
}
