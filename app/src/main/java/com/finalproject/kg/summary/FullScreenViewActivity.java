package com.finalproject.kg.summary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.finalproject.kg.summary.adapter.FullScreenImageAdapter;
import java.util.ArrayList;

//**************************************************
// Full Screen View Activity
// This Activity show the summary in full screen
// Kobi hay (305623969) & Gadi gomaz (305296139)
//**************************************************
public class FullScreenViewActivity extends Activity{

	// Private variable
	private FullScreenImageAdapter adapter;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen_view);

		// Set the pager in the viewPager param
		viewPager = (ViewPager) findViewById(R.id.pager);

		// New Intent
		Intent i = getIntent();
		ArrayList<String> pics = i.getStringArrayListExtra("pics");

		// Set the full screen image in the Adapter
		adapter = new FullScreenImageAdapter(FullScreenViewActivity.this, pics);

		// Link the Adapter to the viewPager
		viewPager.setAdapter(adapter);

		// displaying selected image first
		viewPager.setCurrentItem(0);
	}
}
