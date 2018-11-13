package tech.sadovnikov.configurator;

import android.content.Intent;
import android.os.Message;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

import tech.sadovnikov.configurator.model.Parameter;
import tech.sadovnikov.configurator.presenter.BluetoothBroadcastReceiver;
import tech.sadovnikov.configurator.view.adapter.AvailableDevicesItemView;
import tech.sadovnikov.configurator.view.adapter.PairedDevicesItemView;


public interface Contract {

    interface View {
        String BLUETOOTH_FRAGMENT = "BluetoothFragment";
        String CONFIGURATION_FRAGMENT = "ConfigurationFragment";
        String CONSOLE_FRAGMENT = "ConsoleFragment";
        String CONFIG_BUOY_FRAGMENT = "Буй";
        String CONFIG_MAIN_FRAGMENT = "Основные";
        String CONFIG_NAVIGATION_FRAGMENT = "Навигация";
        String CONFIG_EVENTS_FRAGMENT = "События";
        String CONFIG_SERVER_FRAGMENT = "Сервер";
        String CONFIG_SIM_CARD_FRAGMENT = "SIM карта";
        int FILE_MANAGER_REQUEST_CODE = 1;

        // Показать (установить фрагмент в MainActivity)
        void showFragment(String fragment);

        // Вывести сообщение лога в консоль
        void showLog(String logsMessages);

        void addLogsLine(String line);

        // Скрыть устройства
        void hideDevices();

        // Показать устройства
        void showDevices();

        // Обновить список подключенных устройств
        void updatePairedDevices();

        // Обновить список доступных устройств
        void updateAvailableDevices();

        void showLoadingProgress(int size);

        void hideLoadingProgress();

        void setSwitchBtState(boolean state);

        void unregisterBluetoothBroadcastReceiver(BluetoothBroadcastReceiver bluetoothBroadcastReceiver);

        void setNavigationPosition(String fragment);

        void showToast(String toast);

        String getCommandLineText();

        void showParameter(String name, String value);

        String getEtIdText();

        void startFileManagerActivityWithRequestPermission();

        void startBluetoothDiscoveryWithRequestPermission();

        String getEtBlinkerLxText();

        void setLoadingProgress(int commandNumber, int size);

        String getEtMaxDeviationText();

        String getEtTiltDeviationText();

        String getEtImpactPowText();

        String getEtUpowerThldText();

        String getEtDeviationIntText();

        String getEtMaxActiveText();

        void requestReadPermission();

        void requestWritePermission();

        void requestBluetoothPermission();

        void startFileManagerActivity();

        String getEtLongDeviationText();

        String getEtLatDeviationText();

        String getEtHdopText();

        String getEtFixDelayText();

        String getCheckedAlarmEvents();

        String getCheckedEventsMask();

        String getEtServerText();

        String getEtConnectAttemptsText();

        String getEtSessionTimeText();

        String getEtPacketToutText();

        String getEtNormalIntText();

        String getEtAlarmIntText();

        String getEtSmsCenterText();

        String getEtCmdNumberText();

        String getEtAnswNumberText();

        String getEtApnText();

        String getEtPasswordText();

        String getEtLoginText();

        String getEtPinText();

        String getEtSimAttemptsText();

        String getEtDelivTimeoutText();

        void setTitle(String title);

        void setSwitchBtText(String text);

        void showConfigActionsMenu();

        void hideConfigActionsMenu();

        boolean isBluetoothFragmentResumed();

        void startMapActivity(String latitude, String longitude);

        String getEtLongitude();

        String getEtLatitude();

        String getEtBaseLongitude();

        String getEtBaseLatitude();

        String getStateCbTruePos();

        String getBasePos();

        void setFocusOnEt(EditText editText);
    }

    interface Presenter {
        // Lifecycle -------------------------------------------------------------------------------
        void onMainActivityCreate();

        void onMainActivityDestroy();

        void onBluetoothFragmentStart();

        void onBluetoothFragmentCreateView();

        void onAvailableDevicesFragmentDestroyView();

        void onConfigurationFragmentStart();

        void onConfigBuoyFragmentStart();

        void onConsoleFragmentCreateView();

        void onConsoleFragmentStart();


        void onSwitchBtStateChanged(boolean state);

        void onPairedDevicesRvItemClick(String bluetoothDeviceAddress);

        void onHandleMessage(Message msg);

        boolean onNavigationItemSelected(MenuItem item);

        void onDevicesPageSelected(int position);

        void onBindViewHolderOfAvailableDevicesRvAdapter(AvailableDevicesItemView holder, int position);

        int onGetItemCountOfAvailableDevicesRvAdapter();

        void onBindViewHolderOfPairedDevicesRvAdapter(PairedDevicesItemView holder, int position);

