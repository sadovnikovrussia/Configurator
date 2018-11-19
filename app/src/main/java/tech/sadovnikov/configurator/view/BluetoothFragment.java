package tech.sadovnikov.configurator.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.view.adapter.AvailableDevicesItemView;
import tech.sadovnikov.configurator.view.adapter.DevicesFragmentPagerAdapter;
import tech.sadovnikov.configurator.view.adapter.PairedDevicesItemView;

/**
 * Фрагмент для отображения спаренных и доступных bluetooth устройств
 * <p>
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnBluetoothFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class BluetoothFragment extends Fragment {
    private static final String TAG = "BluetoothFragment";

    // UI
    Switch switchBt;
    TabLayout tabLayout;
    ViewPager viewPager;

    DevicesFragmentPagerAdapter devicesFragmentPagerAdapter;

    private OnBluetoothFragmentInteractionListener listener;

    public BluetoothFragment() {
        // Required empty public constructor
        Log.v(TAG, "onConstructor");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "onCreateView");
        final View inflate = inflater.inflate(R.layout.fragment_bluetooth, container, false);
        initUi(inflate);
        listener.onBluetoothFragmentCreateView();
        return inflate;
    }

    private void initUi(View inflate) {
        switchBt = inflate.findViewById(R.id.sw_bluetooth);
        switchBt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.onSwitchBtStateChanged(isChecked);
            }
        });
        tabLayout = inflate.findViewById(R.id.tabLayout);
        viewPager = inflate.findViewById(R.id.viewPager);
        // openDevices();
    }

    void closeDevices(){
        devicesFragmentPagerAdapter = null;
        viewPager.setAdapter(null);
        // viewPager.removeOnPageChangeListener();
        tabLayout.setupWithViewPager(null);
    }
    void openDevices() {
        devicesFragmentPagerAdapter = new DevicesFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(devicesFragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + String.valueOf(position));
                listener.onDevicesPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setSwitchBtState(boolean state) {
        if (switchBt != null){
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
        if (devicesFragmentPagerAdapter != null) devicesFragmentPagerAdapter.updateAvailableDevices();
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
        if (context instanceof OnBluetoothFragmentInteractionListener) {
            listener = (OnBluetoothFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBluetoothFragmentInteractionListener");
        }
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
        listener.onBluetoothFragmentStart();
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
        listener.onBluetoothFragmentDestroyView();
        Log.v(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.v(TAG, "onDestroy");
        listener.onBluetoothFragmentDestroy();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    // ---------------------------------------------------------------------------------------------
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        Log.e(TAG, "onPrepareOptionsMenu: " + menu);
        menu.setGroupVisible(R.menu.menu_configuration_options, false);
        super.onPrepareOptionsMenu(menu);
    }

    public boolean isAvailableDevicesFragmentResumed() {
        return (devicesFragmentPagerAdapter != null && devicesFragmentPagerAdapter.isAvailableDevicesFragmentResumed());
    }

    public int getSelectedPageOfViewPager() {
        Log.d(TAG, "getSelectedPageOfViewPager() returned: " + viewPager.getCurrentItem());
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
