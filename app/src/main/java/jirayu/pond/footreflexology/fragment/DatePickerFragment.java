package jirayu.pond.footreflexology.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

/**
 * Created by lp700 on 4/2/2560.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    }

}
