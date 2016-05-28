package com.finalproject.kg.summary;

import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;

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
