package tech.sadovnikov.configurator.presentation.console;

import android.os.Build;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import tech.sadovnikov.configurator.App;
import tech.sadovnikov.configurator.di.component.DaggerPresenterComponent;
import tech.sadovnikov.configurator.di.component.PresenterComponent;
import tech.sadovnikov.configurator.model.BluetoothService;
import tech.sadovnikov.configurator.model.data.DataManager;
import tech.sadovnikov.configurator.model.data.FileManager;
import tech.sadovnikov.configurator.model.entities.LogMessage;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

@InjectViewState
public class ConsolePresenter extends MvpPresenter<ConsoleView> {
    public static final String TAG = ConsolePresenter.class.getSimpleName();

    private PresenterComponent presenterComponent;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    BluetoothService bluetoothService;
    @Inject
    DataManager dataManager;

    private FileManager fileManager;

    ConsolePresenter() {
        Log.i(TAG, "ConsolePresenter: ");
        initDaggerComponent();
        fileManager = presenterComponent.getFileManager();
    }

    private void initDaggerComponent() {
        presenterComponent = DaggerPresenterComponent
                .builder()
                .applicationComponent(App.getApplicationComponent())
                .build();
        presenterComponent.injectConsolePresenter(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.i(TAG, "onFirstViewAttach: ");
        getViewState().setAutoScrollState(dataManager.getAutoScrollMode());
    }

    void onSendCommand(String command) {
        bluetoothService.sendData(command);
    }

    void onChangeAutoScrollClick(boolean isChecked) {
        dataManager.setAutoScrollMode(isChecked);
        getViewState().setAutoScrollState(dataManager.getAutoScrollMode());
    }

    void onSaveLog(int writePermission) {
        if (fileManager.isExternalStorageWritable()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (writePermission == PERMISSION_GRANTED) {
                    getViewState().showSaveLogDialog();
                } else getViewState().requestWritePermission();
            } else getViewState().showSaveLogDialog();
        } else {
            getViewState().showMessage("Ваше устройство не поддерживает данную функцию");
        }
    }

    void onPositiveWriteStorageRequestResult() {
        getViewState().showSaveLogDialog();
    }

    void onSaveDialogPositiveClick(final String fileName, String logTabName) {
        List<LogMessage> logMessageList = dataManager.getLogList(logTabName).getLogMessageList();
        fileManager.saveLog(logMessageList, fileName, new FileManager.SaveLogCallback() {
            @Override
            public void onSuccess(final String fileName) {
                getViewState().hideSaveLogDialog();
                getViewState().showSuccessSaveLogMessage(fileName);
            }

            @Override
            public void onError(Exception e) {
                getViewState().showErrorSaveLogMessage(e);
            }
        });
    }

    void onSaveDialogNegativeClick() {
        getViewState().hideSaveLogDialog();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
        compositeDisposable.clear();
    }


}
