package tech.sadovnikov.configurator.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import tech.sadovnikov.configurator.view.BluetoothFragment;
import tech.sadovnikov.configurator.view.PageFragment;

public class DevicesFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "DevicesFragPagerAdapter";
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Подключенные", "Доступные"};
    private Context context;

    public DevicesFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
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
            return PageFragment.newInstance(position + 1);
        } else {
            return new Fragment();
        }
        //return PageFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // генерируем заголовок в зависимости от позиции
        return tabTitles[position];
    }
}
