package tech.sadovnikov.configurator.presentation.configuration.config_tabs;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.model.data.configuration.Configuration;
import tech.sadovnikov.configurator.model.entities.Parameter;
import tech.sadovnikov.configurator.old.OnLlParameterClickListener;
import tech.sadovnikov.configurator.utils.ParametersEntities;


public class ConfigBuoyFragment extends BaseCfgFragment {
    public static final String TAG = ConfigBuoyFragment.class.getSimpleName();

    ConfigBuoyFragment.OnConfigBuoyFragmentInteractionListener listener;

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

    OnLlParameterClickListener onLlParameterClickListener;

    OnParameterChangedListener onParameterChangedListener;

    public ConfigBuoyFragment() {
        // Required empty public constructor
        //Log.v(TAG, "onConstructor");
    }

    public static ConfigBuoyFragment newInstance() {
        //Log.v(TAG, "newInstance: ");
        Bundle args = new Bundle();
        ConfigBuoyFragment fragment = new ConfigBuoyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_config_buoy, container, false);
        ButterKnife.bind(this, view);
        setUp(view);
        return view;
    }

    @Override
    void setUp(View view) {
        //onLlParameterClickListener = new OnLlParameterClickListener(getContext());
        //llId.setOnClickListener(onLlParameterClickListener);
        // TODO <Сделать отслеживание закрытия клавиатуры>
        etId.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus)
                presenter.onParameterChanged(ParametersEntities.ID, etId.getText().toString());
        });
//        btnRestart.setOnClickListener(v -> listener.onBtnRestartClick());
//        btnDefaultSettings.setOnClickListener(v -> listener.onBtnDefaultSettingsClick());
    }

    @Override
    public void showConfiguration(Configuration configuration) {
        Parameter id = configuration.getParameter(ParametersEntities.ID);
        Parameter version = configuration.getParameter(ParametersEntities.FIRMWARE_VERSION);
        if (id != null) etId.setText(id.getValue());
        if (version != null) etVersion.setText(version.getValue());
    }


    public String getEtIdText() {
        return etId.getText().toString();
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
    }


    public interface OnConfigBuoyFragmentInteractionListener {

        void onConfigBuoyFragmentStart();

        void onEtIdFocusChange(boolean hasFocus);

        void onBtnRestartClick();

        void onBtnDefaultSettingsClick();

        void onLlBuoyParameterClick(EditText editText);

    }

}