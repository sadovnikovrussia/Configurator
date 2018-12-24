package tech.sadovnikov.configurator.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import tech.sadovnikov.configurator.R;

public class ConfigMainFragment extends Fragment {
    private static final String TAG = "ConfigMainFragment";

    OnConfigMainFragmentInteractionListener listener;

    // UI
    Spinner spinBlinkerMode;
    Spinner spinBlinkerBrightness;
    TextView tvBlinkerLx;
    EditText etBlinkerLx;
    TextView tvMaxDeviation;
    EditText etMaxDeviation;
    TextView tvTiltAngle;
    EditText etTiltAngle;
    TextView tvImpactPow;
    EditText etImpactPow;
    TextView tvUpowerThld;
    EditText etUpowerThld;
    TextView tvDeviationInt;
    EditText etDeviationInt;
    TextView tvMaxActive;
    EditText etMaxActive;
    TextView tvUpower;
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


    public ConfigMainFragment() {
        // Required empty public constructor
        Log.v(TAG, "onConstructor");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_config_main, container, false);
        initUi(view);
        return view;
    }

    private void initUi(View view) {
        onLlParameterClickListener = new OnLlParameterClickListener(getContext());
        llBlinkerMode = view.findViewById(R.id.ll_blinker_mode);
        llBlinkerMode.setOnClickListener(onLlParameterClickListener);
        llBlinkerBrightness = view.findViewById(R.id.ll_blinker_brightness);
        llBlinkerBrightness.setOnClickListener(onLlParameterClickListener);
        llBlinkerLx = view.findViewById(R.id.ll_blinker_lx);
        llBlinkerLx.setOnClickListener(onLlParameterClickListener);
        llMaxDeviation = view.findViewById(R.id.ll_max_deviation);
        llMaxDeviation.setOnClickListener(onLlParameterClickListener);
        llTiltAngle = view.findViewById(R.id.ll_tilt_angle);
        llTiltAngle.setOnClickListener(onLlParameterClickListener);
        llImpactPow = view.findViewById(R.id.ll_impact_pow);
        llImpactPow.setOnClickListener(onLlParameterClickListener);
        llUpowerThld = view.findViewById(R.id.ll_upower_thld);
        llUpowerThld.setOnClickListener(onLlParameterClickListener);
        llDeviationInt = view.findViewById(R.id.ll_deviation_int);
        llDeviationInt.setOnClickListener(onLlParameterClickListener);
        llMaxActive = view.findViewById(R.id.ll_max_active);
        llMaxActive.setOnClickListener(onLlParameterClickListener);
        spinBlinkerMode = view.findViewById(R.id.spin_blinker_mode);
        spinBlinkerMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onSpinBlinkerModeItemSelected: " + position);
                listener.onSpinBlinkerModeItemSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinBlinkerBrightness = view.findViewById(R.id.spin_blinker_brightness);
        spinBlinkerBrightness.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onSpinBlinkerBrightnessItemSelected: " + position);
                listener.onSpinBlinkerBrightnessItemSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tvBlinkerLx = view.findViewById(R.id.tv_blinker_lx);
        etBlinkerLx = view.findViewById(R.id.et_blinker_lx);
        etBlinkerLx.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtBlinkerLxFocusChange(hasFocus);
            }
        });
        tvMaxDeviation = view.findViewById(R.id.tv_max_deviation);
        etMaxDeviation = view.findViewById(R.id.et_max_deviation);
        etMaxDeviation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtMaxDeviationFocusChange(hasFocus);
            }
        });
        tvTiltAngle = view.findViewById(R.id.tv_tilt_angle);
        etTiltAngle = view.findViewById(R.id.et_tilt_angle);
        etTiltAngle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtTiltAngleFocusChange(hasFocus);
            }
        });
        tvImpactPow = view.findViewById(R.id.tv_impact_pow);
        etImpactPow = view.findViewById(R.id.et_impact_pow);
        etImpactPow.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtImpactPowFocusChange(hasFocus);
            }
        });
        tvUpowerThld = view.findViewById(R.id.tv_upower_thld);
        etUpowerThld = view.findViewById(R.id.et_upower_thld);
        etUpowerThld.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtUpowerThldFocusChange(hasFocus);
            }
        });
        tvDeviationInt = view.findViewById(R.id.tv_deviation_int);
        etDeviationInt = view.findViewById(R.id.et_deviation_int);
        etDeviationInt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtDeviationIntFocusChange(hasFocus);
            }
        });
        tvMaxActive = view.findViewById(R.id.tv_max_active);
        etMaxActive = view.findViewById(R.id.et_max_active);
        etMaxActive.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtMaxActiveFocusChange(hasFocus);
            }
        });
        tvUpower = view.findViewById(R.id.tv_upower);
        etUpower = view.findViewById(R.id.et_upower);
    }


    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "onAttach");
        if (context instanceof ConfigMainFragment.OnConfigMainFragmentInteractionListener) {
            listener = (ConfigMainFragment.OnConfigMainFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnConfigBuoyFragmentInteractionListener");
        }
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
        listener.onConfigMainFragmentStart();
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

    public interface OnConfigMainFragmentInteractionListener {

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
