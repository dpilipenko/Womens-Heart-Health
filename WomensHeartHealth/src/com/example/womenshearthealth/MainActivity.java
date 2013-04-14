package com.example.womenshearthealth;


import com.example.womenshearthealth.helpers.SettingsHelper;
import com.example.womenshearthealth.utils.TabListener;

import android.os.Bundle;
import android.os.Handler;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	private ActionBar actionBar; //holds the tabs
	private Dialog splashscreenDialog;
	private int SPLASH_DURATION = 2000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//takes care of whatever overhead it needs
		super.onCreate(savedInstanceState);

        showSplashScreen();
        
		//loads activity_main.xml UI
		setContentView(R.layout.activity_main);
		
		//adds tab navigation to activity
		setupTabs();
        if (savedInstanceState != null) {
        	actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
    }

	protected void showSplashScreen() {
		splashscreenDialog = new Dialog(this, R.style.SplashScreen);
		splashscreenDialog.setTitle(R.string.app_name);
		ImageView i = new ImageView(this);
		
		i.setOnTouchListener(new OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
            	dismissSplashScreen(); 
                return false;
            }
            
       });
		
		Bitmap myImage = BitmapFactory.decodeResource(getResources(), R.drawable.splashscreen);
		i.setImageBitmap(myImage);
		splashscreenDialog.setContentView(i);
		splashscreenDialog.setCancelable(false);
		splashscreenDialog.show();
		
		// Set Runnable to remove splash screen just in case
	    final Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {
	      @Override
	      public void run() {

	    	  dismissSplashScreen();
	    	  
	    	  if (isInitalRun()) {
	    		  SettingsHelper.setInitialRun(MainActivity.this, false);
	    		  Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
	    		  startActivity(intent);
				
				} else {
					
				}
	    	  
	      }
	    }, SPLASH_DURATION);
		
	}

	private boolean isInitalRun() {
		return SettingsHelper.isInitialRun(this);
	}
	
	protected void dismissSplashScreen() {
		if (splashscreenDialog != null) {
			splashscreenDialog.dismiss();
			splashscreenDialog = null;
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
