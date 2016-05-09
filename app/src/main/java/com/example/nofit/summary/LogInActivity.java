package com.example.nofit.summary;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
     
import com.example.nofit.summary.model.Model;
import com.example.nofit.summary.model.ModelFirebase;
import com.example.nofit.summary.model.Student;

import java.util.List;

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

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Model.instance().login(etEmail.getText().toString(), etPassword.getText().toString(), new Model.SignupListener() {
                    @Override
                    public void success() {
                        Log.d("TAG", "login OK");
                        Intent intent = new Intent(getApplicationContext(), MainNotesActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void fail(String msg) {
                        Log.d("TAG", "login FAIL " + msg);
                    }
                });

                Model.instance().getStudent(new Model.GetStudentListener() {
                    @Override
                    public void done(Student st) {
                        Log.d("TAG", "Get Student");
                    }
                });

            }
        });
    }

    public static Context getContext(){
        return context;
    }
}
