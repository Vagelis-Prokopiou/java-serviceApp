package com.github.vaggos.serviceapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

public class AvailableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Read the data.cvs file.
        InputStream in = getResources().openRawResource(R.raw.data);
        final ReadCSV csv = new ReadCSV(in);
        final List<String[]> dataList = csv.read();

        // Get the Textview.
        final TextView textView_available = (TextView) findViewById(R.id.textView_available);

        // Create a variable to hold all the values to be displayed.
        String message = "The current available data entries are:\n\n";
        for (int i = 1; i < dataList.size(); i++) {
            message = message + dataList.get(i)[0] + ": Last changed on " + dataList.get(i)[1] + ".\n";
        }
        textView_available.setText(message);
    }

}
