package com.finalproject.kg.summary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//**************************************************
// Main Activity
// This is the main activity
// Kobi hay (305623969) & Gadi gomaz (305296139)
//**************************************************
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get all the control from the design
        Button btnLogIn = (Button) findViewById(R.id.btnLogIn);
        Button btnSignUp = (Button) findViewById(R.id.btnSignUp);

        // On click the log in button
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intent);
            }
        });

        // On click the Sign Up button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
