package tech.sadovnikov.configurator.presentation.configuration.config_tabs.config_buoy;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.model.entities.Configuration;
import tech.sadovnikov.configurator.model.entities.Parameter;
import tech.sadovnikov.configurator.old.OnParameterViewGroupClickListener;
import tech.sadovnikov.configurator.presentation.configuration.config_tabs.base.BaseCfgFragment;
import tech.sadovnikov.configurator.utils.ParametersEntities;


public class ConfigBuoyFragment extends BaseCfgFragment implements ConfigBuoyView {
    public static final String TAG = ConfigBuoyFragment.class.getSimpleName();

    // UI
    @BindView(R.id.et_id)
    EditText etId;
    @BindView(R.id.et_version)
    EditText etVersion;
    @BindView(R.id.btn_restart)
    Button btnRestart;
    @BindView(R.id.btn_default_settings)
    Button btnDefaultSettings;
    @BindView(R.id.ll_id)
    LinearLayout llId;

    @InjectPresenter
    ConfigBuoyPresenter configBuoyPresenter;


    public static ConfigBuoyFragment newInstance() {
        Bundle args = new Bundle();
        ConfigBuoyFragment fragment = new ConfigBuoyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "ON_CREATE_VIEW");
        View view = inflater.inflate(R.layout.fragment_config_buoy, container, false);
        ButterKnife.bind(this, view);
        setUp(view);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void setUp(View view) {
        View.OnFocusChangeListener onIdChangedListener = (v, hasFocus) -> {
            if (!hasFocus)
                presenter.onParameterChanged(ParametersEntities.ID, etId.getText().toString());
        };
        llId.setOnClickListener(new OnParameterViewGroupClickListener(Objects.requireNonNull(getContext())));
        etId.setOnFocusChangeListener(onIdChangedListener);
        btnRestart.setOnClickListener(v -> configBuoyPresenter.onRestartClick());
        btnDefaultSettings.setOnClickListener(v -> configBuoyPresenter.onDefaultSettingsClick());
    }

    @Override
    public void showConfiguration(Configuration configuration) {
        Log.d(TAG, "showConfiguration: " + configuration);
        Parameter id = configuration.getParameter(ParametersEntities.ID);
        Parameter version = configuration.getParameter(ParametersEntities.FIRMWARE_VERSION);
        if (id != null) etId.setText(id.getValue());
        if (version != null) etVersion.setText(version.getValue());
    }

    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "onAttach: ");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG, "onStartBaseCfgView");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(TAG, "onDetach: ");
    }
    // ---------------------------------------------------------------------------------------------


}
