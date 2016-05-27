package com.finalproject.kg.summary;

import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;

/**
 * Created by Kobi on 27/05/2016.
 */
public class Global {
    private final static Global instance = new Global();
    private FloatingActionButton fabBtn;
    private MenuItem item;

    private Global()
    {

    }

    public static Global instance() {
        return instance;
    }

    public void setFabBtn(FloatingActionButton fabBtn)
    {
        this.fabBtn = fabBtn;
    }

    public FloatingActionButton getFabBtn()
    {
        return this.fabBtn;
    }

    public void setItem(MenuItem item)
    {
        this.item = item;
    }

    public MenuItem getItem()
    {
        return this.item;
    }
}
