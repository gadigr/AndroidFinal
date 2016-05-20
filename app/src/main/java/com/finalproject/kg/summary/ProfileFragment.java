package com.finalproject.kg.summary;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finalproject.kg.summary.model.Model;
import com.finalproject.kg.summary.model.Student;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER_ID = "userid";


    private String mUserId;
    Student stud;

    private OnFragmentInteractionListener mListener;

    TextView txtName;
    TextView txtEmail;
    TextView txtPassword;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_profile:
                //called when the up affordance/carat in actionbar is pressed
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                intent.putExtra("STUDENT_ID", stud.getId());
                intent.putExtra("STUDENT_MAIL", stud.getEmailaddress());
                intent.putExtra("STUDENT_NAME", stud.getName());
                intent.putExtra("STUDENT_PASS", stud.password);
                intent.putExtra("STUDENT_IMG", stud.getImageName());

                startActivityForResult(intent, 1);
                return true;
        }

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String returnedResult = data.getData().toString();

                Model.instance().getStudent(new Model.GetStudentListener() {
                    @Override
                    public void done(Student st) {
                        txtName.setText(st.getName());
                        txtEmail.setText(st.getEmailaddress());
                        txtPassword.setText(st.password);

                        stud = st;
                    }
                });

            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);

        if (menu != null) {
            menu.findItem(R.id.action_filter).setVisible(false);
        }

        inflater.inflate(R.menu.profile_menu, menu);
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userId Parameter 1.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
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

        Model.instance().getStudent(new Model.GetStudentListener() {
            @Override
            public void done(Student st) {
                txtName.setText(st.getName());
                txtEmail.setText(st.getEmailaddress());
                txtPassword.setText(st.password);

                stud = st;
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
