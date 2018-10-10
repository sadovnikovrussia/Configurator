package tech.sadovnikov.configurator.view;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.presenter.BluetoothService;
import tech.sadovnikov.configurator.view.adapter.AvailableDevicesRvAdapter;
import tech.sadovnikov.configurator.view.adapter.PairedDevicesRvAdapter;

public class AvailableDevicesFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private static final String TAG = "AvailableDeviceFragment";

    RecyclerView rvAvailableDevices;

    BluetoothFragment.OnBluetoothFragmentInteractionListener onBluetoothFragmentInteractionListener;

    public static AvailableDevicesFragment newInstance() {
        //Bundle args = new Bundle();
        //args.putInt(ARG_PAGE, page);
        AvailableDevicesFragment fragment = new AvailableDevicesFragment();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paired_devices, container, false);

        rvAvailableDevices = view.findViewById(R.id.rv_paired_devices);
        // TODO <Узнать, какой контекст подавать в аргумент LL>
        rvAvailableDevices.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        rvAvailableDevices.setAdapter(new AvailableDevicesRvAdapter(onBluetoothFragmentInteractionListener));

        return view;
    }
    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "onAttach");
        if (context instanceof BluetoothFragment.OnBluetoothFragmentInteractionListener) {
            onBluetoothFragmentInteractionListener = (BluetoothFragment.OnBluetoothFragmentInteractionListener) context;
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
    }
    // ---------------------------------------------------------------------------------------------

}
