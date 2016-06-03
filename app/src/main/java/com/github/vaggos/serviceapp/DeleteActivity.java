package com.github.vaggos.serviceapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DeleteActivity extends AppCompatActivity {

    // Create a database variable.
    DatabaseHelper serviceDb = new DatabaseHelper(DeleteActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the text views.
        final TextView textView_results_delete = (TextView) findViewById(R.id.textView_results_delete);

        // Build the String Array with the db data.
        Cursor cursor = serviceDb.getAllData();
        final String[][] dataArray = new String[cursor.getCount()][5];
        int j = 0;
        while (cursor.moveToNext()) {
//            String id = cursor.getString(0);
            String spare_part = cursor.getString(1);
//            String date_changed = cursor.getString(2);
//            String date_interval = cursor.getString(3);
//            String kms_changed = cursor.getString(4);
//            String kms_interval = cursor.getString(5);
            // array[j][0] = id;
            dataArray[j][0] = spare_part;
//            dataArray[j][1] = date_changed;
//            dataArray[j][2] = date_interval;
//            dataArray[j][3] = kms_changed;
//            dataArray[j][4] = kms_interval;
            j++;
        }

        // Prepare the message for the spare part selection.
        String message = "";
        for (int i = 0; i < dataArray.length; i++) {
            String name = dataArray[i][0];
            message += "For " + name + ", enter " + (i + 1) + ".\n";
        }

        // Show the message.
        textView_results_delete.setText(message);






    }

}
