package tech.sadovnikov.configurator.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import tech.sadovnikov.configurator.R;


public class ConfigBuoyFragment extends Fragment {
    private static final String TAG = "ConfigBuoyFragment";

    ConfigBuoyFragment.OnConfigBuoyFragmentInteractionListener onConfigBuoyFragmentInteractionListener;

    // UI
    TextView tvId;
    EditText etId;
    TextView tvVersion;
    EditText etVersion;
    Button btnRestart;
    Button btnDefaultSettings;

    public ConfigBuoyFragment() {
        // Required empty public constructor
        Log.v(TAG, "onConstructor");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_config_buoy, container, false);
        initUi(view);
        return view;
    }

    private void initUi(View view) {
        tvId = view.findViewById(R.id.tv_id);
        etId = view.findViewById(R.id.et_id);
        etId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, "afterTextChanged: etId");
                onConfigBuoyFragmentInteractionListener.onEtIdAfterTextChanged();
            }
        });
        tvVersion = view.findViewById(R.id.tv_version);
        etVersion = view.findViewById(R.id.et_version);
        btnRestart = view.findViewById(R.id.btn_restart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConfigBuoyFragmentInteractionListener.onBtnRestartClick();
            }
        });
        btnDefaultSettings = view.findViewById(R.id.btn_default_settings);
        btnDefaultSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConfigBuoyFragmentInteractionListener.onBtnDefaultSettingsClick();
            }
        });
    }


    public String getEtIdText() {
        return etId.getText().toString();
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
        if (context instanceof ConfigBuoyFragment.OnConfigBuoyFragmentInteractionListener) {
            onConfigBuoyFragmentInteractionListener = (ConfigBuoyFragment.OnConfigBuoyFragmentInteractionListener) context;
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
        onConfigBuoyFragmentInteractionListener.onConfigBuoyFragmentStart();
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

        void onEtIdAfterTextChanged();

        void onBtnRestartClick();

        void onBtnDefaultSettingsClick();
    }

}
