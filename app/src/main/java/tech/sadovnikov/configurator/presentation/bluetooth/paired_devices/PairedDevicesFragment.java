package tech.sadovnikov.configurator.presentation.bluetooth.paired_devices;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.di.component.DaggerFragmentComponent;
import tech.sadovnikov.configurator.di.component.FragmentComponent;
import tech.sadovnikov.configurator.di.module.FragmentModule;

public class PairedDevicesFragment extends MvpAppCompatFragment implements PairedDevicesView, PairedDevicesRvAdapter.Listener {
    private static final String TAG = PairedDevicesFragment.class.getSimpleName();

    @BindView(R.id.rv_paired_devices)
    RecyclerView rvPairedDevices;

    @InjectPresenter
    PairedDevicesPresenter presenter;

    FragmentComponent fragmentComponent;
    @Inject
    PairedDevicesRvAdapter pairedDevicesRvAdapter;
    @Inject
    LinearLayoutManager linearLayoutManager;

    public PairedDevicesFragment() {
        //Log.d(TAG, "onConstructor");
    }

    public static PairedDevicesFragment newInstance() {
        //Log.d(TAG, "newInstance: ");
        Bundle args = new Bundle();
        PairedDevicesFragment fragment = new PairedDevicesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.d(TAG, "onCreateView: " + savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_paired_devices, container, false);
        ButterKnife.bind(this, view);
        initDaggerAndInject();
        setUp();
        return view;
    }

    private void initDaggerAndInject() {
        fragmentComponent = DaggerFragmentComponent
                .builder()
                .fragmentModule(new FragmentModule(this))
                .build();
        fragmentComponent.injectPairedDevicesFragment(this);
    }

    private void setUp() {
        pairedDevicesRvAdapter.setListener(this);
        rvPairedDevices.setLayoutManager(linearLayoutManager);
        rvPairedDevices.setAdapter(pairedDevicesRvAdapter);
    }

    @Override
    public void onDeviceClicked(BluetoothDevice device) {
        presenter.onDeviceClicked(device);
    }

    @Override
    public void setPairedDevices(List<BluetoothDevice> devices) {
        pairedDevicesRvAdapter.setDevices(devices);
    }


    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ////Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d(TAG, "onCreate: ");
        //setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.d(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        //Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        //Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        //Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Log.d(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //Log.d(TAG, "onDetach: ");
    }

    // ---------------------------------------------------------------------------------------------

}
