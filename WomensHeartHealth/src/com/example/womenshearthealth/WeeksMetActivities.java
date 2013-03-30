package com.example.womenshearthealth;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class WeeksMetActivities extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weeks_met_activities);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weeks_met_activities, menu);
		return true;
	}

}
