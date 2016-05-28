package com.finalproject.kg.summary;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.finalproject.kg.summary.model.Model;
import com.firebase.client.AuthData;

//**************************************************
// Log In Activity
// This is the log in activity
// Kobi hay (305623969) & Gadi gomaz (305296139)
//**************************************************
public class LogInActivity extends ActionBarActivity {

    // Variable of the class
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        context = getApplicationContext();

        // Get all the control from the design
        final Button btnLogIn = (Button) findViewById(R.id.btnLogIn);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final ProgressBar pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
        final ProgressDialog dialog = new ProgressDialog(LogInActivity.this);
        dialog.setMessage("Logging in...");

        // On click log in buttton
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Show the loading dialog
                dialog.show();

                // Call to the function that do log in
                Model.instance().login(etEmail.getText().toString(), etPassword.getText().toString(), new Model.SignupListener() {
                    @Override
                    public void success(AuthData authData) {
                        // Display new activity MainNotesActivity
                        Intent intent = new Intent(getApplicationContext(), MainNotesActivity.class);
                        startActivity(intent);

                        // Close the dialog
                        dialog.dismiss();
                    }

                    @Override
                    public void fail(String msg) {
                        // Close the dialog
                        dialog.dismiss();

                        // Write the error
                        Toast toast = Toast.makeText(context, "Login Failed: " + msg, Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            }
        });
    }

    public static Context getContext(){
        return context;
    }
}
