package tech.sadovnikov.configurator.presentation.console;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.model.entities.LogMessage;
import tech.sadovnikov.configurator.presentation.main.MainPresenter;


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

    private Listener listener;


    public ConsoleFragment() {
        //Log.v(TAG, "onConstructor");
    }

    public static Fragment newInstance() {
        //Log.v(TAG, "newInstance: ");
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
        listener.onCreateConsoleView();
        return inflate;
    }

    private void setUp() {
        swAutoScroll.setOnCheckedChangeListener((buttonView, isChecked) -> presenter.onChangeAutoScrollClick(isChecked));
        btnSendCommand.setOnClickListener(view -> presenter.onSendCommand(etCommandLine.getText().toString()));
        tvLogs.setOnLongClickListener(view -> {
            presenter.onSaveLogMessages();
            return false;
        });
        setHasOptionsMenu(true);
    }

    @Override
    public void addMessageToLogScreen(LogMessage message, boolean autoScrollOn) {
        tvLogs.append(message.convertToOriginal());
        if (autoScrollOn) svLogs.fullScroll(ScrollView.FOCUS_DOWN);
    }

    @Override
    public void showMainLogs(List<LogMessage> mainLogMessages, boolean autoScrollOn) {
        Log.v(TAG, "showMainLogs: ");
        for (LogMessage message : mainLogMessages) tvLogs.append(message.convertToOriginal());
        if (autoScrollOn) svLogs.fullScroll(ScrollView.FOCUS_DOWN);
    }

    @Override
    public void clearMainLogs() {
        Log.v(TAG, "clearMainLogs: ");
        tvLogs.setText("");
    }

    @Override
    public void setAutoScrollState(boolean isAutoScroll) {
        Log.v(TAG, "setAutoScrollState: " + isAutoScroll);
        swAutoScroll.setChecked(isAutoScroll);
    }


    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //Log.i(TAG, "onCreateOptionsMenu: ");
        inflater.inflate(R.menu.menu_configuration_options, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //Log.i(TAG, "onPrepareOptionsMenu: ");
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        //Log.i(TAG, "onDestroyOptionsMenu: ");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Log.i(TAG, "onOptionsItemSelected: ");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Listener) getActivity();
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

    public interface Listener{
        void onCreateConsoleView();
    }
}
