package com.finalproject.kg.summary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.finalproject.kg.summary.model.Course;
import com.finalproject.kg.summary.model.LoadPictureTask;
import com.finalproject.kg.summary.model.Model;
import com.finalproject.kg.summary.model.MyConvert;
import com.finalproject.kg.summary.model.Student;
import com.finalproject.kg.summary.model.Summary;
import com.finalproject.kg.summary.model.SummaryComment;
import com.finalproject.kg.summary.model.SummaryLike;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//**************************************************
// Feed List Fragment
// This Fragment show all the feed notes
// Kobi hay (305623969) & Gadi gomaz (305296139)
//**************************************************
public class FeedListFragment extends Fragment {

    // Variable of the class
    ListView list;
    List<Summary> data = new LinkedList<Summary>();
    MyAddapter adapter;
    ProgressBar pbLoading;
    View view;
    Fragment fragment = null;
    public final List<Course> lstCourse = new LinkedList<Course>();
    public FragmentManager fragmentManager1;

    // This function show the button on the menu
    public void showDrawerButton() {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        showDrawerButton();

        // Set the fragment design in view variable
        view = inflater.inflate(R.layout.fragment_feed_list, container, false);

        // Set the list view in list variable
        list = (ListView) view.findViewById(R.id.feed_listview);

        // Set the progress bar in pbLoading variable
        pbLoading = (ProgressBar) view.findViewById(R.id.pbLoading);

        // Call to the function that load all the course of this user
        LoadCourse();

        // Call to the function that load all the summary
        //LoadAllSummary();

        // Set New adapter in the adapter variable
        adapter = new MyAddapter();

        // Link the adapter variable to the list variable
        list.setAdapter(adapter);

        // Return the view
        return view;
    }


    // This function get the Fragment Manager and save him on variable
    public void getFragmentManager(FragmentManager fragmentManager)
    {
        fragmentManager1 = fragmentManager;
    }

