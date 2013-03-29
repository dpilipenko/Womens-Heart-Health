package com.example.womenshearthealth;

public class GeneralMetActivity {

	private String name;
	private double metsvalue;
	
	public GeneralMetActivity(String name, double metsvalue) {
		this.name = name;
		this.metsvalue = metsvalue;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the metsvalue
	 */
	public double getMetsvalue() {
		return metsvalue;
	}

	/**
	 * @param metsvalue the metsvalue to set
	 */
	public void setMetsvalue(double metsvalue) {
		this.metsvalue = metsvalue;
	}

	@Override
	public String toString() {
		return name + " " + metsvalue;
	}
	
	
	
}