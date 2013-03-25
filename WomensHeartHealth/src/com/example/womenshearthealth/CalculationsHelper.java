package com.example.womenshearthealth;

public class CalculationsHelper {

	public static final double HRTARGET_MAX = 1.0;
	public static final double HRTARGET_85 = 0.85;
	public static final double HRTARGET_50 = 0.5;
	
	/**
	 * Returns the Heart Rates at various target levels based on Doctor Gulati's research
	 * @param age User's age in years
	 * @param hrTarget The target level desired. Look at HRTARGET_*
	 * @return Heart rate target at specific level
	 */
	public static int getHeartRateFromAge(int age, double hrTarget) { 
		
		// Calculate the maximum target heart rate based off of entered age
		int maxHR = 206 - 88/100*age;
		
		// Return the desired ratio of the Target Heart Rate
		if (hrTarget == HRTARGET_MAX) {
			return (int)(Math.floor(1.0 *maxHR));
		} else if (hrTarget == HRTARGET_85) {
			return (int)(Math.floor(0.85 *maxHR));
		} else if (hrTarget == HRTARGET_50) {
			return (int)(Math.floor(0.5 *maxHR));
		} else {
			return 0;
		}
		
	}
	
	/**
	 * Returns the calorie count based on the user's weight and the MET*hours being converted.
	 * @param weight Weight of the user in pounds.
	 * @param metHours Number of MET-hours being converted.
	 * @return Calories burnt 
	 */
	public static double getCaloriesFromMet(int weight, double metHours) {
		double weightKilos = 0.453592 * weight;
		return Math.floor(weightKilos * metHours * 100.0)/100.0;
	}
	
}
