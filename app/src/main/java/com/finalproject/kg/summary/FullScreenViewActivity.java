package com.finalproject.kg.summary;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.finalproject.kg.summary.adapter.FullScreenImageAdapter;

import java.util.ArrayList;

public class FullScreenViewActivity extends Activity{

//	private Utils utils;
	private FullScreenImageAdapter adapter;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen_view);

		viewPager = (ViewPager) findViewById(R.id.pager);

//		utils = new Utils(getApplicationContext());

		Intent i = getIntent();
		ArrayList<String> pics = i.getStringArrayListExtra("pics");
//
		adapter = new FullScreenImageAdapter(FullScreenViewActivity.this, pics);

		viewPager.setAdapter(adapter);

		// displaying selected image first
		viewPager.setCurrentItem(0);
	}
}
