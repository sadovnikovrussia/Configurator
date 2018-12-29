package tech.sadovnikov.configurator.ui.bluetooth.paired_devices;

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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.di.component.DaggerFragmentComponent;
import tech.sadovnikov.configurator.di.component.FragmentComponent;
import tech.sadovnikov.configurator.di.module.FragmentModule;
import tech.sadovnikov.configurator.ui.main.MainActivityNew;

public class PairedDevicesFragment extends MvpFragment<PairedDevicesMvp.View, PairedDevicesMvp.Presenter>
        implements PairedDevicesMvp.View, PairedDevicesRvAdapter.Listener {
    private static final String TAG = PairedDevicesFragment.class.getSimpleName();

    @BindView(R.id.rv_paired_devices)
    RecyclerView rvPairedDevices;

    FragmentComponent fragmentComponent;
    @Inject
    PairedDevicesRvAdapter pairedDevicesRvAdapter;
    @Inject
    LinearLayoutManager linearLayoutManager;

    public PairedDevicesFragment() {
        Log.d(TAG, "onConstructor");
    }

    @NonNull
    @Override
    public PairedDevicesMvp.Presenter createPresenter() {
        return new PairedDevicesPresenter(((MainActivityNew) getActivity()).getActivityComponent().getBluetoothService());
    }

    public static PairedDevicesFragment newInstance() {
        Log.d(TAG, "newInstance: ");
        Bundle args = new Bundle();
        PairedDevicesFragment fragment = new PairedDevicesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
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
        rvPairedDevices.setLayoutManager(linearLayoutManager);
        rvPairedDevices.setAdapter(pairedDevicesRvAdapter);
    }

    @Override
    public void onDeviceClicked(BluetoothDevice device) {

    }

    public void updatePairedDevices() {
        pairedDevicesRvAdapter.updatePairedBluetoothDevices();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG, "onHiddenChanged: " + hidden);
    }

    @Override
    public void showPairedDevices(List<BluetoothDevice> devices ) {
        pairedDevicesRvAdapter.setDevices(devices);
    }

    @Override
    public void hidePairedDevices() {

    }


    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        presenter.onStartView();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    // ---------------------------------------------------------------------------------------------

}
