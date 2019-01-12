package tech.sadovnikov.configurator.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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
import tech.sadovnikov.configurator.ui.bluetooth.BluetoothFragment;
import tech.sadovnikov.configurator.ui.console.ConsoleFragment;

public class MainActivity extends MvpAppCompatActivity implements MainView {
    private static final String TAG = MainActivity.class.getSimpleName();

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
    public void showLoadingProcess() {

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_configuration_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_open:
                presenter.onOpenDeviceConfiguration();
                break;
            case R.id.item_save:
                presenter.onSaveDeviceConfiguration();
                break;
            case R.id.item_load:
                presenter.onLoadDeviceConfiguration();
                break;
            case R.id.item_set:
                presenter.onSetDeviceConfiguration();
                break;
//                if (itemId == R.id.item_set) {
//            // TODO? <Что делать со спинерами, когда эти параметр не трогали?>
//            // TODO <Поставить условие на подключение к устройству>
//            // TODO <Показать бегунок загрузки>
//            loader.loadCommandList(repositoryConfiguration.getCommandListForSetConfiguration());
//        } else if (itemId == R.id.item_load) {
//            // TODO <Поставить условие на подключение к устройству>
//            // TODO <Показать бегунок загрузки>
//            loader.loadCommandList(repositoryConfiguration.getCommandListForReadConfiguration());
//        } else if (itemId == R.id.item_open) {
//            mainView.startOpenFileManagerActivityWithRequestPermission();
//        } else if (itemId == R.id.item_save) {
//            mainView.startSaveFileActivityWithRequestPermission();
//        } else if (itemId == R.id.item_update_available_devices){
//            mainView.updateAvailableDevices();
//        }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    public MainActivity() {
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


}
