package tech.sadovnikov.configurator.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.ui.bluetooth.BluetoothFragment;

public class MainActivityNew extends MvpActivity<MainMvp.MainView, MainMvp.MainPresenter>
        implements MainMvp.MainView {
    private static final String TAG = MainActivityNew.class.getSimpleName();

    @BindView(R.id.navigation)
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate: " + navigationView);
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void showBluetoothView() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, BluetoothFragment.newInstance(), BluetoothFragment.TAG)
                .commit();
    }

    @Override
    public void showConsoleView() {

    }

    @Override
    public void showConfigurationView() {

    }
}
