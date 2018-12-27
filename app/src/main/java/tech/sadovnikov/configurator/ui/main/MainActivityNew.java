package tech.sadovnikov.configurator.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
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

    FragmentTransaction fragmentTransaction = getSupportFragmentManager()
            .beginTransaction().addToBackStack(null);

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




    public MainActivityNew() {
        super();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
