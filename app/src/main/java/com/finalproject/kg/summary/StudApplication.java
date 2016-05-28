package com.finalproject.kg.summary;


import android.app.Application;
import android.content.Context;

//**************************************************
// StudA pplication
// Kobi hay (305623969) & Gadi gomaz (305296139)
//**************************************************
public class StudApplication extends Application {
    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
