package tech.sadovnikov.configurator.ui.console;

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
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.entities.Message;


public class ConsoleFragment extends MvpAppCompatFragment implements ConsoleView {
    public static final String TAG = ConsoleFragment.class.getSimpleName();

    @InjectPresenter
    ConsolePresenter presenter;

    // UI
    @BindView(R.id.tv_log_screen)
    TextView tvLogs;
    @BindView(R.id.sv_log_screen)
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

    public static Fragment newInstance() {
        Log.v(TAG, "newInstance: ");
        Bundle args = new Bundle();
        ConsoleFragment fragment = new ConsoleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");
        View inflate = inflater.inflate(R.layout.fragment_console, container, false);
        ButterKnife.bind(this, inflate);
        setUp();
        presenter.onCreateView();
        return inflate;
    }

    private void setUp() {
        swAutoScroll.setChecked(true);
        swAutoScroll.setOnCheckedChangeListener((buttonView, isChecked) -> presenter.onChangeAutoScrollClick());
        btnSendCommand.setOnClickListener(view -> presenter.onSendCommandClick(etCommandLine.getText().toString()));
        tvLogs.setOnLongClickListener(view -> {
            presenter.onLogsLongClick();
            return false;
        });
    }

    @Override
    public void addMessageToLogScreen(Message message) {
        tvLogs.append(message.convertToOriginal() + "\r\n");
    }

    @Override
    public void showMainLogs(List<Message> mainLogMessages) {
        for (Message message : mainLogMessages) tvLogs.append(message.convertToOriginal());
        if (isAutoScrollOn()) svLogs.fullScroll(ScrollView.FOCUS_DOWN);
    }

    @Override
    public void clearMainLogs() {
        tvLogs.setText("");
    }

    boolean isAutoScrollOn() {
        return swAutoScroll.isChecked();
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
