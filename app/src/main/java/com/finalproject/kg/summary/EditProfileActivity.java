package com.finalproject.kg.summary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.finalproject.kg.summary.model.LoadPictureTask;
import com.finalproject.kg.summary.model.Model;
import com.finalproject.kg.summary.model.Student;
import com.firebase.client.FirebaseError;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class EditProfileActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 15;
    String mUserId;
    EditText edName;
    EditText edPass;
    EditText edMail;
    ImageView imImage;
    InputStream in;

//    private class GetPicTask extends AsyncTask<String, Void, Bitmap> {
//        protected Bitmap doInBackground(String... picName) {
//            Bitmap bmp = null;
//            try {
//                bmp = Model.instance().getPic(picName[0]);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return bmp;
//        }
//
//        protected void onProgressUpdate(Integer... progress) {
////            setProgressPercent(progress[0]);
//        }
//
//        protected void onPostExecute(Bitmap result) {
//            imImage.setImageBitmap(result);
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
//        inflater.inflate(R.menu., menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_edit_profile);

//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);

        edName = (EditText)findViewById(R.id.edtxtName);
        edMail = (EditText)findViewById(R.id.edtxtEmail);
        edPass = (EditText)findViewById(R.id.edtxtPassword);
        imImage = (ImageView)findViewById(R.id.imProfileEdit);

        mUserId = (String) getIntent().getExtras().get("STUDENT_ID");
        edName.setText((String)getIntent().getExtras().get("STUDENT_NAME"));
        edPass.setText((String) getIntent().getExtras().get("STUDENT_PASS"));
        edMail.setText((String) getIntent().getExtras().get("STUDENT_MAIL"));

//            imImage.setImageBitmap(Model.instance().getPic ((String) getIntent().getExtras().get("STUDENT_IMG")));
            new LoadPictureTask().execute(imImage, (String) getIntent().getExtras().get("STUDENT_IMG"));


        ((Button)findViewById(R.id.btnChangePic)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO);
            }
        });

        ((Button)findViewById(R.id.buttonCancel)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ((Button)findViewById(R.id.buttonSave)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student st = new Student(mUserId, edName.getText().toString(), edMail.getText().toString(), mUserId);
                try {
                    Model.instance().uploadPic(in, mUserId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                //File to upload to cloudinary
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imImage.setImageBitmap(imageBitmap);

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
}
