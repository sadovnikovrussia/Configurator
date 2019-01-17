package tech.sadovnikov.configurator.presentation.main;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(SkipStrategy.class)
public interface MainView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void navigateToBluetoothView();

    @StateStrategyType(SkipStrategy.class)
    void navigateToConsoleView();

    @StateStrategyType(SkipStrategy.class)
    void navigateToConfigurationView();

    void showLoadingProcess();

    void hideLoadingProgress();

    void updateLoadingProcess(Float percents);

    @StateStrategyType(SkipStrategy.class)
    void setConsoleNavigationPosition();

    @StateStrategyType(SkipStrategy.class)
    void setBluetoothNavigationPosition();

    void setTitle(int title);


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
//        void requestReadPermission();
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

