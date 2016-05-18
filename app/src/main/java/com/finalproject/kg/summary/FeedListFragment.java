package com.finalproject.kg.summary;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.finalproject.kg.summary.model.Model;
import com.finalproject.kg.summary.model.Student;
import com.finalproject.kg.summary.model.Summary;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FeedListFragment extends Fragment {

    ListView list;
    List<Summary> data = new LinkedList<Summary>();
    MyAddapter adapter;
    ProgressBar pbLoading;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feed_list, container, false);
        list = (ListView) view.findViewById(R.id.feed_listview);
        pbLoading = (ProgressBar) view.findViewById(R.id.pbLoading);
        LoadAllSummary();
        adapter = new MyAddapter();
        list.setAdapter(adapter);

        return view;
    }


    public void LoadAllSummary()
    {
        Log.d("TAG","kkkkkk1");
        pbLoading.setVisibility(View.VISIBLE);
        Model.instance().getAllSummariesAsynch(new Model.GetSummaryListener() {
            @Override
            public void onResult(List<Summary> summaries) {
                pbLoading.setVisibility(View.GONE);
                data = summaries;
                Collections.reverse(data);
                adapter.notifyDataSetChanged();
                Log.d("TAG","kkkkkk2");
                Snackbar.make(view, "New Post", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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
            //return 1;
        }

        @Override
        public Object getItem(int position) {
            //return new Object();
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView,
                            ViewGroup parent) {
            if(convertView == null){
                LayoutInflater inflater = getActivity().getLayoutInflater();
                convertView = inflater.inflate(R.layout.feed_list_row, null);
                Log.d("TAG", "create view:" + position);
            }else{
                Log.d("TAG", "use convert view:" + position);
            }

                final TextView feed_list_row_name = (TextView) convertView.findViewById(R.id.feed_list_row_name);
                final TextView feed_list_row_date = (TextView) convertView.findViewById(R.id.feed_list_row_date);
                final TextView feed_list_row_course = (TextView) convertView.findViewById(R.id.feed_list_row_course);
                convertView.setTag(position);

                Summary su = data.get(position);
                feed_list_row_name.setText(su.getName());
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                feed_list_row_date.setText(sdf.format(su.getDateTime().getTime()));
            feed_list_row_course.setText(su.getCourse());



            return convertView;
        }
    }

}
