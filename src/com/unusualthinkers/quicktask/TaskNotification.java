package com.unusualthinkers.quicktask;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class TaskNotification extends Service {
	NotificationManager nm;
	protected boolean mRunning;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent i, int flags, int startid) {
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				TaskNotification.this);
		builder.setSmallIcon(R.drawable.ic_nsi)
				.setContentTitle(
						getResources().getString(R.string.app_name))
				.setOngoing(true).setOnlyAlertOnce(true).setWhen(0)
				.setContentText("TaskNotification이 실행 중 입니다.")
				.setAutoCancel(false);
		i = new Intent(TaskNotification.this, MainActivity.class);
		PendingIntent pi = PendingIntent.getActivity(TaskNotification.this,
				0, i, Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		builder.setContentIntent(pi);
		nm.notify(1, builder.build());
		return START_STICKY_COMPATIBILITY;
	}
	@Override
	public void onDestroy() {
		nm.cancel(1);
		super.onDestroy();
	}
}
