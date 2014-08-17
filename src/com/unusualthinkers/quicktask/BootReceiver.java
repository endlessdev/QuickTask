package com.unusualthinkers.quicktask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context c, Intent i) {
		PreferenceManager.setDefaultValues(c, R.xml.activity_settings, false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        boolean th = sharedPreferences.getBoolean("th", true);
        boolean tn = sharedPreferences.getBoolean("tn", true);
		if (i.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			Log.d("BootReceiver","onReceive");
			if(th == true) {
				Intent intent = new Intent (c,TaskHead.class);
				c.startService(intent);
				Log.d("BootReceiver","TaskHead");
			}
			if(tn == true) {
				Intent intent = new Intent (c,TaskNotification.class);
				c.startService(intent);
				Log.d("BootReceiver","TaskNotification");
			}
		}
	}
}
