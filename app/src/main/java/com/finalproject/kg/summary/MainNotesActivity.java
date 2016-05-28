package com.finalproject.kg.summary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

//**************************************************
// Main Notes Activity
// This is the main notes activity
// Kobi hay (305623969) & Gadi gomaz (305296139)
//**************************************************
public class MainNotesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Variable of the class
    DrawerLayout mDrawer;
    FloatingActionButton fabBtn;
    Fragment fragmentNewSummary = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_notes);

        // Get the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get the ProgressBar
        ProgressBar content_main_notes_pb = (ProgressBar) findViewById(R.id.content_main_notes_pb);

        // Show the Progress Bar -> loading
        content_main_notes_pb.setVisibility(View.VISIBLE);

        // Get the Support Fragment Manager
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        Class fragmentClass;
        fragmentClass = FeedListFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set visible the feed fragment
        ((FeedListFragment)fragment).getFragmentManager(fragmentManager);
        fragmentManager.beginTransaction().replace(R.id.main_frag_container, fragment).commit();

        // Get the fab button
        fabBtn = (FloatingActionButton) findViewById(R.id.fab);

        // Save the fab button in the global class
        Global.instance().setFabBtn(fabBtn);

        // On click the fab button
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hide the fab button
                fabBtn.setVisibility(View.GONE);

                // Get the Connected Student
                Model.instance().getConnectedStudent();
                Class fragmentClass = NewSummaryFragment.class;
                try {
                    fragmentNewSummary = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Over to the new summary fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_frag_container, fragmentNewSummary).addToBackStack(null).commit();
            }
        });

        // Get the Drawer layout
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        // Set the  Navigation On Click Listener
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

        // Get the navigation View
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Hide the Progress Bar -> finish loading
        content_main_notes_pb.setVisibility(View.GONE);
    }

    // Show the Floating Action Button
    public void showFloatingActionButton() {
        fabBtn.show();
    };

    // Hide the Floating Action Button
    public void hideFloatingActionButton() {
        fabBtn.hide();
    };


    // On click Back
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (getSupportFragmentManager().getBackStackEntryCount()>0) {
            // Go out the fragment
            getFragmentManager().popBackStack();

            // If the item dont show
            if(!Global.instance().getItem().isVisible()) {
                Global.instance().getItem().setVisible(true);

            }

            // Show the fab btn
            fabBtn.setVisibility(View.VISIBLE);
            super.onBackPressed();
        }
    }

    // on Activity Result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    // On create the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_notes, menu);
        Global.instance().setItem(menu.findItem(R.id.action_filter));
        return true;
    }

    // On item in the menu selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // if this is the course filter
        if(id == R.id.action_filter) {

            // Hide the course button
            item.setVisible(false);
        }

        // Hide the fab btn
        fabBtn.setVisibility(View.GONE);

        // Show the main Course list fragment
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

