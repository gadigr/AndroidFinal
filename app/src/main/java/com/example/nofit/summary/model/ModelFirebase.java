package com.example.nofit.summary.model;

import android.content.Context;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Kobi on 07/05/2016.
 */
public class ModelFirebase {
    Firebase myFirebaseRef;

    public ModelFirebase(final Context context) {
        Firebase.setAndroidContext(context);
        myFirebaseRef = new Firebase("https://dazzling-torch-343.firebaseio.com/");
    }

    public void getStudent(String id, final Model.GetStudentListener listener) {
        Firebase stRef = myFirebaseRef.child("Students").child(id);
        // Attach a listener to read the data at our posts reference
        stRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student student = dataSnapshot.getValue(Student.class);
                listener.done(student);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.done(null);
            }
        });

    }

    public void add(Student st, Model.AddStudentListener listener) {
//        login(st.getEmailaddress(),st.password,null);
//        st.setId(getUserId());
        Firebase stRef = myFirebaseRef.child("Students").child(st.getId());
        stRef.setValue(st);
    }

    public void signeup(String email, String pwd, final Model.SignupListener listener) {
            myFirebaseRef.createUser(email, pwd, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                listener.success(null);
            }

            @Override
            public void onError(FirebaseError firebaseError) {

                listener.fail(firebaseError.getMessage());
            }
        });
    }

    public void login(String email, String pwd, final Model.SignupListener listener) {
        myFirebaseRef.authWithPassword(email, pwd, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                listener.success(authData);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                listener.fail(firebaseError.getMessage());
            }
        });

    }

    public String getUserId(){
        AuthData authData = myFirebaseRef.getAuth();
        if (authData != null) {
            return authData.getUid();
        }
        return null;
    }
}
