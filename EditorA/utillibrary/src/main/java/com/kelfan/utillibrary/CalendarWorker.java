package com.kelfan.utillibrary;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class CalendarWorker {
    public static void setDate(final EditText editText, Context context, final String format, final String prefix) {
        final Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                c.set(year, monthOfYear, dayOfMonth);
//                String text = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                String text = editText.getText().toString();
                String time = prefix + TimeWorker.formatDate(format, c.getTime()) + " ";
                if (text.contains("@date_")) {
                    text = AtSign.set(text, "date").getRemain() + time;
                } else {
                    text += time;
                }
                editText.setText(text);
                editText.setSelection(text.length());
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public static void setTime(final EditText editText, Context context) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                String text = hourOfDay + ":" + minutes + " ";
                editText.setText(text);
                editText.setSelection(text.length());
            }
        }, hour, minutes, false);
        timePickerDialog.show();
    }
}
