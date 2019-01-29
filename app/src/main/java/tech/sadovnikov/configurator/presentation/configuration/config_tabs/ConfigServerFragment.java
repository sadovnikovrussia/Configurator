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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.old.OnParameterViewGroupClickListener;


public class ConfigServerFragment extends Fragment {
    private static final String TAG = "ConfigServerFragment";

    OnConfigServerFragmentInteractionListener listener;

    // UI
    EditText etServer;
    EditText etConnectAttempts;
    EditText etSessionTime;
    EditText etPacketTout;
    Spinner spinPriorityChnl;
    EditText etNormalInt;
    EditText etAlarmInt;
    EditText etSmsCenter;
    EditText etCmdNumber;
    EditText etAnswNumber;
    EditText etPackets;
    EditText etPacketsPercents;
    Button btnClearArchive;
    Button btnCloseConnect;
    LinearLayout llServer;
    LinearLayout llConnectAttempts;
    LinearLayout llSessionTime;
    LinearLayout llPacketTout;
    LinearLayout llNormalInt;
    LinearLayout llAlarmInt;
    LinearLayout llSmsCenter;
    LinearLayout llCmdNumber;
    LinearLayout llAnswNumber;
    LinearLayout llPriorityChnl;
    OnParameterViewGroupClickListener onLlParameterClickListener;

    public ConfigServerFragment() {
        // Required empty public constructor
        Log.v(TAG, "onConstructor");
    }

    private void initUi(View view) {
        onLlParameterClickListener = new OnParameterViewGroupClickListener(getContext());

        llServer = view.findViewById(R.id.ll_server);
        llServer.setOnClickListener(onLlParameterClickListener);
        llConnectAttempts = view.findViewById(R.id.ll_connect_attempts);
        llConnectAttempts.setOnClickListener(onLlParameterClickListener);
        llSessionTime = view.findViewById(R.id.ll_session_time);
        llSessionTime.setOnClickListener(onLlParameterClickListener);
        llPacketTout = view.findViewById(R.id.ll_packet_tout);
        llPacketTout.setOnClickListener(onLlParameterClickListener);
        llNormalInt = view.findViewById(R.id.ll_normal_int);
        llNormalInt.setOnClickListener(onLlParameterClickListener);
        llAlarmInt = view.findViewById(R.id.ll_alarm_int);
        llAlarmInt.setOnClickListener(onLlParameterClickListener);
        llSmsCenter = view.findViewById(R.id.ll_sms_center);
        llSmsCenter.setOnClickListener(onLlParameterClickListener);
        llCmdNumber = view.findViewById(R.id.ll_cmd_number);
        llCmdNumber.setOnClickListener(onLlParameterClickListener);
        llAnswNumber = view.findViewById(R.id.ll_answ_number);
        llAnswNumber.setOnClickListener(onLlParameterClickListener);
        llPriorityChnl = view.findViewById(R.id.ll_priority_chnl);
        llPriorityChnl.setOnClickListener(onLlParameterClickListener);

        etServer = view.findViewById(R.id.et_server);
        etServer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtServerFocusChange(hasFocus);
            }
        });
        etConnectAttempts = view.findViewById(R.id.et_connect_attempts);
        etConnectAttempts.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtConnectAttemptsFocusChange(hasFocus);
            }
        });
        etSessionTime = view.findViewById(R.id.et_session_time);
        etSessionTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtSessionTimeFocusChange(hasFocus);
            }
        });
        etPacketTout = view.findViewById(R.id.et_packet_tout);
        etPacketTout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtPacketToutFocusChange(hasFocus);
            }
        });
        spinPriorityChnl = view.findViewById(R.id.spin_priority_chnl);
        spinPriorityChnl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listener.onSpinPriorityChnlItemClick(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etNormalInt = view.findViewById(R.id.et_normal_int);
        etNormalInt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtNormalIntFocusChange(hasFocus);
            }
        });
        etAlarmInt = view.findViewById(R.id.et_alarm_int);
        etAlarmInt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtAlarmIntFocusChange(hasFocus);
            }
        });
        etSmsCenter = view.findViewById(R.id.et_sms_center);
        etSmsCenter.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtSmsCenterFocusChange(hasFocus);
            }
        });
        etCmdNumber = view.findViewById(R.id.et_cmd_number);
        etCmdNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtCmdNumberFocusChange(hasFocus);
            }
        });
        etAnswNumber = view.findViewById(R.id.et_answ_number);
        etAnswNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtAnswNumberFocusChange(hasFocus);
            }
        });
        etPackets = view.findViewById(R.id.et_packets);
        etPacketsPercents = view.findViewById(R.id.et_packets_percents);
        btnClearArchive = view.findViewById(R.id.btn_clear_archive);
        btnClearArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnClearArchiveClick();
            }
        });
        btnCloseConnect = view.findViewById(R.id.btn_close_connect);
        btnCloseConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnCloseConnectClick();
            }
        });
    }


    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_config_server, container, false);
        initUi(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnConfigServerFragmentInteractionListener) {
            listener = (OnConfigServerFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnConfigServerFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
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
        listener.onConfigServerFragmentStart();
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

    public interface OnConfigServerFragmentInteractionListener {
        void onConfigServerFragmentStart();

        void onEtServerFocusChange(boolean hasFocus);

        void onEtConnectAttemptsFocusChange(boolean hasFocus);

        void onEtSessionTimeFocusChange(boolean hasFocus);

        void onEtPacketToutFocusChange(boolean hasFocus);

        void onSpinPriorityChnlItemClick(int position);

        void onEtNormalIntFocusChange(boolean hasFocus);

        void onEtAlarmIntFocusChange(boolean hasFocus);

        void onEtSmsCenterFocusChange(boolean hasFocus);

        void onEtCmdNumberFocusChange(boolean hasFocus);

        void onEtAnswNumberFocusChange(boolean hasFocus);

        void onBtnClearArchiveClick();

        void onBtnCloseConnectClick();
    }

}
