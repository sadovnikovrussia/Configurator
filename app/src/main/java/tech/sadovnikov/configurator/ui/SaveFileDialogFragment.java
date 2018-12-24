package tech.sadovnikov.configurator.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
    private static final String TAG = "SaveFileDialogFragment";

    OnSaveFileDialogFragmentInteractionListener listener;

    // UI
    EditText etFileName;

    public SaveFileDialogFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_save_file, null, false);
        etFileName = view.findViewById(R.id.file_name);
        etFileName.setPaintFlags(View.INVISIBLE);
        builder.setView(view);
        builder.setMessage(R.string.saving_configuration)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onSaveFileDialogPositiveClick(etFileName.getText().toString());

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        SaveFileDialogFragment.this.getDialog().cancel();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "onAttach");
        if (context instanceof ConfigMainFragment.OnConfigMainFragmentInteractionListener) {
            listener = (SaveFileDialogFragment.OnSaveFileDialogFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSaveFileDialogFragmentInteractionListener");
        }
    }

    interface OnSaveFileDialogFragmentInteractionListener {

        void onSaveFileDialogPositiveClick(String s);
    }
}
