package com.example.nofit.summary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nofit.summary.model.Model;
import com.example.nofit.summary.model.ModelFirebase;
import com.example.nofit.summary.model.Student;
import com.firebase.client.AuthData;

import java.util.List;

public class SignUpActivity extends ActionBarActivity {

    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        context = getApplicationContext();
        Button btnSignUp = (Button) findViewById(R.id.btnSignUp);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etFullName = (EditText) findViewById(R.id.etFullName);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance().signeup(etEmail.getText().toString(), etPassword.getText().toString(), new Model.SignupListener() {
                    @Override
                    public void success(AuthData authData) {
                        Log.d("TAG", "login OK");
                        Model.instance().login(etEmail.getText().toString(), etPassword.getText().toString(), new Model.SignupListener() {
                            @Override
                            public void success(AuthData authData) {
                                Student st = new Student(authData.getUid(), etFullName.getText().toString(),etEmail.getText().toString(),"image");
                                st.password = etPassword.getText().toString();
                                Model.instance().add(st, new Model.AddStudentListener() {
                                    @Override
                                    public void done(Student st) {
                                        Log.d("TAG", "add OK");
                                    }
                                });

                                Intent intent = new Intent(getApplicationContext(), MainNotesActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void fail(String msg) {

                            }
                        });
                    }

                    @Override
                    public void fail(String msg) {
                        Log.d("TAG", "login FAIL " + msg);
                    }
                });



//                Student st = new Student(Model.instance().getUserId(),etFullName.getText().toString(),etEmail.getText().toString(),"image");
//                st.password = etPassword.getText().toString();
//                Model.instance().add(st, new Model.AddStudentListener() {
//                    @Override
//                    public void done(Student st) {
//                        Log.d("TAG", "add OK");
//                    }
//                });

            }
        });
    }

    public static Context getContext(){
        return context;
    }
}
