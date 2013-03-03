package com.example.womenshearthealth;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SettingsHelper {

	public final static String PREF_NAME = "womenshearthealth_preferences";
	
	/**
	 * Returns the age
	 * @param a Should be 'this'.
	 * @return Returns the age. -1 if there is no age.
	 */
	public static int getAge(Activity a) {
		// 'a' needs to be an Activity from within the application that is using SettingsHelper
		// this is in order to access settings that are global only within the application
		
		// grabs the preferences for the application that contains 'a'
		SharedPreferences prefs = a.getApplicationContext().getSharedPreferences(PREF_NAME, 0);
		
		// returns saved age, or -1 if there is no saved age yet
		return prefs.getInt(a.getApplicationContext().getString(R.string.pref_age_key), -1);
	}
	
	/**
	 * Sets the age
	 * @param a Should be 'this'.
	 * @param new_age The new age.
	 * @return Returns true if the new age was saved.
	 */
	public static boolean setAge(Activity a, int new_age) {
		// 'a' needs to be an Activity from within the application that is using SettingsHelper
		// this is in order to access settings that are global only within the application
		
		// grabs the editor for the preferences for the application that contains 'a'
		Editor prefsEditor = a.getApplicationContext().getSharedPreferences(PREF_NAME, 0).edit();
		
		// changes the saved Age value
		prefsEditor.putInt(a.getApplicationContext().getString(R.string.pref_age_key), new_age);
		
		// saves the changes to preferences
		// returns true if successful, false if not
		return prefsEditor.commit();
	}
	
	/**
	 * Returns the weight
	 * @param a Should be 'this'.
	 * @return Returns the Weight. -1 if there is no weight.
	 */
	public static int getWeight(Activity a) {
		// 'a' needs to be an Activity from within the application that is using SettingsHelper
		// this is in order to access settings that are global only within the application
		
		// grabs the preferences for the application that contains 'a'
		SharedPreferences prefs = a.getApplicationContext().getSharedPreferences(PREF_NAME, 0);
		
		// returns saved weight, or -1 if there is no saved weight yet
		return prefs.getInt(a.getApplicationContext().getString(R.string.pref_weight_key), -1);
	}
	
	/**
	 * Sets the weight
	 * @param a Should be 'this'.
	 * @param new_weight The new weight.
	 * @return Returns true if the new weight was saved.
	 */
	public static boolean setWeight(Activity a, int new_weight) {
		// 'a' needs to be an Activity from within the application that is using SettingsHelper
		// this is in order to access settings that are global only within the application
		
		// grabs the editor for the preferences for the application that contains 'a'
		Editor prefsEditor = a.getApplicationContext().getSharedPreferences(PREF_NAME, 0).edit();
		
		// changes the saved Weight value
		prefsEditor.putInt(a.getApplicationContext().getString(R.string.pref_weight_key), new_weight);
		
		// saves the changes to preferences
		// returns true if successful, false if not
		return prefsEditor.commit();
	}
	
	/**
	 * Returns if this is the initial run
	 * @param a Should be 'this'
	 * @return Returns true if it is the first time this method is ever called. False, otherwise.
	 */
	public static boolean isInitialRun(Activity a) {
		// 'a' needs to be an Activity from within the application that is using SettingsHelper
		// this is in order to access settings that are global only within the application
		
		// grabs the preferences for the application that contains 'a'
		SharedPreferences prefs = a.getApplicationContext().getSharedPreferences(PREF_NAME, 0);
		
		// grabs the String used to uniquely identify this preference
		String initRunKey = a.getApplicationContext().getString(R.string.pref_isinitialrun_key); 
		
		// returns the saved isInitialRun value, or true if does not exist
		// 
		// based on the work flow, the first time the SettingsHelper is run, isInitialRun should
		// not yet exist and this returns true. Then elsewhere in the project, the flag is created
		// and set to false
		
		return prefs.getBoolean(initRunKey, true);
	}
	
	/**
	 * Sets the initialRun preference value
	 * @param a Should be 'this'
	 * @param value 'true' or 'false'
	 * @return Returns the initialRun property
	 */
	public static boolean setInitialRun(Activity a, boolean value) {
		// 'a' needs to be an Activity from within the application that is using SettingsHelper
		// this is in order to access settings that are global only within the application
		
		// grabs the editor for the preferences for the application that contains 'a'
		Editor prefsEditor = a.getApplicationContext().getSharedPreferences(PREF_NAME, 0).edit();
		
		// grabs the String used to uniquely identify this preference
		String initRunKey = a.getApplicationContext().getString(R.string.pref_isinitialrun_key); 
		
		// changes the saved isInitialRun value
		
		prefsEditor.putBoolean(initRunKey, value);
		
		// saves the changes to preferences
		// returns true if successful, false if not
		return prefsEditor.commit();
	}
	
}
