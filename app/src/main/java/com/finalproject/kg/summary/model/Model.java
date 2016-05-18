package com.finalproject.kg.summary.model;

import com.finalproject.kg.summary.StudApplication;
import com.firebase.client.AuthData;

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

    public interface AddSummaryListener {
        void done(Summary su);
    }

    public void addStudent(Student st, AddStudentListener listener) {
        firebaseModel.addStudent(st, listener);
    }

    public void addSummary(Summary su, AddSummaryListener listener) {
        firebaseModel.addSummary(su, listener);
    }

    private Model() {
        firebaseModel = new ModelFirebase(StudApplication.getContext());
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
        public void success(AuthData authData);

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
