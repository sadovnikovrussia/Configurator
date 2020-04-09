package tech.sadovnikov.configurator.presentation.configuration.config_tabs.config_server;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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

import static android.view.View.OnFocusChangeListener;

public class ConfigServerFragment extends BaseCfgFragment implements ConfigServerView {
    public static final String TAG = ConfigServerFragment.class.getSimpleName();

    // UI
    @BindView(R.id.et_server)
    EditText etServer;
    @BindView(R.id.et_connect_attempts)
    EditText etConnectAttempts;
    @BindView(R.id.et_session_time)
    EditText etSessionTime;
    @BindView(R.id.et_packet_tout)
    EditText etPacketTout;
    @BindView(R.id.spin_priority_chnl)
    Spinner spinPriorityChnl;
    @BindView(R.id.et_normal_int)
    EditText etNormalInt;
    @BindView(R.id.et_alarm_int)
    EditText etAlarmInt;
    @BindView(R.id.et_sms_center)
    EditText etSmsCenter;
    @BindView(R.id.et_cmd_number)
    EditText etCmdNumber;
    @BindView(R.id.et_answ_number)
    EditText etAnswNumber;
    @BindView(R.id.et_packets)
    EditText etPackets;
    @BindView(R.id.et_packets_percents)
    EditText etPacketsPercents;
    @BindView(R.id.btn_clear_archive)
    Button btnClearArchive;
    @BindView(R.id.btn_close_connect)
    Button btnCloseConnect;

    @BindViews({R.id.ll_server,
            R.id.ll_connect_attempts,
            R.id.ll_session_time,
            R.id.ll_packet_tout,
            R.id.ll_normal_int,
            R.id.ll_alarm_int,
            R.id.ll_sms_center,
            R.id.ll_cmd_number,
            R.id.ll_answ_number,
            R.id.ll_priority_chnl})
    List<ViewGroup> parameterViews;

    @InjectPresenter
    ConfigServerPresenter configServerPresenter;

    AdapterView.OnItemSelectedListener onSpinParameterListener;


