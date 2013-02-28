package com.example.womenshearthealth;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.view.Menu;

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
