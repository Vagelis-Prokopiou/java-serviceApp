package com.github.vaggos.serviceapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the data passed from the main activity.
        Bundle extras = getIntent().getExtras();
        String dataList = null;
        if (extras != null) {
            dataList = extras.getString("dataList");
        }

        // Get the text field.
        TextView textView_test = (TextView) findViewById(R.id.textView_test);
        textView_test.setText(dataList);
    }

}
