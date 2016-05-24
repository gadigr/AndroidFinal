package com.finalproject.kg.summary.model;

import java.io.InputStream;
import java.util.List;

import android.graphics.Bitmap;
import android.text.style.UpdateAppearance;
import com.finalproject.kg.summary.StudApplication;
import com.firebase.client.AuthData;
import com.firebase.client.FirebaseError;

import java.io.IOException;

import java.util.List;

/**
 * Created by Kobi on 08/05/2016.
 */
public class Model {

    private final static Model instance = new Model();

    private Student mConnectedStud;

    ModelFirebase firebaseModel;
    ModelCloudinary cloudinaryModel;

    public static Model instance() {
        return instance;
    }

    public interface GetSummaryListener{
        public void onResult(List<Summary> summaries);
        public void onCancel();
    }

    public void getAllSummariesAsynch(GetSummaryListener listener){
        firebaseModel.getAllSummariesAsynch(listener);
    }

    public interface AddStudentListener {
        void done(Student st);
    }

    public interface doLikeToSummaryListener {
        void done();
    }

    public interface AddCommentListener {
        void done();
    }

    public interface AddSummaryListener {
        void done(Summary su);
    }

    public interface UpdateStudentListenr {
        void done(FirebaseError err);
    }

    public void updateStudent(Student st, UpdateStudentListenr listener) {
        firebaseModel.updateStudent(st, listener);
    }

    public void addStudent(Student st, AddStudentListener listener) {
        firebaseModel.addStudent(st, listener);
    }

    public void doLikeToSummary(Summary su, Model.doLikeToSummaryListener listener) {
        firebaseModel.doLikeToSummary(su, listener);
    }

    public void addCommentToSummary(Summary su, Model.AddCommentListener listener) {
        firebaseModel.addCommentToSummary(su, listener);
    }

    public void addSummary(Summary su, AddSummaryListener listener) {
        firebaseModel.addSummary(su, listener);
    }

    private Model() {
        firebaseModel = new ModelFirebase(StudApplication.getContext());
        cloudinaryModel = new ModelCloudinary();
    }

    public interface GetStudentListener {
        void done(Student st);
    }

    public void getStudent(final GetStudentListener listener) {
        firebaseModel.getStudent(getUserId(), new GetStudentListener() {
            @Override
            public void done(Student st) {
                if (mConnectedStud == null) {
                    mConnectedStud = st;
                }
                listener.done(st);
            }
        });
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

    public void uploadPic(InputStream pic, String name) throws IOException {
        cloudinaryModel.uploadPicture(pic, name);
    }

    public Bitmap getPic(String name) throws IOException {
        return cloudinaryModel.getPicture(name);
    }

    public Student getConnectedStudent() {

        if (mConnectedStud == null) {
            firebaseModel.getStudent(getUserId(), new GetStudentListener() {
                @Override
                public void done(Student st) {
                        mConnectedStud = st;
                }
            });
        }

        return mConnectedStud;
    }

    public String getUserId(){
        return firebaseModel.getUserId();
    }
}
