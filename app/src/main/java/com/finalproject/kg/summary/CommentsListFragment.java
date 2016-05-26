package com.finalproject.kg.summary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.finalproject.kg.summary.model.Model;
import com.finalproject.kg.summary.model.Student;
import com.finalproject.kg.summary.model.Summary;
import com.finalproject.kg.summary.model.SummaryComment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class CommentsListFragment extends Fragment {

    public CommentsListFragment() {
        // Required empty public constructor
    }

    Summary su;
    MyAddapter adapter;
    List<SummaryComment> data = new LinkedList<SummaryComment>();
    View Mainview;
    ProgressBar pbLoading;
    ListView list;

    public void setSummary(Summary su) {
        this.su = su;
        data = su.getLstComment();
        if(data.get(0).getShow() == false)
        {
            data.remove(0);
        }
    }

    public void UpdateData()
    {
        if(data.size()!=0) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Mainview = inflater.inflate(R.layout.fragment_comments_list, container, false);
        pbLoading = (ProgressBar) Mainview.findViewById(R.id.comments_loading);
        pbLoading.setVisibility(View.VISIBLE);
        list = (ListView) Mainview.findViewById(R.id.comments_listview);
        final Button comments_list_add_comment = (Button) Mainview.findViewById(R.id.comments_list_add_comment);
        final TextView comments_list_add_text_comment = (TextView) Mainview.findViewById(R.id.comments_list_add_text_comment);

        adapter = new MyAddapter();
        list.setAdapter(adapter);

        comments_list_add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SummaryComment newSC = new SummaryComment();
                newSC.setUserWriterId(Model.instance().getUserId());
                Calendar cc = Calendar.getInstance();
                cc.set(cc.get(Calendar.YEAR), cc.get(Calendar.MONTH),cc.get(Calendar.DAY_OF_MONTH) ,  cc.get(Calendar.HOUR),  cc.get(Calendar.MINUTE),  cc.get(Calendar.SECOND));
                newSC.setDateTime(cc);
                newSC.setShow(true);
                newSC.setComment(String.valueOf(comments_list_add_text_comment.getText()));

                su.getLstComment().add(newSC);

                Model.instance().addCommentToSummary(su, new Model.AddCommentListener() {
                    @Override
                    public void done() {
                    }
                });
            }
        });

        pbLoading.setVisibility(View.GONE);

        return Mainview;
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
                convertView = inflater.inflate(R.layout.comments_list_row, null);
                Log.d("TAG", "create view:" + position);
            } else {
                Log.d("TAG", "use convert view:" + position);
            }

            final TextView comments_list_row_name = (TextView) convertView.findViewById(R.id.comments_list_row_name);
            final TextView comments_list_row_comments = (TextView) convertView.findViewById(R.id.comments_list_row_comments);
            final TextView comments_list_row_datetime = (TextView) convertView.findViewById(R.id.comments_list_row_datetime);

            convertView.setTag(position);

            final SummaryComment sc = data.get(position);


            Model.instance().getStudentById(sc.getUserWriterId(),new Model.GetStudentListener() {
                @Override
                public void done(Student st) {
                    comments_list_row_name.setText(st.getName());
                }
            });

            comments_list_row_comments.setText(sc.getComment());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            comments_list_row_datetime.setText(sdf.format(sc.getDateTime().getTime()));

            return convertView;
        }
    }
}
