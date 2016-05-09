package com.example.nofit.summary.model;
import com.example.nofit.summary.LogInActivity;

import java.util.List;

/**
 * Created by Kobi on 08/05/2016.
 */
public class Model {

    private final static Model instance = new Model();

    ModelFirebase firebaseModel;

    public static Model instance() {
        return instance;
    }

    public interface AddStudentListener {
        void done(Student st);
    }

    public void add(Student st, AddStudentListener listener) {
        firebaseModel.add(st, listener);
    }

    private Model() {
        firebaseModel = new ModelFirebase(LogInActivity.getContext());
    }

    public interface GetStudentListener {
        void done(Student st);
    }

    public void getStudent(GetStudentListener listener) {
        firebaseModel.getStudent(listener);
    }

    public interface SignupListener {
        public void success();

        public void fail(String msg);
    }

    public void signeup(String email, String pwd, final SignupListener listener) {
        firebaseModel.signeup(email, pwd, listener);
    }

    public void login(String email, String pwd, final SignupListener listener) {
        firebaseModel.login(email, pwd, listener);
    }

    public String getUserId(){
        return firebaseModel.getUserId();
    }
}
