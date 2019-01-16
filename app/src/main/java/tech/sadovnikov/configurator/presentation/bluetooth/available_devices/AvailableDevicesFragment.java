package tech.sadovnikov.configurator.presentation.bluetooth.available_devices;

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

public class AvailableDevicesFragment extends MvpAppCompatFragment implements AvailableDevicesView, AvailableDevicesRvAdapter.Listener {
    private static final String TAG = AvailableDevicesFragment.class.getSimpleName();

    @BindView(R.id.rv_available_devices)
    RecyclerView rvAvailableDevices;

    @InjectPresenter
    AvailableDevicesPresenter presenter;

    FragmentComponent fragmentComponent;
    @Inject
    AvailableDevicesRvAdapter availableDevicesRvAdapter;
    @Inject
    LinearLayoutManager linearLayoutManager;

    public AvailableDevicesFragment() {
        Log.i(TAG, "onConstructor");
    }

    public static AvailableDevicesFragment newInstance() {
        Log.i(TAG, "newInstance: ");
        Bundle args = new Bundle();
        AvailableDevicesFragment fragment = new AvailableDevicesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: " + savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_available_devices, container, false);
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
        fragmentComponent.injectAvailableDevicesFragment(this);
    }

    private void setUp() {
        availableDevicesRvAdapter.setListener(this);
        rvAvailableDevices.setLayoutManager(linearLayoutManager);
        rvAvailableDevices.setAdapter(availableDevicesRvAdapter);
    }

    @Override
    public void setAvailableDevices(List<BluetoothDevice> availableDevices) {
        availableDevicesRvAdapter.setDevices(availableDevices);
    }

    @Override
    public void onDeviceClicked(BluetoothDevice device) {
        presenter.onDeviceClicked(device);
    }


    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        //setRetainInstance(true);
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
