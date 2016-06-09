package com.finalproject.kg.summary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.finalproject.kg.summary.model.Course;
import com.finalproject.kg.summary.model.LoadPictureTask;
import com.finalproject.kg.summary.model.Model;
import com.finalproject.kg.summary.model.Student;
import com.firebase.client.FirebaseError;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

//**************************************************
// Edit Profile Activity
// This Activity get to the user option to edit
// and save him profile
// Kobi hay (305623969) & Gadi gomaz (305296139)
//**************************************************
public class EditProfileActivity extends AppCompatActivity {

    // Variable of the class
    private static final int REQUEST_TAKE_PHOTO = 15;
    String mUserId;
    EditText edName;
    EditText edPass;
    EditText edMail;
    ImageView imImage;
    InputStream in;


    // Create the menu options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Get the control from the design
        edName = (EditText)findViewById(R.id.edtxtName);
        edMail = (EditText)findViewById(R.id.edtxtEmail);
        edPass = (EditText)findViewById(R.id.edtxtPassword);
        imImage = (ImageView)findViewById(R.id.imProfileEdit);

        mUserId = (String) getIntent().getExtras().get("STUDENT_ID");
        edName.setText((String)getIntent().getExtras().get("STUDENT_NAME"));
        edPass.setText((String) getIntent().getExtras().get("STUDENT_PASS"));
        edMail.setText((String) getIntent().getExtras().get("STUDENT_MAIL"));

        new LoadPictureTask().execute(imImage, (String) getIntent().getExtras().get("STUDENT_IMG"));

        // On click the change pic button
        ((Button)findViewById(R.id.btnChangePic)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO);
            }
        });

        // On click the cancel button
        ((Button)findViewById(R.id.buttonCancel)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // On click the save button
        ((Button)findViewById(R.id.buttonSave)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Course> lstCourse = new LinkedList<Course>();

                // Create new student
                Student st = new Student(mUserId, edName.getText().toString(), edMail.getText().toString(), mUserId,lstCourse);
                try {
                    // Upload the new user picture
                    Model.instance().uploadPic(in, mUserId);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Update the student
                Model.instance().updateStudent(st, new Model.UpdateStudentListenr() {
                    @Override
                    public void done(FirebaseError err) {
                        if (err == null) {
                            Intent i = new Intent();
                            i.setData(Uri.parse(mUserId));
                            setResult(RESULT_OK, i);
                            finish();
                        }
                        else {
                            Snackbar.make(findViewById(android.R.id.content), "There was an error with updating the data", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                });
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If the req code is take a photo
        if (requestCode == REQUEST_TAKE_PHOTO) {

            // If res code is ok
            if (resultCode == RESULT_OK) {

                //File to upload to cloudinary
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                if (getResources().getConfiguration().orientation == getResources().getConfiguration().ORIENTATION_PORTRAIT)
                    imageBitmap = Global.instance().rotateImage(imageBitmap, 90);

                imImage.setImageBitmap(imageBitmap);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();
                in = new ByteArrayInputStream(bitmapdata);
            }
        }
    }
}
