package tech.sadovnikov.configurator.presentation.main;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import tech.sadovnikov.configurator.presentation.AddToEndSingleByTagStateStrategy;

@StateStrategyType(SkipStrategy.class)
public interface MainView extends MvpView {

    void showBluetoothView();
    void showConsoleView();
    void showConfigurationView();
    void showCfgTab(String cfgTab);

    void setBluetoothNavigationPosition();
    void setConfigurationNavigationPosition();
    void setConsoleNavigationPosition();

    void setTitle(int title);

    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "loadingProcess")
    void setLoadingProcess(int progress);
    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "loadingProcess")
    void hideLoadingProgress();

    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "saveDialog")
    void showSaveDialog();
    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "saveDialog")
    void hideDialogSave();

    void showMessage(String message);

    void showSuccessSaveCfgMessage(String name);
    void showErrorSaveCfgMessage(Exception e);
    void requestWritePermission();


    void showSuccessOpenCfgMessage(String cfgName);
    void showErrorOpenCfgMessage(String cfgName, Exception e);
    void requestReadStoragePermission();
    void startOpenFileManagerActivity();



//        // Вывести сообщение лога в консоль
//        void showLog(String logsMessages);
//
//        void addLogsLine(String line);
//
//        // Скрыть устройства
//        void hideDevices();
//
//        // Показать устройства
//        void showDevices();
//
//        // Обновить список подключенных устройств
//        void updatePairedDevices();
//
//        // Обновить список доступных устройств
//        void updateAvailableDevices();
//
//        void showLoadingProgress(int size);
//
//        void hideLoadingProgress();
//
//        void setSwitchBtState(boolean state);
//
//        void unregisterBluetoothBroadcastReceiver(BluetoothBroadcastReceiver bluetoothBroadcastReceiver);
//
//        void setNavigationPosition(String fragment);
//
//        void showToast(String toast);
//
//        String getCommandLineText();
//
//        void showParameter(String name, String value);
//
//        String getEtIdText();
//
//        void startOpenFileManagerActivityWithRequestPermission();
//
//        void startSaveFileActivityWithRequestPermission();
//
//        void startBluetoothDiscoveryWithRequestPermission();
//
//        String getEtBlinkerLxText();
//
//        void setLoadingProgress(int commandNumber, int size);
//
//        String getEtMaxDeviationText();
//
//        String getEtTiltDeviationText();
//
//        String getEtImpactPowText();
//
//        String getEtUpowerThldText();
//
//        String getEtDeviationIntText();
//
//        String getEtMaxActiveText();
//
//        void requestReadStoragePermission();
//
//        void requestWritePermission();
//
//        void requestBluetoothPermission();
//
//        void startOpenFileManagerActivity();
//
//        void showSaveFileDialog();
//
//        String getEtLongDeviationText();
//
//        String getEtLatDeviationText();
//
//        String getEtHdopText();
//
//        String getEtFixDelayText();
//
//        String getCheckedAlarmEvents();
//
//        String getCheckedEventsMask();
//
//        String getEtServerText();
//
//        String getEtConnectAttemptsText();
//
//        String getEtSessionTimeText();
//
//        String getEtPacketToutText();
//
//        String getEtNormalIntText();
//
//        String getEtAlarmIntText();
//
//        String getEtSmsCenterText();
//
//        String getEtCmdNumberText();
//
//        String getEtAnswNumberText();
//
//        String getEtApnText();
//
//        String getEtPasswordText();
//
//        String getEtLoginText();
//
//        String getEtPinText();
//
//        String getEtSimAttemptsText();
//
//        String getEtDelivTimeoutText();
//
//        void setTitle(String title);
//
//        void setSwitchBtText(String text);
//
//        void showConfigActionsMenuGroup();
//
//        void hideConfigActionsMenuGroup();
//
//        boolean isBluetoothFragmentResumed();
//
//        void startMapActivity(String latitude, String longitude);
//
//        String getEtLongitude();
//
//        String getEtLatitude();
//
//        String getEtBaseLongitude();
//
//        String getEtBaseLatitude();
//
//        String getStateCbTruePos();
//
//        String getBasePos();
//
//        void setFocusOnEt(EditText editText);
//
//        void showItemUpdateAvailableDevices();
//
//        void hideItemUpdateAvailableDevices();
//
//        boolean isAvailableDevicesFragmentResumed();
//
//        void openDevices();
//
//        void closeDevices();
//
//        int getSelectedPageOfViewPager();

}

