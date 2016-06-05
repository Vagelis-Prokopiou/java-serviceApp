package com.github.vaggos.serviceapp;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CheckActivity extends AppCompatActivity {

    // Create the global_kms variable;
    private static int global_kms = 0;

    // Create a database variable.
    DatabaseHelper serviceDb = new DatabaseHelper(CheckActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Build the String Array with the db data.
        Cursor cursor = serviceDb.getAllData();
        final String[][] dataList = new String[cursor.getCount()][5];
        int j = 0;
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String spare_part = cursor.getString(1);
            String date_changed = cursor.getString(2);
            String date_interval = cursor.getString(3);
            String kms_changed = cursor.getString(4);
            String kms_interval = cursor.getString(5);
            // array[j][0] = id;
            dataList[j][0] = spare_part;
            dataList[j][1] = date_changed;
            dataList[j][2] = date_interval;
            dataList[j][3] = kms_changed;
            dataList[j][4] = kms_interval;
            j++;
        }

        // Create the global_date.
        final Date today = new Date();

        // Get the buttons.
        Button btn_proceed = (Button) findViewById(R.id.btn_proceed);

        // Get the Textview.
        final TextView textView_results = (TextView) findViewById(R.id.textView_results);

        // Get the total kms input.
        final EditText editText_total_kms = (EditText) findViewById(R.id.editText_total_kms);

        // Set a listener to btn_proceed.
        btn_proceed.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // If a value has been provided execute try.
                        try {
                            // Get the value of the text field.
                            int val = Integer.parseInt(editText_total_kms.getText().toString());
                            // Set the global_kms variable.
                            CheckActivity.global_kms = val;
                        } catch (NumberFormatException e) {/* Code here if necessary. */}

                        // Run the check.
                        // Prepare the message that will be displayed.
                        String message = "Results:\n\n";
                        // Check if the MainActivity.global_kms has been set.
                        if (CheckActivity.global_kms > 0) {
                            // Loop over your data.
                            for (int i = 1; i < dataList.length; i++) {
                                // Create the local variables.
                                String spare_part = dataList[i][0];
                                int kms_changed = Integer.parseInt(dataList[i][3].toString());
                                int kms_interval = Integer.parseInt(dataList[i][4].toString());
                                // Build the date_changed date.
                                String dateChanged = dataList[i][1].toString();
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Date date_changed = null;
                                try {
                                    date_changed = format.parse(dateChanged);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                // Build the date_interval date.
                                int dateInterval = Integer.parseInt(dataList[i][2].toString());
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date_changed);
                                calendar.add(Calendar.MONTH, dateInterval);
                                Date date_interval = calendar.getTime();

                                // Check the kms.
                                if (CheckActivity.global_kms - kms_changed >= kms_interval) {
                                    // Build the message.
                                    message += spare_part + " kms status:\nExceeded the allowed " + kms_interval + " kms between changes, for " + (CheckActivity.global_kms - kms_changed) + " kms.\n\n";
                                }

                                // Check the dates.
                                if (today.after(date_interval)) {
                                    // Format the date to ISO, for printing.
                                    SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
                                    String stringDate = date_format.format(date_interval);
                                    message += spare_part + " months status:\nExceeded the allowed " + dateInterval + " months between changes. It should have been changed on " + stringDate + ".\n\n";
                                }
                            }
                            // Print the result.
                            textView_results.setText(message);
                        } else {
                            // The CheckActivity.global_kms has not been provided. Show alert message.
                            new AlertDialog.Builder(CheckActivity.this)
                                    .setTitle("Vehicle kilometres")
                                    .setMessage("Please provide the vehicle's total kilometres/miles to continue.")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {/*Code here*/}
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    }
                }
        );
    }
}
