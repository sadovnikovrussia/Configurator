package tech.sadovnikov.configurator.ui.bluetooth.paired_devices;

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

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.di.component.DaggerFragmentComponent;
import tech.sadovnikov.configurator.ui.bluetooth.BluetoothFragment;
import tech.sadovnikov.configurator.ui.adapter.PairedDevicesRvAdapter;

public class PairedDevicesFragment extends MvpFragment<PairedDevicesMvp.View, PairedDevicesMvp.Presenter>
        implements PairedDevicesMvp.View, PairedDevicesRvAdapter.Listener {
    private static final String TAG = PairedDevicesFragment.class.getSimpleName();

    @BindView(R.id.rv_paired_devices)
    RecyclerView rvPairedDevices;

    @Inject
    PairedDevicesRvAdapter pairedDevicesRvAdapter;

    public PairedDevicesFragment() {
        Log.v(TAG, "onConstructor");
    }

    @NonNull
    @Override
    public PairedDevicesMvp.Presenter createPresenter() {
        return new PairedDevicesPresenter();
    }

//    @NonNull
//    @Override
//    public PairedDevicesMvp.Presenter createPresenter() {
//        return new PairedDevicesPresenter();
//    }


    public static PairedDevicesFragment newInstance() {
        Bundle args = new Bundle();
        PairedDevicesFragment fragment = new PairedDevicesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_paired_devices, container, false);
        ButterKnife.bind(this, view);

        //DaggerFragmentComponent.builder().applicationComponent().build().

        //pairedDevicesRvAdapter = new PairedDevicesRvAdapter();
        rvPairedDevices.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        rvPairedDevices.setAdapter(pairedDevicesRvAdapter);

        return view;
    }

    @Override
    public void onDeviceClicked(BluetoothDevice device) {

    }

    public void updatePairedDevices() {
        pairedDevicesRvAdapter.updatePairedBluetoothDevices();
    }


    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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

    @Override
    public void showPairedDevices() {

    }

    @Override
    public void hidePairedDevices() {

    }

    // ---------------------------------------------------------------------------------------------

}