    // This function load all the course of the current user
    public void LoadCourse() {
        // Show the progress bar -> loading
        pbLoading.setVisibility(View.VISIBLE);

        // Call to function that get all the user course
        Model.instance().getCourseAsynch(new Model.GetCourseListener() {
            @Override
            public void onResult(Student st) {
                // Clear the list
                lstCourse.clear();

                // Add all the course to the class list
                lstCourse.addAll(st.getLstCourse());

                // Load all the summary
                LoadAllSummary();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    // This function load all the summary by the user course
    public void LoadAllSummary() {
        Model.instance().getAllSummariesAsynch(new Model.GetSummaryListener() {
            @Override
            public void onResult(List<Summary> summaries) {
                // Create list of Summary
                List<Summary> lst = new LinkedList<Summary>();

                // Run all over the Summaries
                for (Summary currSm : summaries) {
                    boolean bShowCourse = false;

                    // Check if the summary is in the user list course
                    for (Course currCourse : lstCourse) {
                        if (currSm.getCourse().equals(currCourse.getCourseName())) {
                            bShowCourse = true;
                        }
                    }

                    // If the summary is in the user Course list
                    if(bShowCourse)
                    {
                        lst.add(currSm);
                    }
                }

                // Reverse the course list
                Collections.reverse(lst);

                // Set the list in the data variable
                data = lst;

                // Set Changed
                adapter.notifyDataSetChanged();

                // If the fragment is not null
                if(fragment!=null)
                {
                    ((CommentsListFragment)fragment).UpdateData();
                }

                try {
                    Snackbar.make(view, "Feed Update", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }
                catch (Exception e)
                {

                }

                // Hide the progress bar -> finish loading
                pbLoading.setVisibility(View.GONE);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    // New public class
    // Name: MyAddapter
    // Extends: BaseAdapter
    class MyAddapter extends BaseAdapter {

        // Public function getCount (return the number of the items in the list)
        @Override
        public int getCount() {
            return data.size();
        }

        // Public function getItem (return the current item from the list)
        @Override
        public Object getItem(int position) {
            return data.get(position);
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
                convertView = inflater.inflate(R.layout.feed_list_row, null);
            }

            // Define variable for the Controls on the fragment
            final TextView feed_list_row_name = (TextView) convertView.findViewById(R.id.feed_list_row_name);
            final TextView feed_list_row_date = (TextView) convertView.findViewById(R.id.feed_list_row_date);
            final TextView feed_list_row_course = (TextView) convertView.findViewById(R.id.feed_list_row_course);
            final ImageButton feed_list_row_like_image = (ImageButton) convertView.findViewById(R.id.feed_list_row_like_image);
            final Button feed_list_row_like = (Button) convertView.findViewById(R.id.feed_list_row_like);
            final TextView feed_list_row_like_count = (TextView) convertView.findViewById(R.id.feed_list_row_like_count);
            final Button feed_list_row_comment = (Button) convertView.findViewById(R.id.feed_list_row_comment);
            final TextView feed_list_row_comment_count = (TextView) convertView.findViewById(R.id.feed_list_row_comment_count);
            final ImageView feed_list_row_profile_image = (ImageView)convertView.findViewById(R.id.feed_list_row_profile_image);
            final ImageView feed_list_row_summary_pictures = (ImageView)convertView.findViewById(R.id.feed_list_row_summary_image);
            final ProgressBar feed_lsit_row_progress_bar = (ProgressBar)convertView.findViewById(R.id.img_progressBar);
            convertView.setTag(position);

            // Get the current summary
            final Summary su = data.get(position);

            // Set all the text in the place
            feed_list_row_name.setText(su.getName());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            feed_list_row_date.setText(sdf.format(su.getDateTime().getTime()));
            feed_list_row_course.setText(su.getCourse());
            new LoadPictureTask().execute(feed_list_row_profile_image, su.getStudentId());
            new LoadPictureTask().execute(feed_list_row_summary_pictures, su.getSummaryImage(), feed_lsit_row_progress_bar);

            // On click the summary picture
            feed_list_row_summary_pictures.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] pics = new String[] {su.getSummaryImage()};
                    Intent i = new Intent(getActivity(), FullScreenViewActivity.class);
                    i.putStringArrayListExtra("pics", new ArrayList<String>(Arrays.asList(pics)));
                    getActivity().startActivity(i);
                }
            });


            // Count the number of the Comments
            int nCountComments = 0;
            for (SummaryComment currSc : su.getLstComment()) {
                if (currSc.getShow()) {
                    nCountComments++;
                }
            }

            // Set the number of the comments in the place
            feed_list_row_comment_count.setText(String.valueOf(nCountComments));

            // Check if the user do like, and count the number of the like
            boolean bDoLike = false;
            int nCountLike = 0;
            for (SummaryLike currSl : su.getLstLike()) {
                if (currSl.getUserId().equals(Model.instance().getUserId())) {
                    bDoLike = currSl.getLike();
                }
                if(currSl.getLike())
                {
                    nCountLike++;
                }
            }

            // IF the user do like
            if(bDoLike)
            {
                feed_list_row_like_image.setImageResource(R.mipmap.ic_thumb_up_white_24dp);
            }
            else
            {
                feed_list_row_like_image.setImageResource(R.mipmap.ic_thumb_up_black_24dp);
            }

            // Write the number of like
            feed_list_row_like_count.setText(String.valueOf(nCountLike));

            // On Click Comment
            feed_list_row_comment.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Class fragmentClass;
                     fragmentClass = CommentsListFragment.class;
                     try {
                         fragment = (Fragment) fragmentClass.newInstance();
                         ((CommentsListFragment)fragment).setSummary(su);
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                     Global.instance().getFabBtn().setVisibility(View.GONE);
                     fragmentManager1.beginTransaction().replace(R.id.main_frag_container, fragment).addToBackStack(null).commit();
                 }
             });

            // On click Like
            feed_list_row_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Check if the user do like
                    boolean bFind = false;
                    for (SummaryLike currSl : su.getLstLike()) {
                        if (currSl.getUserId().equals(Model.instance().getUserId())) {
                            // if the user do like change the like status
                            bFind = true;
                            if (currSl.getLike()) {
                                currSl.setLike(false);
                            } else {
                                currSl.setLike(true);
                            }
                        }
                    }

                    // If the user dont do like add new value for the list
                    if(!bFind)
                    {
                        SummaryLike sl = new SummaryLike();
                        sl.setUserId(Model.instance().getUserId());
                        sl.setLike(true);
                        su.getLstLike().add(sl);
                    }

                    // Update the Summary
                    Model.instance().doLikeToSummary(su, new Model.doLikeToSummaryListener() {
                        @Override
                        public void done() {
                        }
                    });
                }
            });

            // Return the convert View
            return convertView;
        }

    }
}
