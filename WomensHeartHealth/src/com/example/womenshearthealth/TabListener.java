package com.example.womenshearthealth;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Build;


public class TabListener<T extends Fragment> implements ActionBar.TabListener {
	
	private Fragment fragment;
	private final Activity activity;
	private final Class<T> fragmentClass;
	private final String tag;
	
	public TabListener(Activity activity, String tag, Class<T> fragmentClass)
	{
		this.activity = activity;
		this.fragmentClass = fragmentClass;
		this.tag = tag;
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft)
	{	
		if (fragment == null) 
		{
			fragment = (Fragment)Fragment.instantiate(activity, fragmentClass.getName());
			ft.add(R.id.fragmentContainer, fragment, tag);
		} else {
			ft.attach(fragment);
		}	
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		
	}


	@SuppressLint("NewApi")
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		if (fragment != null) {
			ft.detach(fragment);
			//deven test comment.
		}
	}


}
