package tech.sadovnikov.configurator.ui.bluetooth.available_devices;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.ui.adapter.AvailableDevicesRvAdapter;
import tech.sadovnikov.configurator.ui.bluetooth.BluetoothFragment;

public class AvailableDevicesFragment extends Fragment {
    private static final String TAG = "AvailableDeviceFragment";

    RecyclerView rvAvailableDevices;
    AvailableDevicesRvAdapter availableDevicesRvAdapter;

    BluetoothFragment.OnBluetoothFragmentInteractionListener listener;

    private static AvailableDevicesFragment availableDevicesFragment;

    public AvailableDevicesFragment() {
        Log.i(TAG, "onConstructor");
    }

    public static AvailableDevicesFragment getInstance(){
        if (availableDevicesFragment == null) {
            availableDevicesFragment = new AvailableDevicesFragment();
        }
        return availableDevicesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_paired_devices, container, false);
        rvAvailableDevices = view.findViewById(R.id.rv_paired_devices);
        availableDevicesRvAdapter = new AvailableDevicesRvAdapter(listener);
        // TODO <Узнать, какой контекст подавать в аргумент LL>
        rvAvailableDevices.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        rvAvailableDevices.setAdapter(availableDevicesRvAdapter);

        return view;
    }

    public void updateAvailableDevices() {
        Log.d(TAG, "updateAvailableDevices: availableDevicesRvAdapter = " + availableDevicesRvAdapter);
        if (availableDevicesRvAdapter != null) availableDevicesRvAdapter.updateAvailableBluetoothDevices();
    }

    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach");
    }
    // ---------------------------------------------------------------------------------------------


}