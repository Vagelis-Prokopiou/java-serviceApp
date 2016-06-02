package com.github.vaggos.serviceapp;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;


public class UpdateActivity extends AppCompatActivity {

    private static int spare_part_id = -1;
    private static int kms_changed = -1;
    private static int kms_interval = -1;
    private static String date_changed = null;
    private static int date_interval = -1;

    // To be used with the data retrieved from the sql query.
    private static String original_id = null;
    private static String original_spare_part = null;
    private static String original_date_changed = null;
    private static String original_date_interval = null;
    private static String original_kms_changed = null;
    private static String original_kms_interval = null;

    // Create a database variable.
    DatabaseHelper serviceDb = new DatabaseHelper(UpdateActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Build the String Array with the db data.
        Cursor cursor = serviceDb.getAllData();
        final String[][] dataArray = new String[cursor.getCount()][5];
        int j = 0;
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String spare_part = cursor.getString(1);
            String date_changed = cursor.getString(2);
            String date_interval = cursor.getString(3);
            String kms_changed = cursor.getString(4);
            String kms_interval = cursor.getString(5);
            // array[j][0] = id;
            dataArray[j][0] = spare_part;
            dataArray[j][1] = date_changed;
            dataArray[j][2] = date_interval;
            dataArray[j][3] = kms_changed;
            dataArray[j][4] = kms_interval;
            j++;
        }

        // Get the text views.
        final TextView textView_results_update = (TextView) findViewById(R.id.textView_results_update);
        final TextView editText_spare_part = (TextView) findViewById(R.id.editText_spare_part);
        final TextView editText_kms_changed = (TextView) findViewById(R.id.editText_kms_changed);
        final TextView editText_kms_interval = (TextView) findViewById(R.id.editText_kms_interval);
        final TextView editText_date_interval = (TextView) findViewById(R.id.editText_date_interval);

        // Get the datepicker.
        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);

        // Get the Proceed button.
        Button btn_proceed_update = (Button) findViewById(R.id.btn_proceed_update);

        // Prepare the message for the spare part selection.
        String message = "";
        for (int i = 0; i < dataArray.length; i++) {
            String name = dataArray[i][0];
            message += "For " + name + ", enter " + (i + 1) + ".\n";
        }

        // Show the message.
        textView_results_update.setText(message);

        // Set listener on Proceed button.
        btn_proceed_update.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // If a value for the spare part has been provided execute try.
                try {
                    // Get the value of the spare part text field.
                    int spare_part = Integer.parseInt(editText_spare_part.getText().toString());
                    // Check if it is a valid spare part.
                    if (spare_part > 0 && spare_part <= dataArray.length) {
                        // Set the spare_part_id variable.
                        UpdateActivity.spare_part_id = spare_part;
                    }
                } catch (NumberFormatException e) {/* Code here if needed. */}

                // Start the checks.
                // First check is the spare_part_id has been set.
                if (UpdateActivity.spare_part_id > 0) {
                    // Try to get the kms_changed if it has been set.
                    // If a value for the kms_changed has been provided execute try.
                    try {
                        // Get the value of the text field.
                        int kms_update = Integer.parseInt(editText_kms_changed.getText().toString());
                        // Check if it is a valid spare part.
                        if (kms_update > 0) {
                            // Set the spare_part_id variable.
                            UpdateActivity.kms_changed = kms_update;
                        }
                    } catch (NumberFormatException e) {/* Code here if needed. */}

                    // Try to get the kms_interval if it has been set.
                    try {
                        // Get the value of the text field.
                        int kms_interval = Integer.parseInt(editText_kms_interval.getText().toString());
                        // Check if it is a valid spare part.
                        if (kms_interval > 0) {
                            // Set the spare_part_id variable.
                            UpdateActivity.kms_interval = kms_interval;
                        }
                    } catch (NumberFormatException e) {/* Code here if needed. */}

                    // Try to get the date_changed if it has been set.
                    try {
                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth() + 1;
                        int year = datePicker.getYear();
                        // Create the ISO date that will be written to the csv file.
                        UpdateActivity.date_changed = String.format("%02d", year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
                    } catch (NumberFormatException e) {/* Code here if needed. */}

                    // Try to get the date_interval if it has been set.
                    try {
                        // Get the value of the text field.
                        int date_interval = Integer.parseInt(editText_date_interval.getText().toString());
                        // Check if it is a valid spare part.
                        if (date_interval > 0) {
                            // Set the spare_part_id variable.
                            UpdateActivity.date_interval = date_interval;
                        }
                    } catch (NumberFormatException e) {/* Code here if needed. */}

                    // Get the original values.
                    SQLiteDatabase db = serviceDb.getReadableDatabase();
                    Cursor c = db.rawQuery("SELECT * FROM service_table WHERE ID = ?", new String[]{String.valueOf(spare_part_id)});
                    while (c.moveToNext()) {
                        original_id = c.getString(0);
                        original_spare_part = c.getString(1);
                        original_date_changed = c.getString(2);
                        original_date_interval = c.getString(3);
                        original_kms_changed = c.getString(4);
                        original_kms_interval = c.getString(5);
                    }

                    // If the kms_changed has not been provided, alert for more data.
                    if (kms_changed == -1) {
                        // Launch alert message.
                        alert("Missing data", "Please, provide the \"kms changed\" too.");
                    } else if (kms_changed < 1000) {
                        alert("Are you sure?", "The provided kms are very few. Is this a new vehicle?");
                    } else if (kms_changed >= 1000 && kms_interval == -1 && date_interval == -1) {
                        boolean isUpdated = serviceDb.updateData(original_id, original_spare_part, date_changed, Integer.parseInt(original_date_interval), kms_changed, Integer.parseInt(original_kms_interval));
                        if (isUpdated) {
                            Toast.makeText(UpdateActivity.this, "Data successfully updated.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(UpdateActivity.this, "Data failed to update.", Toast.LENGTH_LONG).show();
                        }
                    } else if (kms_changed >= 1000 && date_interval != -1 && kms_interval == -1) {
                        boolean isUpdated = serviceDb.updateData(original_id, original_spare_part, date_changed, date_interval, kms_changed, Integer.parseInt(original_kms_interval));
                        if (isUpdated) {
                            Toast.makeText(UpdateActivity.this, "Data successfully updated.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(UpdateActivity.this, "Data failed to update.", Toast.LENGTH_LONG).show();
                        }
                    } else if (kms_changed >= 1000 && date_interval == -1 && kms_interval != -1) {
                        boolean isUpdated = serviceDb.updateData(original_id, original_spare_part, date_changed, Integer.parseInt(original_date_interval), kms_changed, kms_interval);
                        if (isUpdated) {
                            Toast.makeText(UpdateActivity.this, "Data successfully updated.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(UpdateActivity.this, "Data failed to update.", Toast.LENGTH_LONG).show();
                        }
                    } else if (kms_changed >= 1000 && date_interval != -1 && kms_interval != -1) {
                        // All data has been provided.
                        boolean isUpdated = serviceDb.updateData(original_id, original_spare_part, date_changed, date_interval, kms_changed, kms_interval);
                        if (isUpdated) {
                            Toast.makeText(UpdateActivity.this, "Data successfully updated.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(UpdateActivity.this, "Data failed to update.", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    // The spare_part_id has not been set.
                    // Show the popup warning message.
                    alert("Selected spare part", "Please select a valid spare part to continue.");
                }
            }
        });
    }

    public void alert(String title, String message) {
        new AlertDialog.Builder(UpdateActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {/*  Code here if needed. */}
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
