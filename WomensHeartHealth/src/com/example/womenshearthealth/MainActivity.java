package com.example.womenshearthealth;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	private ActionBar actionBar; //holds the tabs
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//takes care of whatever overhead it needs
		super.onCreate(savedInstanceState);
		//loads activity_main.xml UI
		setContentView(R.layout.activity_main);
		//adds tab navigation to activity
		setupTabs();
        if (savedInstanceState != null) {
        	actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
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
		
		case R.id.menu_settings: //user selects "Settings" from the menu
			
			//sets up a redirect from 'this' activity to a new instance of SettingsActivity
			//this is done only to go from Activity to Activity, not for Fragments
			Intent intent = new Intent(this, SettingsActivity.class);
			//this executes the redirect
			this.startActivity(intent);
	
			return true;
			
		default:
			return false;
				
		}
		
	}

	/**
	 * Initializes and displays the tabs
	 */
	private void setupTabs() {
		
		//initializes the action bar
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		//creates a new tab, "Home"
		Tab homeTab = actionBar.newTab();
		homeTab.setText("Home");
		
		//the TabListener waits for tab to be clicked on to execute code
		//if you ever need to add a new tab, just follow the same pattern 
		homeTab.setTabListener(new TabListener<HomeFragment>(this,
				"home", HomeFragment.class));
		//adds Home tab to the action bar
		actionBar.addTab(homeTab);
		
		//repeat process for METs and About tab
		
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
