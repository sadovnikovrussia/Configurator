package tech.sadovnikov.configurator.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    private OnBluetoothFragmentInteractionListener onBluetoothFragmentInteractionListener;

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
        onBluetoothFragmentInteractionListener.onBluetoothFragmentCreateView();
        return inflate;
    }

    private void initUi(View inflate) {
        switchBt = inflate.findViewById(R.id.sw_bluetooth);
        switchBt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onBluetoothFragmentInteractionListener.onSwitchBtStateChanged(isChecked);
            }
        });
        tabLayout = inflate.findViewById(R.id.tabLayout);
        viewPager = inflate.findViewById(R.id.viewPager);
        devicesFragmentPagerAdapter = new DevicesFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(devicesFragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + String.valueOf(position));
                onBluetoothFragmentInteractionListener.onDevicesPageSelected(position);
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
        // TODO <Или здесь нужно создание компонентов, а не изменение их видимости?>
        viewPager.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
    }

    public void hideDevices() {
        Log.d(TAG, "hideDevices: ");
        viewPager.setVisibility(View.INVISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);
    }

    public void updateAvailableDevices() {
        devicesFragmentPagerAdapter.updateAvailableDevices();
    }

    public void updatePairedDevices() {
        devicesFragmentPagerAdapter.updatePairedDevices();
    }

    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "onAttach");
        if (context instanceof OnBluetoothFragmentInteractionListener) {
            onBluetoothFragmentInteractionListener = (OnBluetoothFragmentInteractionListener) context;
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
        onBluetoothFragmentInteractionListener.onBluetoothFragmentStart();
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
        super.onDestroyView();
        Log.v(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onBluetoothFragmentInteractionListener = null;
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnBluetoothFragmentInteractionListener {
        void onSwitchBtStateChanged(boolean state);

        void onPairedDevicesRvItemClicked(String bluetoothDeviceAddress);

        void onAvailableDevicesRvItemClicked(String bluetoothDeviceAddress);

        void onBluetoothFragmentCreateView();

        void onBluetoothFragmentStart();

        void onDevicesPageSelected(int position);

        void onBindViewHolderOfAvailableDevicesRvAdapter(AvailableDevicesItemView holder, int position);

        int onGetItemCountOfAvailableDevicesRvAdapter();

        void onBindViewHolderOfPairedDevicesRvAdapter(PairedDevicesItemView holder, int position);

        int onGetItemCountOfPairedDevicesRvAdapter();

        void onAvailableDevicesFragmentDestroyView();
    }

}
