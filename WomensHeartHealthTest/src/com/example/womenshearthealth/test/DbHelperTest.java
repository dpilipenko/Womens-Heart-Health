package com.example.womenshearthealth.test;

import java.util.Date;
import java.util.List;

import com.example.womenshearthealth.*;
import com.example.womenshearthealth.helpers.*;
import com.example.womenshearthealth.models.*;

import android.test.ActivityInstrumentationTestCase2;

public class DbHelperTest extends ActivityInstrumentationTestCase2<AllMetsPrintedActivity> {

	private AllMetsPrintedActivity mActivity;
	private SQLDatabaseHelper mSqlDatabaseHelper;
	
	public DbHelperTest() {
		this("DbHelperTest");
	}
	
	public DbHelperTest(String name) {
		super("com.example.womenshearthealth", AllMetsPrintedActivity.class);
		setName(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mActivity = getActivity();
		mSqlDatabaseHelper = new SQLDatabaseHelper(mActivity);
	}
	
	/**
	 * Quick test to assert setUp() worked correctly
	 */
	public final void testPreconitions() {
		assertNotNull(mActivity);
		assertNotNull(mSqlDatabaseHelper);
	}
	
	/**
	 * Test to assert that adding a MetActivity object to the DB works correctly
	 */
	public final void testAddMetActivity() {
		String metActivityName = "Activity Example 1";
		Double metActivityMets = 5.5;
		int    metActivityMins = 35;
		Date   metActivityDate = new Date();
		
		MetActivity metActivity = new MetActivity(null, metActivityName, metActivityMets, metActivityMins);
		
		mSqlDatabaseHelper.saveMetActivity(metActivity);
		
		List<MetActivity> listActivities = mSqlDatabaseHelper.getAllMetActivities();
		
		System.out.println(metActivity.toString());
		
		boolean foundActivity = false;
		MetActivity addedRecord = null;
		for (MetActivity tempActivity : listActivities) {
			System.out.println(tempActivity.toString());
			
			String tempActivityName = tempActivity.getName();
			Double tempActivityMets = tempActivity.getMetsvalue();
			int    tempActivityMins = tempActivity.getMinutes();
			Date   tempActivityDate = tempActivity.getDateSaved();
			
			if (tempActivityName.equalsIgnoreCase(metActivityName) &&
					tempActivityMets.equals(metActivityMets) &&
					tempActivityMins == metActivityMins &&
					tempActivityDate.getYear() == metActivityDate.getYear() &&
					tempActivityDate.getMonth() == metActivityDate.getMonth() &&
					tempActivityDate.getDay() == metActivityDate.getDay()){
				
				foundActivity = true;
				addedRecord = tempActivity;
				break;				
			}					
		}
		
		mSqlDatabaseHelper.deleteMetActivity(addedRecord);
		
		String message = "MET Activity not successfully added to DB.";
		assertTrue(message, foundActivity);
	}

}
