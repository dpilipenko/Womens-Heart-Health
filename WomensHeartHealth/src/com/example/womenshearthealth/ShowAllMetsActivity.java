package com.example.womenshearthealth;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShowAllMetsActivity extends Activity {

	private ListView listview;
	private ArrayAdapter<MetActivity> listviewadapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weeks_met_activities);
		listview = (ListView)findViewById(R.id.weeksmets_listview);
		listviewadapter = new ArrayAdapter<MetActivity>(this, android.R.layout.simple_list_item_1);
		listview.setAdapter(listviewadapter);
		
		repopulateActivitiesList();
	}
	
	public void repopulateActivitiesList() {
		listviewadapter.clear();
		SQLDatabaseHelper dbhelper = new SQLDatabaseHelper(this);
		List<MetActivity> activities = dbhelper.getAllMetActivities();
		for (MetActivity a: activities) {
			listviewadapter.add(a);
		}
		listviewadapter.notifyDataSetChanged();
	}
	
}