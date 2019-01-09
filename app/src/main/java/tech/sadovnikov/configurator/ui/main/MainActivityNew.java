package tech.sadovnikov.configurator.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

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

    private ActivityComponent activityComponent;
    @InjectPresenter
    MainPresenter presenter;

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
                .build();
    }

    @Override
    public void showBluetoothView() {
        Log.w(TAG, "showBluetoothView: ");
        fragmentTransaction
                .replace(R.id.container, BluetoothFragment.newInstance(), BluetoothFragment.TAG)
                .commit();
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.title_bluetooth);
    }

    @Override
    public void showConfigurationView() {

    }

    @Override
    public void showConsoleView() {
        Log.w(TAG, "showConsoleView: ");
        fragmentTransaction
                .replace(R.id.container, BluetoothFragment.newInstance(), ConsoleFragment.TAG)
                .commit();
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.title_console);

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
