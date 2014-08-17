package com.unusualthinkers.quicktask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class SettingsActivity extends PreferenceFragment {
	CheckBoxPreference th, tn, ts;
	Preference ns;
	int width, height;
	Display display;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		view.setBackgroundColor(getResources().getColor(android.R.color.white));

		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.activity_settings);
		th = (CheckBoxPreference) findPreference("th");
		tn = (CheckBoxPreference) findPreference("tn");
		th.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				boolean value = (Boolean) newValue;
				Intent th = new Intent(getActivity(), TaskHead.class);
				if (value) {
					getActivity().startService(th);
				} else {
					getActivity().stopService(th);
				}
				return true;
			}
		});
		tn.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				boolean value = (Boolean) newValue;
				Intent tn = new Intent(getActivity(), TaskNotification.class);
				if (value) {
					getActivity().startService(tn);
				} else {
					getActivity().stopService(tn);
				}
				return true;
			}
		});
	}
}
