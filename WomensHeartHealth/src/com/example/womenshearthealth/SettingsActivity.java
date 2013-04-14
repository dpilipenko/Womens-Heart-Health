package com.example.womenshearthealth;

import java.util.Calendar;
import java.util.Date;

import com.example.womenshearthealth.utils.SettingsHelper;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends Activity implements OnClickListener,
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
		setContentView(R.layout.activity_settings);

		birthdayTextView = (TextView) findViewById(R.id.txtvw_settings_bday);
		weightTextView = (TextView) findViewById(R.id.txtvw_settings_weight);
		saveButton = (Button) findViewById(R.id.btn_settings_savebutton);

		birthdayTextView.setOnClickListener(this);
		weightTextView.setOnClickListener(this);
		saveButton.setOnClickListener(this);

		// loads preference data and fills in UI elements
		populateUI();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		populateUI();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View view) {

		switch (view.getId()) {

		case R.id.btn_settings_savebutton:
			finish();
			break;
		case R.id.txtvw_settings_bday:
			// I'm sorry but the android way of using a fragment with datepicker
			// and then having to have to create listeners, seems awfully dumb
			// for something so small and basic. If it ain't broke, don't fix
			// it! ;)
			showDialog(DATE_DIALOG_ID);
			break;
		case R.id.txtvw_settings_weight:
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Update Weight");
			alert.setMessage("Please enter in your weight in pounds:");
			final EditText input = new EditText(this);
			input.setInputType(InputType.TYPE_CLASS_NUMBER);
			alert.setView(input);
			alert.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							String value = input.getText().toString();
							int weight = Integer.valueOf(value).intValue();
							SettingsHelper.setWeight(SettingsActivity.this,
									weight);
							populateUI();
						}
					});
			alert.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// do nothing
						}
					});
			alert.show();
			input.setText(""+this.weight);
			input.requestFocus();
			break;

		}
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		Calendar c = Calendar.getInstance();
		c.set(year, monthOfYear, dayOfMonth);
		SettingsHelper.setBirthdate(this, c.getTime());
		populateUI();
	}

	/**
	 * Loads saved data and fills in the UI elements
	 */
	private void populateUI() {
		clearUI();

		this.weight = SettingsHelper.getWeight(this);
		this.birthdate = SettingsHelper.getBirthdate(this);

		Calendar c = Calendar.getInstance();
		c.setTime(birthdate);
		int month = c.get(Calendar.MONTH)+1;
		int date = c.get(Calendar.DATE);
		int year = c.get(Calendar.YEAR);
		StringBuilder dateBuilder = new StringBuilder();
		if (month < 10) {
			dateBuilder.append("0");
		}
		dateBuilder.append(month + "/");
		if (date < 10) {
			dateBuilder.append("0");
		}
		dateBuilder.append(date + "/" + year);

		birthdayTextView.setText(dateBuilder.toString());
		weightTextView.setText(weight + " lbs");

	}

	private void clearUI() {
		birthdayTextView.setText(" ");
		weightTextView.setText(" ");
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case DATE_DIALOG_ID:
			Calendar c = Calendar.getInstance();
			c.setTime(birthdate);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DATE);
			return new DatePickerDialog(this, this, year, month, day);

		}

		return super.onCreateDialog(id);
	}

}