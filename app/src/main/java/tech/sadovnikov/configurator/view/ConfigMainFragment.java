package tech.sadovnikov.configurator.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.Spinner;

import tech.sadovnikov.configurator.R;

public class ConfigMainFragment extends Fragment {
    private static final String TAG = "ConfigMainFragment";

    OnConfigMainFragmentInteractionListener onConfigMainFragmentInteractionListener;

    // UI
    Spinner spinBlinkerMode;
    Spinner spinBlinkerBrightness;


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
        spinBlinkerMode = view.findViewById(R.id.spin_blinker_mode);
        spinBlinkerMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: " + position);
                onConfigMainFragmentInteractionListener.onSpinBlinkerModeItemSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinBlinkerBrightness = view.findViewById(R.id.spin_blinker_brightness);
        spinBlinkerBrightness.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: " + position);
                onConfigMainFragmentInteractionListener.onSpinBlinkerBrightnessItemSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        Log.v(TAG, "onAttach");
        if (context instanceof ConfigMainFragment.OnConfigMainFragmentInteractionListener) {
            onConfigMainFragmentInteractionListener = (ConfigMainFragment.OnConfigMainFragmentInteractionListener) context;
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
        onConfigMainFragmentInteractionListener.onConfigMainFragmentStart();
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

    public String getSpinBlinkerModeValue() {
        return spinBlinkerMode.getSelectedItem().toString();
    }

    public String getSpinBlinkerBrightnessValue() {
        return spinBlinkerBrightness.getSelectedItem().toString();
    }

    public void setSpinBlinkerModePosition(Integer position) {
        spinBlinkerBrightness.setSelection(position);
    }


    public interface OnConfigMainFragmentInteractionListener {

        void onConfigMainFragmentStart();

        void onSpinBlinkerModeItemSelected(int position);

        void onSpinBlinkerBrightnessItemSelected(int position);
    }

}
