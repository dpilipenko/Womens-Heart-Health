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
		SharedPreferences prefs = a.getApplicationContext().getSharedPreferences(PREF_NAME, 0);
		return prefs.getInt(a.getApplicationContext().getString(R.string.pref_age_key), -1);
	}
	
	/**
	 * Sets the age
	 * @param a Should be 'this'.
	 * @param new_age The new age.
	 * @return Returns true if the new age was saved.
	 */
	public static boolean setAge(Activity a, int new_age) {
		Editor prefsEditor = a.getApplicationContext().getSharedPreferences(PREF_NAME, 0).edit();
		prefsEditor.putInt(a.getApplicationContext().getString(R.string.pref_age_key), new_age);
		return prefsEditor.commit();
	}
	
	/**
	 * Returns the weight
	 * @param a Should be 'this'.
	 * @return Returns the Weight. -1 if there is no weight.
	 */
	public static int getWeight(Activity a) {
		SharedPreferences prefs = a.getApplicationContext().getSharedPreferences(PREF_NAME, 0);
		return prefs.getInt(a.getApplicationContext().getString(R.string.pref_weight_key), -1);
	}
	
	/**
	 * Sets the weight
	 * @param a Should be 'this'.
	 * @param new_weight The new weight.
	 * @return Returns true if the new weight was saved.
	 */
	public static boolean setWeight(Activity a, int new_weight) {
		Editor prefsEditor = a.getApplicationContext().getSharedPreferences(PREF_NAME, 0).edit();
		prefsEditor.putInt(a.getApplicationContext().getString(R.string.pref_weight_key), new_weight);
		return prefsEditor.commit();
	}
	
	/**
	 * Returns if this is the initial run
	 * @param a Should be 'this'
	 * @return Returns true if it is the first time this method is ever called. False, otherwise.
	 */
	public static boolean isInitialRun(Activity a) {
		SharedPreferences prefs = a.getApplicationContext().getSharedPreferences(PREF_NAME, 0);
		return prefs.getBoolean("isInitialRun", true);
	}
	
	/**
	 * Sets the initialRun preference value
	 * @param a Should be 'this'
	 * @param value 'true' or 'false'
	 * @return Returns the initialRun property
	 */
	public static boolean setInitialRun(Activity a, boolean value) {
		Editor prefsEditor = a.getApplicationContext().getSharedPreferences(PREF_NAME, 0).edit();
		prefsEditor.putBoolean("isInitialRun", value);
		return prefsEditor.commit();
	}
	
}
