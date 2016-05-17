package com.example.nofit.summary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.nofit.summary.model.Model;
import com.example.nofit.summary.model.ModelFirebase;
import com.example.nofit.summary.model.Student;
import com.firebase.client.AuthData;

import java.io.IOException;
import java.util.List;

public class SignUpActivity extends ActionBarActivity {

    static Context context;
    Bitmap pic;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        context = getApplicationContext();
        Button btnSignUp = (Button) findViewById(R.id.btnSignUp);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etFullName = (EditText) findViewById(R.id.etFullName);
        iv = (ImageView) findViewById(R.id.ivSignUpImage);
        Button btnUpload = (Button) findViewById(R.id.btnUploadPicture);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance().signeup(etEmail.getText().toString(), etPassword.getText().toString(), new Model.SignupListener() {
                    @Override
                    public void success(AuthData authData) {
                        Log.d("TAG", "login OK");
                        Model.instance().login(etEmail.getText().toString(), etPassword.getText().toString(), new Model.SignupListener() {
                            @Override
                            public void success(AuthData authData) throws IOException {

                                Student st = new Student(authData.getUid(), etFullName.getText().toString(),etEmail.getText().toString(),"image");
                                st.password = etPassword.getText().toString();
                                Model.instance().addStudent(st, new Model.AddStudentListener() {
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        pic = (Bitmap) data.getExtras().get("data");
        iv.setImageBitmap(pic);

    }

    public static Context getContext(){
        return context;
    }
}
