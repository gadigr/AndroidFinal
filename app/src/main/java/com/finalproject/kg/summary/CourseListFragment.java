package com.finalproject.kg.summary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.finalproject.kg.summary.model.Course;
import com.finalproject.kg.summary.model.CourseList;
import com.finalproject.kg.summary.model.Model;
import com.finalproject.kg.summary.model.Student;
import com.finalproject.kg.summary.model.Summary;
import com.firebase.client.FirebaseError;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseListFragment extends Fragment {

    MyAddapter adapter;
    ListView list;
    public Student saveST;
    public List<CourseList> lstCourse = new LinkedList<CourseList>();

    public CourseListFragment() {
        lstCourse.add(new CourseList("Algebra",false));
        lstCourse.add(new CourseList("Statistic",false));
        lstCourse.add(new CourseList("Infi",false));
    }
    public void showBackButton() {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);
        list = (ListView) view.findViewById(R.id.course_listview);
        showBackButton();
        Model.instance().getStudentById(Model.instance().getUserId(), new Model.GetStudentListener(){
                    @Override
                    public void done(Student st) {
                        saveST = st;
                        for(Course currCO : st.getLstCourse())
                        {
                            for (CourseList currCL: lstCourse)
                            {
                                if (currCL.getCourseName().equals(currCO.getCourseName()))
                                {
                                    currCL.setSelected(true);
                                }
                            }
                        }
                        adapter = new MyAddapter();
                        list.setAdapter(adapter);
                    }
                });


        return view;
    }


    class MyAddapter extends BaseAdapter {
        @Override
        public int getCount() {
            return lstCourse.size();
        }

        @Override
        public Object getItem(int position) {
            return lstCourse.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                convertView = inflater.inflate(R.layout.course_list_row, null);
                Log.d("TAG", "create view:" + position);
            } else {
                Log.d("TAG", "use convert view:" + position);
            }

            final CheckBox course_list_row_cb = (CheckBox) convertView.findViewById(R.id.course_list_row_cb);
            convertView.setTag(position);

            final CourseList cl = lstCourse.get(position);
            course_list_row_cb.setText(cl.getCourseName());
            course_list_row_cb.setChecked(cl.getSelected());

            course_list_row_cb.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (course_list_row_cb.isChecked()) {
                        saveST.getLstCourse().add(new Course(String.valueOf(course_list_row_cb.getText())));
                    }
                    else
                    {
                        for(Course cuurCo: saveST.getLstCourse())
                        {
                            if(cuurCo.getCourseName().equals(String.valueOf(course_list_row_cb.getText())))
                            {
                                saveST.getLstCourse().remove(cuurCo);
                            }
                        }
                    }

                    Model.instance().updateCourse(saveST, new Model.UpdateCourseListener() {
                        @Override
                        public void done() {}
                    });
                }
            });


            return convertView;
        }
    }
}
