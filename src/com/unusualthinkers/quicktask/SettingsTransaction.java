package com.unusualthinkers.quicktask;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class SettingsTransaction extends Fragment {

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_settings, container, false);
		FragmentManager fragmentManager = getActivity().getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		SettingsActivity fragment1 = new SettingsActivity();
		fragmentTransaction.add(R.id.settings_layout, fragment1);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
		return v;
	}
}