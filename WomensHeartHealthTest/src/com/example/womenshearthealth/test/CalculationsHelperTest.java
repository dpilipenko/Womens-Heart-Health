package com.example.womenshearthealth.test;

import junit.framework.TestCase;
import com.example.womenshearthealth.*;
import com.example.womenshearthealth.utils.CalculationsHelper;

public class CalculationsHelperTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testCalculateMaxHeartRate() {
		int age, expectedHR, calculatedHR;
		
		age = 100;
		expectedHR = 118;
		calculatedHR = CalculationsHelper.getTargetHeartRateFromAge(age, CalculationsHelper.TARGET_MAX);
		assertTrue("Age: " + age + "; Expected HR: " + expectedHR + "; Calculated HR: " + calculatedHR + ".",
				calculatedHR == expectedHR);
		
		age = 80;
		expectedHR = 135;
		calculatedHR = CalculationsHelper.getTargetHeartRateFromAge(age, CalculationsHelper.TARGET_MAX);
		assertTrue("Age: " + age + "; Expected HR: " + expectedHR + "; Calculated HR: " + calculatedHR + ".",
				calculatedHR == expectedHR);
		
		age = 60;
		expectedHR = 153;
		calculatedHR = CalculationsHelper.getTargetHeartRateFromAge(age, CalculationsHelper.TARGET_MAX);
		assertTrue("Age: " + age + "; Expected HR: " + expectedHR + "; Calculated HR: " + calculatedHR + ".",
				calculatedHR == expectedHR);
		
		age = 40;
		expectedHR = 170;
		calculatedHR = CalculationsHelper.getTargetHeartRateFromAge(age, CalculationsHelper.TARGET_MAX);
		assertTrue("Age: " + age + "; Expected HR: " + expectedHR + "; Calculated HR: " + calculatedHR + ".",
				calculatedHR == expectedHR);
		
		age = 20;
		expectedHR = 188;
		calculatedHR = CalculationsHelper.getTargetHeartRateFromAge(age, CalculationsHelper.TARGET_MAX);
		assertTrue("Age: " + age + "; Expected HR: " + expectedHR + "; Calculated HR: " + calculatedHR + ".",
				calculatedHR == expectedHR);
		
