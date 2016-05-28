package com.finalproject.kg.summary;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.finalproject.kg.summary.model.Model;
import com.finalproject.kg.summary.model.Summary;
import com.finalproject.kg.summary.model.SummaryComment;
import com.finalproject.kg.summary.model.SummaryLike;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class NewSummaryFragment extends Fragment {
    private static final int REQUEST_TAKE_PHOTO = 15;
    ImageView iv;
    InputStream in;
    ProgressDialog dialog;

    public NewSummaryFragment() {
        // Required empty public constructor
    }

    public void showBackButton() {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        MenuItem i = item;

        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_summary, container, false);
        final Spinner spinner = (Spinner) view.findViewById(R.id.new_summary_spinner_course);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.course_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        showBackButton();
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Uploading Summary...");

        iv = (ImageView) view.findViewById(R.id.new_summary_image);
        Button new_summary_image_upload = (Button) view.findViewById(R.id.new_summary_image_upload);
        new_summary_image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO);
            }
        });


        Button new_summary_upload_button = (Button) view.findViewById(R.id.new_summary_upload_button);
        new_summary_upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateNewSummary(spinner.getSelectedItem().toString());

            }
        });
        return view;
    }

    public void CreateNewSummary(String Course)
    {
        dialog.show();
        Calendar cc = Calendar.getInstance();
        cc.set(cc.get(Calendar.YEAR), cc.get(Calendar.MONTH),cc.get(Calendar.DAY_OF_MONTH) ,  cc.get(Calendar.HOUR),  cc.get(Calendar.MINUTE),  cc.get(Calendar.SECOND));
        List<SummaryLike> lstLike = new LinkedList<SummaryLike>();
        SummaryLike sl = new SummaryLike();
        sl.setUserId(Model.instance().getUserId());
        sl.setLike(false);
        lstLike.add(sl);

        try {
            Model.instance().uploadPic(in, "post/"+ Model.instance().getUserId()+"/"+ cc.getTimeInMillis());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SummaryComment> lstComment = new LinkedList<SummaryComment>();
        SummaryComment sc = new SummaryComment();
        sc.setShow(false);
        sc.setUserWriterId(Model.instance().getUserId());
        sc.setComment("");
        sc.setDateTime(cc);
        lstComment.add(sc);
        Summary ss = new Summary("",Model.instance().getConnectedStudent().getName(), Model.instance().getUserId(), "post/"+ Model.instance().getUserId()+"/"+ cc.getTimeInMillis(), cc,Course,lstLike,lstComment);
        Model.instance().addSummary(ss,new Model.AddSummaryListener() {
            @Override
            public void done(Summary su) {
                Log.d("TAG", "Wirte New Feed ");
                dialog.dismiss();
                Global.instance().getFabBtn().setVisibility(View.VISIBLE);
                getActivity().onBackPressed();
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == getActivity().RESULT_OK) {
                //File to upload to cloudinary
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                iv.setImageBitmap(imageBitmap);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();
                in = new ByteArrayInputStream(bitmapdata);



            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // User cancelled the image capture
                //finish();
            }
        }

    }

}