package com.example.nofit.summary;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nofit.summary.model.Model;

import java.util.List;

public class FeedListFragment extends Fragment {

    ListView list;
    //List<Student> data;
    MyAddapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed_list, container, false);
        list = (ListView) view.findViewById(R.id.feed_listview);

        //data = Model.instance().getStudents();

        adapter = new MyAddapter();
        list.setAdapter(adapter);

        return view;
    }

    class MyAddapter extends BaseAdapter {


        @Override
        public int getCount() {
            //return data.size();
            return 1;
        }

        @Override
        public Object getItem(int position) {
            //return data.get(position);
            return new Object();
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

            return convertView;
        }
    }

}