		age = 0;
		expectedHR = 206;
		calculatedHR = CalculationsHelper.getTargetHeartRateFromAge(age, CalculationsHelper.TARGET_MAX);
		assertTrue("Age: " + age + "; Expected HR: " + expectedHR + "; Calculated HR: " + calculatedHR + ".",
				calculatedHR == expectedHR);
	}
	
	public void testCalculate85HeartRate() {
		int age, expectedHR, calculatedHR;
		
		age = 100;
		expectedHR = 100;
		calculatedHR = CalculationsHelper.getTargetHeartRateFromAge(age, CalculationsHelper.TARGET_85);
		assertTrue("Age: " + age + "; Expected HR: " + expectedHR + "; Calculated HR: " + calculatedHR + ".",
				calculatedHR == expectedHR);
		
		age = 80;
		expectedHR = 115;
		calculatedHR = CalculationsHelper.getTargetHeartRateFromAge(age, CalculationsHelper.TARGET_85);
		assertTrue("Age: " + age + "; Expected HR: " + expectedHR + "; Calculated HR: " + calculatedHR + ".",
				calculatedHR == expectedHR);
		
		age = 60;
		expectedHR = 130;
		calculatedHR = CalculationsHelper.getTargetHeartRateFromAge(age, CalculationsHelper.TARGET_85);
		assertTrue("Age: " + age + "; Expected HR: " + expectedHR + "; Calculated HR: " + calculatedHR + ".",
				calculatedHR == expectedHR);
		
		age = 40;
		expectedHR = 145;
		calculatedHR = CalculationsHelper.getTargetHeartRateFromAge(age, CalculationsHelper.TARGET_85);
		assertTrue("Age: " + age + "; Expected HR: " + expectedHR + "; Calculated HR: " + calculatedHR + ".",
				calculatedHR == expectedHR);
		
		age = 20;
		expectedHR = 160;
		calculatedHR = CalculationsHelper.getTargetHeartRateFromAge(age, CalculationsHelper.TARGET_85);
		assertTrue("Age: " + age + "; Expected HR: " + expectedHR + "; Calculated HR: " + calculatedHR + ".",
				calculatedHR == expectedHR);
		
		age = 0;
		expectedHR = 175;
		calculatedHR = CalculationsHelper.getTargetHeartRateFromAge(age, CalculationsHelper.TARGET_85);
		assertTrue("Age: " + age + "; Expected HR: " + expectedHR + "; Calculated HR: " + calculatedHR + ".",
				calculatedHR == expectedHR);
	}
	
	public void testCalculate50HeartRate() {
		int age, expectedHR, calculatedHR;
		
		age = 100;
		expectedHR = 59;
		calculatedHR = CalculationsHelper.getTargetHeartRateFromAge(age, CalculationsHelper.TARGET_50);
		assertTrue("Age: " + age + "; Expected HR: " + expectedHR + "; Calculated HR: " + calculatedHR + ".",
				calculatedHR == expectedHR);
		
		age = 80;
		expectedHR = 67;
		calculatedHR = CalculationsHelper.getTargetHeartRateFromAge(age, CalculationsHelper.TARGET_50);
		assertTrue("Age: " + age + "; Expected HR: " + expectedHR + "; Calculated HR: " + calculatedHR + ".",
				calculatedHR == expectedHR);
		
		age = 60;
		expectedHR = 76;
		calculatedHR = CalculationsHelper.getTargetHeartRateFromAge(age, CalculationsHelper.TARGET_50);
		assertTrue("Age: " + age + "; Expected HR: " + expectedHR + "; Calculated HR: " + calculatedHR + ".",
				calculatedHR == expectedHR);
		
		age = 40;
		expectedHR = 85;
		calculatedHR = CalculationsHelper.getTargetHeartRateFromAge(age, CalculationsHelper.TARGET_50);
		assertTrue("Age: " + age + "; Expected HR: " + expectedHR + "; Calculated HR: " + calculatedHR + ".",
				calculatedHR == expectedHR);
		
		age = 20;
		expectedHR = 94;
		calculatedHR = CalculationsHelper.getTargetHeartRateFromAge(age, CalculationsHelper.TARGET_50);
		assertTrue("Age: " + age + "; Expected HR: " + expectedHR + "; Calculated HR: " + calculatedHR + ".",
				calculatedHR == expectedHR);
		
		age = 0;
		expectedHR = 103;
		calculatedHR = CalculationsHelper.getTargetHeartRateFromAge(age, CalculationsHelper.TARGET_50);
		assertTrue("Age: " + age + "; Expected HR: " + expectedHR + "; Calculated HR: " + calculatedHR + ".",
				calculatedHR == expectedHR);
	}
	
	public void testCalculateCaloriesFromMet()
	{
		double ACCEPTABLE_PERCENT_ERROR = 0.05;
		
		int weight;
		double metHours, calculatedCals, expectedCals;
		
		weight = 220;
		metHours = 3;
		expectedCals = 299;
		calculatedCals = CalculationsHelper.getCaloriesFromMetHours(weight, metHours);
		assertTrue("Calculated Calories (" + calculatedCals + ") outside acceptable error of " +
					"Expected Calories (" + expectedCals + "). Acceptable Error: " + ACCEPTABLE_PERCENT_ERROR,
				(Math.abs(expectedCals - calculatedCals) / expectedCals) < ACCEPTABLE_PERCENT_ERROR);
		
		weight = 100;
		metHours = 13.5;
		expectedCals = 612;
		calculatedCals = CalculationsHelper.getCaloriesFromMetHours(weight, metHours);
		assertTrue("Calculated Calories (" + calculatedCals + ") outside acceptable error of " +
					"Expected Calories (" + expectedCals + "). Acceptable Error: " + ACCEPTABLE_PERCENT_ERROR,
				(Math.abs(expectedCals - calculatedCals) / expectedCals) < ACCEPTABLE_PERCENT_ERROR);
		
		weight = 135;
		metHours = 3.3;
		expectedCals = 202;
		calculatedCals = CalculationsHelper.getCaloriesFromMetHours(weight, metHours);
		assertTrue("Calculated Calories (" + calculatedCals + ") outside acceptable error of " +
					"Expected Calories (" + expectedCals + "). Acceptable Error: " + ACCEPTABLE_PERCENT_ERROR,
				(Math.abs(expectedCals - calculatedCals) / expectedCals) < ACCEPTABLE_PERCENT_ERROR);
		
		weight = 160;
		metHours = 6.5;
		expectedCals = 472;
		calculatedCals = CalculationsHelper.getCaloriesFromMetHours(weight, metHours);
		assertTrue("Calculated Calories (" + calculatedCals + ") outside acceptable error of " +
					"Expected Calories (" + expectedCals + "). Acceptable Error: " + ACCEPTABLE_PERCENT_ERROR,
				(Math.abs(expectedCals - calculatedCals) / expectedCals) < ACCEPTABLE_PERCENT_ERROR);
		
	}

}
