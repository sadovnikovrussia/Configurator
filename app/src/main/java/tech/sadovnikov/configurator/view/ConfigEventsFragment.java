package tech.sadovnikov.configurator.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import tech.sadovnikov.configurator.R;

public class ConfigEventsFragment extends Fragment {
    private static final String TAG = "ConfigEventsFragment";

    private OnConfigEventsFragmentInteractionListener listener;

    // UI
    CheckBox cb1;
    CheckBox cb2;
    CheckBox cb3;
    CheckBox cb4;
    CheckBox cb5;
    CheckBox cb6;
    CheckBox cb7;
    CheckBox cb8;
    Button btnAlarmEvents;
    CheckBox cb9;
    CheckBox cb10;
    CheckBox cb11;
    CheckBox cb12;
    CheckBox cb13;
    CheckBox cb14;
    CheckBox cb15;
    CheckBox cb16;

    public ConfigEventsFragment() {
        // Required empty public constructor
        Log.v(TAG, "onConstructor");
    }

    private void initUi(View view) {
        cb1 = view.findViewById(R.id.cb_1);
        cb2 = view.findViewById(R.id.cb_2);
        cb3 = view.findViewById(R.id.cb_3);
        cb4 = view.findViewById(R.id.cb_4);
        cb5 = view.findViewById(R.id.cb_5);
        cb6 = view.findViewById(R.id.cb_6);
        cb7 = view.findViewById(R.id.cb_7);
        cb8 = view.findViewById(R.id.cb_8);
        btnAlarmEvents = view.findViewById(R.id.btn_alarm_events);
        btnAlarmEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnAlarmEventsClick();
            }
        });
        cb9 = view.findViewById(R.id.cb_9);
        cb10 = view.findViewById(R.id.cb_10);
        cb11 = view.findViewById(R.id.cb_11);
        cb12 = view.findViewById(R.id.cb_12);
        cb13 = view.findViewById(R.id.cb_13);
        cb14 = view.findViewById(R.id.cb_14);
        cb15 = view.findViewById(R.id.cb_15);
        cb16 = view.findViewById(R.id.cb_16);
        cb9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEventsMaskCbClick();
            }
        });
        cb10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEventsMaskCbClick();
            }
        });
        cb11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEventsMaskCbClick();
            }
        });
        cb12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEventsMaskCbClick();
            }
        });
        cb13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEventsMaskCbClick();
            }
        });
        cb14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEventsMaskCbClick();
            }
        });
        cb15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEventsMaskCbClick();
            }
        });
        cb16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEventsMaskCbClick();
            }
        });
    }

    public void uncheckAllEventsMaskCb() {
        cb9.setChecked(false);
        cb10.setChecked(false);
        cb11.setChecked(false);
        cb12.setChecked(false);
        cb13.setChecked(false);
        cb14.setChecked(false);
        cb15.setChecked(false);
        cb16.setChecked(false);
    }

    public void setCheckedEventsMaskCb(String value) {
        uncheckAllEventsMaskCb();
        String[] positions = value.split(",");
        for (String position : positions) {
            switch (position){
                case "1":
                    cb9.setChecked(true);
                    break;
                case "2":
                    cb10.setChecked(true);
                    break;
                case "3":
                    cb11.setChecked(true);
                    break;
                case "4":
                    cb12.setChecked(true);
                    break;
                case "5":
                    cb13.setChecked(true);
                    break;
                case "6":
                    cb14.setChecked(true);
                    break;
                case "7":
                    cb15.setChecked(true);
                    break;
                case "8":
                    cb16.setChecked(true);
                    break;
            }
        }
    }

    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_config_events, container, false);
        initUi(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnConfigEventsFragmentInteractionListener) {
            listener = (OnConfigEventsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnConfigEventsFragmentInteractionListener");
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
        listener.onConfigEventsFragmentStart();
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

    interface OnConfigEventsFragmentInteractionListener {

        void onConfigEventsFragmentStart();

        void onBtnAlarmEventsClick();

        void onEventsMaskCbClick();
    }
}
