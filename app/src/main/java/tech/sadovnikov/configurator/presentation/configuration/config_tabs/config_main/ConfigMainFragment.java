package tech.sadovnikov.configurator.presentation.configuration.config_tabs.config_main;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.model.entities.Configuration;
import tech.sadovnikov.configurator.model.entities.Parameter;
import tech.sadovnikov.configurator.presentation.base.OnParameterViewGroupClickListener;
import tech.sadovnikov.configurator.presentation.configuration.config_tabs.base.BaseCfgFragment;
import tech.sadovnikov.configurator.utils.ParametersEntities;

public class ConfigMainFragment extends BaseCfgFragment implements ConfigMainView {
    public static final String TAG = ConfigMainFragment.class.getSimpleName();

    // UI
    @BindView(R.id.spin_blinker_mode)
    Spinner spinBlinkerMode;
    @BindView(R.id.spin_blinker_brightness)
    Spinner spinBlinkerBrightness;
    @BindView(R.id.et_blinker_lx)
    EditText etBlinkerLx;
    @BindView(R.id.et_max_deviation)
    EditText etMaxDeviation;
    @BindView(R.id.et_tilt_angle)
    EditText etTiltAngle;
    @BindView(R.id.et_impact_pow)
    EditText etImpactPow;
    @BindView(R.id.et_upower_thld)
    EditText etUpowerThld;
    @BindView(R.id.et_deviation_int)
    EditText etDeviationInt;
    @BindView(R.id.et_max_active)
    EditText etMaxActive;
    @BindView(R.id.et_upower)
    EditText etUpower;

    @BindViews({R.id.ll_blinker_mode, R.id.ll_blinker_brightness, R.id.ll_blinker_lx, R.id.ll_max_deviation, R.id.ll_tilt_angle, R.id.ll_impact_pow, R.id.ll_upower_thld, R.id.ll_deviation_int, R.id.ll_max_active})
    List<ViewGroup> parameterViews;
    @BindViews({R.id.spin_blinker_mode, R.id.spin_blinker_brightness})
    List<Spinner> spinnerParameterValues;
    @BindViews({R.id.et_blinker_lx, R.id.et_max_deviation, R.id.et_tilt_angle, R.id.et_impact_pow, R.id.et_upower_thld, R.id.et_deviation_int, R.id.et_max_active})
    List<EditText> etParameterValues;

    @InjectPresenter
    ConfigMainPresenter configMainPresenter;

    Listener listener;

    AdapterView.OnItemSelectedListener onSpinParameterListener;


    public static ConfigMainFragment newInstance() {
        Bundle args = new Bundle();
        ConfigMainFragment fragment = new ConfigMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "ON_CREATE_VIEW");
        View view = inflater.inflate(R.layout.fragment_config_main, container, false);
        ButterKnife.bind(this, view);
        super.onCreateView(inflater, container, savedInstanceState);
        setUp(view);
        return view;
    }

    @Override
    public void setUp(View view) {
        onSpinParameterListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    switch (view.getId()) {
                        case R.id.spin_blinker_mode:
                            presenter.onParameterChanged(ParametersEntities.BLINKER_MODE, String.valueOf(position));
                            break;
                        case R.id.spin_blinker_brightness:
                            presenter.onParameterChanged(ParametersEntities.BLINKER_BRIGHTNESS, String.valueOf(position));
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        View.OnFocusChangeListener onEtParameterChangedListener = (v, hasFocus) -> {
            if (!hasFocus)
                switch (v.getId()) {
                    case R.id.et_blinker_lx:
                        presenter.onParameterChanged(ParametersEntities.BLINKER_LX, etBlinkerLx.getText().toString());
                        break;
                    case R.id.et_max_deviation:
                        presenter.onParameterChanged(ParametersEntities.MAX_DEVIATION, etMaxDeviation.getText().toString());
                        break;
                    case R.id.et_tilt_angle:
                        presenter.onParameterChanged(ParametersEntities.TILT_ANGLE, etTiltAngle.getText().toString());
                        break;
                    case R.id.et_impact_pow:
                        presenter.onParameterChanged(ParametersEntities.IMPACT_POW, etImpactPow.getText().toString());
                        break;
                    case R.id.et_upower_thld:
                        presenter.onParameterChanged(ParametersEntities.UPOWER_THLD, etUpowerThld.getText().toString());
                        break;
                    case R.id.et_deviation_int:
                        presenter.onParameterChanged(ParametersEntities.DEVIATION_INT, etDeviationInt.getText().toString());
                        break;
                    case R.id.et_max_active:
                        presenter.onParameterChanged(ParametersEntities.MAX_ACTIVE, etMaxActive.getText().toString());
                        break;
                }

        };
        OnParameterViewGroupClickListener onParameterViewGroupClickListener = new OnParameterViewGroupClickListener(Objects.requireNonNull(getContext()));

        for (ViewGroup vg : parameterViews)
            vg.setOnClickListener(onParameterViewGroupClickListener);
        for (Spinner spinner : spinnerParameterValues)
            spinner.setOnItemSelectedListener(onSpinParameterListener);
        for (EditText editText : etParameterValues)
            editText.setOnFocusChangeListener(onEtParameterChangedListener);
    }

    @Override
    public void showConfiguration(Configuration configuration) {
        Log.d(TAG, "showConfiguration: " + configuration);
        Parameter blinkerMode = configuration.getParameter(ParametersEntities.BLINKER_MODE);
        Parameter blinkerBrightness = configuration.getParameter(ParametersEntities.BLINKER_BRIGHTNESS);
        Parameter blinkerLx = configuration.getParameter(ParametersEntities.BLINKER_LX);
        Parameter maxDeviation = configuration.getParameter(ParametersEntities.MAX_DEVIATION);
        Parameter tiltAngle = configuration.getParameter(ParametersEntities.TILT_ANGLE);
        Parameter impactPow = configuration.getParameter(ParametersEntities.IMPACT_POW);
        Parameter upowerThld = configuration.getParameter(ParametersEntities.UPOWER_THLD);
        Parameter deviationInt = configuration.getParameter(ParametersEntities.DEVIATION_INT);
        Parameter maxActive = configuration.getParameter(ParametersEntities.MAX_ACTIVE);
        Parameter upower = configuration.getParameter(ParametersEntities.UPOWER);
        for (Spinner spinner : spinnerParameterValues) {
            spinner.setOnItemSelectedListener(null);
        }
        if (blinkerMode != null)
            spinBlinkerMode.setSelection(Integer.valueOf(blinkerMode.getValue()) + 1);
        if (blinkerBrightness != null) {
            spinBlinkerBrightness.setSelection(Integer.valueOf(blinkerBrightness.getValue()) + 1);
        }
        for (Spinner spinner : spinnerParameterValues) {
            spinner.setOnItemSelectedListener(onSpinParameterListener);
        }
        if (blinkerLx != null) etBlinkerLx.setText(blinkerLx.getValue());
        if (maxDeviation != null) etMaxDeviation.setText(maxDeviation.getValue());
        if (tiltAngle != null) etTiltAngle.setText(tiltAngle.getValue());
        if (impactPow != null) etImpactPow.setText(impactPow.getValue());
        if (upowerThld != null) etUpowerThld.setText(upowerThld.getValue());
        if (deviationInt != null) etDeviationInt.setText(deviationInt.getValue());
        if (maxActive != null) etMaxActive.setText(maxActive.getValue());
        if (upower != null) etUpower.setText(upower.getValue());
    }

    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "onAttach: ");
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
        super.onDetach();
    }
// ---------------------------------------------------------------------------------------------

}
