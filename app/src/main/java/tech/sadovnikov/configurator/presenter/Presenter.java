package tech.sadovnikov.configurator.presenter;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import tech.sadovnikov.configurator.Contract;
import tech.sadovnikov.configurator.model.Device;
import tech.sadovnikov.configurator.model.Logs;
import tech.sadovnikov.configurator.view.MainActivity;

public class Presenter implements Contract.Presenter, Logs.OnLogsActionsListener {
    private static final String TAG = "Presenter";

    private Contract.View mainActivity;
    private Contract.Repository device;
    private Contract.Log logs;

    private BluetoothService bluetoothService;

    public Presenter(Contract.View mainActivity) {
        Log.v(TAG, "onConstructor");
        this.mainActivity = mainActivity;
        this.device = Device.getInstance();
        this.logs = Logs.getInstance(this);
        UiHandler uiHandler = new UiHandler((Activity) mainActivity, this);
        this.bluetoothService = new BluetoothService(uiHandler);
    }

    @Override
    public void onSwitchBtStateChanged() {

    }

    @Override
    public void onPairedDevicesRvItemClick(BluetoothDevice bluetoothDevice) {
        // Logs.d(TAG, "Ща будем подключаться к " + bluetoothDevice.getName());
        bluetoothService.connectTo(bluetoothDevice);
    }



    @Override
    public void onHandleMessage(Message msg) {
        Object obj = msg.obj;
        switch (msg.what) {
            // Отправка полученных данных в консоль
            case DataAnalyzer.WHAT_MAIN_LOG:
                String message = (String) obj;
                logs.addLine(message);
                break;
            // Загрузка данных в LiveData
            case DataAnalyzer.WHAT_COMMAND_DATA:
                HashMap msgData = (HashMap) obj;
                String data = (String) msgData.get(DataAnalyzer.DATA);
                String command = (String) msgData.get(DataAnalyzer.PARAMETER_NAME);
                break;
        }
    }

    @Override
    public void onConsoleFragmentCreateView() {
        mainActivity.showLog(logs.getLogsMessages());
    }

    @Override
    public void onAddLogsLineEvent(String line) {
        mainActivity.addLogsLine(line);
    }

    public static class UiHandler extends Handler {
        WeakReference<Activity> activityWeakReference;

        Contract.Presenter presenter;

        UiHandler(Activity activity, Contract.Presenter presenter) {
            activityWeakReference = new WeakReference<>(activity);
            this.presenter = presenter;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = (MainActivity) activityWeakReference.get();
            if (activity != null) {
                presenter.onHandleMessage(msg);
            }
        }

    }

}
