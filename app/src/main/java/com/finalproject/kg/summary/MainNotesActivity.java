package com.finalproject.kg.summary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.finalproject.kg.summary.model.Model;
import com.finalproject.kg.summary.model.Summary;
import com.finalproject.kg.summary.model.SummaryComment;
import com.finalproject.kg.summary.model.SummaryLike;

import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class MainNotesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout mDrawer;
    FloatingActionButton fabBtn;

    Fragment fragmentNewSummary = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ProgressBar content_main_notes_pb = (ProgressBar) findViewById(R.id.content_main_notes_pb);
        content_main_notes_pb.setVisibility(View.VISIBLE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.beginTransaction().commit();

        Fragment fragment = null;
        Class fragmentClass;
        fragmentClass = FeedListFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ((FeedListFragment)fragment).getFragmentManager(fragmentManager);
        fragmentManager.beginTransaction().replace(R.id.main_frag_container, fragment).commit();

        fabBtn = (FloatingActionButton) findViewById(R.id.fab);
        Global.instance().setFabBtn(fabBtn);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabBtn.setVisibility(View.GONE);
                Model.instance().getConnectedStudent();
//
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                //Fragment fragment = null;
                Class fragmentClass = NewSummaryFragment.class;
                try {
                    fragmentNewSummary = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_frag_container, fragmentNewSummary).addToBackStack(null).commit();
            }
        });

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    onBackPressed();
                }else if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                    mDrawer.closeDrawer(GravityCompat.START);
                } else {
                    mDrawer.openDrawer(Gravity.LEFT);
                }
                toggle.syncState();
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        content_main_notes_pb.setVisibility(View.GONE);
    }

    public void showFloatingActionButton() {
        fabBtn.show();
    };

    public void hideFloatingActionButton() {
        fabBtn.hide();
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (getSupportFragmentManager().getBackStackEntryCount()>0) {
            getFragmentManager().popBackStack();


            if(!Global.instance().getItem().isVisible()) {
                Global.instance().getItem().setVisible(true);

            }
            fabBtn.setVisibility(View.VISIBLE);
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_notes, menu);
        Global.instance().setItem(menu.findItem(R.id.action_filter));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.action_filter) {


            item.setVisible(false);
        }
        fabBtn.setVisibility(View.GONE);
        Log.d("TAG", "clickkkk " + id);

        Fragment fragment = null;
        Class fragmentClass = CourseListFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_frag_container, fragment).addToBackStack(null).commit();

        return super.onOptionsItemSelected(item);
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE! Make sure to override the method with only a single `Bundle` argument
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        Class fragmentClass;
        switch(item.getItemId()) {
            case R.id.nav_feed:
                fragmentClass = FeedListFragment.class;
                break;
            case R.id.nav_profile:
                Global.instance().getFabBtn().setVisibility(View.GONE);
                fragmentClass = ProfileFragment.class;
                break;
            default:
                fragmentClass = ProfileFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_frag_container, fragment).addToBackStack(null).commit();

        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);
        // Set action bar title
        setTitle(item.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();

        return true;
    }
}

