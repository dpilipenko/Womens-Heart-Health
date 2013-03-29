package com.example.womenshearthealth;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;

public class SplashScreenActivity extends Activity {

	private boolean backButtonIsPressed;
	public static final int SPLASH_DURATION = 1000; // 1 second
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		final boolean isInitRun = isInitalRun(); 
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				finish();
				if (!backButtonIsPressed) {
					
					if (isInitRun) {
						SettingsHelper.setInitialRun(SplashScreenActivity.this, false);
						//SettingsActivity finishes when done. So next activity (Main) will be called here
						Intent intent = new Intent(SplashScreenActivity.this, SettingsActivity.class);
						Intent intent2 = new Intent(SplashScreenActivity.this, MainActivity.class);
						SplashScreenActivity.this.startActivity(intent2);
						SplashScreenActivity.this.startActivity(intent);
						
					} else {
						Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
						SplashScreenActivity.this.startActivity(intent);
					}
					finish();
					
				}
			}
		}, SPLASH_DURATION);
	}

	@Override
	public void onBackPressed() {
		backButtonIsPressed = true;
		super.onBackPressed();
	}
	
	private boolean isInitalRun() {
		return SettingsHelper.isInitialRun(SplashScreenActivity.this);
	}
	

}
