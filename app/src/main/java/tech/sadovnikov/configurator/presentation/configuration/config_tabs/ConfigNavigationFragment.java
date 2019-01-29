package tech.sadovnikov.configurator.presentation.configuration.config_tabs;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.old.OnParameterViewGroupClickListener;

public class ConfigNavigationFragment extends Fragment {
    private static final String TAG = "ConfigNavigationFragmen";

    OnConfigNavigationFragmentInteractionListener listener;

    // UI
    EditText etLongitude;
    EditText etLatitude;
    EditText etBaseLongitude;
    EditText etBaseLatitude;
    Button btnRcvColdStart;
    EditText etLongDeviation;
    EditText etLatDeviation;
    EditText etHdop;
    EditText etFixDelay;
    Spinner spinSatelliteSystem;
    Button btnRequestBasePos;
    Button btnShowMapCurrentPos;
    Button btnShowMapBasePos;
    CheckBox cbTruePos;
    LinearLayout llBaseLatitude;
    LinearLayout llBaseLongitude;
    LinearLayout llLatDeviation;
    LinearLayout llLongDeviation;
    LinearLayout llHdop;
    LinearLayout llFixDelay;
    LinearLayout llSatelliteSystem;

    OnParameterViewGroupClickListener onLlParameterClickListener;

    public ConfigNavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_config_navigation, container, false);
        initUi(view);
        return view;
    }

    private void initUi(View view) {
        onLlParameterClickListener = new OnParameterViewGroupClickListener(getContext());
        llBaseLatitude = view.findViewById(R.id.ll_base_latitude);
        llBaseLatitude.setOnClickListener(onLlParameterClickListener);
        llBaseLongitude = view.findViewById(R.id.ll_base_longitude);
        llBaseLongitude.setOnClickListener(onLlParameterClickListener);
        llLatDeviation = view.findViewById(R.id.ll_lat_deviation);
        llLatDeviation.setOnClickListener(onLlParameterClickListener);
        llLongDeviation = view.findViewById(R.id.ll_long_deviation);
        llLongDeviation.setOnClickListener(onLlParameterClickListener);
        llHdop = view.findViewById(R.id.ll_hdop);
        llHdop.setOnClickListener(onLlParameterClickListener);
        llFixDelay = view.findViewById(R.id.ll_fix_delay);
        llFixDelay.setOnClickListener(onLlParameterClickListener);
        llSatelliteSystem = view.findViewById(R.id.ll_satellite_system);
        llSatelliteSystem.setOnClickListener(onLlParameterClickListener);

        etLongitude = view.findViewById(R.id.et_longitude);
        etLatitude = view.findViewById(R.id.et_latitude);
        etBaseLongitude = view.findViewById(R.id.et_base_longitude);
        etBaseLongitude.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtBaseLongitudeFocusChange(hasFocus);
            }
        });
        etBaseLatitude = view.findViewById(R.id.et_base_latitude);
        etBaseLatitude.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtBaseLatitudeFocusChange(hasFocus);
            }
        });
        btnRcvColdStart = view.findViewById(R.id.btn_rcv_coldstart);
        btnRcvColdStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnRcvColdStartClick();
            }
        });
        etLongDeviation = view.findViewById(R.id.et_long_deviation);
        etLongDeviation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtLongDeviationFocusChanged(hasFocus);
            }
        });
        etLatDeviation = view.findViewById(R.id.et_lat_deviation);
        etLatDeviation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtLatDeviationFocusChanged(hasFocus);
            }
        });
        etHdop = view.findViewById(R.id.et_hdop);
        etHdop.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtHdopFocusChanged(hasFocus);
            }
        });
        etFixDelay = view.findViewById(R.id.et_fix_delay);
        etFixDelay.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtFixDelayFocusChanged(hasFocus);
            }
        });
        spinSatelliteSystem = view.findViewById(R.id.spin_satellite_system);
        spinSatelliteSystem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listener.onSpinSatelliteSystemItemSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnRequestBasePos = view.findViewById(R.id.btn_request_base_pos);
        btnRequestBasePos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnRequestBasePosClick();
            }
        });
        btnShowMapCurrentPos = view.findViewById(R.id.btn_show_map_current_pos);
        btnShowMapCurrentPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnShowMapCurrentPosClick();
            }
        });
        btnShowMapBasePos = view.findViewById(R.id.btn_show_map_base_pos);
        btnShowMapBasePos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnShowMapBasePosClick();
            }
        });
        cbTruePos = view.findViewById(R.id.cb_true_pos);
        cbTruePos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCbTruePosClick();
            }
        });
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
        Log.v(TAG, "onStartBaseCfgView");
        if (context instanceof ConfigNavigationFragment.OnConfigNavigationFragmentInteractionListener) {
            listener = (ConfigNavigationFragment.OnConfigNavigationFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnConfigNavigationFragmentInteractionListener");
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
        Log.v(TAG, "onStartBaseCfgView");
        listener.onConfigNavigationFragmentStart();
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
        super.onDetach();
    }
    // ---------------------------------------------------------------------------------------------

    public interface OnConfigNavigationFragmentInteractionListener {
        void onConfigNavigationFragmentStart();

        void onBtnRcvColdStartClick();

        void onEtLongDeviationFocusChanged(boolean hasFocus);

        void onEtLatDeviationFocusChanged(boolean hasFocus);

        void onEtHdopFocusChanged(boolean hasFocus);

        void onEtFixDelayFocusChanged(boolean hasFocus);

        void onSpinSatelliteSystemItemSelected(int position);

        void onBtnRequestBasePosClick();

        void onBtnShowMapCurrentPosClick();

        void onBtnShowMapBasePosClick();

        void onCbTruePosClick();

        void onEtBaseLongitudeFocusChange(boolean hasFocus);

        void onEtBaseLatitudeFocusChange(boolean hasFocus);

        void onLlNavigationParameterClick(EditText editText);
    }
}
