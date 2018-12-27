package tech.sadovnikov.configurator.ui.bluetooth;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.di.component.DaggerFragmentComponent;
import tech.sadovnikov.configurator.di.component.FragmentComponent;
import tech.sadovnikov.configurator.di.module.FragmentModule;
import tech.sadovnikov.configurator.ui.adapter.AvailableDevicesItemView;
import tech.sadovnikov.configurator.ui.adapter.DevicesFragmentPagerAdapter;
import tech.sadovnikov.configurator.ui.adapter.PairedDevicesItemView;


public class BluetoothFragment extends MvpAppCompatFragment implements BluetoothView {
    public static final String TAG = BluetoothFragment.class.getSimpleName();

    @InjectPresenter
    BluetoothPresenter bluetoothPresenter;

    // UI
    @BindView(R.id.sw_bluetooth)
    Switch switchBt;
    @BindView(R.id.tab_layout_devices)
    TabLayout tabLayout;
    @BindView(R.id.view_pager_devices)
    ViewPager viewPager;

    @Inject
    DevicesFragmentPagerAdapter devicesFragmentPagerAdapter;

    FragmentComponent fragmentComponent;

    private static final BluetoothFragment fragment = new BluetoothFragment();

    private OnBluetoothFragmentInteractionListener listener;

    public static BluetoothFragment newInstance() {
        Log.v(TAG, "newInstance: ");
        Bundle args = new Bundle();
        BluetoothFragment fragment = new BluetoothFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static BluetoothFragment getInstance() {
        Log.e(TAG, "getInstance: " + fragment);
        return fragment;
    }

    public BluetoothFragment() {
        Log.v(TAG, "BluetoothFragment: ");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");
        View inflate = inflater.inflate(R.layout.fragment_bluetooth, container, false);
        ButterKnife.bind(this, inflate);
        initDaggerAndInject();
        setUp();
        return inflate;
    }

    private void initDaggerAndInject() {
        fragmentComponent = DaggerFragmentComponent
                .builder()
                .fragmentModule(new FragmentModule(this))
                .build();
        fragmentComponent.injectBluetoothFragment(this);
    }

    private void setUp() {
        switchBt.setOnCheckedChangeListener((buttonView, isChecked) -> bluetoothPresenter.onBtSwitchClick(isChecked));
        devicesFragmentPagerAdapter.setTabsCount(tabLayout.getTabCount());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + String.valueOf(position));
                //listener.onDevicesPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(devicesFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void displayBluetoothState(boolean state) {
        switchBt.setChecked(state);
    }

    @Override
    public void showDevicesContainer() {
//        viewPager.setVisibility(View.VISIBLE);
//        tabLayout.setVisibility(View.VISIBLE);
        //devicesFragmentPagerAdapter
        viewPager.setAdapter(devicesFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void hideDevicesContainer() {
//        viewPager.setVisibility(View.INVISIBLE);
//        tabLayout.setVisibility(View.INVISIBLE);

        viewPager.setAdapter(null);
        tabLayout.setupWithViewPager(null);
    }

    public void closeDevices() {
        //devicesFragmentPagerAdapter = null;
        viewPager.setAdapter(null);
        // viewPager.removeOnPageChangeListener();
        tabLayout.setupWithViewPager(null);
    }

    public void openDevices() {
        viewPager.setAdapter(devicesFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setSwitchBtState(boolean state) {
        if (switchBt != null) {
            switchBt.setChecked(state);
        }
    }

    public void showDevices() {
        viewPager.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
    }

    public void hideDevices() {
        Log.d(TAG, "hideDevices: ");
        viewPager.setVisibility(View.INVISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);
    }

    public void updateAvailableDevices() {
        Log.d(TAG, "updateAvailableDevices: ");
        if (devicesFragmentPagerAdapter != null)
            devicesFragmentPagerAdapter.updateAvailableDevices();
    }

    public void updatePairedDevices() {
        if (devicesFragmentPagerAdapter != null) devicesFragmentPagerAdapter.updatePairedDevices();
    }

    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate: ");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG, "onStart");
        bluetoothPresenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        Log.v(TAG, "onDestroyView");
        super.onDestroyView();
        //listener.onBluetoothFragmentDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.v(TAG, "onDestroy");
        super.onDestroy();
        //listener.onBluetoothFragmentDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(TAG, "onDetach: ");
        //listener = null;
    }

    // ---------------------------------------------------------------------------------------------
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.setGroupVisible(R.menu.menu_configuration_options, false);
        super.onPrepareOptionsMenu(menu);
    }

//    public boolean isAvailableDevicesFragmentResumed() {
//        return (devicesFragmentPagerAdapter != null && devicesFragmentPagerAdapter.isAvailableDevicesFragmentResumed());
//    }

    public int getSelectedPageOfViewPager() {
        // Log.d(TAG, "getSelectedPageOfViewPager() returned: " + viewPager.getCurrentItem());
        return viewPager.getCurrentItem();
    }


    public interface OnBluetoothFragmentInteractionListener {
        void onSwitchBtStateChanged(boolean state);

        void onPairedDevicesRvItemClicked(String bluetoothDeviceAddress);

        void onAvailableDevicesRvItemClicked(String bluetoothDeviceAddress);

        void onBluetoothFragmentCreateView();

        void onBluetoothFragmentStart();

        void onBluetoothFragmentDestroyView();

        void onBluetoothFragmentDestroy();

        void onDevicesPageSelected(int position);

        void onBindViewHolderOfAvailableDevicesRvAdapter(AvailableDevicesItemView holder, int position);

        int onGetItemCountOfAvailableDevicesRvAdapter();

        void onBindViewHolderOfPairedDevicesRvAdapter(PairedDevicesItemView holder, int position);

        int onGetItemCountOfPairedDevicesRvAdapter();

        void onAvailableDevicesFragmentDestroyView();

        void onAvailableDevicesFragmentStart();

        void onAvailableDevicesFragmentPause();

        void onAvailableDevicesFragmentResume();
    }

}
