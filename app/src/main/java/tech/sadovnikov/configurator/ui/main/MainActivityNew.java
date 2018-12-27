package tech.sadovnikov.configurator.ui.main;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.ui.bluetooth.BluetoothFragment;

public class MainActivityNew extends MvpAppCompatActivity implements MainView {
    private static final String TAG = MainActivityNew.class.getSimpleName();

    @InjectPresenter
    MainPresenter presenter;

    @BindView(R.id.navigation)
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUp();
    }

    private void setUp() {
        navigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.navigation_bluetooth:
                    presenter.onBluetoothNavigationItemSelected();
                    break;
                case R.id.navigation_configuration:
                    presenter.onNavNavigationItemSelected();
                    break;
                case R.id.navigation_console:
                    presenter.onConsoleNavigationItemSelected();
                    break;
            }
            return false;
        });
    }


    @Override
    public void showBluetoothView() {
        Log.e(TAG, "showBluetoothView: ");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, BluetoothFragment.newInstance())
                .commitAllowingStateLoss();
    }

    @Override
    public void showConsoleView() {
        getSupportFragmentManager()
                .beginTransaction()
                //.replace(R.id.container, ConfigurationFragment.newInstance(), ConfigurationFragment.TAG)
                .commit();
    }

    @Override
    public void showConfigurationView() {
        getSupportFragmentManager()
                .beginTransaction()
                //.replace(R.id.container, ConsoleFragment.newInstance(), ConsoleFragment.TAG)
                .commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        presenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

}
