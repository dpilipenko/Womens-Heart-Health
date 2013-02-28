package com.example.womenshearthealth;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//takes care of whatever overhead it needs
		super.onCreate(savedInstanceState);
		//loads activity_settings.xml UI
		setContentView(R.layout.activity_settings);
		//grabs access to the OK button in the UI
		Button btn = (Button)findViewById(R.id.btn_saveSettings);
		//now SettingsActivity is told when OK button is clicked
		btn.setOnClickListener(this);
		//loads preference data and fills in UI elements
		populateUI();
	}
	
	

	//this method is called when ever the SettingsActivity is about to get shown to the screen
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		//takes care of whatever overhead it needs
		super.onRestoreInstanceState(savedInstanceState);
		//loads preference data and fills in UI elements
		populateUI();
	}



	@Override
	public void onClick(View view) {
		
		switch(view.getId()) {
		
		case R.id.btn_saveSettings: //user clicks on the OK button in the UI
			
			if (saveUI()) { //checks UI values to see if they are valid entries
				
				//Android handles the flow of activities as a stack. 
				//Depending on how user got to settings screen, either a new activity will be
				//added to the stack, or the current one will finish, "pop off", and next activity
				//in the stack will resume.
				if (SettingsHelper.isInitialRun(this)) { 
					//this is the first time the application has been ran. 
					//user reached this activity from the splash screen, so 
					//now the need to be directed to the Main activity.
					//(Adding to the stack)
					
					//this flag is used to check if initial run of activity or not.
					SettingsHelper.setInitialRun(this, false);
					//for the life of this application, this if shall never again execute
					
					//Redirects user from SettingsActivity to new instance of MainActivity
					//this adds a MainActivity to the top of the stack
					Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
					SettingsActivity.this.startActivity(intent);
					
				} else {
					//user reached the settings from the menu bar in the application
					//finish this activity, pop it off the stack, and return to previous activity
					finish();
				}
				
			} else {
				//somehow inform user that data is bad and it won't be saved
				//TODO alert user of bad data
			}
			
			break;
		}
	}
	
	/**
	 * Loads saved data and fills in the UI elements
	 */
	private void populateUI() {
		
		//loads saved data, or -1 when no there is no such saved data yet
		//the passed-in parameter to use SettingsHelper should always be 'this'
		int age = SettingsHelper.getAge(this);
		int weight = SettingsHelper.getWeight(this);
		
		//grabs the Age and Weight texts boxes from the UI
		EditText etAge = (EditText)findViewById(R.id.et_Age);
		EditText etWeight = (EditText)findViewById(R.id.et_Weight);
		
		//displays the Age and Weight values to the UI
		etAge.setText(""+age);
		etWeight.setText(""+weight);
		
	}
	
	private boolean saveUI() {

		EditText etAge = (EditText)findViewById(R.id.et_Age);
		EditText etWeight = (EditText)findViewById(R.id.et_Weight);
		
		int age = Integer.valueOf(etAge.getText().toString()).intValue();
		int weight = Integer.valueOf(etWeight.getText().toString()).intValue();
		
		boolean dataIsGood = true;
		dataIsGood = dataIsGood&& (age > 0); //age must be at least 1
		dataIsGood = dataIsGood&& (age < 130); //reasonable to assume people are dead by 130yrs old
		dataIsGood = dataIsGood&& (weight > 0); //weight must be at least 1
		dataIsGood = dataIsGood&& (weight < 2000); //fattest person ever was 1,400 pounds
		
		if (dataIsGood) {
			SettingsHelper.setAge(this, age);
			SettingsHelper.setWeight(this, weight);
		} else {
			// inform user that data is bad for saving
		}
		
		return dataIsGood;
		
		
	}

}
