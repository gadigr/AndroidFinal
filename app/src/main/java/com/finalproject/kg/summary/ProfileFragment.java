package com.finalproject.kg.summary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.finalproject.kg.summary.model.LoadPictureTask;
import com.finalproject.kg.summary.model.Model;
import com.finalproject.kg.summary.model.Student;


//**************************************************
// Profile Fragment
// This Fragment show the user profile
// Kobi hay (305623969) & Gadi gomaz (305296139)
//**************************************************
public class ProfileFragment extends Fragment {

    // Variable of the class
    private static final String ARG_USER_ID = "userid";
    private String mUserId;
    Student stud;
    private OnFragmentInteractionListener mListener;
    TextView txtName;
    TextView txtEmail;
    TextView txtPassword;
    ImageView imView;

    // Empty Constructor
    public ProfileFragment() {}

    // This function show the button on the menu
    public void showDrawerButton() {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_profile:

                // Start new intent with param
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                intent.putExtra("STUDENT_ID", stud.getId());
                intent.putExtra("STUDENT_MAIL", stud.getEmailaddress());
                intent.putExtra("STUDENT_NAME", stud.getName());
                intent.putExtra("STUDENT_PASS", stud.password);
                intent.putExtra("STUDENT_IMG", stud.getImageName());

                // Start the activity
                startActivityForResult(intent, 1);
                return true;
        }

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If the request code is 1
        if (requestCode == 1) {

            // If res code is ok
            if (resultCode == Activity.RESULT_OK) {
                String returnedResult = data.getData().toString();

                // call to function to get the student
                Model.instance().getStudent(new Model.GetStudentListener() {
                    @Override
                    public void done(Student st)  {
                        txtName.setText(st.getName());
                        txtEmail.setText(st.getEmailaddress());
                        txtPassword.setText(st.password);

                        new LoadPictureTask().execute(imView, st.getImageName());
                        stud = st;
                    }
                });

            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu != null) {
            menu.findItem(R.id.action_filter).setVisible(false);
        }

        inflater.inflate(R.menu.profile_menu, menu);
    }

    // On create the profile fragment
    public static ProfileFragment newInstance(String userId) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showDrawerButton();
        setHasOptionsMenu(true);
        ((MainNotesActivity)getActivity()).hideFloatingActionButton();

        if (getArguments() != null) {
            mUserId = getArguments().getString(ARG_USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        txtName = (TextView)rootView.findViewById(R.id.txtName);
        txtEmail = (TextView)rootView.findViewById(R.id.txtEmail);
        txtPassword = (TextView)rootView.findViewById(R.id.txtPassword);
        imView = (ImageView)rootView.findViewById(R.id.imProfile) ;
        Model.instance().getStudent(new Model.GetStudentListener() {
            @Override
            public void done(Student st) {
                txtName.setText(st.getName());
                txtEmail.setText(st.getEmailaddress());
                txtPassword.setText(st.password);
                new LoadPictureTask().execute(imView, st.getImageName());
                stud = st;
            }
        });

        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
