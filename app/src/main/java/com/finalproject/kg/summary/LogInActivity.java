package com.finalproject.kg.summary;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.finalproject.kg.summary.model.Model;
import com.finalproject.kg.summary.model.Student;
import com.firebase.client.AuthData;

public class LogInActivity extends ActionBarActivity {

    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        context = getApplicationContext();
        Button btnLogIn = (Button) findViewById(R.id.btnLogIn);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final ProgressBar pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
        final ProgressDialog dialog = new ProgressDialog(LogInActivity.this);
        dialog.setMessage("Logging in...");

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
                Model.instance().login(etEmail.getText().toString(), etPassword.getText().toString(), new Model.SignupListener() {
                    @Override
                    public void success(AuthData authData) {
                        Log.d("TAG", "login OK");

                        Intent intent = new Intent(getApplicationContext(), MainNotesActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }

                    @Override
                    public void fail(String msg) {
                        Log.d("TAG", "login FAIL " + msg);
                        dialog.dismiss();
                        Toast toast = Toast.makeText(context, "Login Failed: " + msg, Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
/*
                Model.instance().getStudent(new Model.GetStudentListener() {
                    @Override
                    public void done(Student st) {
                        Log.d("TAG", "Get Student: ");
                    }
                });
*/
            }
        });
    }

    public static Context getContext(){
        return context;
    }
}
