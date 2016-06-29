package com.finalproject.kg.summary;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Date;

//**************************************************
// Global class
// Kobi hay (305623969) & Gadi gomaz (305296139)
//**************************************************
public class Global {

    // Variable of the class
    private final static Global instance = new Global();
    private FloatingActionButton fabBtn;
    private MenuItem item;

    // Empty Constructor
    private Global()
    {

    }

    public static String getDate()
    {
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        //SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return (dateFormatGmt.format(new Date()).toString());
    }

    // Global instance
    public static Global instance() {
        return instance;
    }

    // Set fab btn
    public void setFabBtn(FloatingActionButton fabBtn)
    {
        this.fabBtn = fabBtn;
    }

    // Get fab btn
    public FloatingActionButton getFabBtn()
    {
        return this.fabBtn;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }


    // Set menu item
    public void setItem(MenuItem item)
    {
        this.item = item;
    }

    // Get menu item
    public MenuItem getItem()
    {
        return this.item;
    }
}
