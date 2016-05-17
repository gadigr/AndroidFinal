package com.example.nofit.summary.model;

import android.graphics.Bitmap;

import com.example.nofit.summary.StudApplication;
import com.firebase.client.AuthData;

import java.io.IOException;

/**
 * Created by Kobi on 08/05/2016.
 */
public class Model {

    private final static Model instance = new Model();

    ModelFirebase firebaseModel;
    ModelCloudinary cloudinaryModel;

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
        firebaseModel = new ModelFirebase(StudApplication.getContext());
        cloudinaryModel = new ModelCloudinary();
    }

    public interface GetStudentListener {
        void done(Student st);
    }

    public void getStudent(GetStudentListener listener) {
        firebaseModel.getStudent(getUserId(), listener);
    }

    public void getStudentById(String id, Model.GetStudentListener listener) {
        firebaseModel.getStudent(id, listener);
    }

    public interface SignupListener {
        public void success(AuthData authData) throws IOException;

        public void fail(String msg);
    }

    public void signeup(String email, String pwd, final SignupListener listener) {
        firebaseModel.signeup(email, pwd, listener);
    }

    public void login(String email, String pwd, final SignupListener listener) {
        firebaseModel.login(email, pwd, listener);
    }

    public String uploadPic(Bitmap pic, String name) throws IOException {
        return cloudinaryModel.uploadPicture(pic, name);
    }

    public String getUserId(){
        return firebaseModel.getUserId();
    }
}
