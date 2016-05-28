package com.finalproject.kg.summary;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.finalproject.kg.summary.model.LoadPictureTask;
import com.finalproject.kg.summary.model.Model;
import com.finalproject.kg.summary.model.Student;
import com.finalproject.kg.summary.model.Summary;
import com.finalproject.kg.summary.model.SummaryComment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

//**************************************************
// Comments List Fragment
// This Fragment show all the comments
// Kobi hay (305623969) & Gadi gomaz (305296139)
//**************************************************
public class CommentsListFragment extends Fragment {

    // Empty Constructor
    public CommentsListFragment() {
    }

    // Variable of the class
    Summary su;
    MyAddapter adapter;
    List<SummaryComment> data = new LinkedList<SummaryComment>();
    View Mainview;
    ProgressBar pbLoading;
    ListView list;

    // This function set the current summary in the class
    public void setSummary(Summary su) {

        // Set the summary in the summary virable
        this.su = su;

        // Set the comments of this summary in the data variable
        data = su.getLstComment();

        // Check if show this command or not
        if(!data.get(0).getShow())
        {
            // Dno't show this comments -> remove from the data variable
            data.remove(0);
        }
    }

    // This function show the back button on the menu
    public void showBackButton() {
        // Check if the activity is AppCompatActivity type
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    // This function care to update the comments
    public void UpdateData()
    {
        // if the size of the comments array is different form 0
        if(data.size()!=0) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set the fragment design in Main view variable
        Mainview = inflater.inflate(R.layout.fragment_comments_list, container, false);

        // Set the progress bar in pbLoading variable
        pbLoading = (ProgressBar) Mainview.findViewById(R.id.comments_loading);

        // Show the progress bar -> loading
        pbLoading.setVisibility(View.VISIBLE);

        // Set the list view in list variable
        list = (ListView) Mainview.findViewById(R.id.comments_listview);

        // Set the button add comment in the final comments_list_add_comment variable
        final Button comments_list_add_comment = (Button) Mainview.findViewById(R.id.comments_list_add_comment);
        // Set the TextView comment text in the final comments_list_add_text_comment variable
        final TextView comments_list_add_text_comment = (TextView) Mainview.findViewById(R.id.comments_list_add_text_comment);

        // Call to the function showBackButton to show the back button
        showBackButton();

        // Set New adapter in the adapter variable
        adapter = new MyAddapter();

        // Link the adapter variable to the list variable
        list.setAdapter(adapter);

        // On click the button add comment
        comments_list_add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create new SummaryComment
                SummaryComment newSC = new SummaryComment();

                // Set the id of the writer
                newSC.setUserWriterId(Model.instance().getUserId());

                // Set this date in the newSC variable
                Calendar cc = Calendar.getInstance();
                cc.set(cc.get(Calendar.YEAR), cc.get(Calendar.MONTH),cc.get(Calendar.DAY_OF_MONTH) ,  cc.get(Calendar.HOUR),  cc.get(Calendar.MINUTE),  cc.get(Calendar.SECOND));
                newSC.setDateTime(cc);

                // Define show this comment
                newSC.setShow(true);

                // Set the text of this comment
                newSC.setComment(String.valueOf(comments_list_add_text_comment.getText()));

                // Add the comments to the list comment of the summary
                su.getLstComment().add(newSC);

                // Call to the function that add the comment
                Model.instance().addCommentToSummary(su, new Model.AddCommentListener() {
                    @Override
                    public void done() {
                    }
                });

                comments_list_add_text_comment.setText("");
            }
        });

        // Hide the progress bar -> finish loading
        pbLoading.setVisibility(View.GONE);

        // Return the main view variable
        return Mainview;
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
                convertView = inflater.inflate(R.layout.comments_list_row, null);
            }

            // Define variable for the Controls on the fragment
            final TextView comments_list_row_name = (TextView) convertView.findViewById(R.id.comments_list_row_name);
            final TextView comments_list_row_comments = (TextView) convertView.findViewById(R.id.comments_list_row_comments);
            final TextView comments_list_row_datetime = (TextView) convertView.findViewById(R.id.comments_list_row_datetime);
            final ImageView comments_list_row_image = (ImageView) convertView.findViewById(R.id.comments_list_row_image);

            // Set tag position in the convertView
            convertView.setTag(position);

            // Get the current comment
            final SummaryComment sc = data.get(position);

            // Call to the function getStudentById to get the Details of the student
            Model.instance().getStudentById(sc.getUserWriterId(),new Model.GetStudentListener() {
                @Override
                public void done(Student st) {
                    // Set the name of the student in the current place
                    comments_list_row_name.setText(st.getName());
                    new LoadPictureTask().execute(comments_list_row_image, st.getId());
                }
            });

            // Set the comment text in the current place
            comments_list_row_comments.setText(sc.getComment());

            // Set the comment date in the current place
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            comments_list_row_datetime.setText(sdf.format(sc.getDateTime().getTime()));

            // Return the view
            return convertView;
        }
    }
}
