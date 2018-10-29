package tech.sadovnikov.configurator.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import tech.sadovnikov.configurator.R;

public class ConfigNavigationFragment extends Fragment {
    private static final String TAG = "ConfigNavigationFragmen";

    OnConfigNavigationFragmentInteractionListener listener;

    // UI
    EditText etLongitude;
    EditText etLatitude;
    Button btnRcvColdStart;

    public ConfigNavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_config_navigation, container, false);
        initUi(view);
        return view;
    }

    private void initUi(View view) {
        etLongitude = view.findViewById(R.id.et_longitude);
        etLatitude = view.findViewById(R.id.et_latitude);
        btnRcvColdStart = view.findViewById(R.id.btn_rcv_coldstart);
        btnRcvColdStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnRcvColdStartClick();
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
        Log.v(TAG, "onStart");
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

    interface OnConfigNavigationFragmentInteractionListener{
        void onConfigNavigationFragmentStart();

        void onBtnRcvColdStartClick();
    }
}
