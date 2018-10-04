package tech.sadovnikov.configurator.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Selection;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import tech.sadovnikov.configurator.R;


public class ConsoleFragment extends Fragment {

    private static final String TAG = "ConsoleFragment";

    // UI
    TextView tvLog;
    Button btnSendCommand;
    EditText etCommandLine;
    Switch swAutoScroll;

    OnConsoleFragmentInteractionListener onConsoleFragmentInteractionListener;
    private Editable editableText;

    public ConsoleFragment() {
        // Required empty public constructor
        Log.v(TAG, "onConstructor");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_console, container, false);
        initUi(inflate);
        return inflate;
    }

    private void initUi(View inflate) {
        swAutoScroll = inflate.findViewById(R.id.sw_auto_scroll);
        tvLog = inflate.findViewById(R.id.tv_log);
        tvLog.setMovementMethod(new ScrollingMovementMethod());
        etCommandLine = inflate.findViewById(R.id.et_command_line);
        btnSendCommand = inflate.findViewById(R.id.btn_send_command);
        btnSendCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String command = etCommandLine.getText().toString();
                if (command.length() != 0) {
                    onConsoleFragmentInteractionListener.onBtnSendCommandClick(command);
                }
            }
        });
        BottomNavigationView navigation = getActivity().findViewById(R.id.navigation);
        navigation.getMenu().getItem(2).setChecked(true);
    }

    void showLog(String line) {
        if (tvLog != null) {
            tvLog.append(line + "\r\n");
            if (swAutoScroll.isChecked()){
                editableText = tvLog.getEditableText();
                Selection.setSelection(editableText, editableText.length());
            } else {
                tvLog.clearFocus();
            }
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    // ---------------------------------------------------------------------------------------------
    // States
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnConsoleFragmentInteractionListener) {
            onConsoleFragmentInteractionListener = (OnConsoleFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onConsoleFragmentInteractionListener");
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v(TAG, "onActivityCreated");
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
        onConsoleFragmentInteractionListener = null;
    }
    // ---------------------------------------------------------------------------------------------

    interface OnConsoleFragmentInteractionListener {

        void onBtnSendCommandClick(String line);

    }

}
