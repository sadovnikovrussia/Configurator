package tech.sadovnikov.configurator.presentation.configuration.config_tabs.config_events;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.model.data.configuration.Configuration;
import tech.sadovnikov.configurator.model.entities.Parameter;
import tech.sadovnikov.configurator.presentation.configuration.config_tabs.base.BaseCfgFragment;
import tech.sadovnikov.configurator.utils.ParametersEntities;

import static android.view.View.OnClickListener;

public class ConfigEventsFragment extends BaseCfgFragment implements ConfigEventsView {
    public static final String TAG = ConfigEventsFragment.class.getSimpleName();

    // UI
    @BindView(R.id.btn_alarm_events)
    Button btnAlarmEvents;
    @BindView(R.id.cb_9)
    CheckBox cb9;
    @BindView(R.id.cb_10)
    CheckBox cb10;
    @BindView(R.id.cb_11)
    CheckBox cb11;
    @BindView(R.id.cb_12)
    CheckBox cb12;
    @BindView(R.id.cb_13)
    CheckBox cb13;
    @BindView(R.id.cb_14)
    CheckBox cb14;
    @BindView(R.id.cb_15)
    CheckBox cb15;
    @BindView(R.id.cb_16)
    CheckBox cb16;

    @BindViews({R.id.cb_9, R.id.cb_10, R.id.cb_11, R.id.cb_12, R.id.cb_13, R.id.cb_14, R.id.cb_15, R.id.cb_16})
    List<CheckBox> cbEvents;

    @InjectPresenter
    ConfigEventsPresenter configEventsPresenter;

    private AlarmEventDialogFragment alarmEventDialog;
    private static final int REQUEST_ALARM_EVENTS_DIALOG = 10;


    public static ConfigEventsFragment newInstance() {
        Bundle args = new Bundle();
        ConfigEventsFragment fragment = new ConfigEventsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "ON_CREATE_VIEW");
        View view = inflater.inflate(R.layout.fragment_config_events, container, false);
        ButterKnife.bind(this, view);
        super.onCreateView(inflater, container, savedInstanceState);
        setUp(view);
        return view;
    }

    @Override
    public void setUp(View view) {
        btnAlarmEvents.setOnClickListener(v -> configEventsPresenter.onCreateAlarmEventsMessage());
        OnClickListener onEventClickListener = v -> {
            StringBuilder value = new StringBuilder();
            if (cb9.isChecked()) value.append("1,");
            if (cb10.isChecked()) value.append("2,");
            if (cb11.isChecked()) value.append("3,");
            if (cb12.isChecked()) value.append("4,");
            if (cb13.isChecked()) value.append("5,");
            if (cb14.isChecked()) value.append("6,");
            if (cb15.isChecked()) value.append("7,");
            if (cb16.isChecked()) value.append("8,");
            if (value.length() != 0) value.deleteCharAt(value.length() - 1);
            presenter.onParameterChanged(ParametersEntities.EVENTS_MASK, value.toString());
        };
        for (CheckBox checkBox : cbEvents) checkBox.setOnClickListener(onEventClickListener);
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

    @Override
    public void showCreateAlarmEventsMessageView() {
        if (getFragmentManager() != null) {
            alarmEventDialog = new AlarmEventDialogFragment();
            alarmEventDialog.setTargetFragment(this, REQUEST_ALARM_EVENTS_DIALOG);
            alarmEventDialog.show(getFragmentManager(), AlarmEventDialogFragment.TAG);
        }
    }

    @Override
    public void hideAlarmEventsMessageView() {
        if (alarmEventDialog != null) {
            alarmEventDialog.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ALARM_EVENTS_DIALOG:
                if (resultCode == Activity.RESULT_OK) {
                    configEventsPresenter.onAlarmEventsDialogPositiveClick(data.getStringExtra("events"));
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    configEventsPresenter.onAlarmEventsDialogNegativeClick();
                }
                break;
        }
    }
    @Override
    public void showConfiguration(Configuration configuration) {
        Parameter eventsMask = configuration.getParameter(ParametersEntities.EVENTS_MASK);
        uncheckAllEventsMaskCb();
        if (eventsMask != null) {
            String[] positions = eventsMask.getValue().split(",");
            for (String position : positions) {
                switch (position) {
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

    }

    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        Log.v(TAG, "onDetach: ");
        super.onDetach();
    }
    // ---------------------------------------------------------------------------------------------


}
