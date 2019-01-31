package tech.sadovnikov.configurator.presentation.configuration.config_tabs.config_events;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;


public class AlarmEventDialogFragment extends DialogFragment {
    public static final String TAG = AlarmEventDialogFragment.class.getSimpleName();

    // UI
    @BindView(R.id.cb_1)
    CheckBox cb1;
    @BindView(R.id.cb_2)
    CheckBox cb2;
    @BindView(R.id.cb_3)
    CheckBox cb3;
    @BindView(R.id.cb_4)
    CheckBox cb4;
    @BindView(R.id.cb_5)
    CheckBox cb5;
    @BindView(R.id.cb_6)
    CheckBox cb6;
    @BindView(R.id.cb_7)
    CheckBox cb7;
    @BindView(R.id.cb_8)
    CheckBox cb8;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_alarm_events, null, false);
        ButterKnife.bind(this, view);
        builder.setView(view);
        builder.setTitle(R.string.alarm_event_dialog_title)
                .setMessage(R.string.alarm_event_dialog_message)
                .setPositiveButton(R.string.send_btn,
                        (dialog, whichButton) -> {
                            if (getTargetFragment() != null) {
                                StringBuilder value = new StringBuilder();
                                if (cb1.isChecked()) value.append("1,");
                                if (cb2.isChecked()) value.append("2,");
                                if (cb3.isChecked()) value.append("3,");
                                if (cb4.isChecked()) value.append("4,");
                                if (cb5.isChecked()) value.append("5,");
                                if (cb6.isChecked()) value.append("6,");
                                if (cb7.isChecked()) value.append("7,");
                                if (cb8.isChecked()) value.append("8,");
                                if (value.length() != 0) {
                                    value.deleteCharAt(value.length() - 1);
                                    getTargetFragment()
                                            .onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, getActivity().getIntent().putExtra("events", value.toString()));
                                }
                            }
                        })
                .setNegativeButton(R.string.cancel_button,
                        (dialog, whichButton) -> {
                            if (getTargetFragment() != null) {
                                getTargetFragment()
                                        .onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, getActivity().getIntent());
                            }
                        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
