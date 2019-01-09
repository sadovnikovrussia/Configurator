package tech.sadovnikov.configurator.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.di.component.ActivityComponent;
import tech.sadovnikov.configurator.di.component.DaggerActivityComponent;
import tech.sadovnikov.configurator.ui.bluetooth.BluetoothFragment;
import tech.sadovnikov.configurator.ui.console.ConsoleFragment;

public class MainActivityNew extends MvpAppCompatActivity implements MainView {
    private static final String TAG = MainActivityNew.class.getSimpleName();

    @BindView(R.id.navigation)
    BottomNavigationView navigationView;
    @BindView(R.id.container)
    FrameLayout container;

    private ActivityComponent activityComponent;
    @InjectPresenter
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeDaggerComponent();
        setUp();
    }

    private void setUp() {
        navigationView.setOnNavigationItemSelectedListener(menuItem -> {
            boolean isFragmentOpened = navigationView.getSelectedItemId() == menuItem.getItemId();
            switch (menuItem.getItemId()) {
                case R.id.navigation_bluetooth:
                    presenter.onBluetoothClick(isFragmentOpened);
                    return true;
                case R.id.navigation_configuration:
                    presenter.onConfigurationClick(isFragmentOpened);
                    return true;
                case R.id.navigation_console:
                    presenter.onConsoleClick(isFragmentOpened);
                    return true;
            }
            return false;
        });
    }

    private void initializeDaggerComponent() {
        activityComponent = DaggerActivityComponent
                .builder()
                .applicationComponent((App.getApplicationComponent()))
                .build();
    }

    @NonNull
    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    @Override
    public void showBluetoothView() {
        Log.w(TAG, "showBluetoothView: ");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, BluetoothFragment.newInstance(), BluetoothFragment.TAG)
                .addToBackStack(null)
                .commit();
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.title_bluetooth);
    }

    @Override
    public void showConfigurationView() {

    }

    @Override
    public void showConsoleView() {
        Log.w(TAG, "showConsoleView: ");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ConsoleFragment.newInstance(), ConsoleFragment.TAG)
                .addToBackStack(null)
                .commit();
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.title_console);
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
