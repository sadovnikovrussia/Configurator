package tech.sadovnikov.configurator.presentation.configuration.config_tabs.config_sim;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

import static tech.sadovnikov.configurator.utils.ParametersEntities.APN;
import static tech.sadovnikov.configurator.utils.ParametersEntities.DELIV_TIMEOUT;
import static tech.sadovnikov.configurator.utils.ParametersEntities.LOGIN;
import static tech.sadovnikov.configurator.utils.ParametersEntities.PASSWORD;
import static tech.sadovnikov.configurator.utils.ParametersEntities.PIN;
import static tech.sadovnikov.configurator.utils.ParametersEntities.SIM_ATTEMPTS;


public class ConfigSimFragment extends BaseCfgFragment implements ConfigSimView {
    public static final String TAG = ConfigSimFragment.class.getSimpleName();

    // UI
    @BindView(R.id.et_apn)
    EditText etApn;
    @BindView(R.id.et_login)
    EditText etLogin;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_default_apn)
    Button btnDefaultApn;
    @BindView(R.id.btn_default_login)
    Button btnDefaultLogin;
    @BindView(R.id.btn_default_password)
    Button btnDefaultPassword;
    @BindView(R.id.btn_clear_apn)
    Button btnClearApn;
    @BindView(R.id.btn_clear_login)
    Button btnClearLogin;
    @BindView(R.id.btn_clear_password)
    Button btnClearPassword;
    @BindView(R.id.et_pin)
    EditText etPin;
    @BindView(R.id.btn_enter_pin)
    Button btnEnterPin;
    @BindView(R.id.btn_clear_pin)
    Button btnClearPin;
    @BindView(R.id.et_sim_attempts)
    EditText etSimAttempts;
    @BindView(R.id.et_deliv_timeout)
    EditText etDelivTimeOut;

    @BindViews({R.id.ll_apn,
            R.id.ll_login,
            R.id.ll_password,
            R.id.ll_pin,
            R.id.ll_sim_attempts,
            R.id.ll_deliv_timeout})
    List<ViewGroup> parameterViews;


    @InjectPresenter
    ConfigSimPresenter configSimPresenter;


    public static ConfigSimFragment newInstance() {
        Bundle args = new Bundle();
        ConfigSimFragment fragment = new ConfigSimFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "ON_CREATE_VIEW");
        View view = inflater.inflate(R.layout.fragment_config_sim_card, container, false);
        ButterKnife.bind(this, view);
        super.onCreateView(inflater, container, savedInstanceState);
        setUp(view);
        return view;
    }

    @Override
    public void setUp(View view) {
        View.OnFocusChangeListener onEtParameterChangedListener = (v, hasFocus) -> {
            if (!hasFocus)
                switch (v.getId()) {
                    case R.id.et_apn:
                        presenter.onParameterChanged(APN, etApn.getText().toString());
                        break;
                    case R.id.et_login:
                        presenter.onParameterChanged(LOGIN, etLogin.getText().toString());
                        break;
                    case R.id.et_password:
                        presenter.onParameterChanged(PASSWORD, etPassword.getText().toString());
                        break;
                    case R.id.et_pin:
                        presenter.onParameterChanged(PIN, etPin.getText().toString());
                        break;
                    case R.id.et_sim_attempts:
                        presenter.onParameterChanged(SIM_ATTEMPTS, etSimAttempts.getText().toString());
                        break;
                    case R.id.et_deliv_timeout:
                        presenter.onParameterChanged(DELIV_TIMEOUT, etDelivTimeOut.getText().toString());
                        break;
                }
        };
        etApn.setOnFocusChangeListener(onEtParameterChangedListener);
        etLogin.setOnFocusChangeListener(onEtParameterChangedListener);
        etPassword.setOnFocusChangeListener(onEtParameterChangedListener);
        etPin.setOnFocusChangeListener(onEtParameterChangedListener);
        etSimAttempts.setOnFocusChangeListener(onEtParameterChangedListener);
        etDelivTimeOut.setOnFocusChangeListener(onEtParameterChangedListener);

        btnDefaultApn.setOnClickListener(v -> configSimPresenter.onSetApnDefaultAction());
        btnDefaultLogin.setOnClickListener(v -> configSimPresenter.onSetLoginDefaultAction());
        btnDefaultPassword.setOnClickListener(v -> configSimPresenter.onSetPasswordDefaultAction());
        btnClearApn.setOnClickListener(v -> configSimPresenter.onClearApnAction());
        btnClearLogin.setOnClickListener(v -> configSimPresenter.onClearLoginAction());
        btnClearPassword.setOnClickListener(v -> configSimPresenter.onClearPasswordAction());
        btnEnterPin.setOnClickListener(v -> configSimPresenter.onEnterPinAction(etPin.getText().toString()));
        btnClearPin.setOnClickListener(v -> configSimPresenter.onClearPinAction(etPin.getText().toString()));

        OnParameterViewGroupClickListener onParameterViewGroupClickListener = new OnParameterViewGroupClickListener(Objects.requireNonNull(getContext()));
        for (ViewGroup vg : parameterViews)
            vg.setOnClickListener(onParameterViewGroupClickListener);
    }

    @Override
    public void showConfiguration(Configuration configuration) {
        Parameter apn = configuration.getParameter(APN);
        Parameter login = configuration.getParameter(LOGIN);
        Parameter password = configuration.getParameter(PASSWORD);
        Parameter pin = configuration.getParameter(PIN);
        Parameter simAttempts = configuration.getParameter(SIM_ATTEMPTS);
        Parameter delivTimeout = configuration.getParameter(DELIV_TIMEOUT);
        if (apn != null) {
            String value = apn.getValue().replaceAll("\"", "");
            if (value.equals("''")) etApn.setText("");
            else etApn.setText(value);
        }
        if (login != null) {
            String value = login.getValue().replaceAll("\"", "");
            if (value.equals("''")) etLogin.setText("");
            else etLogin.setText(value);
        }
        if (password != null) {
            String value = password.getValue().replaceAll("\"", "");
            if (value.equals("''")) etPassword.setText("");
            else etPassword.setText(value);
        }
        if (pin != null) etPin.setText(pin.getValue());
        if (simAttempts != null) etSimAttempts.setText(simAttempts.getValue());
        if (delivTimeout != null) etDelivTimeOut.setText(delivTimeout.getValue());
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
