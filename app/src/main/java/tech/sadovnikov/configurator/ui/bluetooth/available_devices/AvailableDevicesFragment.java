package tech.sadovnikov.configurator.ui.bluetooth.available_devices;

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

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.di.component.DaggerFragmentComponent;
import tech.sadovnikov.configurator.di.component.FragmentComponent;
import tech.sadovnikov.configurator.di.module.FragmentModule;
import tech.sadovnikov.configurator.ui.main.MainActivityNew;

public class AvailableDevicesFragment extends MvpFragment<AvailableDevicesMvp.View, AvailableDevicesMvp.Presenter>
        implements AvailableDevicesMvp.View, AvailableDevicesRvAdapter.Listener {
    private static final String TAG = AvailableDevicesFragment.class.getSimpleName();

    @BindView(R.id.rv_available_devices)
    RecyclerView rvAvailableDevices;

    FragmentComponent fragmentComponent;
    @Inject
    AvailableDevicesRvAdapter availableDevicesRvAdapter;
    @Inject
    LinearLayoutManager linearLayoutManager;

    public AvailableDevicesFragment() {
        Log.i(TAG, "onConstructor");
    }

    @NonNull
    @Override
    public AvailableDevicesMvp.Presenter createPresenter() {
        return new AvailableDevicesPresenter(((MainActivityNew) Objects.requireNonNull(getActivity())).getActivityComponent().getBluetoothService());
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
        rvAvailableDevices.setLayoutManager(linearLayoutManager);
        rvAvailableDevices.setAdapter(availableDevicesRvAdapter);
    }

    @Override
    public void showAvailableDevices(List<BluetoothDevice> availableDevices) {
        availableDevicesRvAdapter.setDevices(availableDevices);
    }

    @Override
    public void onDeviceClicked(BluetoothDevice device) {
        getPresenter().onDeviceClicked(device);
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
        setRetainInstance(true);
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
        getPresenter().onStartView();
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
