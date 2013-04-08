package com.example.womenshearthealth;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ShowAllMetsActivity extends Activity implements OnItemClickListener {

	private ListView listview;
	private ArrayAdapter<MetActivity> listviewadapter;
	private SQLDatabaseHelper dbhelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weeks_met_activities);
		dbhelper = new SQLDatabaseHelper(this);
		listview = (ListView)findViewById(R.id.weeksmets_listview);
		listviewadapter = new ArrayAdapter<MetActivity>(this, android.R.layout.simple_list_item_1);
		listview.setAdapter(listviewadapter);
		listview.setOnItemClickListener(this);
		repopulateActivitiesList();
	}
	
	public void repopulateActivitiesList() {
		listviewadapter.clear();
		List<MetActivity> activities = dbhelper.getAllMetActivities();
		for (MetActivity a: activities) {
			listviewadapter.add(a);
		}
		listviewadapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		// display dialog to see if want to delete
		// and then add this as listener
		
		switch (parent.getId()) {
		case R.id.weeksmets_listview:
			final MetActivity metActivity = listviewadapter.getItem(position);
			String name = metActivity.getName();
			int min = metActivity.getMinutes();
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle(""+name);
			alert.setMessage("You did "+name+" for "+min+" minutes");
			alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// Do nothing
				}
			});

			alert.setNegativeButton("Delete Record", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// Canceled.
					dbhelper.deleteMetActivity(metActivity);
					listviewadapter.remove(metActivity);
					listviewadapter.notifyDataSetChanged();
				}
			});

			alert.show();
			
			break;
		}
		
		
	}
	
}