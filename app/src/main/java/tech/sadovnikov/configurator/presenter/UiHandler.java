package tech.sadovnikov.configurator.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

import tech.sadovnikov.configurator.Contract;
import tech.sadovnikov.configurator.view.MainActivity;

public class UiHandler extends Handler {
    static final int WHAT_CONNECTING_ERROR = 13;

    private WeakReference<Activity> activityWeakReference;

    private Contract.Presenter presenter;


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
