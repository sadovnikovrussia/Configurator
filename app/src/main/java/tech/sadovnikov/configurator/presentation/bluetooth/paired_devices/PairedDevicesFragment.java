package tech.sadovnikov.configurator.presentation.bluetooth.paired_devices;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;
import java.util.Objects;

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

    AlertDialog alertDialog;

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
        //Log.v(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_paired_devices, container, false);
        ButterKnife.bind(this, view);
        initDagger();
        fragmentComponent.injectPairedDevicesFragment(this);
        setUp();
        return view;
    }

    private void initDagger() {
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
    public void onDeviceLongClick(BluetoothDevice device) {
        presenter.onDeviceLongClick(device);
    }

    @Override
    public void setPairedDevices(List<BluetoothDevice> devices, BluetoothDevice connectedDevice, Integer connectionState) {
        Log.d(TAG, "setPairedDevices: ");
        pairedDevicesRvAdapter.setDevices(devices, connectedDevice, connectionState);
    }

    @Override
    public void showCloseConnectionDialog(BluetoothDevice device) {
        alertDialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()))
                .setTitle("Отключиться?")
                .setMessage("Произойдет разъединение соединения с устройством " + device.getName())
                .setPositiveButton(R.string.ok, (dialog, which) -> presenter.onCloseBtConnection())
                .setNegativeButton(R.string.cancel, (dialog, id) -> presenter.onCancelCloseConnectionDialog())
                .create();
        alertDialog.show();
    }

    @Override
    public void hideCloseConnectionDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }


    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Log.v(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.v(TAG, "onCreate: ");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.v(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        //Log.v(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.v(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        //Log.v(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        //Log.v(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //Log.v(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Log.v(TAG, "onDestroy");
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //Log.v(TAG, "onDetach: ");
    }
    // ---------------------------------------------------------------------------------------------

}
