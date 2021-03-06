package tech.sadovnikov.configurator.presentation.bluetooth;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.di.component.DaggerFragmentComponent;
import tech.sadovnikov.configurator.di.component.FragmentComponent;
import tech.sadovnikov.configurator.di.module.FragmentModule;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;


public class BluetoothFragment extends MvpAppCompatFragment implements BluetoothView {
    public static final String TAG = BluetoothFragment.class.getSimpleName();

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "Bluetooth")
    BluetoothPresenter presenter;

    // UI
    @BindView(R.id.sw_bluetooth)
    Switch switchBt;
    @BindView(R.id.tab_layout_devices)
    TabLayout tabLayout;
    @BindView(R.id.view_pager_devices)
    ViewPager viewPager;
    @BindView(R.id.tv_bluetooth_turning_on)
    TextView tvTurningOn;

    @Inject
    DevicesFragmentPagerAdapter devicesFragmentPagerAdapter;

    FragmentComponent fragmentComponent;

    private Menu menu;

    private Listener listener;

    public BluetoothFragment() {
        //Log.v(TAG, "onConstructor: ");
    }

    public static BluetoothFragment newInstance() {
        ////Log.v(TAG, "newInstance: ");
        Bundle args = new Bundle();
        BluetoothFragment fragment = new BluetoothFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView: ");
        View inflate = inflater.inflate(R.layout.fragment_bluetooth, container, false);
        ButterKnife.bind(this, inflate);
        initDaggerAndInject();
        setUp();
        listener.onCreateViewBluetooth();
        return inflate;
    }

    private void initDaggerAndInject() {
        fragmentComponent = DaggerFragmentComponent
                .builder()
                .fragmentModule(new FragmentModule(this))
                .build();
        fragmentComponent.injectBluetoothFragment(this);
    }

    private void setUp() {
        switchBt.setOnCheckedChangeListener((buttonView, isChecked) -> presenter.onBtSwitchClick(isChecked));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ////Log.v(TAG, "onPageSelected: " + String.valueOf(position));
                // Todo Сделать восстоновление состояния adapter
                if (position == 1) presenter.onAvailableDevicesViewShown();
                else presenter.onPairedDevicesViewShown();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(devicesFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setHasOptionsMenu(true);
    }

    @Override
    public void displayBluetoothState(boolean state) {
        Log.d(TAG, "displayBluetoothState: " + state);
        switchBt.setChecked(state);
    }

    @Override
    public void showDevices() {
        Log.d(TAG, "showDevices: ");
        viewPager.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDevices() {
        Log.d(TAG, "hideDevices: ");
        viewPager.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
    }

    @Override
    public void showTurningOn() {
        Log.d(TAG, "showTurningOn: ");
        tvTurningOn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTurningOn() {
        Log.d(TAG, "hideTurningOn: ");
        tvTurningOn.setVisibility(View.GONE);
    }

    @Override
    public void hideUpdateDevicesView() {
        Log.d(TAG, "hideUpdateDevicesView: ");
        menu.setGroupVisible(R.id.group_update_devices, false);
    }

    @Override
    public void showUpdateDevicesView() {
        Log.d(TAG, "showUpdateDevicesView: ");
        menu.setGroupVisible(R.id.group_update_devices, true);

    }

    @Override
    public void setSwBluetoothStateText(String text) {
        Log.d(TAG, "setSwBluetoothStateText: " + text);
        switchBt.setText(text);
    }

    @Override
    public void setPairedDevicesPage() {
        Log.d(TAG, "setPairedDevicesPage: 0");
        viewPager.setCurrentItem(0);
    }

    @Override
    public void setAvailableDevicesPage() {
        Log.d(TAG, "setAvailableDevicesPage: 1");
        viewPager.setCurrentItem(1);
    }

    @Override
    public void requestBtPermission() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ////Log.d(TAG, "onRequestPermissionsResult: ");
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION) {
            if (permissions.length > 0 && permissions[0].equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED)
                    presenter.onPositiveBtRequestResult();
            }
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
        inflater.inflate(R.menu.menu_bluetooth, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        presenter.onPrepareOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        presenter.onUpdateDevicesClick();
        return super.onOptionsItemSelected(item);
    }

    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "onAttach");
        if (context instanceof Listener) {
            listener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement BluetoothFragment.Listener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate: ");
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
        Log.v(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.v(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(TAG, "onDetach: ");
        listener = null;
    }
    // ---------------------------------------------------------------------------------------------

    public interface Listener{
        void onCreateViewBluetooth();
    }


}
