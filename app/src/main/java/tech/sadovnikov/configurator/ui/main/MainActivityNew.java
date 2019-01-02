package tech.sadovnikov.configurator.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.di.component.ActivityComponent;
import tech.sadovnikov.configurator.di.component.DaggerActivityComponent;
import tech.sadovnikov.configurator.di.module.ActivityModule;
import tech.sadovnikov.configurator.ui.bluetooth.BluetoothFragment;

public class MainActivityNew extends MvpActivity<MainMvp.MainView, MainMvp.MainPresenter>
        implements MainMvp.MainView {
    private static final String TAG = MainActivityNew.class.getSimpleName();

    private ActivityComponent activityComponent;

    @BindView(R.id.navigation)
    BottomNavigationView navigationView;
//    @Bind(R.id.group_configuration_actions_menu)
//    Menu actionBarMenu;
//    @BindView(R.id.item_update_available_devices)
//    MenuItem itemUpdateAvailableDevices;

    FragmentTransaction fragmentTransaction = getSupportFragmentManager()
            .beginTransaction().addToBackStack(null);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeDaggerComponent();
    }

    private void initializeDaggerComponent() {
        activityComponent = DaggerActivityComponent
                .builder()
                .applicationComponent((App.getApplicationComponent()))
                .activityModule(new ActivityModule(this))
                .build();
    }

    @NonNull
    @Override
    public MainMvp.MainPresenter createPresenter() {
        return new MainPresenter();
    }


    @Override
    public void showBluetoothView() {
        Log.w(TAG, "showBluetoothView: ");
        fragmentTransaction
                .replace(R.id.container, BluetoothFragment.newInstance(), BluetoothFragment.TAG)
                .commit();
    }

    @Override
    public void showConfigurationView() {

    }

    @Override
    public void showConsoleView() {

    }

    @NonNull
    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    public MainActivityNew() {
        super();
        Log.w(TAG, "onConstructor: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "onDestroy: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "onPause: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "onResume: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG, "onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.w(TAG, "onRestart: ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        Log.w(TAG, "onSaveInstanceState: " + outState);
//        outState.putBoolean("restart", true);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        boolean restart = savedInstanceState.getBoolean("restart");
//        Log.w(TAG, "onRestoreInstanceState: " + restart);
//        getPresenter().onRestoreInstanceState(restart);
        super.onRestoreInstanceState(savedInstanceState);
    }
}
