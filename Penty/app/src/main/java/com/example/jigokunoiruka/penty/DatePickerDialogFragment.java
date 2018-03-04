package com.example.jigokunoiruka.penty;

/**
 * Created by JigokuNoIruka on 2018/01/09.
 */

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // 最小値
        GregorianCalendar minDate = new GregorianCalendar();
        minDate.set(year, month, dayOfMonth);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme,this, year, month, dayOfMonth);

        DatePicker datePicker = datePickerDialog.getDatePicker();
        if (datePicker != null) {
            datePicker.setMinDate(minDate.getTimeInMillis());
        }

        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //日付が選択されたときの処理
        MainActivity mainActivity=(MainActivity)getActivity();
        mainActivity.setDate(year,month+1,day);
    }

}