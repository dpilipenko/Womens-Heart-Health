package com.example.womenshearthealth;


public class MetActivity extends GeneralMetActivity {

	private int minutes;
	
	public MetActivity(GeneralMetActivity activity, int minutes) {
		super(activity.getName(), activity.getMetsvalue());
		this.minutes = 0; // default value in case input is invalid
		setMinutes(minutes);
		
	}
	
	public MetActivity(String name, double metsvalue, int minutes) {
		super(name, metsvalue);
		this.minutes = 0; // default value in case input is invalid
		setMinutes(minutes);
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		boolean validMins = true;
		// check if mets input is valid
		validMins = (minutes > 0);
		
		if (validMins) {
			this.minutes = minutes;
		}
		
	}
	
	public double getMetMinutes() {
		double activityMetCount = getMetsvalue();
		int minutes = getMinutes();
		return activityMetCount * minutes;
	}
	
	public double getMetHours() {
		double metmins = getMetMinutes();
		return metmins / 60.0;
	}
	
	@Override
	public String toString() {
		String name = getName();
		String min = getMinutes() + " mins";
		String metsvalue = getMetMinutes() + " Met-mins";
		return name + " " + min + " " + metsvalue;
	}

}
