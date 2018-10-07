package tech.sadovnikov.configurator.presenter;

import android.os.Message;
import android.util.Log;

import java.util.HashMap;

import tech.sadovnikov.configurator.Contract;
import tech.sadovnikov.configurator.model.Device;

public class Presenter implements Contract.Presenter {
    private static final String TAG = "Presenter";

    private Contract.View mainActivity;
    private Contract.Repository device;

    BluetoothService bluetoothService;

    public Presenter(Contract.View mainActivity) {
        Log.v(TAG, "onConstructor");
        this.mainActivity = mainActivity;
        this.device = Device.getInstance();
    }

    @Override
    public void onSwitchBtStateChanged() {

    }

    @Override
    public void onRvPairedDevicesItemClick() {

    }

    @Override
    public void onHandleMessage(Message msg) {
        Object obj = msg.obj;
        switch (msg.what) {
            // Отправка полученных данных в консоль
            case DataAnalyzer.WHAT_MAIN_LOG:
                mainActivity.showLog((String)obj);
                break;
            // Загрузка данных в LiveData
            case DataAnalyzer.WHAT_COMMAND_DATA:
                HashMap msgData = (HashMap) obj;
                String data = (String) msgData.get(DataAnalyzer.DATA);
                String command = (String) msgData.get(DataAnalyzer.PARAMETER_NAME);
                break;
        }

    }
}
