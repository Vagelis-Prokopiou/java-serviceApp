// Todo: Fix the main page. Make everything a unique activity.
package com.github.vaggos.serviceapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

    private static int global_kms = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Read the data.cvs file.
        InputStream in = getResources().openRawResource(R.raw.data);
        final ReadCSV csv = new ReadCSV(in);
        final List<String[]> dataList = csv.read();

        // Get the buttons.
        Button btn_check = (Button) findViewById(R.id.btn_check);
        Button btn_update = (Button) findViewById(R.id.btn_update);
        Button btn_insert = (Button) findViewById(R.id.btn_insert);
        Button btn_available = (Button) findViewById(R.id.btn_available);

        // Get the Textview.
        final TextView textView_results = (TextView) findViewById(R.id.textView_results);

        // Get the total kms input.
        final EditText editText_total_kms = (EditText) findViewById(R.id.editText_total_kms);


        // Set a listener to btn_check.
        btn_check.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Launch the Check Activity.
                // Launch the Update Activity.
                Intent checkIntent = new Intent(MainActivity.this, CheckActivity.class);
                // Prepare to send the dataList.
                // Todo: Maybe I dont have to pass this. Construct it directly in the update activity.
//                List<String[]> dataList_to_pass =dataList;
////                dataList_to_pass = dataList;
//                updateIntent.putExtra("dataList", (Parcelable) dataList_to_pass);
                startActivity(checkIntent);








//                // Prepare the message that will be displayed.
//                String message = "Results:\n";
//                // Check if the MainActivity.global_kms has been set.
//                if (MainActivity.global_kms > 0) {
//                    // Loop over your data.
//                    for (int i = 1; i < dataList.size(); i++) {
//                        // Create the local variables.
//                        String spare_part = dataList.get(i)[0];
//                        int kms_changed = Integer.parseInt(dataList.get(i)[3].toString());
//                        int kms_interval = Integer.parseInt(dataList.get(i)[4].toString());
//                        // Todo: Construct the date and check the dates.
//                        // Build the date_changed date.
//                        String dateChanged = dataList.get(i)[1].toString();
//                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                        Date date_changed = null;
//                        try {
//                            date_changed = format.parse(dateChanged);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        // Build the date_interval date.
//                        int dateInterval = Integer.parseInt(dataList.get(i)[2].toString());
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime(date_changed);
//                        calendar.add(Calendar.MONTH, dateInterval);
//                        Date date_interval = calendar.getTime();
//
//                        // Check the kms.
//                        if (MainActivity.global_kms - kms_changed >= kms_interval) {
//                            // Build the message.
//                            message += "• " + spare_part + ": Exceeded the allowed " + kms_interval + " kms between changes, for " + (MainActivity.global_kms - kms_changed) + " kms.\n";
//                        }
//
//                        // Check the dates.
//                        if (today.after(date_interval)) {
//                            // Format the date to ISO, for printing.
//                            SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
//                            String stringDate = date_format.format(date_interval);
//                            message += "• " + spare_part + ": Exceeded the allowed " + dateInterval + " months between changes. It should have been changed on " + stringDate + ".\n";
//                        }
//                    }
//                    // Print the result.
//                    textView_results.setText(message);
//                } else {
//                    // The MainActivity.global_kms has not been provided. Show alert message.
//                    alert(MainActivity.global_kms);
//                }
            }
        });

        // Set a listener to btn_update.
        btn_update.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Launch the Update Activity.
                Intent updateIntent = new Intent(MainActivity.this, UpdateActivity.class);
                // Prepare to send the dataList.
                // Todo: Maybe I dont have to pass this. Construct it directly in the update activity.
//                List<String[]> dataList_to_pass =dataList;
////                dataList_to_pass = dataList;
//                updateIntent.putExtra("dataList", (Parcelable) dataList_to_pass);
                startActivity(updateIntent);
            }
        });

        // Set a listener to btn_available.
        btn_available.setOnClickListener(new Button.OnClickListener() {
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

//    public static void alert(int global_kms) {
//        if (global_kms == 0) {
//            // Launch alert message
//            new AlertDialog.Builder(MainActivity.this)
//                    .setTitle("Vehicle kilometres")
//                    .setMessage("Please provide the vehicle's total kilometres/miles to continue.")
//                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            // Code here.
//                        }
//                    })
////                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
////                                public void onClick(DialogInterface dialog, int which) {
////                                    // do nothing
////                                }
////                            })
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .show();
//        }
//    }
}


