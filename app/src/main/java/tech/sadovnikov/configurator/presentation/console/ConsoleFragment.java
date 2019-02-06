package tech.sadovnikov.configurator.presentation.console;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.sadovnikov.configurator.R;
import tech.sadovnikov.configurator.model.entities.LogMessage;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;


public class ConsoleFragment extends MvpAppCompatFragment implements ConsoleView {
    public static final String TAG = ConsoleFragment.class.getSimpleName();

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "Console")
    ConsolePresenter presenter;

    // UI
    @BindView(R.id.btn_send_command)
    Button btnSendCommand;
    @BindView(R.id.et_command_line)
    EditText etCommandLine;
    @BindView(R.id.sw_auto_scroll)
    Switch swAutoScroll;
    @BindView(R.id.tabLayout_log_tabs)
    TabLayout tabLayoutLogTabs;
    @BindView(R.id.view_pager_log_tabs)
    ViewPager viewPagerLogTabs;

    private Listener listener;
    private SaveLogDialogFragment saveDialog;
    LogMessagesFragmentPagerAdapter pagerAdapter;
    private static final int REQUEST_WRITE_STORAGE = 1;
    private static final int REQUEST_SAVE_LOG_DIALOG = 10;


    public static Fragment newInstance() {
        Bundle args = new Bundle();
        ConsoleFragment fragment = new ConsoleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "ON_CREATE_VIEW");
        View inflate = inflater.inflate(R.layout.fragment_console, container, false);
        ButterKnife.bind(this, inflate);
        setUp();
        listener.onCreateViewConsole();
        return inflate;
    }

    private void setUp() {
        pagerAdapter =  new LogMessagesFragmentPagerAdapter(getChildFragmentManager());
        viewPagerLogTabs.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPagerLogTabs.setAdapter(pagerAdapter);
        tabLayoutLogTabs.setupWithViewPager(viewPagerLogTabs);
        btnSendCommand.setOnClickListener(view -> presenter.onSendCommand(etCommandLine.getText().toString()));
        setHasOptionsMenu(true);
    }

//    @Override
//    public void addMessageToLogScreen(LogMessage message, boolean autoScrollOn) {
//        tvLogs.append(message.convertToOriginal());
//        if (autoScrollOn) svLogs.fullScroll(ScrollView.FOCUS_DOWN);
//    }
//
//    @Override
//    public void showMainLogs(List<LogMessage> mainLogMessages, boolean autoScrollOn) {
//        Log.v(TAG, "showMainLogs: ");
//        for (LogMessage message : mainLogMessages) tvLogs.append(message.convertToOriginal());
//        if (autoScrollOn) svLogs.fullScroll(ScrollView.FOCUS_DOWN);
//    }
//
//    @Override
//    public void clearMainLogs() {
//        Log.v(TAG, "clearMainLogs: ");
//        tvLogs.setText("");
//    }

    @Override
    public void setAutoScrollState(boolean isAutoScroll) {
        Log.v(TAG, "setAutoScrollState: " + isAutoScroll);
        swAutoScroll.setChecked(isAutoScroll);
    }

    @Override
    public void showSaveLogDialog() {
        saveDialog = new SaveLogDialogFragment();
        saveDialog.setTargetFragment(this, REQUEST_SAVE_LOG_DIALOG);
        saveDialog.show(((AppCompatActivity) listener).getSupportFragmentManager(), SaveLogDialogFragment.TAG);
    }

    @Override
    public void hideSaveLogDialog() {
        if (saveDialog != null) {
            saveDialog.dismiss();
            saveDialog = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_SAVE_LOG_DIALOG:
                if (resultCode == Activity.RESULT_OK) {
                    presenter.onSaveDialogPositiveClick(data.getStringExtra("file_name"));
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    presenter.onSaveDialogNegativeClick();
                }
                break;
        }
    }

    @Override
    public void requestWritePermission() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_STORAGE) {
            if (permissions.length > 0 && permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED)
                    presenter.onPositiveWriteStorageRequestResult();
            }
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessSaveLogMessage(String fileName) {
        Toast.makeText(getContext(), "Лог-файл \"" + fileName + "\" сохранён в папку Загрузки", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorSaveLogMessage(Exception e) {
        Toast.makeText(getContext(), "Не удалось сохранить лог-файл" + "\r\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_console_options, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_save_log:
                int writePermission = checkSelfPermission(((Activity) listener).getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                presenter.onSaveLog(writePermission);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "onAttach: ");
        if (context instanceof Listener) {
            listener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ConsoleFragment.Listener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG, "onStartBaseCfgView");
        swAutoScroll.setOnCheckedChangeListener((buttonView, isChecked) -> presenter.onChangeAutoScrollClick(isChecked));
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
        Log.v(TAG, "onDetach");
    }
    // ---------------------------------------------------------------------------------------------

    public interface Listener {
        void onCreateViewConsole();
    }
}
