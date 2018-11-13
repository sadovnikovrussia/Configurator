package tech.sadovnikov.configurator.view;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

public class OnEtLlParameterClickListener implements OnClickListener {
    Context context;

    OnEtLlParameterClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        LinearLayout linearLayout = (LinearLayout) v;
        final EditText editText = (EditText) linearLayout.getChildAt(1);
        editText.requestFocus();
        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        }, 300);
        editText.setSelection(editText.getText().length());
    }
}
