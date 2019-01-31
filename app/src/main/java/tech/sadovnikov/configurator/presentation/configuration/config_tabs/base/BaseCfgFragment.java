package tech.sadovnikov.configurator.presentation.configuration.config_tabs.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.utils.ParametersEntities;


public abstract class BaseCfgFragment extends MvpAppCompatFragment implements BaseCfgView {
    private static final String TAG = BaseCfgFragment.class.getSimpleName();

    @InjectPresenter
    public
    BaseCfgPresenter presenter;

    private Listener listener;

    public AdapterView.OnItemSelectedListener spinParameterListener;

    //todo Сделать inputType в зависимости от типа данных параметров
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v(TAG, "ON_CREATE_VIEW");
        listener.onCreateViewBaseCfgView();
        setHasOptionsMenu(true);
        init();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void init() {
//        spinParameterListener = new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (view != null) {
//                    switch (view.getId()) {
//                        case R.id.spin_blinker_mode:
//                            presenter.onParameterChanged(ParametersEntities.BLINKER_MODE, String.valueOf(position));
//                            break;
//                        case R.id.spin_blinker_brightness:
//                            presenter.onParameterChanged(ParametersEntities.BLINKER_BRIGHTNESS, String.valueOf(position));
//                            break;
//                        case R.id.spin_satellite_system:
//                            presenter.onParameterChanged(ParametersEntities.SATELLITE_SYSTEM, String.valueOf(position));
//                            break;
//                    }
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        };
    }

    public abstract void setUp(View view);


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_configuration_options, menu);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "onAttach: ");
        if (context instanceof Listener) {
            listener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement BaseCfgFragment.Listener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        Log.v(TAG, "onDetach: ");
        listener = null;
        super.onDetach();
    }

    public interface Listener {
        void onCreateViewBaseCfgView();
    }
}
