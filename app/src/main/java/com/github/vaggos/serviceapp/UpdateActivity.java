package com.github.vaggos.serviceapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {

    private static int selected_spare_part = -1;
    private static int kms_changed = -1;
    private static int kms_interval = -1;
    private static int date_interval = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Read the data.cvs file.
        InputStream in = getResources().openRawResource(R.raw.data);
        final ReadCSV csv = new ReadCSV(in);
        final List<String[]> dataList = csv.read();

        // Get the text views.
        final TextView textView_results_update = (TextView) findViewById(R.id.textView_results_update);
        final TextView editText_spare_part = (TextView) findViewById(R.id.editText_spare_part);
        final TextView editText_kms_changed = (TextView) findViewById(R.id.editText_kms_changed);
        final TextView editText_kms_interval = (TextView) findViewById(R.id.editText_kms_interval);
        TextView editText_date_changed = (TextView) findViewById(R.id.editText_date_changed);
        final TextView editText_date_interval = (TextView) findViewById(R.id.editText_date_interval);

        // Get the Proceed button.
        Button btn_proceed_update = (Button) findViewById(R.id.btn_proceed_update);

        // Prepare the message for the spare part selection.
        String message = "";
        for (int i = 1; i < dataList.size(); i++) {
            String name = dataList.get(i)[0];
            message += "For " + name + " enter " + i + ".\n";
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
                    if (spare_part > 0 && spare_part <= dataList.size()) {
                        // Set the selected_spare_part variable.
                        UpdateActivity.selected_spare_part = spare_part;
                    }
                } catch (NumberFormatException e) {/* Code here if needed. */}

                // Start the checks.
                // First check is the selected_spare_part has been set.
                if (UpdateActivity.selected_spare_part > 0) {
                    // Try to get the kms_changed if it has been set.
                    // If a value for the kms_changed has been provided execute try.
                    try {
                        // Get the value of the text field.
                        int kms_update = Integer.parseInt(editText_kms_changed.getText().toString());
                        // Check if it is a valid spare part.
                        if (kms_update > 0) {
                            // Set the selected_spare_part variable.
                            UpdateActivity.kms_changed = kms_update;
                        }
                    } catch (NumberFormatException e) {/* Code here if needed. */}

                    // Try to get the kms_interval if it has been set.
                    try {
                        // Get the value of the text field.
                        int kms_interval = Integer.parseInt(editText_kms_interval.getText().toString());
                        // Check if it is a valid spare part.
                        if (kms_interval > 0) {
                            // Set the selected_spare_part variable.
                            UpdateActivity.kms_interval = kms_interval;
                        }
                    } catch (NumberFormatException e) {/* Code here if needed. */}

                    // Try to get the date_changed if it has been set.
                    // Code here.

                    // Try to get the date_interval if it has been set.
                    try {
                        // Get the value of the text field.
                        int date_interval = Integer.parseInt(editText_date_interval.getText().toString());
                        // Check if it is a valid spare part.
                        if (date_interval > 0) {
                            // Set the selected_spare_part variable.
                            UpdateActivity.date_interval = date_interval;
                        }
                    } catch (NumberFormatException e) {/* Code here if needed. */}

                    // If only the spare part has been provided, alert for more data.
                    if (kms_changed == -1 && kms_interval == -1 && date_interval == -1) {
                        // Launch alert message.
                        new AlertDialog.Builder(UpdateActivity.this)
                                .setTitle("Missing data")
                                .setMessage("Only the spare part has been selected. Please. provide more data to continue.")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {/*  Code here if needed. */}
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } else {
                        // Todo: Write the csv.
                    }
                } else {
                    // The selected_spare_part has not been set.
                    // Show the popup warning message.
                    alert();
                }
            }
        });
    }

    public void alert() {
        // Launch alert message.
        new AlertDialog.Builder(UpdateActivity.this)
                .setTitle("Selected spare part")
                .setMessage("Please select a valid spare part to continue.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {/*  Code here if needed. */}
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
