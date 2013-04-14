package com.example.womenshearthealth.models;

import java.util.Calendar;
import java.util.Date;



public class MetActivity extends GeneralMetActivity {

	private String mUUID;
	private int mMinutes;
	private Date mDateSaved;
	
	public MetActivity(GeneralMetActivity activity, int minutes) {
		super(activity.getName(), activity.getMetsvalue());
		
		mMinutes = 0;
		setMinutes(minutes);
		mUUID = java.util.UUID.randomUUID().toString();
	}
	
	public MetActivity(String uuid, String name, double metsvalue, int minutes) {
		super(name, metsvalue);
		
		mMinutes = 0;
		setMinutes(minutes);
		this.mUUID = uuid;
	}

	public int getMinutes() {
		return mMinutes;
	}

	public void setMinutes(int minutes) {
		if (minutes > 0) {
			this.mMinutes = minutes;
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
		
		if (mDateSaved != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(mDateSaved);
			int month = Integer.valueOf(c.get(Calendar.MONTH))+1;
			int day = Integer.valueOf(c.get(Calendar.DAY_OF_MONTH));
			String date = "("+month+"/"+day+")";
			
			display = date + " - ";
		}
		display += name+"\n\t "+time+", "+metsvalue;
		return display;
	}

	public Date getDateSaved() {
		return mDateSaved;
	}

	public void setDateSaved(Date dateSaved) {
		this.mDateSaved = dateSaved;
	}

	public void setUUID(String uuid) {
		try {
			mUUID = java.util.UUID.fromString(uuid).toString();
		} catch (Exception e) {
			
		}
	}
	
	public String getUUID() {
		return this.mUUID;
	}
}
