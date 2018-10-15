package tech.sadovnikov.configurator.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import tech.sadovnikov.configurator.R;


public class ConfigBuoyFragment extends Fragment {
    private static final String TAG = "ConfigBuoyFragment";

    ConfigBuoyFragment.OnConfigBuoyFragmentInteractionListener onConfigurationFragmentInteractionListener;

    // UI
    TextView tvId;
    EditText etId;
    TextView tvVersion;
    EditText etVersion;

    public ConfigBuoyFragment() {
        // Required empty public constructor
        Log.v(TAG, "onConstructor");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_config_buoy, container, false);
        initUi(inflate);
        return inflate;
    }

    private void initUi(View inflate) {
        tvId = inflate.findViewById(R.id.tv_id);
        etId = inflate.findViewById(R.id.et_id);
        tvVersion = inflate.findViewById(R.id.tv_version);
        etVersion = inflate.findViewById(R.id.et_version);
    }


    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "onAttach");
        if (context instanceof ConfigBuoyFragment.OnConfigBuoyFragmentInteractionListener) {
            onConfigurationFragmentInteractionListener = (ConfigBuoyFragment.OnConfigBuoyFragmentInteractionListener) context;
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
        onConfigurationFragmentInteractionListener.onConfigBuoyFragmentStart();
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
    }

}
