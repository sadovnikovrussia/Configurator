package tech.sadovnikov.configurator.presentation.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import tech.sadovnikov.configurator.presentation.bluetooth.BluetoothFragment;
import tech.sadovnikov.configurator.presentation.console.ConsoleFragment;

public class MainActivity extends MvpAppCompatActivity implements MainView, ConsoleFragment.Listener, BluetoothFragment.Listener {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.navigation)
    BottomNavigationView navigationView;
    @BindView(R.id.container)
    FrameLayout container;

    private ActivityComponent activityComponent;
    @InjectPresenter
    MainPresenter presenter;

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener;

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
        navigationListener = menuItem -> {
            Log.w(TAG, "OnNavigationItemSelected: " + menuItem.toString());
            switch (menuItem.getItemId()) {
                case R.id.navigation_bluetooth:
                    presenter.onBluetoothClick();
                    break;
                case R.id.navigation_configuration:
                    presenter.onConfigurationClick();
                    break;
                case R.id.navigation_console:
                    presenter.onConsoleClick();
                    break;
            }
            return true;
        };
        navigationView.setOnNavigationItemReselectedListener(menuItem -> Log.w(TAG, "OnNavigationItemReselected: " + menuItem.toString()));
        navigationView.setOnNavigationItemSelectedListener(navigationListener);
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
    public void navigateToBluetoothView() {
        Log.w(TAG, "navigateToBluetoothView: ");
        showFragment(BluetoothFragment.newInstance(), BluetoothFragment.TAG, R.id.navigation_bluetooth);
        setTitle(R.string.title_bluetooth);
    }

    @Override
    public void navigateToConfigurationView() {

    }

    @Override
    public void navigateToConsoleView() {
        Log.w(TAG, "navigateToConsoleView: ");
        showFragment(ConsoleFragment.newInstance(), ConsoleFragment.TAG, R.id.navigation_console);
        setTitle(R.string.title_console);
    }

    @Override
    public void showLoadingProcess() {

    }

    @Override
    public void hideLoadingProgress() {

    }

    @Override
    public void updateLoadingProcess(Float percents) {

    }

    @Override
    public void setConsoleNavigationPosition() {
        Log.w(TAG, "setConsoleNavigationPosition: ");
        navigationView.setOnNavigationItemSelectedListener(null);
        navigationView.setSelectedItemId(R.id.navigation_console);
        navigationView.setOnNavigationItemSelectedListener(navigationListener);
    }

    @Override
    public void setBluetoothNavigationPosition() {
        Log.w(TAG, "setBluetoothNavigationPosition: ");
        navigationView.setOnNavigationItemSelectedListener(null);
        navigationView.setSelectedItemId(R.id.navigation_bluetooth);
        navigationView.setOnNavigationItemSelectedListener(navigationListener);
    }

    @Override
    public void setTitle(int title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
    }

    private void showFragment(Fragment fragment, String tag, int navigation) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        Log.d(TAG, "onBackPressed: " + backStackEntryCount);
        if (backStackEntryCount < 1) finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Log.w(TAG, "onCreateOptionsMenu1: " + menu);
        //menu.clear();
        //getMenuInflater().inflate(R.menu.menu_configuration_options, menu);
        ////Log.w(TAG, "onCreateOptionsMenu2: " + menu);
        //getMenuInflater().inflate(R.menu.menu_bluetooth, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //Log.w(TAG, "onPrepareOptionsMenu: " + menu.hasVisibleItems());
        //menu.setGroupVisible(R.id.group_update_devices, false);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Log.w(TAG, "onOptionsItemSelected: " + item.getItemId());
        switch (item.getItemId()) {
            case R.id.item_open:
                presenter.onOpenConfiguration();
                break;
            case R.id.item_save:
                presenter.onSaveConfiguration();
                break;
            case R.id.item_load:
                presenter.onReadConfiguration();
                break;
            case R.id.item_set:
                presenter.onSetConfiguration();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        //Log.w(TAG, "onOptionsMenuClosed: ");
        super.onOptionsMenuClosed(menu);
    }


    public MainActivity() {
        super();
        //Log.w(TAG, "onConstructor: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.w(TAG, "onDestroy: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Log.w(TAG, "onPause: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.w(TAG, "onResume: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Log.w(TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Log.w(TAG, "onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Log.w(TAG, "onRestart: ");
    }


    @Override
    public void onCreateViewConsole() {
        presenter.onCreateConsoleView();
    }

    @Override
    public void onCreateViewBluetooth() {
        presenter.onCreateBluetoothView();
    }
}
