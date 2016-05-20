package com.finalproject.kg.summary.model;

import android.app.ProgressDialog;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.utils.StringUtils;

import org.cloudinary.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gadig on 5/16/2016.
 */
public class ModelCloudinary {

    Cloudinary myCloudinary;
    JSONObject Result;
    ProgressDialog dialog = null;

    public ModelCloudinary() {

        // Init cloudinary data
        Map config = new HashMap();
        config.put("cloud_name", "kg");
        config.put("api_key", "219979565861914");
        config.put("api_secret", "50Juy5wtexCr0wbodYCui8fGPRw");

        // init cloudinary object
        myCloudinary = new Cloudinary(config);
    }


        public Bitmap getPicture(String name) throws IOException {
            URL url = new URL(myCloudinary.url().generate(name));


            return BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }

        public void uploadPicture(final InputStream inputStream, final String publicId) {

            final Map<String, String> options = new HashMap<>();
            options.put("public_id", publicId);

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        myCloudinary.uploader().upload(inputStream, options);
                    } catch (IOException e) {
                        //TODO: better error handling when image uploading fails
                        e.printStackTrace();
                    }
                }
            };

            new Thread(runnable).start();
        }

//    public String getPicture(){
//
//    }
}
