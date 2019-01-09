package tech.sadovnikov.configurator.ui.console;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;


public class ConsoleFragment extends MvpAppCompatFragment implements ConsoleView {
    public static final String TAG = ConsoleFragment.class.getSimpleName();

    @InjectPresenter
    ConsolePresenter presenter;

    // UI
    @BindView(R.id.tv_logs)
    TextView tvLogs;
    @BindView(R.id.sv_logs)
    ScrollView svLogs;
    @BindView(R.id.btn_send_command)
    Button btnSendCommand;
    @BindView(R.id.et_command_line)
    EditText etCommandLine;
    @BindView(R.id.sw_auto_scroll)
    Switch swAutoScroll;


    public ConsoleFragment() {
        Log.v(TAG, "onConstructor");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");
        View inflate = inflater.inflate(R.layout.fragment_console, container, false);
        ButterKnife.bind(this, inflate);
        setUp();
        return inflate;
    }

    private void setUp() {
        swAutoScroll.setChecked(true);
        swAutoScroll.setOnCheckedChangeListener((buttonView, isChecked) -> presenter.onChangeAutoScrollClick());
        btnSendCommand.setOnClickListener(view -> presenter.onSendCommandClick());
        tvLogs.setOnLongClickListener(view -> {
            presenter.onLogsLongClick();
            return false;
        });
    }

    @Override
    public String getCommandLineText() {
        return String.valueOf(etCommandLine.getText());
    }

    @Override
    public void addMessageToLog(String message) {

    }

    void showLog(String logsMessages) {
        // Logs.d(TAG, "onShowLog: " + logsMessages);
        if (tvLogs != null) {
            tvLogs.setText(logsMessages);
        }
    }

    void addLogsLine(String line) {
        if (tvLogs != null) {
            tvLogs.append(line + "\r\n");
            if (swAutoScroll.isChecked()) {
//                Editable editableText = tvLogs.getEditableText();
//                tvLogs.setSelected(true);
//                Selection.setSelection(editableText, editableText.length());
                svLogs.fullScroll(ScrollView.FOCUS_DOWN);
            } else {
                tvLogs.setSelected(false);
                // tvLogs.clearFocus();
            }
        }
    }


    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "onStart");
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v(TAG, "onActivityCreated");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
    }

    public void onStart() {
        super.onStart();
        Log.v(TAG, "onStart");
    }

    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
    }

    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause");
    }

    public void onStop() {
        super.onStop();
        Log.v(TAG, "onStop");
    }

    public void onDestroyView() {
        super.onDestroyView();
        Log.v(TAG, "onDestroyView");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }

    public void onDetach() {
        super.onDetach();
        Log.v(TAG, "onDetach");
    }
    // ---------------------------------------------------------------------------------------------

}
