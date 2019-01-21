package tech.sadovnikov.configurator.presentation.configuration.config_tabs;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.model.data.configuration.Configuration;
import tech.sadovnikov.configurator.model.entities.Parameter;
import tech.sadovnikov.configurator.old.OnLlParameterClickListener;
import tech.sadovnikov.configurator.utils.ParametersEntities;

public class ConfigMainFragment extends BaseCfgFragment {
    private static final String TAG = ConfigMainFragment.class.getSimpleName();

    Listener listener;

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

    LinearLayout llBlinkerLx;
    LinearLayout llMaxDeviation;
    LinearLayout llTiltAngle;
    LinearLayout llImpactPow;
    LinearLayout llUpowerThld;
    LinearLayout llDeviationInt;
    LinearLayout llMaxActive;
    LinearLayout llBlinkerMode;
    LinearLayout llBlinkerBrightness;

    OnLlParameterClickListener onLlParameterClickListener;

    AdapterView.OnItemSelectedListener onBlinkerModeSelectedListener;

    public ConfigMainFragment() {
        // Required empty public constructor
        //Log.v(TAG, "onConstructor");
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        Log.v(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_config_main, container, false);
        ButterKnife.bind(this, view);
        setUp(view);
        return view;
    }

    @Override
    public void setUp(View view) {
        onBlinkerModeSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.onParameterChanged(ParametersEntities.BLINKER_MODE, String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinBlinkerMode.setOnItemSelectedListener(onBlinkerModeSelectedListener);

//        onLlParameterClickListener = new OnLlParameterClickListener(getContext());
//        llBlinkerMode.setOnClickListener(onLlParameterClickListener);
//        llBlinkerBrightness.setOnClickListener(onLlParameterClickListener);
//        llBlinkerLx.setOnClickListener(onLlParameterClickListener);
//        llMaxDeviation.setOnClickListener(onLlParameterClickListener);
//        llTiltAngle.setOnClickListener(onLlParameterClickListener);
//        llImpactPow.setOnClickListener(onLlParameterClickListener);
//        llUpowerThld.setOnClickListener(onLlParameterClickListener);
//        llDeviationInt.setOnClickListener(onLlParameterClickListener);
//        llMaxActive.setOnClickListener(onLlParameterClickListener);
//        spinBlinkerBrightness.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(TAG, "onSpinBlinkerBrightnessItemSelected: " + position);
//                listener.onSpinBlinkerBrightnessItemSelected(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        etBlinkerLx.setOnFocusChangeListener((v, hasFocus) -> listener.onEtBlinkerLxFocusChange(hasFocus));
//        etMaxDeviation.setOnFocusChangeListener((v, hasFocus) -> listener.onEtMaxDeviationFocusChange(hasFocus));
//        etTiltAngle.setOnFocusChangeListener((v, hasFocus) -> listener.onEtTiltAngleFocusChange(hasFocus));
//        etImpactPow.setOnFocusChangeListener((v, hasFocus) -> listener.onEtImpactPowFocusChange(hasFocus));
//        etUpowerThld.setOnFocusChangeListener((v, hasFocus) -> listener.onEtUpowerThldFocusChange(hasFocus));
//        etDeviationInt.setOnFocusChangeListener((v, hasFocus) -> listener.onEtDeviationIntFocusChange(hasFocus));
//        etMaxActive.setOnFocusChangeListener((v, hasFocus) -> listener.onEtMaxActiveFocusChange(hasFocus));
    }

    @Override
    public void showConfiguration(Configuration configuration) {
        Parameter blinkerMode = configuration.getParameter(ParametersEntities.BLINKER_MODE);
        if (blinkerMode!=null) {
            spinBlinkerBrightness.setOnItemSelectedListener(null);
            spinBlinkerBrightness.setSelection(Integer.valueOf(blinkerMode.getValue()));
            spinBlinkerBrightness.setOnItemSelectedListener(onBlinkerModeSelectedListener);
        }
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
//        Log.v(TAG, "onStartBaseCfgView");
//        if (context instanceof Listener) {
//            listener = (Listener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnConfigBuoyFragmentInteractionListener");
//        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG, "onStart");
        //listener.onConfigMainFragmentStart();
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
        Log.v(TAG, "onDetach");
        listener = null;
        super.onDetach();
    }
    // ---------------------------------------------------------------------------------------------


    public interface Listener {

        void onConfigMainFragmentStart();

        void onSpinBlinkerModeItemSelected(int position);

        void onSpinBlinkerBrightnessItemSelected(int position);

        void onEtMaxDeviationFocusChange(boolean hasFocus);

        void onEtTiltAngleFocusChange(boolean hasFocus);

        void onEtImpactPowFocusChange(boolean hasFocus);

        void onEtUpowerThldFocusChange(boolean hasFocus);

        void onEtDeviationIntFocusChange(boolean hasFocus);

        void onEtMaxActiveFocusChange(boolean hasFocus);

        void onEtBlinkerLxFocusChange(boolean hasFocus);

        void onLlMainParameterClick(EditText etBlinkerLx);
    }

}
