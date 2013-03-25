package com.example.womenshearthealth;

import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity implements OnClickListener,
		OnDateSetListener {

	private TextView birthdayTextView;
	private TextView weightTextView;
	private Button saveButton;

	private Date birthdate;
	private int weight;

	static final int DATE_DIALOG_ID = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		birthdate = new Date();
		weight = 0;

		// takes care of whatever overhead it needs
		super.onCreate(savedInstanceState);
		// loads activity_settings.xml UI
	//	setContentView(R.layout.activity_settings);
		addPreferencesFromResource(R.layout.activity_settings);

//		this.weight = SettingsHelper.getWeight(this);
//		this.birthdate = SettingsHelper.getBirthdate(this);
//
//		Calendar c = Calendar.getInstance();
//		c.setTime(birthdate);
//		int month = c.get(Calendar.MONTH) + 1;
//		int date = c.get(Calendar.DATE);
//		int year = c.get(Calendar.YEAR);
//		StringBuilder dateBuilder = new StringBuilder();
//		dateBuilder.append(year + ".");
//		if (month < 10) {
//			dateBuilder.append("0");
//		}
//		dateBuilder.append(month + ".");
//		if (date < 10) {
//			dateBuilder.append("0");
//		}
//		dateBuilder.append(date);
//		getPreference("birthday_Preference").setDate(dateBuilder.toString());
//		weightTextView.setText(weight + " lbs");
		Preference pref = (Preference) findPreference("save_button");
		pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
		  @Override
		  public boolean onPreferenceClick(Preference preference) {
				Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
				SettingsActivity.this.startActivity(intent);	
				SettingsActivity.this.finish();
				return true;
		  }
		});
	}
	  private DatePreference getPreference(String key) {
		    return (DatePreference)getPreferenceManager().findPreference(key);
		  }


	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		
        Calendar c  = DatePreference.getDateFor(
        	    PreferenceManager.getDefaultSharedPreferences(this),
        	    "birthday_Preference");
		SettingsHelper.setBirthdate(this, c.getTime());

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
