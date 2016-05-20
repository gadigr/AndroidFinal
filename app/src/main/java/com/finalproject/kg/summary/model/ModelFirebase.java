package com.finalproject.kg.summary.model;

import android.content.Context;
import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kobi on 07/05/2016.
 */
public class ModelFirebase {
    Firebase myFirebaseRef;

    public ModelFirebase(final Context context) {
        Firebase.setAndroidContext(context);
        myFirebaseRef = new Firebase("https://dazzling-torch-343.firebaseio.com/");
    }

    public void getAllSummariesAsynch(final Model.GetSummaryListener listener) {
        Firebase  stRef = myFirebaseRef.child("Summaries");
        // Attach an listener to read the data at our posts reference
        //stRef.addListenerForSingleValueEvent(new ValueEventListener() {
        stRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final List<Summary> suList = new LinkedList<Summary>();
                Log.d("TAG", "There are " + snapshot.getChildrenCount() + " blog posts");
                for (DataSnapshot stSnapshot : snapshot.getChildren()) {
                    Summary su = stSnapshot.getValue(Summary.class);
                    //Log.d("TAG", st.getFname() + " - " + st.getId());
                    Log.d("TAG","kkkkkk3");
                    suList.add(su);
                }
                listener.onResult(suList);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
                listener.onCancel();
            }
        });
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

    public void addStudent(Student st, Model.AddStudentListener listener) {
//        login(st.getEmailaddress(),st.password,null);
//        st.setId(getUserId());
        Firebase stRef = myFirebaseRef.child("Students").child(st.getId());
        stRef.setValue(st);
    }

    public void doLikeToSummary(Summary su, Model.doLikeToSummaryListener listener) {
        Firebase stRef = myFirebaseRef.child("Summaries").child(su.getId());
        stRef.setValue(su);
    }

    public void addSummary(Summary su, Model.AddSummaryListener listener) {
        //Firebase stRef = myFirebaseRef.child("Summaries").child(su.getId());
        Firebase stRef = myFirebaseRef.child("Summaries");
        Firebase stRefPush = stRef.push();
        su.setId(stRefPush.getKey());
        stRefPush.setValue(su);
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
