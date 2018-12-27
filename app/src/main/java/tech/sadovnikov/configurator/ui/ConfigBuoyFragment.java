package tech.sadovnikov.configurator.ui;


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
import android.widget.TextView;

import tech.sadovnikov.configurator.R;


public class ConfigBuoyFragment extends Fragment {
    private static final String TAG = "ConfigBuoyFragment";

    ConfigBuoyFragment.OnConfigBuoyFragmentInteractionListener listener;

    // UI
    TextView tvId;
    EditText etId;
    TextView tvVersion;
    EditText etVersion;
    Button btnRestart;
    Button btnDefaultSettings;
    LinearLayout llId;
    OnLlParameterClickListener onLlParameterClickListener;

    public ConfigBuoyFragment() {
        // Required empty public constructor
        Log.v(TAG, "onConstructor");
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_config_buoy, container, false);
        initUi(view);
        return view;
    }

    private void initUi(View view) {
        onLlParameterClickListener = new OnLlParameterClickListener(getContext());
        llId = view.findViewById(R.id.ll_id);
        llId.setOnClickListener(onLlParameterClickListener);
        tvId = view.findViewById(R.id.tv_id);
        etId = view.findViewById(R.id.et_id);
        // TODO <Сделать отслеживание закрытия клавиатуры>
        etId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                listener.onEtIdFocusChange(hasFocus);
            }
        });
        tvVersion = view.findViewById(R.id.tv_version);
        etVersion = view.findViewById(R.id.et_version);
        btnRestart = view.findViewById(R.id.btn_restart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnRestartClick();
            }
        });
        btnDefaultSettings = view.findViewById(R.id.btn_default_settings);
        btnDefaultSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnDefaultSettingsClick();
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
        Log.v(TAG, "onStart");
        if (context instanceof ConfigBuoyFragment.OnConfigBuoyFragmentInteractionListener) {
            listener = (ConfigBuoyFragment.OnConfigBuoyFragmentInteractionListener) context;
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
        listener.onConfigBuoyFragmentStart();
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
