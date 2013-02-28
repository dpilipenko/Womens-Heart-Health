package com.example.womenshearthealth;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		Button btn = (Button)findViewById(R.id.btn_saveSettings);
		btn.setOnClickListener(this);
		populateUI();
	}
	
	

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		populateUI();
	}



	@Override
	public void onClick(View view) {
		
		switch(view.getId()) {
		case R.id.btn_saveSettings:
			if (saveUI()) {
				finish();
			}
			break;
			
		}
		
	}
	
	private void populateUI() {
		
		int age = SettingsHelper.getAge(this);
		int weight = SettingsHelper.getWeight(this);
		
		EditText etAge = (EditText)findViewById(R.id.et_Age);
		EditText etWeight = (EditText)findViewById(R.id.et_Weight);
		
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
