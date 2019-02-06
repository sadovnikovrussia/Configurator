package tech.sadovnikov.configurator.presentation.console.log_messages;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.model.data.logs.LogList;
import tech.sadovnikov.configurator.model.entities.LogMessage;


public class LogMessagesFragment extends MvpAppCompatFragment implements LogMessagesView {
    private static final String TAG = LogMessagesFragment.class.getSimpleName();

    @BindView(R.id.tv_log_screen)
    TextView tvLogs;
    @BindView(R.id.sv_log_screen)
    ScrollView svLogs;

    @InjectPresenter
    LogMessagesPresenter presenter;

    public static LogMessagesFragment newInstance(String name) {
        LogMessagesFragment fragment = new LogMessagesFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate: ");
        if (getArguments() != null) {
            presenter.onCreate(getArguments().getString("name"));
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "ON_CREATE_VIEW");
        View view = inflater.inflate(R.layout.fragment_log_messages, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void addMessageToLogScreen(LogMessage logMessage, boolean autoScrollOn) {
        tvLogs.append(logMessage.getConverted());
        if (autoScrollOn) svLogs.fullScroll(ScrollView.FOCUS_DOWN);
    }

    @Override
    public void addMessageToMainLogScreen(LogMessage logMessage, boolean autoScrollOn) {
        tvLogs.append(logMessage.convertToOriginal());
        if (autoScrollOn) svLogs.fullScroll(ScrollView.FOCUS_DOWN);
    }

    @Override
    public void showLogs(LogList logList, boolean autoScrollMode) {
        for (LogMessage logMessage : logList.getLogMessageList())
            tvLogs.append(logMessage.getConverted());
        if (autoScrollMode) svLogs.fullScroll(ScrollView.FOCUS_DOWN);
    }

    @Override
    public void showMainLogs(List<LogMessage> mainLogList, boolean autoScrollMode) {
        for (LogMessage logMessage : mainLogList)
            tvLogs.append(logMessage.convertToOriginal());
        if (autoScrollMode) svLogs.fullScroll(ScrollView.FOCUS_DOWN);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "onAttach: ");
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
        super.onDetach();
        Log.v(TAG, "onDetach: ");
    }

}
