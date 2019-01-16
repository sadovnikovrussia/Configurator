package tech.sadovnikov.configurator.old;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class OnLlParameterClickListener implements OnClickListener {
    private static final String TAG = "OnEtLlParameterClickLis";

    Context context;

    public OnLlParameterClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        LinearLayout linearLayout = (LinearLayout) v;
        View layoutChildAt = linearLayout.getChildAt(1);
        Log.d(TAG, "onClick: " + layoutChildAt);
        if (layoutChildAt instanceof EditText) {
            final EditText editText = (EditText) layoutChildAt;
            editText.requestFocus();
            editText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager != null) {
                        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                    }
                }
            }, 0);
            editText.setSelection(editText.getText().length());
        } else if (layoutChildAt instanceof Spinner) {
            Spinner spinner = (Spinner) layoutChildAt;
            spinner.performClick();
        }
    }
}
