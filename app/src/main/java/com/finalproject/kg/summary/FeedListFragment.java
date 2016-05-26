package com.finalproject.kg.summary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
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

public class FeedListFragment extends Fragment {

    ListView list;
    List<Summary> data = new LinkedList<Summary>();
    MyAddapter adapter;
    ProgressBar pbLoading;
    View view;

    CommentsListFragment fragB = new CommentsListFragment();

    public final List<Course> lstCourse = new LinkedList<Course>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feed_list, container, false);
        list = (ListView) view.findViewById(R.id.feed_listview);
        pbLoading = (ProgressBar) view.findViewById(R.id.pbLoading);
        LoadCourse();
        LoadAllSummary();
        adapter = new MyAddapter();
        list.setAdapter(adapter);

        return view;
    }

    public void LoadCourse() {
        Model.instance().getCourseAsynch(new Model.GetCourseListener() {
            @Override
            public void onResult(Student st) {
                lstCourse.clear();
                lstCourse.addAll(st.getLstCourse());
                LoadAllSummary();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    public void LoadAllSummary() {
        pbLoading.setVisibility(View.VISIBLE);
        Model.instance().getAllSummariesAsynch(new Model.GetSummaryListener() {
            @Override
            public void onResult(List<Summary> summaries) {
                List<Summary> lst = new LinkedList<Summary>();
                for (Summary currSm : summaries) {
                    boolean bShowCourse = false;
                    for (Course currCourse : lstCourse) {
                        if (currSm.getCourse().equals(currCourse.getCourseName())) {
                            bShowCourse = true;
                        }
                    }

                    if(bShowCourse)
                    {
                        lst.add(currSm);
                    }
                }
                Collections.reverse(lst);
                data = lst;
                adapter.notifyDataSetChanged();
                fragB.UpdateData();
                Snackbar.make(view, "New Post", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                pbLoading.setVisibility(View.GONE);
            }

            @Override
            public void onCancel() {

            }
        });
    }



    class MyAddapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
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
                convertView = inflater.inflate(R.layout.feed_list_row, null);
                Log.d("TAG", "create view:" + position);
            } else {
                Log.d("TAG", "use convert view:" + position);
            }

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
            convertView.setTag(position);

            final Summary su = data.get(position);
            feed_list_row_name.setText(su.getName());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            feed_list_row_date.setText(sdf.format(su.getDateTime().getTime()));
            feed_list_row_course.setText(su.getCourse());
            new LoadPictureTask().execute(feed_list_row_profile_image, su.getStudentId());

            feed_list_row_summary_pictures.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    FragmentManager childFragMan = getChildFragmentManager();
//                    FragmentTransaction childFragTrans = childFragMan.beginTransaction();
//                    fragViewr.setImages(new String[] {su.getSummaryImage()});
//                    childFragTrans.add(R.id.fragment, fragViewr);
//                    childFragTrans.addToBackStack("B");
//                    childFragTrans.commit();
                    String[] pics = new String[] {su.getSummaryImage(), su.getSummaryImage(), su.getSummaryImage()};
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
                     //child fragment
                     FragmentManager childFragMan = getChildFragmentManager();
                     FragmentTransaction childFragTrans = childFragMan.beginTransaction();
                     fragB.setSummary(su);
                     childFragTrans.add(R.id.fragment, fragB);
                     childFragTrans.addToBackStack("B");
                     childFragTrans.commit();
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


            return convertView;
        }

    }
}
