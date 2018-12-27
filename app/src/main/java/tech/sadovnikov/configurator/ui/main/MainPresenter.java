package tech.sadovnikov.configurator.ui.main;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    private static final String TAG = MainPresenter.class.getSimpleName();

    public MainPresenter() {
        Log.e(TAG, "MainPresenter: ");
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.e(TAG, "onFirstViewAttach: ");
        getViewState().showBluetoothView();
        getViewState().setBluetoothNavigationPosition();
    }

    public void onBluetoothNavigationItemSelected() {
        Log.e(TAG, "onBluetoothNavigationItemSelected: ");
        getViewState().showBluetoothView();
        getViewState().setBluetoothNavigationPosition();
    }

    public void onConfigurationNavigationItemSelected() {
        Log.e(TAG, "onConfigurationNavigationItemSelected: ");
        getViewState().showConfigurationView();
        getViewState().setConfigurationNavigationPosition();
    }

    public void onConsoleNavigationItemSelected() {
        //getViewState().showConfigurationView();
    }

    public void onStart() {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }
}
