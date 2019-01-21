package tech.sadovnikov.configurator.presentation.configuration;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import tech.sadovnikov.configurator.R;


public class SaveFileDialogFragment extends DialogFragment {
    public static final String TAG = SaveFileDialogFragment.class.getSimpleName();

    Listener listener;

    // UI
    EditText etFileName;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder((Context) listener);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_save_file, null, false);
        etFileName = view.findViewById(R.id.file_name);
        etFileName.setPaintFlags(View.INVISIBLE);
        builder.setView(view);
        builder.setMessage(R.string.saving_configuration)
                .setPositiveButton(R.string.save, (dialog, id) -> listener.onSaveDialogPositiveClick(etFileName.getText().toString()))
                .setNegativeButton(R.string.cancel, (dialog, id) -> listener.onSaveDialogNegativeClick());
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "onStartBaseCfgView");
        if (context instanceof Listener) {
            listener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SaveFileDialogFragment.Listener");
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    public interface Listener {
        void onSaveDialogPositiveClick(String s);

        void onSaveDialogNegativeClick();
    }
}
