package com.finalproject.kg.summary.model;

import android.content.Context;
import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;


/**
 * Created by Kobi on 07/05/2016.
 */
public class ModelFirebase {
    Firebase myFirebaseRef;

    public ModelFirebase(final Context context) {
        Firebase.setAndroidContext(context);
        myFirebaseRef = new Firebase("https://dazzling-torch-343.firebaseio.com/");
    }

    public void updateStudent(Student st, final Model.UpdateStudentListenr listener){
        Firebase stRef = myFirebaseRef.child("Students").child(st.getId());
        Map<String, Object> update = new HashMap<String, Object>();
        update.put("emailaddress", st.getEmailaddress());
        update.put("name", st.getName());
        update.put("imageName", st.getImageName());
//        update.put("password", st.password);




        stRef.updateChildren(update, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                    listener.done(firebaseError);
                }
            }
        });

        }

    public void getCourseAsynch(final Model.GetCourseListener listener) {
        Firebase  stRef = myFirebaseRef.child("Students").child(getUserId());
        // Attach an listener to read the data at our posts reference
        //stRef.addListenerForSingleValueEvent(new ValueEventListener() {
        stRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Student st = snapshot.getValue(Student.class);
                listener.onResult(st);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
                listener.onCancel();
            }
        });
    }

    public void getAllSummariesAsynch(final Model.GetSummaryListener listener) {
        Firebase  stRef = myFirebaseRef.child("Summaries");
        // Attach an listener to read the data at our posts reference
        //stRef.addListenerForSingleValueEvent(new ValueEventListener() {
        stRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final List<Summary> suList = new LinkedList<Summary>();
                for (DataSnapshot stSnapshot : snapshot.getChildren()) {
                    Summary su = stSnapshot.getValue(Summary.class);
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

    public void doLikeToSummary(Summary su, final Model.doLikeToSummaryListener listener) {
        Firebase stRef = myFirebaseRef.child("Summaries").child(su.getId());
        stRef.setValue(su, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                listener.done();
            }
        });
    }

    public void addCommentToSummary(Summary su, final Model.AddCommentListener listener) {
        Firebase stRef = myFirebaseRef.child("Summaries").child(su.getId());
        stRef.setValue(su, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                listener.done();
            }
        });
    }

    public void updateCourse(Student st, final Model.UpdateCourseListener listener) {
        Firebase stRef = myFirebaseRef.child("Students").child(st.getId());
        stRef.setValue(st, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                listener.done();
            }
        });
    }

    public void addSummary(final Summary su, final Model.AddSummaryListener listener) {
        //Firebase stRef = myFirebaseRef.child("Summaries").child(su.getId());
        Firebase stRef = myFirebaseRef.child("Summaries");
        Firebase stRefPush = stRef.push();
        su.setId(stRefPush.getKey());
        stRefPush.setValue(su, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                listener.done(su);
            }
        });
    }

    public void signeup(String email, String pwd, final Model.SignupListener listener) {
            myFirebaseRef.createUser(email, pwd, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                try {
                    listener.success(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                try {
                    listener.success(authData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
