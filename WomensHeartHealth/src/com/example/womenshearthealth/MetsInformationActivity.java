package com.example.womenshearthealth;

import java.util.List;

import com.example.womenshearthealth.models.MetActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MetsInformationActivity extends Activity {

	private ListView listview;
	private ArrayAdapter<MetActivity> listviewadapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_totals);
	}
	
}