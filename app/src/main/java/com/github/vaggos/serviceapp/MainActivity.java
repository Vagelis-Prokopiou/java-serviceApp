package com.github.vaggos.serviceapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the global_date.
        final Date today = new Date();

        // Create the global_kms variable;
        final int[] global_kms = {0};

        // Read the data.cvs file.
        InputStream in = getResources().openRawResource(R.raw.data);
        final ReadCSV csv = new ReadCSV(in);
        final List<String[]> dataList = csv.read();

        // Get the buttons.
        Button btn_proceed = (Button) findViewById(R.id.btn_proceed);
        Button btn_check = (Button) findViewById(R.id.btn_check);
        Button btn_update = (Button) findViewById(R.id.btn_update);
        Button btn_insert = (Button) findViewById(R.id.btn_insert);
        Button btn_available = (Button) findViewById(R.id.btn_available);
        Button btn_done = (Button) findViewById(R.id.btn_done);

        // Get the Textview.
        final TextView textview = (TextView) findViewById(R.id.textView);
        final TextView textView_results = (TextView) findViewById(R.id.textView_results);

        // Get the total kms input.
        final EditText editText_total_kms = (EditText) findViewById(R.id.editText_total_kms);

        // Set a listener to btn_proceed.
        btn_proceed.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // If a value has been provided execute try.
                try {
                    // Get the value of the text field.
                    int val = Integer.parseInt(editText_total_kms.getText().toString());
                    // Set the global_kms variable.
                    global_kms[0] = val;
                    // Update the text view.
                    textview.setText(
                            "You can proceed." +
                                    "\nThe total kms are " + String.valueOf(global_kms[0]) + " kms."
                    );
                } catch (NumberFormatException e) {
                    // If no value has been provided, show this message.
                    textview.setText(
                            "You must provide a value.\nPlease, try again."
                    );
                }
            }
        });

        // Set a listener to btn_check.
        btn_check.setOnClickListener(new Button.OnClickListener() {
            // See: http://www.dummies.com/how-to/content/use-array-lists-in-java.html
            public void onClick(View v) {
                // Prepare the message that will be displayed.
                String message = "Results\n";
                // Check if the global_kms has been set.
                if (global_kms[0] > 0) {
                    // Loop over your data.
                    for (int i = 1; i < dataList.size(); i++) {
                        // Create the local variables.
                        String spare_part = dataList.get(i)[0];
                        int kms_changed = Integer.parseInt(dataList.get(i)[3].toString());
                        int kms_interval = Integer.parseInt(dataList.get(i)[4].toString());
                        // Todo: Construct the date and check the dates.
                        // Build the date_changed date.
                        // See: http://stackoverflow.com/questions/8573250/android-how-can-i-convert-string-to-date (#134)
                        String dateChanged = dataList.get(i)[1].toString();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date date_changed = null;
                        try {
                            date_changed = format.parse(dateChanged);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        // Build the date_interval date.
                        // See: http://stackoverflow.com/questions/1311143/java-util-date-deleting-three-months-from-a-date (#38)
                        int dateInterval = Integer.parseInt(dataList.get(i)[2].toString());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date_changed);
                        calendar.add(Calendar.MONTH, dateInterval);
                        Date date_interval = calendar.getTime();

                        // Check the kms.
                        if (global_kms[0] - kms_changed >= kms_interval) {
                            // Build the message.
                            message += "• " + spare_part + ": Exceeded the allowed " + kms_interval + " kms between changes, for " + (global_kms[0] - kms_changed) + " kms.\n";
                        }

                        // Check the dates.
                        if (today.after(date_interval)) {
                            // Format the date to ISO, for printing.
                            // See: http://java67.blogspot.gr/2013/01/how-to-format-date-in-java-simpledateformat-example.html
                            SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
                            String stringDate = date_format.format(date_interval);
                            message += "• " + spare_part + ": Exceeded the allowed " + dateInterval + " months between changes. It should have been changed on " + stringDate + ".\n";
                        }
                    }
                    // Print the result.
                    textView_results.setText(message);
                } else {
                    // The global_kms has not been provided.
                    textView_results.setText("Please, provide the total kms of the vehicle.");
                }
            }
        });

        // Set a listener to btn_available.
        btn_available.setOnClickListener(new Button.OnClickListener() {
            // See: http://www.dummies.com/how-to/content/use-array-lists-in-java.html
            public void onClick(View v) {
                // Create a variable to hold all the values to be displayed.
                String message = "Results:\n";
                for (int i = 1; i < dataList.size(); i++) {
                    message = message + dataList.get(i)[0] + ": Last changed on " + dataList.get(i)[1] + ".\n";
                }
                textView_results.setText(message);
            }
        });
    }
}


