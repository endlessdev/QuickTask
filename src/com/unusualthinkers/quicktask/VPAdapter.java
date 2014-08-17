package com.unusualthinkers.quicktask;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class VPAdapter extends FragmentPagerAdapter {
	 
    private final int PAGES = 4;
 
    public VPAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NowActivity();
            case 1:
                return new AppsActivity();
            case 2:
                return new ActionsActivity();
            case 3:
            	return new SettingsTransaction();
            default:
                throw new IllegalArgumentException("" + PAGES);
        }
    }
 
    @Override
    public int getCount() {
        return PAGES;
    }
}