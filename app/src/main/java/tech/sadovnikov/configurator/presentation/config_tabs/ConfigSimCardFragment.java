package tech.sadovnikov.configurator.presentation.config_tabs;


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

import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.presentation.OnLlParameterClickListener;


public class ConfigSimCardFragment extends Fragment {
    private static final String TAG = "ConfigSimCardFragment";

    OnConfigSimCardFragmentInteractionListener listener;

    // UI
    EditText etApn;
    EditText etLogin;
    EditText etPassword;
    Button btnDefaultApn;
    Button btnDefaultLogin;
    Button btnDefaultPassword;
    Button btnClearApn;
    Button btnClearLogin;
    Button btnClearPassword;
    EditText etPin;
    Button btnEnterPin;
    Button btnClearPin;
    EditText etSimAttempts;
    EditText etDelivTimeOut;
    LinearLayout llApn;
    LinearLayout llLogin;
    LinearLayout llPassword;
    LinearLayout llPin;
    LinearLayout llSimAttempts;
    LinearLayout llDelivTimeOut;
    OnLlParameterClickListener onLlParameterClickListener;

    public ConfigSimCardFragment() {
        // Required empty public constructor
    }

    private void initUi(View view) {
        onLlParameterClickListener = new OnLlParameterClickListener(getContext());

        llApn = view.findViewById(R.id.ll_apn);
        llLogin = view.findViewById(R.id.ll_login);
        llPassword = view.findViewById(R.id.ll_password);
        llPin = view.findViewById(R.id.ll_pin);
        llSimAttempts = view.findViewById(R.id.ll_sim_attempts);
        llDelivTimeOut = view.findViewById(R.id.ll_deliv_timeout);

        llApn.setOnClickListener(onLlParameterClickListener);
        llLogin.setOnClickListener(onLlParameterClickListener);
        llPassword.setOnClickListener(onLlParameterClickListener);
        llPin.setOnClickListener(onLlParameterClickListener);
        llSimAttempts.setOnClickListener(onLlParameterClickListener);
        llDelivTimeOut.setOnClickListener(onLlParameterClickListener);


        etApn = view.findViewById(R.id.et_apn);
        etApn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtApnFocusChange(hasFocus);
            }
        });
        etLogin = view.findViewById(R.id.et_login);
        etLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtLoginFocusChange(hasFocus);
            }
        });
        etPassword = view.findViewById(R.id.et_password);
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtPasswordFocusChange(hasFocus);
            }
        });
        btnDefaultApn = view.findViewById(R.id.btn_default_apn);
        btnDefaultApn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnDefaultApnClick();
            }
        });
        btnDefaultLogin = view.findViewById(R.id.btn_default_login);
        btnDefaultLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnDefaultLoginClick();
            }
        });
        btnDefaultPassword = view.findViewById(R.id.btn_default_password);
        btnDefaultPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnDefaultPasswordClick();
            }
        });
        btnClearApn = view.findViewById(R.id.btn_clear_apn);
        btnClearApn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnClearApnClick();
            }
        });
        btnClearLogin = view.findViewById(R.id.btn_clear_login);
        btnClearLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnClearLoginClick();
            }
        });
        btnClearPassword = view.findViewById(R.id.btn_clear_password);
        btnClearPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnClearPasswordClick();
            }
        });
        etPin = view.findViewById(R.id.et_pin);
        btnEnterPin = view.findViewById(R.id.btn_enter_pin);
        btnEnterPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnEnterPinClick();
            }
        });
        btnClearPin = view.findViewById(R.id.btn_clear_pin);
        btnClearPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnClearPinClick();
            }
        });
        etSimAttempts = view.findViewById(R.id.et_sim_attempts);
        etSimAttempts.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtSimAttemptsFocusChange(hasFocus);
            }
        });
        etDelivTimeOut = view.findViewById(R.id.et_deliv_timeout);
        etDelivTimeOut.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtDelivTimeoutFocusChange(hasFocus);
            }
        });


    }



    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_config_sim_card, container, false);
        initUi(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnConfigSimCardFragmentInteractionListener) {
            listener = (OnConfigSimCardFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnConfigSimCardFragmentInteractionListener");
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
        Log.v(TAG, "onStart");
        listener.onConfigSimCardFragmentStart();
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

    public interface OnConfigSimCardFragmentInteractionListener {

        void onConfigSimCardFragmentStart();

        void onEtApnFocusChange(boolean hasFocus);

        void onEtLoginFocusChange(boolean hasFocus);

        void onEtPasswordFocusChange(boolean hasFocus);


        void onBtnDefaultApnClick();

        void onBtnDefaultLoginClick();

        void onBtnDefaultPasswordClick();

        void onBtnClearApnClick();

        void onBtnClearLoginClick();

        void onBtnClearPasswordClick();

        void onBtnEnterPinClick();

        void onBtnClearPinClick();

        void onEtSimAttemptsFocusChange(boolean hasFocus);

        void onEtDelivTimeoutFocusChange(boolean hasFocus);
    }

}
