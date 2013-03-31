package com.example.womenshearthealth;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MetActivity extends GeneralMetActivity {

	private int minutes;
	private Date dateSaved;
	
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
		String display = name + " " + min + " " + metsvalue;
		
		if (dateSaved != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(dateSaved);
			String month = c.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
			int day = Integer.valueOf(c.get(Calendar.DAY_OF_MONTH));
			String date = month+"/"+day;
			
			display = "("+date+") "+name+" "+min+" "+metsvalue;
		}
		return display;
	}

	public Date getDateSaved() {
		return dateSaved;
	}

	public void setDateSaved(Date dateSaved) {
		this.dateSaved = dateSaved;
	}

}
