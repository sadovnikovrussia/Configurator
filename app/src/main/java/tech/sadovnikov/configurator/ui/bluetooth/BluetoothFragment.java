package tech.sadovnikov.configurator.ui.bluetooth;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.di.component.DaggerFragmentComponent;
import tech.sadovnikov.configurator.di.component.FragmentComponent;
import tech.sadovnikov.configurator.di.module.FragmentModule;
import tech.sadovnikov.configurator.ui.main.MainActivityNew;

public class BluetoothFragment extends MvpFragment<BluetoothMvp.View, BluetoothMvp.Presenter> implements BluetoothMvp.View {
    public static final String TAG = BluetoothFragment.class.getSimpleName();

    // UI
    @BindView(R.id.sw_bluetooth)
    Switch switchBt;
    @BindView(R.id.tab_layout_devices)
    TabLayout tabLayout;
    @BindView(R.id.view_pager_devices)
    ViewPager viewPager;
    @BindView(R.id.tv_bluetooth_turning_on)
    TextView tvTurningOn;

    @Inject
    DevicesFragmentPagerAdapter devicesFragmentPagerAdapter;

    FragmentComponent fragmentComponent;

    private OnBluetoothFragmentInteractionListener listener;
    private int page;

    public BluetoothFragment() {
        Log.v(TAG, "onConstructor: ");
    }

    public static BluetoothFragment newInstance() {
        Log.v(TAG, "newInstance: ");
        Bundle args = new Bundle();
        BluetoothFragment fragment = new BluetoothFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public BluetoothMvp.Presenter createPresenter() {
        return new BluetoothPresenter(((MainActivityNew) Objects.requireNonNull(getActivity())).getActivityComponent().getBluetoothService());
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
        switchBt.setOnCheckedChangeListener((buttonView, isChecked) -> getPresenter().onBtSwitchClick(isChecked));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + String.valueOf(position));
                // Todo Сделать восстоновление состояния adapter
                page = position;
                if (position == 1) getPresenter().onAvailableDevicesViewShown();
                else getPresenter().onPairedDevicesViewShown();
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
        viewPager.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
        //        viewPager.setAdapter(devicesFragmentPagerAdapter);
        //        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void hideDevicesContainer() {
        viewPager.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
//        viewPager.setAdapter(null);
//        tabLayout.setupWithViewPager(null);
    }

    @Override
    public void showTurningOn() {
        tvTurningOn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTurningOn() {
        tvTurningOn.setVisibility(View.GONE);
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
        getPresenter().onStart();
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("page", page);
        super.onSaveInstanceState(outState);
    }


    // ---------------------------------------------------------------------------------------------
//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        menu.setGroupVisible(R.menu.menu_configuration_options, false);
//        super.onPrepareOptionsMenu(menu);
//    }

//    public boolean isAvailableDevicesFragmentResumed() {
//        return (devicesFragmentPagerAdapter != null && devicesFragmentPagerAdapter.isAvailableDevicesFragmentResumed());
//    }

//    public int getSelectedPageOfViewPager() {
//        // Log.d(TAG, "getSelectedPageOfViewPager() returned: " + viewPager.getCurrentItem());
//        return viewPager.getCurrentItem();
//    }


    public interface OnBluetoothFragmentInteractionListener {
        void onSwitchBtStateChanged(boolean state);

        void onPairedDevicesRvItemClicked(String bluetoothDeviceAddress);

        void onAvailableDevicesRvItemClicked(String bluetoothDeviceAddress);

        void onBluetoothFragmentCreateView();

        void onBluetoothFragmentStart();

        void onBluetoothFragmentDestroyView();

        void onBluetoothFragmentDestroy();

        void onDevicesPageSelected(int position);

        int onGetItemCountOfAvailableDevicesRvAdapter();

        int onGetItemCountOfPairedDevicesRvAdapter();

        void onAvailableDevicesFragmentDestroyView();

        void onAvailableDevicesFragmentStart();

        void onAvailableDevicesFragmentPause();

        void onAvailableDevicesFragmentResume();
    }

}
