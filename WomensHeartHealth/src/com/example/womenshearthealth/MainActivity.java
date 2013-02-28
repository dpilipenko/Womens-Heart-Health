package com.example.womenshearthealth;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	// This is a comment

	private ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupTabs();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		switch(item.getItemId()) {
		case R.id.menu_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			this.startActivity(intent);
			return true;
		default:
			return false;
				
		}
		
	}

	private void setupTabs() {
		
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab homeTab = actionBar.newTab();
		homeTab.setText("Home");
		homeTab.setTabListener(new TabListener<HomeFragment>(this,
				"home", HomeFragment.class));
		actionBar.addTab(homeTab);
		
		Tab metsTab = actionBar.newTab();
		metsTab.setText("METs");
		metsTab.setTabListener(new TabListener<METListFragment>(this,
				"mets", METListFragment.class));
		actionBar.addTab(metsTab);
		
		Tab aboutTab = actionBar.newTab();
		aboutTab.setText("About");
		aboutTab.setTabListener(new TabListener<AboutFragment>(this,
				"about", AboutFragment.class));
		actionBar.addTab(aboutTab);
		
	}

}
