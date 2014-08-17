package com.unusualthinkers.quicktask;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.WindowManager;

public class MainActivity extends ActionBarActivity implements
		ActionBar.TabListener {
	int width, height;
	Display display;
	private ActionBar ab;
	private ViewPager vp;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);

		showAsPopup();
		vp = (ViewPager) findViewById(R.id.pager);
		vp.setOnPageChangeListener(onPageChangeListener);
		vp.setAdapter(new VPAdapter(getSupportFragmentManager()));
		addActionBarTabs();
	}

	private ViewPager.SimpleOnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
		@Override
		public void onPageSelected(int position) {
			super.onPageSelected(position);
			ab.setSelectedNavigationItem(position);
		}
	};

	private void addActionBarTabs() {
		ab = getSupportActionBar();
		;
		ActionBar.Tab tab = ab.newTab();
		tab = ab.newTab().setText("Now").setTabListener(tabListener);
		ab.addTab(tab);
		tab = ab.newTab().setText("Apps").setTabListener(tabListener);
		ab.addTab(tab);
		tab = ab.newTab().setText("Actions").setTabListener(tabListener);
		ab.addTab(tab);
		tab = ab.newTab().setText("Settings").setTabListener(tabListener);
		ab.addTab(tab);
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	}

	private ActionBar.TabListener tabListener = new ActionBar.TabListener() {
		@Override
		public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
			vp.setCurrentItem(tab.getPosition());
		}

		@Override
		public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
		}

		@Override
		public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
		}
	};

	@SuppressWarnings("deprecation")

	public void showAsPopup() {
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE))
				.getDefaultDisplay();
		width = (int) (display.getWidth() * 0.90);
		height = (int) (display.getHeight() * 0.80);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,
				WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.alpha = 1.0f;
		params.dimAmount = 0.5f;
		getWindow().setAttributes(params);
		getWindow().setLayout(width, height);
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}
}
