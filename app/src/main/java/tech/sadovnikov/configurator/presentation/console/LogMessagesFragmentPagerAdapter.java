package tech.sadovnikov.configurator.presentation.console;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

import io.reactivex.disposables.Disposable;
import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.di.component.AdapterComponent;
import tech.sadovnikov.configurator.di.component.DaggerAdapterComponent;
import tech.sadovnikov.configurator.model.data.DataManager;
import tech.sadovnikov.configurator.presentation.console.log_messages.LogMessagesFragment;
import tech.sadovnikov.configurator.utils.rx.RxTransformers;

public class LogMessagesFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = LogMessagesFragmentPagerAdapter.class.getSimpleName();

    private DataManager dataManager;
    private List<String> tabNames;

    LogMessagesFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        Log.d(TAG, "onConstructor: ");
        AdapterComponent adapterComponent = DaggerAdapterComponent
                .builder()
                .applicationComponent(App.getApplicationComponent())
                .build();
        dataManager = adapterComponent.getDataManager();
        Disposable subscribe = dataManager.getObservableLogTabs()
                .compose(RxTransformers.applySchedulers())
                .subscribe(tabs -> {
                            tabNames = tabs;
                            notifyDataSetChanged();
                        },
                        Throwable::printStackTrace,
                        () -> {
                        },
                        disposable -> {
                            tabNames = dataManager.getLogTabs();
                        }
                );
    }

    @Override
    public int getCount() {
        return tabNames.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return LogMessagesFragment.newInstance(tabNames.get(position));
    }

}
