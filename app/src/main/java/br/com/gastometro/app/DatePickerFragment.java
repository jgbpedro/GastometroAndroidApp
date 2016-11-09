package br.com.gastometro.app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by 15251395 on 18/10/2016.
 */


    public class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

    private EditText txt_data;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(),R.style.DialogTheme , this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

            String dataEscolhida = String.format("%02d/%02d/%d", day, month+1, year);

            getTxt_data().setText(dataEscolhida);
        }

    public EditText getTxt_data() {
        return txt_data;
    }

    public DatePickerFragment setTxt_data(EditText txt_data) {
        this.txt_data = txt_data;

        return this;
    }
}

