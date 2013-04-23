package com.example.womenshearthealth.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.IntentSender.SendIntentException;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.test.ViewAsserts;
import android.test.MoreAsserts;

import com.example.womenshearthealth.*;
import com.example.womenshearthealth.helpers.SettingsHelper;

public class SettingsActivityTest extends ActivityInstrumentationTestCase2<SettingsActivity> {
	
	private SettingsActivity mActivity;
	private View mBirthday, mWeight;
	
	public SettingsActivityTest() {
		this("SettingsActivityTest");
	}
	
	@SuppressWarnings("deprecation")
	public SettingsActivityTest(String name) {
		super("com.example.womenshearthealth", SettingsActivity.class);
		setName(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mActivity = getActivity();
		mBirthday = mActivity.findViewById(com.example.womenshearthealth.R.id.txtvw_settings_bday);
		mWeight   = mActivity.findViewById(com.example.womenshearthealth.R.id.txtvw_settings_weight);
	}
	
	/**
	 * Quick test to assert setUp() worked correctly
	 */
	public final void testPreconditions() {
		assertNotNull(mActivity);
	}
	
	/**
	 * Test to assert that input fields for settings activity are instantiated
	 */
	public final void testHasInputFields() {
		assertNotNull(mBirthday);
		assertNotNull(mWeight);
	}
	
	/**
	 * Test to assert that input fields for settings activity appear on the
	 * screen of the device
	 */
	public final void testFieldsOnScreen() {
		final Window window = mActivity.getWindow();
		final View origin = window.getDecorView();
		ViewAsserts.assertOnScreen(origin, mBirthday);
		ViewAsserts.assertOnScreen(origin, mWeight);
	}
	
	/**
	 * Test to assert changing the weight in the settings helper
	 * works properly and persists
	 */
	public final void testSettingsHelperSetWeight() {
		int expectedWeight, actualWeight;
		String errorMessage;
		
		/* Test 1 */
		expectedWeight = 150;
		
		SettingsHelper.setWeight(mActivity, expectedWeight);
		actualWeight = SettingsHelper.getWeight(mActivity);
		
		errorMessage = "Weights don't match. Expected: " + expectedWeight 
				+ "; Actual: " + actualWeight;
		
		assertTrue(errorMessage, expectedWeight == actualWeight);
		
		mActivity.finish();
		mActivity = getActivity();
		
		errorMessage = "Weights don't match. Expected: " + expectedWeight 
				+ "; Actual: " + actualWeight;
		
		assertTrue(errorMessage, expectedWeight == actualWeight);
		
		/* Test 2 */
		expectedWeight = 185;
		
		SettingsHelper.setWeight(mActivity, expectedWeight);
		actualWeight = SettingsHelper.getWeight(mActivity);
		
		errorMessage = "Weights don't match. Expected: " + expectedWeight 
				+ "; Actual: " + actualWeight;
		
		assertTrue(errorMessage, expectedWeight == actualWeight);
		
		mActivity.finish();
		mActivity = getActivity();
		
		errorMessage = "Weights don't match. Expected: " + expectedWeight 
				+ "; Actual: " + actualWeight;
		
		assertTrue(errorMessage, expectedWeight == actualWeight);
	}
	
	/**
	 * Test to assert changing the birthdate in the settings helper
	 * works properly and persists
	 */
	@SuppressWarnings("deprecation")
	public final void testSettingsHelperSetBirthdate() {
		Date expectedDate, actualDate;
		String errorMessage;
		
		/* Test 1 */
		expectedDate = new Date(1991, 7, 17);
		SettingsHelper.setBirthdate(mActivity, expectedDate);
		actualDate = SettingsHelper.getBirthdate(mActivity);
		
		errorMessage = "Years don't match. Expected: " + expectedDate.getYear()
				+ "; Actual: " + actualDate.getYear();		
		assertTrue(errorMessage, expectedDate.getYear() == actualDate.getYear());
		
		errorMessage = "Months don't match. Expected: " + expectedDate.getMonth()
				+ "; Actual: " + actualDate.getMonth();		
		assertTrue(errorMessage, expectedDate.getMonth() == actualDate.getMonth());
		
		errorMessage = "Dates don't match. Expected: " + expectedDate.getDate()
				+ "; Actual: " + actualDate.getDate();		
		assertTrue(errorMessage, expectedDate.getDate() == actualDate.getDate());
		
		/* Test 2 */
		expectedDate = new Date(1970, 3, 5);
		SettingsHelper.setBirthdate(mActivity, expectedDate);
		actualDate = SettingsHelper.getBirthdate(mActivity);
		
		errorMessage = "Years don't match. Expected: " + expectedDate.getYear()
				+ "; Actual: " + actualDate.getYear();		
		assertTrue(errorMessage, expectedDate.getYear() == actualDate.getYear());
		
		errorMessage = "Months don't match. Expected: " + expectedDate.getMonth()
				+ "; Actual: " + actualDate.getMonth();		
		assertTrue(errorMessage, expectedDate.getMonth() == actualDate.getMonth());
		
		errorMessage = "Dates don't match. Expected: " + expectedDate.getDate()
				+ "; Actual: " + actualDate.getDate();		
		assertTrue(errorMessage, expectedDate.getDate() == actualDate.getDate());
	}

}
