package com.finalproject.kg.summary;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import com.finalproject.kg.summary.model.Course;
import com.finalproject.kg.summary.model.CourseList;
import com.finalproject.kg.summary.model.Model;
import com.finalproject.kg.summary.model.Student;
import java.util.LinkedList;
import java.util.List;

//**************************************************
// Course List Fragment
// This Fragment show all the course and marker
// the course of the current user
// Kobi hay (305623969) & Gadi gomaz (305296139)
//**************************************************
public class CourseListFragment extends Fragment {

    // Variable of the class
    public Student saveST;
    public List<CourseList> lstCourse = new LinkedList<CourseList>();
    MyAddapter adapter;
    ListView list;
    View view;

    // Empty Constructor
    public CourseListFragment() {
        // Add all the Course to the list
        lstCourse.add(new CourseList("Algebra",false));
        lstCourse.add(new CourseList("Statistic",false));
        lstCourse.add(new CourseList("Infi",false));
    }

    // This function show the back button on the menu
    public void showBackButton() {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Set the fragment design in view variable
        view = inflater.inflate(R.layout.fragment_course_list, container, false);

        // Set the list view in list variable
        list = (ListView) view.findViewById(R.id.course_listview);

        // Call to the function showBackButton to show the back button
        showBackButton();

        // Call to the function getStudentById to get the Details of the student
        Model.instance().getStudentById(Model.instance().getUserId(), new Model.GetStudentListener(){
                    @Override
                    public void done(Student st) {

                        // Save the user object
                        saveST = st;

                        // Run all over the user Course
                        for(Course currCO : st.getLstCourse())
                        {
                            // Run all over the list Course
                            for (CourseList currCL: lstCourse)
                            {
                                // If the user have this course in him list
                                if (currCL.getCourseName().equals(currCO.getCourseName()))
                                {
                                    // Marker the course
                                    currCL.setSelected(true);
                                }
                            }
                        }

                        // Set New adapter in the adapter variable
                        adapter = new MyAddapter();

                        // Link the adapter variable to the list variable
                        list.setAdapter(adapter);
                    }
                });

        // Return the view variable
        return view;
    }

    // New public class
    // Name: MyAddapter
    // Extends: BaseAdapter
    class MyAddapter extends BaseAdapter {

        // Public function getCount (return the number of the items in the list)
        @Override
        public int getCount() {
            return lstCourse.size();
        }

        // Public function getItem (return the current item from the list)
        @Override
        public Object getItem(int position) {
            return lstCourse.get(position);
        }

        // Public function getItemId (return the current id item from the list)
        @Override
        public long getItemId(int position) {
            return position;
        }

        // Public function getView
        @Override
        public View getView(int position, View convertView,
                            ViewGroup parent) {
            // Check if the convertView is null
            if (convertView == null) {
                // Define new LayoutInflater
                LayoutInflater inflater = getActivity().getLayoutInflater();
                convertView = inflater.inflate(R.layout.course_list_row, null);
            }

            // Define variable for the Controls on the fragment
            final CheckBox course_list_row_cb = (CheckBox) convertView.findViewById(R.id.course_list_row_cb);

            // Set tag position in the convertView
            convertView.setTag(position);

            // Define variable for the Controls on the fragment
            final CourseList cl = lstCourse.get(position);

            // Set the course name
            course_list_row_cb.setText(cl.getCourseName());

            // Set Checked course
            course_list_row_cb.setChecked(cl.getSelected());

            // On click the button add comment
            course_list_row_cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Snackbar.make(view, "Updating your courses list", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                    // If new course is select
                    if (course_list_row_cb.isChecked()) {
                        // Add the course to the user course list
                        saveST.getLstCourse().add(new Course(String.valueOf(course_list_row_cb.getText())));
                    }
                    else
                    {
                        List<Course> lstCourse2 = new LinkedList<Course>();
                        lstCourse2.addAll(saveST.getLstCourse());
                        // Run all over the user course list
                        for(Course cuurCo: saveST.getLstCourse())
                        {
                            // Find the course to remove
                            if(cuurCo.getCourseName().equals(String.valueOf(course_list_row_cb.getText())))
                            {
                                // Remove the course from the user course list
                                lstCourse2.remove(cuurCo);
                            }
                        }

                        saveST.setLstCourse(lstCourse2);
                    }

                    // Call to the function that update the user list
                    Model.instance().updateCourse(saveST, new Model.UpdateCourseListener() {
                        @Override
                        public void done() {}
                    });
                }
            });

            // Return the convertView
            return convertView;
        }
    }
}
