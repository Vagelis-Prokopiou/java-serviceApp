package com.github.vaggos.serviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the Donate button.
        Button btn_donate_about = (Button) findViewById(R.id.btn_donate_about);

        // Set listener to btn_donate_about button.
        btn_donate_about.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch the About Activity.
                        Intent donateIntent = new Intent(AboutActivity.this, DonateActivity.class);
                        startActivity(donateIntent);
                    }
                }
        );
    }

}
