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

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.di.component.DaggerFragmentComponent;
import tech.sadovnikov.configurator.di.component.FragmentComponent;
import tech.sadovnikov.configurator.di.module.FragmentModule;
import tech.sadovnikov.configurator.ui.adapter.AvailableDevicesRvAdapter;
import tech.sadovnikov.configurator.ui.bluetooth.BluetoothFragment;

public class AvailableDevicesFragment extends MvpAppCompatFragment
        implements AvailableDevicesView {
    private static final String TAG = AvailableDevicesFragment.class.getSimpleName();

    @InjectPresenter
    AvailableDevicesPresenter presenter;

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
        Log.i(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_available_devices, container, false);
        //rvAvailableDevices = view.findViewById(R.id.rv_available_devices);
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

    public void updateAvailableDevices() {
//        Log.i(TAG, "updateAvailableDevices: availableDevicesRvAdapter = " + availableDevicesRvAdapter);
//        if (availableDevicesRvAdapter != null)
//            availableDevicesRvAdapter.updateAvailableBluetoothDevices();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i(TAG, "onHiddenChanged: " + hidden);
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
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        //listener.onAvailableDevicesFragmentStart();
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


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        Log.i(TAG, "onPrepareOptionsMenu: " + menu);
        //menu.setGroupVisible(R.id.group_update_available_devices, true);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void showDevices() {

    }

    @Override
    public void hideDevices() {

    }
}
