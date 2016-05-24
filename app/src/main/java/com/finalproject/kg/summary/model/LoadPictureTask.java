package com.finalproject.kg.summary.model;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by gadig on 5/24/2016.
 */
public class LoadPictureTask extends AsyncTask<Object, Void, Bitmap> {
    View view;
    Bitmap bmp;

    @Override
    protected Bitmap doInBackground(Object... objects) {
        view = (View) objects[0];
        String uri = (String)objects[1];


        try {
            bmp = Model.instance().getPic(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null && view != null) {

            ((ImageView)view).setImageBitmap(bitmap);
        }
    }

}