    public static ConfigServerFragment newInstance() {
        Bundle args = new Bundle();
        ConfigServerFragment fragment = new ConfigServerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "ON_CREATE_VIEW");
        View view = inflater.inflate(R.layout.fragment_config_server, container, false);
        ButterKnife.bind(this, view);
        super.onCreateView(inflater, container, savedInstanceState);
        setUp(view);
        return view;
    }

    @Override
    public void setUp(View view) {
        OnFocusChangeListener onEtParameterChangedListener = (v, hasFocus) -> {
            if (!hasFocus)
                switch (v.getId()) {
                    case R.id.et_server:
                        presenter.onParameterChanged(ParametersEntities.SERVER, etServer.getText().toString());
                        break;
                    case R.id.et_connect_attempts:
                        presenter.onParameterChanged(ParametersEntities.CONNECT_ATTEMPTS, etConnectAttempts.getText().toString());
                        break;
                    case R.id.et_session_time:
                        presenter.onParameterChanged(ParametersEntities.SESSION_TIME, etSessionTime.getText().toString());
                        break;
                    case R.id.et_packet_tout:
                        presenter.onParameterChanged(ParametersEntities.PACKET_TOUT, etPacketTout.getText().toString());
                        break;
                    case R.id.et_normal_int:
                        presenter.onParameterChanged(ParametersEntities.NORMAL_INT, etNormalInt.getText().toString());
                        break;
                    case R.id.et_alarm_int:
                        presenter.onParameterChanged(ParametersEntities.ALARM_INT, etAlarmInt.getText().toString());
                        break;
                    case R.id.et_sms_center:
                        presenter.onParameterChanged(ParametersEntities.SMS_CENTER, etSmsCenter.getText().toString());
                        break;
                    case R.id.et_cmd_number:
                        presenter.onParameterChanged(ParametersEntities.CMD_NUMBER, etCmdNumber.getText().toString());
                        break;
                    case R.id.et_answ_number:
                        presenter.onParameterChanged(ParametersEntities.ANSW_NUMBER, etAnswNumber.getText().toString());
                        break;
                }
        };
        onSpinParameterListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    presenter.onParameterChanged(ParametersEntities.PRIORITY_CHNL, String.valueOf(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        etServer.setOnFocusChangeListener(onEtParameterChangedListener);
        etConnectAttempts.setOnFocusChangeListener(onEtParameterChangedListener);
        etSessionTime.setOnFocusChangeListener(onEtParameterChangedListener);
        etPacketTout.setOnFocusChangeListener(onEtParameterChangedListener);
        etNormalInt.setOnFocusChangeListener(onEtParameterChangedListener);
        etAlarmInt.setOnFocusChangeListener(onEtParameterChangedListener);
        etSmsCenter.setOnFocusChangeListener(onEtParameterChangedListener);
        etCmdNumber.setOnFocusChangeListener(onEtParameterChangedListener);
        etAnswNumber.setOnFocusChangeListener(onEtParameterChangedListener);
        etPackets.setOnFocusChangeListener(onEtParameterChangedListener);

        spinPriorityChnl.setOnItemSelectedListener(onSpinParameterListener);

        btnClearArchive.setOnClickListener(v -> configServerPresenter.onClearArchiveAction());
        btnCloseConnect.setOnClickListener(v -> configServerPresenter.onCloseConnectAction());

        OnParameterViewGroupClickListener onParameterViewGroupClickListener = new OnParameterViewGroupClickListener(Objects.requireNonNull(getContext()));
        for (ViewGroup vg : parameterViews)
            vg.setOnClickListener(onParameterViewGroupClickListener);
    }

    @Override
    public void showConfiguration(Configuration configuration) {
        Parameter server = configuration.getParameter(ParametersEntities.SERVER);
        Parameter connectAttempts = configuration.getParameter(ParametersEntities.CONNECT_ATTEMPTS);
        Parameter sessionTime = configuration.getParameter(ParametersEntities.SESSION_TIME);
        Parameter packetTout = configuration.getParameter(ParametersEntities.PACKET_TOUT);
        Parameter priorityChnl = configuration.getParameter(ParametersEntities.PRIORITY_CHNL);
        Parameter normalInt = configuration.getParameter(ParametersEntities.NORMAL_INT);
        Parameter alarmInt = configuration.getParameter(ParametersEntities.ALARM_INT);
        Parameter smsCenter = configuration.getParameter(ParametersEntities.SMS_CENTER);
        Parameter cmdNumber = configuration.getParameter(ParametersEntities.CMD_NUMBER);
        Parameter answNumber = configuration.getParameter(ParametersEntities.ANSW_NUMBER);
        Parameter packets = configuration.getParameter(ParametersEntities.PACKETS);
        if (priorityChnl != null) {
            spinPriorityChnl.setOnItemSelectedListener(null);
            spinPriorityChnl.setSelection(Integer.valueOf(priorityChnl.getValue()) + 1);
            spinPriorityChnl.setOnItemSelectedListener(spinParameterListener);
        }
        if (server != null) etServer.setText(server.getValue());
        if (connectAttempts != null) etConnectAttempts.setText(connectAttempts.getValue());
        if (sessionTime != null) etSessionTime.setText(sessionTime.getValue());
        if (packetTout != null) etPacketTout.setText(packetTout.getValue());
        if (normalInt != null) etNormalInt.setText(normalInt.getValue());
        if (alarmInt != null) etAlarmInt.setText(alarmInt.getValue());
        if (smsCenter != null) etSmsCenter.setText(smsCenter.getValue());
        if (cmdNumber != null) etCmdNumber.setText(cmdNumber.getValue());
        if (answNumber != null) etAnswNumber.setText(answNumber.getValue());
        if (packets != null) {
            String[] value = packets.getValue().split(",");
            etPackets.setText(value[0]);
            etPacketsPercents.setText(value[1]);
        }
    }


    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG, "onStart");
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
        Log.v(TAG, "onDetach");
    }
    // ---------------------------------------------------------------------------------------------


}
