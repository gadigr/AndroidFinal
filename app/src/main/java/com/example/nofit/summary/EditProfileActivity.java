package com.example.nofit.summary;

import android.content.Intent;
import android.net.Uri;
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

import com.example.nofit.summary.model.Model;
import com.example.nofit.summary.model.Student;
import com.firebase.client.FirebaseError;

public class EditProfileActivity extends AppCompatActivity {

    String mUserId;
    EditText edName;
    EditText edPass;
    EditText edMail;

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

        mUserId = (String) getIntent().getExtras().get("STUDENT_ID");
        edName.setText((String)getIntent().getExtras().get("STUDENT_NAME"));
        edPass.setText((String) getIntent().getExtras().get("STUDENT_PASS"));
        edMail.setText((String) getIntent().getExtras().get("STUDENT_MAIL"));
//        (String) getIntent().getExtras().get("STUDENT_IMG")

        ((Button)findViewById(R.id.buttonCancel)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ((Button)findViewById(R.id.buttonSave)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student st = new Student(mUserId, edName.getText().toString(), edMail.getText().toString(), "image");

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
}
