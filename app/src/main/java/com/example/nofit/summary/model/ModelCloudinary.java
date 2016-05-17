package com.example.nofit.summary.model;

import android.app.ProgressDialog;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
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
import java.io.IOException;
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

    public String uploadPicture(Bitmap img, String name) throws IOException {




        return "";
    }

//    public String getPicture(){
//
//    }
}
