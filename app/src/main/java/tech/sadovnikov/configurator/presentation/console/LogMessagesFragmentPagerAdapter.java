package tech.sadovnikov.configurator.presentation.console;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

import io.reactivex.disposables.Disposable;
import tech.sadovnikov.configurator.model.data.DataManager;
import tech.sadovnikov.configurator.presentation.console.log_messages.LogMessagesFragment;
import tech.sadovnikov.configurator.utils.rx.RxTransformers;

public class LogMessagesFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = LogMessagesFragmentPagerAdapter.class.getSimpleName();

    private List<String> tabNames;
    DataManager dataManager;

    LogMessagesFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        Disposable subscribe = dataManager.getObservableLogTabs()
                .compose(RxTransformers.applySchedulers())
                .subscribe(strings -> {
                            Log.d(TAG, "onNext: " + strings);
                            tabNames = strings;
                            notifyDataSetChanged();
                        },
                        Throwable::printStackTrace,
                        () -> {
                        },
                        disposable -> {
                        }
                );
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount: " + tabNames.size());
        return tabNames.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Log.d(TAG, "getPageTitle: " + position);
        return tabNames.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return LogMessagesFragment.newInstance(tabNames.get(position));
    }

}
