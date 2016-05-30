package com.github.vaggos.serviceapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {

    private static int selected_spare_part = -1;
    private static int kms_changed = -1;
    private static int kms_interval = -1;


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
        TextView textView_results_update = (TextView) findViewById(R.id.textView_results_update);
        final TextView editText_spare_part = (TextView) findViewById(R.id.editText_spare_part);
        TextView editText_kms_update = (TextView) findViewById(R.id.editText_kms_update);
        TextView editText_kms_interval = (TextView) findViewById(R.id.editText_kms_interval);
        TextView editText_date_changed = (TextView) findViewById(R.id.editText_date_changed);
        TextView editText_date_interval = (TextView) findViewById(R.id.editText_date_interval);

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
        // Set a listener to btn_proceed.
        btn_proceed_update.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // If a value for the spare part has been provided execute try.
                try {
                    // Get the value of the text field.
                    int spare_part = Integer.parseInt(editText_spare_part.getText().toString());
                    // Check if it is a valid spare part.
                    if (spare_part > 0 && spare_part <= dataList.size()) {
                        // Set the selected_spare_part variable.
                        UpdateActivity.selected_spare_part = spare_part;
                    }
                } catch (NumberFormatException e) {/* Code here if needed. */}

                // Start the checks.
                // First check is the selected_spare_part has been set.
                if (UpdateActivity.selected_spare_part != -1) {
                    // Try to get the kms_changed if it has been set.
                    // Try to get the kms_interval if it has been set.
                    // Try to get the date_changed if it has been set.
                    // Try to get the date_interval if it has been set.
                } else {
                    // The selected_spare_part has not been set.
                    // Show the popup warning message.
                    alert(UpdateActivity.selected_spare_part);
                }

            }
        });


    }

    public void alert(int selected_spare_part) {
        if (selected_spare_part == -1) {
            // Launch alert message
            new AlertDialog.Builder(UpdateActivity.this)
                    .setTitle("Selected spare part")
                    .setMessage("Please select a valid spare part to continue.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Code here.
                        }
                    })
//                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // do nothing
//                                }
//                            })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

}
