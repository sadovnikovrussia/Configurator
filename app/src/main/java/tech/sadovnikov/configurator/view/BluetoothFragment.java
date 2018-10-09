package tech.sadovnikov.configurator.view;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
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
import tech.sadovnikov.configurator.view.adapter.DevicesFragmentPagerAdapter;


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
    Button btnTest;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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

        viewPager.setAdapter(new DevicesFragmentPagerAdapter(getChildFragmentManager(), getContext()));
        tabLayout.setupWithViewPager(viewPager);
        btnTest = inflate.findViewById(R.id.button);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBluetoothFragmentInteractionListener.startDiscovery();
            }
        });
    }

    public void setSwitchBtState(boolean state) {
        switchBt.setChecked(state);
    }

    void showPairedDevices() {
        viewPager.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setAdapter(new DevicesFragmentPagerAdapter(getChildFragmentManager(), getContext()));
        tabLayout.setupWithViewPager(viewPager);
    }

    public void hidePairedDevices() {
        viewPager.setVisibility(View.INVISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);
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

        void onPairedDevicesRvItemClicked(BluetoothDevice bluetoothDevice);

        void onBluetoothFragmentCreateView();

        void onBluetoothFragmentStart();

        void startDiscovery();

    }


}
