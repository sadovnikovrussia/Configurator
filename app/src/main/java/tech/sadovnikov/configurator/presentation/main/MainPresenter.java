package tech.sadovnikov.configurator.presentation.main;

import android.os.Build;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.di.ReadPermission;
import tech.sadovnikov.configurator.di.WritePermission;
import tech.sadovnikov.configurator.di.component.DaggerPresenterComponent;
import tech.sadovnikov.configurator.di.component.PresenterComponent;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.model.CfgLoader;
import tech.sadovnikov.configurator.model.data.FileManager;
import tech.sadovnikov.configurator.model.data.DataManager;
import tech.sadovnikov.configurator.model.data.configuration.Configuration;
import tech.sadovnikov.configurator.presentation.bluetooth.BluetoothView;
import tech.sadovnikov.configurator.presentation.configuration.ConfigurationView;
import tech.sadovnikov.configurator.presentation.configuration.config_tabs.base.BaseCfgView;
import tech.sadovnikov.configurator.presentation.console.ConsoleView;
import tech.sadovnikov.configurator.utils.rx.RxTransformers;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static tech.sadovnikov.configurator.model.BluetoothService.CONNECTION_STATE_CONNECTED;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    private static final String TAG = MainPresenter.class.getSimpleName();

    private PresenterComponent presenterComponent;
    @Inject
    CfgLoader cfgLoader;
    @Inject
    BluetoothService bluetoothService;
    @Inject
    DataManager dataManager;
    @Inject
    FileManager fileManager;
    @Inject
    @WritePermission
    int writePermission;
    @Inject
    @ReadPermission
    int readPermission;


    private Class currentView;
    private CompositeDisposable subscriptions;

    MainPresenter() {
        super();
        Log.w(TAG, "onConstructor: ");
        initDaggerComponent();
        presenterComponent.injectMainPresenter(this);
        subscriptions = new CompositeDisposable();
        cfgLoader.setBluetoothService(bluetoothService);
        cfgLoader.setDataManager(dataManager);
    }

    private void initDaggerComponent() {
        presenterComponent = DaggerPresenterComponent
                .builder()
                .applicationComponent(App.getApplicationComponent())
                .build();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.w(TAG, "onFirstViewAttach: ");
        Disposable subscriptionConnectionState = bluetoothService.getConnectionStateObservable()
                .compose(RxTransformers.applySchedulers())
                .subscribe(state -> {
                    switch (state) {
                        case CONNECTION_STATE_CONNECTED:
                            getViewState().showMessage("Подключено устройство " + bluetoothService.getConnectedDevice().getName());
                            break;
                    }
                });
        getViewState().showBluetoothView();
        subscriptions.add(subscriptionConnectionState);
    }

    @Override
    public void onDestroy() {
        subscriptions.clear();
    }

    void onNavigateToBluetooth() {
        Log.d(TAG, "onNavigateToBluetooth: ");
        if (!currentView.equals(BluetoothView.class)) getViewState().showBluetoothView();
    }

    void onNavigateToConfiguration() {
        if (!currentView.equals(ConfigurationView.class)) getViewState().showConfigurationView();
    }

    void onNavigateToConsole() {
        Log.d(TAG, "onNavigateToConsole: ");
        if (!currentView.equals(ConsoleView.class)) getViewState().showConsoleView();
    }

    void onNavigateToCfgTab(String cfgTab) {
        Log.d(TAG, "onNavigateToCfgTab: ");
        getViewState().showCfgTab(cfgTab);
    }


    void onReadConfiguration() {
        if (bluetoothService.getConnectionState() == CONNECTION_STATE_CONNECTED) {
            Disposable progressSubscription = cfgLoader.readFullConfiguration()
                    .compose(RxTransformers.applySchedulers())
                    .subscribe(integer -> getViewState().setLoadingProcess(integer),
                            throwable -> Log.i(TAG, "onError: " + throwable),
                            () -> {
                                getViewState().hideLoadingProgress();
                                getViewState().showMessage("Считывание конфигурации завершено");
                            },
                            disposable -> getViewState().setLoadingProcess(0));
        }
    }

    void onSetConfiguration() {
        if (bluetoothService.getConnectionState() == CONNECTION_STATE_CONNECTED) {
            Disposable progressSubscription = cfgLoader.setCurrentConfiguration()
                    .compose(RxTransformers.applySchedulers())
                    .subscribe(integer -> getViewState().setLoadingProcess(integer),
                            Throwable::printStackTrace,
                            () -> {
                                getViewState().hideLoadingProgress();
                                getViewState().showMessage("Установка конфигурации завершена");
                            },
                            disposable -> getViewState().setLoadingProcess(0)
                    );
        }
    }

    void onSaveConfiguration() {
        if (fileManager.isExternalStorageWritable()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (writePermission == PERMISSION_GRANTED) {
                    getViewState().showSaveDialog();
                } else getViewState().requestWritePermission();
            } else getViewState().showSaveDialog();
        } else {
            getViewState().showMessage("Ваше устройство не поддерживает данную функцию");
        }

    }

    void onPositiveWriteRequestResult() {
        writePermission = PERMISSION_GRANTED;
        getViewState().showSaveDialog();
    }

    void onSaveDialogPositiveClick(String name) {
        fileManager.saveConfiguration(dataManager.getConfiguration(), name, new FileManager.SaveCfgCallback() {
            @Override
            public void onSuccess(String fileName) {
                getViewState().hideDialogSave();
                getViewState().showSuccessSaveCfgMessage(fileName);
            }

            @Override
            public void onError(Exception e) {
                getViewState().showErrorSaveCfgMessage(e);
            }
        });
    }

    void onSaveDialogNegativeClick() {
        getViewState().hideDialogSave();
    }

    void onOpenConfiguration() {
        if (fileManager.isExternalStorageReadable()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (readPermission == PERMISSION_GRANTED) {
                    getViewState().startOpenFileManagerActivity();
                } else {
                    getViewState().requestReadStoragePermission();
                }
            } else {
                getViewState().startOpenFileManagerActivity();
            }
        } else {
            getViewState().showMessage("Ваше устройство не поддерживает данную функцию");
        }
    }

    void onPositiveReadStorageRequestResult() {
        readPermission = PERMISSION_GRANTED;
        getViewState().startOpenFileManagerActivity();
    }

    void onGetCfgPath(String path) {
        Log.d(TAG, "onGetCfgPath: ");
        fileManager.openConfiguration(path, new FileManager.OpenCfgCallback() {
            @Override
            public void onSuccess(String cfgName, Configuration configuration) {
                Log.d(TAG, "onSuccess: ");
                dataManager.setConfiguration(configuration);
                // todo Почему отсюда не вызываются некоторые методоы во view ???
                getViewState().showSuccessOpenCfgMessage(cfgName);
            }

            @Override
            public void onError(String cfgName, Exception e) {
                getViewState().showErrorOpenCfgMessage(cfgName, e);
            }
        });
    }

    void onCreateMainView() {

    }

    void onCreateViewBluetooth() {
        currentView = BluetoothView.class;
        getViewState().setBluetoothNavigationPosition();
        getViewState().setTitle(R.string.title_bluetooth);
    }

    void onCreateViewConfiguration() {
        currentView = ConfigurationView.class;
        getViewState().setConfigurationNavigationPosition();
        getViewState().setTitle(R.string.title_configuration);
    }

    void onCreateViewConsole() {
        currentView = ConsoleView.class;
        getViewState().setConsoleNavigationPosition();
        getViewState().setTitle(R.string.title_console);
    }

    void onCreateViewBaseCfgView() {
        currentView = BaseCfgView.class;
        getViewState().setConfigurationNavigationPosition();
        getViewState().setTitle(R.string.title_configuration);
    }

}
