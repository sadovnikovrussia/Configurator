package tech.sadovnikov.configurator.presentation.base;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class OnParameterViewGroupClickListener implements OnClickListener {

    private InputMethodManager inputMethodManager;

    public OnParameterViewGroupClickListener(Context context) {
        inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onClick(View v) {
        ViewGroup viewGroup = (ViewGroup) v;
        View child = viewGroup.getChildAt(1);
        if (child instanceof EditText) {
            EditText editText = (EditText) child;
            editText.requestFocus();
            editText.setSelection(editText.getText().length());
            inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        } else if (child instanceof Spinner) {
            Spinner spinner = (Spinner) child;
            spinner.performClick();
        }
    }
}
