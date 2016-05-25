package com.github.vaggos.serviceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    // Create the global variables.
    private static int global_kms;
    private static boolean proceed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    MainActivity.global_kms = val;
                    // Set the global proceed variable.
                    MainActivity.proceed = true;
                    // Update the text view.
                    textview.setText(
                            "You can proceed." +
                                    "\nThe total kms are " + String.valueOf(MainActivity.global_kms) + " kms."
                    );
                } catch (NumberFormatException e) {
                    // If no value has been provided, show this message.
                    MainActivity.proceed = false;
                    textview.setText(
                            "You must provide a value.\nPlease, try again."
                    );
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
        }

    );
}
}