        int onGetItemCountOfPairedDevicesRvAdapter();

        void onTvLogsLongClick();

        void onAvailableDevicesRvItemClicked(String bluetoothDeviceAddress);

        void onBtnSendCommandClick();

        void onConfigTabsRvItemClick(String tab);

        void onConfigurationOptionsItemSelected(MenuItem item);

        void onEtIdFocusChange(boolean hasFocus);

        void onAddLogsLineEvent(String line);

        void onMainActivityResult(int requestCode, int resultCode, Intent data);

        void onConfigMainFragmentStart();

        void onBtnRestartClick();

        void onBtnDefaultSettingsClick();

        void onSpinBlinkerBrightnessItemSelected(int position);

        void onSpinBlinkerModeItemSelected(int position);

        void onEtMaxDeviationFocusChange(boolean hasFocus);

        void onEtTiltAngleFocusChange(boolean hasFocus);

        void onEtImpactPowFocusChange(boolean hasFocus);

        void onEtUpowerThldFocusChange(boolean hasFocus);

        void onEtDeviationIntFocusChange(boolean hasFocus);

        void onEtMaxActiveFocusChange(boolean hasFocus);

        void onConfigNavigationFragmentStart();

        void onBtnRcvColdStartClick();

        void onEtLongDeviationFocusChanged(boolean hasFocus);

        void onEtLatDeviationFocusChanged(boolean hasFocus);

        void onEtHdopFocusChanged(boolean hasFocus);

        void onEtFixDelayFocusChanged(boolean hasFocus);

        void onSpinSatelliteSystemItemSelected(int position);

        void onBtnRequestBasePosClick();

        void OnConfigEventsFragmentStart();

        void onBtnAlarmEventsClick();

        void onEventsMaskCbClick();

        void onEtServerFocusChange(boolean hasFocus);

        void onConfigServerFragmentStart();

        void onEtConnectAttemptsFocusChange(boolean hasFocus);

        void onEtSessionTimeFocusChange(boolean hasFocus);

        void onEtPacketToutFocusChange(boolean hasFocus);

        void onSpinPriorityChnlItemClick(int position);

        void onEtNormalIntFocusChange(boolean hasFocus);

        void onEtAlarmIntFocusChange(boolean hasFocus);

        void onEtSmsCenterFocusChange(boolean hasFocus);

        void onEtCmdNumberFocusChange(boolean hasFocus);

        void onEtAnswNumberFocusChange(boolean hasFocus);

        void onEtBlinkerLxFocusChange(boolean hasFocus);

        void onBtnClearArchiveClick();

        void onBtnCloseConnectClick();

        void onEtApnFocusChange(boolean hasFocus);

        void onConfigSimCardFragmentStart();

        void onEtPasswordFocusChange(boolean hasFocus);

        void onEtLoginFocusChange(boolean hasFocus);

        void onBtnDefaultApnClick();

        void onBtnDefaultLoginClick();

        void onBtnDefaultPasswordClick();

        void onBtnClearApnClick();

        void onBtnClearLoginClick();

        void onBtnClearPasswordClick();

        void onBtnEnterPinClick();

        void onBtnClearPinClick();

        void onEtSimAttemptsFocusChange(boolean hasFocus);

        void onEtDelivTimeoutFocusChange(boolean hasFocus);

        void onNegativeRequestReadExternalStoragePermissionRequestResult();

        void onPositiveRequestReadExternalStoragePermissionRequestResult();

        void onPositiveRequestWriteExternalStoragePermissionRequestResult();

        void onNegativeRequestWriteExternalStoragePermissionRequestResult();

        void onPositiveRequestAccessCoarseLocationPermissionRequestResult();

        void onNegativeRequestAccessCoarseLocationPermissionRequestResult();

        void onBluetoothFragmentDestroyView();

        void onBluetoothFragmentDestroy();

        void onCreateOptionsMenu();

        void onBtnShowMapCurrentPosClick();

        void onBtnShowMapBasePosClick();

        void onCbTruePosClick();

        void onEtBaseLongitudeFocusChange(boolean hasFocus);

        void onEtBaseLatitudeFocusChange(boolean hasFocus);

        void onLlParameterClick(EditText editText);
    }

    interface Logs {
        void addLine(String line);

        String getLogsMessages();
    }

    interface RepositoryConfiguration {

        void setUiConfiguration(tech.sadovnikov.configurator.model.Configuration uiConfiguration);

        tech.sadovnikov.configurator.model.Configuration getConfigurationForSave();

        String getParameterValue(String name);

        void setParameter(Parameter parameter);

        void setParameter(String name, String value);

        void setParameterFromUi(String name, String value);

        ArrayList<String> getCommandListForReadConfiguration();

        ArrayList<String> getCommandListForSetConfiguration();

    }

}
