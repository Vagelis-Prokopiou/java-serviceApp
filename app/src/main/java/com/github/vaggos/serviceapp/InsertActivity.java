package com.github.vaggos.serviceapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {

    private static String spare_part, date_changed;
    private static int kms_changed, kms_interval, date_interval;

    // Create a database variable/instance.
    DatabaseHelper serviceDb = new DatabaseHelper(InsertActivity.this);

    // Get the widgets.
    Button btn_proceed_insert;
    EditText editText_spare_part_insert, editText_kms_changed_insert, editText_kms_interval_insert, editText_date_interval_insert;
    DatePicker datePicker_insert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the text views.
        editText_spare_part_insert = (EditText) findViewById(R.id.editText_spare_part_insert);
        editText_kms_changed_insert = (EditText) findViewById(R.id.editText_kms_changed_insert);
        editText_kms_interval_insert = (EditText) findViewById(R.id.editText_kms_interval_insert);
        editText_date_interval_insert = (EditText) findViewById(R.id.editText_date_interval_insert);

        // Get the datepicker.
        datePicker_insert = (DatePicker) findViewById(R.id.datePicker_insert);

        // Get the Proceed button.
        btn_proceed_insert = (Button) findViewById(R.id.btn_proceed_insert);

        // Call AddData.
        AddData();
    }

    public void AddData() {
        // Get the values from the fields.
        // Set listener on Proceed button.
        btn_proceed_insert.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // If a value for the spare part has been provided execute try.
                try {
                    // Get the value of the spare part text field.
                    InsertActivity.spare_part = editText_spare_part_insert.getText().toString();
                } catch (NumberFormatException e) {/* Code here if needed. */}

                // If a value for the kms_changed has been provided execute try.
                try {
                    // Get the value of the kms_changed text field.
                    InsertActivity.kms_changed = Integer.parseInt(editText_kms_changed_insert.getText().toString());
                } catch (NumberFormatException e) {/* Code here if needed. */}

                // If a value for the kms_interval has been provided execute try.
                try {
                    // Get the value of the kms_interval text field.
                    InsertActivity.kms_interval = Integer.parseInt(editText_kms_interval_insert.getText().toString());
                } catch (NumberFormatException e) {/* Code here if needed. */}

                // If a value for the date_interval has been provided execute try.
                try {
                    int day = datePicker_insert.getDayOfMonth();
                    int month = datePicker_insert.getMonth() + 1;
                    int year = datePicker_insert.getYear();
                    // Create the ISO date that will be written to the csv file.
                    InsertActivity.date_changed = String.format("%02d", year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
                } catch (NumberFormatException e) {/* Code here if needed. */}

                // If a value for the date_interval has been provided execute try.
                try {
                    // Get the value of the date_interval text field.
                    InsertActivity.date_interval = Integer.parseInt(editText_date_interval_insert.getText().toString());
                } catch (NumberFormatException e) {/* Code here if needed. */}

                // Message for testing.
//                String message = "spare part: " + spare_part + "\n" +
//                        "date_changed: " + date_changed + "\n" +
//                        "kms_changed: " + kms_changed + "\n" +
//                        "date_interval: " + date_interval + "\n" +
//                        "kms_interval: " + kms_interval;
//                Toast.makeText(InsertActivity.this, message, Toast.LENGTH_LONG).show();


                // Call the method to insert the data.
                boolean isInserted = serviceDb.insertData(spare_part, date_changed, date_interval, kms_changed, kms_interval);
                if (isInserted) {
                    Toast.makeText(InsertActivity.this, "Data successfully inserted.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(InsertActivity.this, "Data failed to be inserted.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
