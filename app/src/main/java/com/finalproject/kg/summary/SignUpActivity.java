package com.finalproject.kg.summary;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.finalproject.kg.summary.model.Course;
import com.finalproject.kg.summary.model.Model;
import com.finalproject.kg.summary.model.Student;
import com.firebase.client.AuthData;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class SignUpActivity extends ActionBarActivity {

    public static final int REQUEST_TAKE_PHOTO = 12;

    static Context context;
    Bitmap pic;
    ImageView iv;
    File photoFile;
        InputStream in;

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

        final ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);
        dialog.setMessage("Signing up and logging in...");


        btnUpload.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                Model.instance().signeup(etEmail.getText().toString(), etPassword.getText().toString(), new Model.SignupListener() {
                    @Override
                    public void success(AuthData authData) {
                        Log.d("TAG", "login OK");
                        Model.instance().login(etEmail.getText().toString(), etPassword.getText().toString(), new Model.SignupListener() {
                            @Override
                            public void success(AuthData authData) throws IOException {
                                Model.instance().uploadPic(in, authData.getUid());
                                List<Course> lstCourse = new LinkedList<Course>();
                                lstCourse.add(new Course("null"));
                                Student st = new Student(authData.getUid(), etFullName.getText().toString(),etEmail.getText().toString(),authData.getUid(),lstCourse);
                                st.password = etPassword.getText().toString();
                                Model.instance().addStudent(st, new Model.AddStudentListener() {
                                    @Override
                                    public void done(Student st) {
                                        Log.d("TAG", "add OK");
                                    }
                                });
                                dialog.dismiss();
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

        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                //File to upload to cloudinary
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                iv.setImageBitmap(imageBitmap);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();
                in = new ByteArrayInputStream(bitmapdata);



            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
                //finish();
            }
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "capturedImage" + timeStamp;
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }

    public static Context getContext(){
        return context;
    }
}
