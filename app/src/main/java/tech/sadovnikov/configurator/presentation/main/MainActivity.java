package tech.sadovnikov.configurator.presentation.main;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.di.component.ActivityComponent;
import tech.sadovnikov.configurator.di.component.DaggerActivityComponent;
import tech.sadovnikov.configurator.old.SaveFileDialogFragment;
import tech.sadovnikov.configurator.presentation.bluetooth.BluetoothFragment;
import tech.sadovnikov.configurator.presentation.configuration.ConfigurationFragment;
import tech.sadovnikov.configurator.presentation.configuration.config_tabs.BaseCfgFragment;
import tech.sadovnikov.configurator.presentation.configuration.config_tabs.ConfigBuoyFragment;
import tech.sadovnikov.configurator.presentation.console.ConsoleFragment;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class MainActivity extends MvpAppCompatActivity implements MainView,
        SaveFileDialogFragment.Listener,
        ConsoleFragment.Listener, BluetoothFragment.Listener, ConfigurationFragment.Listener,
        BaseCfgFragment.Listener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_WRITE_STORAGE = 1;
    private static final int REQUEST_READ_STORAGE = 2;
    public static final int OPEN_FILE_MANAGER_REQUEST_CODE = 11;

    @BindView(R.id.navigation)
    BottomNavigationView navigationView;
    @BindView(R.id.container)
    FrameLayout container;

    private SaveFileDialogFragment saveDialog;

    private ActivityComponent activityComponent;
    @InjectPresenter(type = PresenterType.GLOBAL)
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
                    presenter.onNavigateToBluetooth();
                    break;
                case R.id.navigation_configuration:
                    presenter.onNavigateToConfiguration();
                    break;
                case R.id.navigation_console:
                    presenter.onNavigateToConsole();
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
        showFragment(BluetoothFragment.newInstance(), BluetoothFragment.TAG);
    }

    @Override
    public void navigateToConfigurationView() {
        showFragment(ConfigurationFragment.newInstance(), ConfigurationFragment.TAG);
    }

    @Override
    public void navigateToConsoleView() {
        Log.w(TAG, "navigateToConsoleView: ");
        showFragment(ConsoleFragment.newInstance(), ConsoleFragment.TAG);
    }

    @Override
    public void showLoadingProcess() {
        Log.d(TAG, "showLoadingProcess: ");

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
    public void setConfigurationNavigationPosition() {
        Log.w(TAG, "setConfigurationNavigationPosition: ");
        navigationView.setOnNavigationItemSelectedListener(null);
        navigationView.setSelectedItemId(R.id.navigation_configuration);
        navigationView.setOnNavigationItemSelectedListener(navigationListener);
    }

    @Override
    public void setTitle(int title) {
        Log.d(TAG, "setTitle: ");
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
    }

    @Override
    public void showSaveDialog() {
        Log.d(TAG, "showSaveDialog: ");
        saveDialog = new SaveFileDialogFragment();
        saveDialog.show(getSupportFragmentManager(), SaveFileDialogFragment.TAG);
    }

    @Override
    public void hideDialogSave() {
        saveDialog.dismiss();
        saveDialog = null;
    }

    @Override
    public void showSuccessSaveCfgMessage(String name) {
        Log.d(TAG, "showSuccessSaveCfgMessage: ");
        showToast("Конфигурация " + name + " сохранена в папку Загрузки");
    }

    @Override
    public void showErrorSaveCfgMessage(Exception e) {
        Log.d(TAG, "showErrorSaveCfgMessage: ");
        showToast("Не удалось сохранить конфигурацию" + "\r\n" + e);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void requestWritePermission() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void requestReadStoragePermission() {
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
    }

    @Override
    public void onSaveDialogPositiveClick(String cfgName) {
        presenter.onSaveDialogPositiveClick(cfgName);
    }

    @Override
    public void onSaveDialogNegativeClick() {
        presenter.onSaveDialogNegativeClick();
    }

    private void showFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public void showMessage(String message) {
        Log.d(TAG, "showMessage: ");
        showToast(message);
    }

    private void showToast(String message) {
        Log.d(TAG, "showToast: ");
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        Log.d(TAG, "onBackPressed: " + backStackEntryCount);
        if (backStackEntryCount < 1) finishAffinity();
    }



    @Override
    public void startOpenFileManagerActivity() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, OPEN_FILE_MANAGER_REQUEST_CODE);
    }

    @Override
    public void showErrorOpenCfgMessage(String cfgName, Exception e) {
        Log.d(TAG, "showErrorOpenCfgMessage: ");
        showToast("Не удалось открыть " + cfgName + "\r\n" + e.toString());
    }

    @Override
    public void showSuccessOpenCfgMessage(String cfgName) {
        Log.d(TAG, "showSuccessOpenCfgMessage: ");
        showToast(cfgName + " открыта успешно");
    }

    @Override
    public void navigateToCfgTab(String cfgTab) {
        switch (cfgTab){
            case "Буй":
                showFragment(ConfigBuoyFragment.newInstance(), ConfigBuoyFragment.TAG);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_STORAGE) {
            if (permissions.length > 0 && permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED)
                    presenter.onPositiveWriteRequestResult();
            }
        } else if (requestCode == REQUEST_READ_STORAGE) {
            if (permissions.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED)
                    presenter.onPositiveReadStorageRequestResult();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case OPEN_FILE_MANAGER_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    presenter.onGetCfgPath(Objects.requireNonNull(Objects.requireNonNull(data).getData()).getPath());
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(TAG, "onCreateOptionsMenu: " + menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.v(TAG, "onPrepareOptionsMenu: " + menu.hasVisibleItems());
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.w(TAG, "onOptionsItemSelected: " + item.getItemId());
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
        Log.w(TAG, "onOptionsMenuClosed: ");
        super.onOptionsMenuClosed(menu);
    }

    @Override
    public void onCfgTabClick(String cfgTab) {
        presenter.onCfgTabClick(cfgTab);
    }



    public MainActivity() {
        super();
        //Log.w(TAG, "onConstructor: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "onStartBaseCfgView: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v(TAG, "onRestart: ");
    }


    @Override
    public void onCreateViewBluetooth() {
        presenter.onCreateViewBluetooth();
    }

    @Override
    public void onCreateViewConfiguration() {
        presenter.onCreateViewConfiguration();
    }

    @Override
    public void onCreateViewConsole() {
        presenter.onCreateViewConsole();
    }

    @Override
    public void onStartBaseCfgView() {
        presenter.onStartBaseCfgView();
    }
}
