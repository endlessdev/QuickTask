package com.unusualthinkers.quicktask;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

public class TaskHead extends Service {

	private WindowManager wm;
	private static ImageButton taskHead;

	private View layout;
	private WindowManager.LayoutParams paramsF;
	private int mode;
	Handler handler = new Handler();
	private long touchtime;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public static void showView() {
		taskHead.setVisibility(View.VISIBLE);
	}

	public void onCreate() {
		super.onCreate();

		wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		layout = ((LayoutInflater) getApplicationContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.layout_floating, null);
		taskHead = (ImageButton) layout.findViewById(R.id.floating_button);

		final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);

		params.gravity = Gravity.TOP | Gravity.LEFT;
		params.x = 0;
		params.y = 100;

		wm.addView(layout, params);
		paramsF = params;
		attach();
		mode = 1;
		try {
			taskHead.setClickable(true);
			taskHead.setImageResource(R.drawable.ic_head);
			taskHead.setOnTouchListener(new View.OnTouchListener() {
				private int initialX;
				private int initialY;
				private float initialTouchX;
				private float initialTouchY;

				@SuppressLint("NewApi")
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						taskHead.setImageResource(R.drawable.ic_head);
						taskHead.setX(0.0f);
						touchtime = System.currentTimeMillis();
						initialX = paramsF.x;
						initialY = paramsF.y;
						initialTouchX = event.getRawX();
						initialTouchY = event.getRawY();
						mode = 0;
						break;
					case MotionEvent.ACTION_UP:
						taskHead.setImageResource(R.drawable.ic_head);
						if (System.currentTimeMillis() - touchtime <= 75) {
							Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
							vb.vibrate(25);
							Intent i = new Intent(TaskHead.this,
									MainActivity.class);
							i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							getApplication().startActivity(i);
							Toast.makeText(TaskHead.this, "Loading",
									Toast.LENGTH_SHORT).show();
						}
						mode = 1;
						break;
					case MotionEvent.ACTION_MOVE:
						paramsF.x = initialX
								+ (int) (event.getRawX() - initialTouchX);
						paramsF.y = initialY
								+ (int) (event.getRawY() - initialTouchY);
						wm.updateViewLayout(layout, paramsF);
						mode = 0;
						break;
					}
					return false;
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@SuppressLint("NewApi")
	private void attach() {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				attach();
			}
		}, 10);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int x = paramsF.x + layout.getWidth() / 2;
		if (mode == 1) {
			if (paramsF.x < taskHead.getWidth() / 20) {
				if (taskHead.getX() > -taskHead.getWidth() / 2) {
					paramsF.x = 0;
					wm.updateViewLayout(layout, paramsF);
					taskHead.setX(taskHead.getX()
							- (taskHead.getWidth() / 2 + taskHead.getX()) / 10);
					return;
				}
			} else if (size.x - paramsF.x - taskHead.getWidth() < taskHead
					.getWidth() / 20) {
				if (taskHead.getX() < taskHead.getWidth() / 2) {
					paramsF.x = size.x - taskHead.getWidth();
					wm.updateViewLayout(layout, paramsF);
					taskHead.setX(taskHead.getX()
							+ (taskHead.getWidth() / 2 - taskHead.getX()) / 10);
					return;
				}
			}
		}
		if (mode != 1)
			return;
		if (x < 5 || size.x - x - layout.getWidth() / 2 < 5) {
			return;
		}
		if (x < size.x / 2) {
			paramsF.x -= paramsF.x / 10;
		} else {
			paramsF.x += (size.x - layout.getWidth() - paramsF.x) / 10;
		}
		wm.updateViewLayout(layout, paramsF);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (taskHead != null) {
			wm.removeView(layout);
			handler.removeMessages(0);
		}
	}
}
