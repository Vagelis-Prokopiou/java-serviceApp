// Todo: Add scrollview to available activity.
// Todo: Add scrollview to check activity.
package com.github.vaggos.serviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the buttons.
        Button btn_check = (Button) findViewById(R.id.btn_check);
        Button btn_update = (Button) findViewById(R.id.btn_update);
        Button btn_insert = (Button) findViewById(R.id.btn_insert);
        Button btn_available = (Button) findViewById(R.id.btn_available);


        // Set a listener to btn_check.
        btn_check.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Launch the Check Activity.
                Intent checkIntent = new Intent(MainActivity.this, CheckActivity.class);
                startActivity(checkIntent);
            }
        });

        // Set a listener to btn_update.
        btn_update.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Launch the Update Activity.
                Intent updateIntent = new Intent(MainActivity.this, UpdateActivity.class);
                startActivity(updateIntent);
            }
        });

        // Set a listener to btn_insert.
        btn_insert.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Launch the Update Activity.
                Intent insertIntent = new Intent(MainActivity.this, InsertActivity.class);
                startActivity(insertIntent);
            }
        });

        // Set a listener to btn_available.
        btn_available.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Launch the Update Activity.
                Intent availableIntent = new Intent(MainActivity.this, AvailableActivity.class);
                startActivity(availableIntent);


                // Create a variable to hold all the values to be displayed.
//                String message = "Results:\n";
//                for (int i = 1; i < dataList.size(); i++) {
//                    message = message + dataList.get(i)[0] + ": Last changed on " + dataList.get(i)[1] + ".\n";
//                }
            }
        });
    }
}


