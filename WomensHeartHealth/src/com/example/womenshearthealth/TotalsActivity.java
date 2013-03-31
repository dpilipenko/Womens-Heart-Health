package com.example.womenshearthealth;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TotalsActivity extends Activity {

	private ListView listview;
	private ArrayAdapter<MetActivity> listviewadapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_totals);
	}
	
}