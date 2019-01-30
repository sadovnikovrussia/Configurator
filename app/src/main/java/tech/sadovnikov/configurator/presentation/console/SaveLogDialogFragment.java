package tech.sadovnikov.configurator.presentation.console;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import tech.sadovnikov.configurator.R;


public class SaveLogDialogFragment extends DialogFragment {
    public static final String TAG = SaveLogDialogFragment.class.getSimpleName();

    // UI
    EditText etFileName;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_save_file, null, false);
        etFileName = view.findViewById(R.id.file_name);
        etFileName.setPaintFlags(View.INVISIBLE);
        builder.setView(view);
        builder.setTitle(R.string.saving_log)
                .setMessage("Введите имя лог-файла")
                .setPositiveButton(R.string.save_button,
                        (dialog, whichButton) -> {
                            if (getTargetFragment() != null) {
                                getTargetFragment()
                                        .onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, getActivity().getIntent().putExtra("file_name", etFileName.getText().toString()));
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
