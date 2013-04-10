package com.example.womenshearthealth;

import java.util.Calendar;
import java.util.Date;


public class MetActivity extends GeneralMetActivity {

	private String uuid;
	private int minutes;
	private Date dateSaved;
	
	public MetActivity(GeneralMetActivity activity, int minutes) {
		super(activity.getName(), activity.getMetsvalue());
		this.minutes = 0; // default value in case input is invalid
		setMinutes(minutes);
		
	}
	
	public MetActivity(String uuid, String name, double metsvalue, int minutes) {
		super(name, metsvalue);
		this.minutes = 0; // default value in case input is invalid
		setMinutes(minutes);
		this.uuid = uuid;
		
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
		String min = ""+getMinutes()%60;
		if(min.length() == 1)
			min = "0"+min;
		String time = getMinutes()/60+":"+min;
		double mets_double = Math.floor(getMetMinutes() * 100) / 100;
		String metsvalue = mets_double + " MET-mins";
		String display = "";
		
		if (dateSaved != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(dateSaved);
			int month = Integer.valueOf(c.get(Calendar.MONTH))+1;
			int day = Integer.valueOf(c.get(Calendar.DAY_OF_MONTH));
			String date = "("+month+"/"+day+")";
			
			display = date + " - ";
		}
		display += name+"\n\t "+time+", "+metsvalue;
		return display;
	}

	public Date getDateSaved() {
		return dateSaved;
	}

	public void setDateSaved(Date dateSaved) {
		this.dateSaved = dateSaved;
	}

	public String getUUID() {
		return this.uuid;
	}
}
