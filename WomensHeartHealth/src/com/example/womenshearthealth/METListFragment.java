package com.example.womenshearthealth;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class METListFragment extends Fragment implements OnClickListener,
		OnItemClickListener {

	SQLDatabaseHelper dbHelper;
	Activity activity;

	// ui elems
	private ListView loadedMetsListView;
	private ListView extraListView;
	private Button saveButton;

	// ui adapter elems
	private ArrayAdapter<GeneralMetActivity> loadedMetsListAdapter;
	private ArrayAdapter<MetActivity> extraListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		dbHelper = new SQLDatabaseHelper(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_metlist, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = getActivity();
		if (activity == null) {
			return;
		}

		// selected mets list
		extraListAdapter = new ArrayAdapter<MetActivity>(activity,
				android.R.layout.simple_list_item_1);
		extraListView = (ListView) activity
				.findViewById(R.id.lstvw_metlistfragment_extralistview);
		extraListView.setAdapter(extraListAdapter);
		extraListView.setOnItemClickListener(this);

		// loaded mets list
		loadedMetsListAdapter = new ArrayAdapter<GeneralMetActivity>(activity,
				android.R.layout.simple_list_item_1);
		loadedMetsListView = (ListView) activity
				.findViewById(R.id.lstvw_metlistfragment_loadedmets);
		loadedMetsListView.setAdapter(loadedMetsListAdapter);
		loadedMetsListView.setOnItemClickListener(this);

		// save button
		saveButton = (Button) activity
				.findViewById(R.id.btn_metlistfragment_savebutton);
		saveButton.setOnClickListener(this);

	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	public void onResume() {
		super.onResume();
		loadMetsActivities();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	public void addMets() {
		/*
		 * dbHelper.addMET("Running", 23, "June 6, 1972");
		 * dbHelper.addMET("Kicking", 21, "June 77, 1972");
		 * dbHelper.addMET("punching", 42, "June 34, 1979");
		 * 
		 * dbHelper.displayAllMETs();
		 */
	}

	public void loadMetsActivities() {
		List<GeneralMetActivity> l = METSCSVHelper
				.getAllMetActivities(activity);
		loadedMetsListAdapter.clear();
		for (GeneralMetActivity a : l) {
			loadedMetsListAdapter.add(a);
		}
		loadedMetsListAdapter.notifyDataSetChanged();
	}

	public List<GeneralMetActivity> getAllMetActivities() {
		Context context = activity.getApplicationContext();
		return METSCSVHelper.getAllMetActivities(context);
	}

	protected void addToCart(MetActivity a) {
		extraListAdapter.add(a);
		extraListAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		int a = parent.getId();
		int b = view.getId();

		switch (parent.getId()) {

		case R.id.lstvw_metlistfragment_extralistview:
			b(position);
			Log.v("hi", "hi");
			return;
		case R.id.lstvw_metlistfragment_loadedmets:
			a(position);
			Log.v("hi", "hi");
			return;

		}

	}

	public void a(int position) {
		final GeneralMetActivity metActivity = loadedMetsListAdapter
				.getItem(position);

		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

		alert.setTitle("For How Long Did You Do It?");
		alert.setMessage("For how many minutes did you do: "
				+ metActivity.getName());

		// Set an EditText view to get user input
		final EditText input = new EditText(getActivity());
		input.setHint("How many minutes?");
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		alert.setView(input);

		alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				if (value.isEmpty())
					value = "0";
				int a = Integer.valueOf(value);
				MetActivity m = new MetActivity(metActivity, a);
				addToCart(m);
				Log.v("Metlist", m.toString());
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

		alert.show();
	}

	public void b(int position) {
		final MetActivity metActivity = extraListAdapter.getItem(position);

		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

		alert.setTitle("For How Long Did You Do It?");
		alert.setMessage("For how many minutes did you do: "
				+ metActivity.getName());

		// Set an EditText view to get user input
		final EditText input = new EditText(getActivity());
		input.setText("" + metActivity.getMinutes());
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		alert.setView(input);

		alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				if (value.isEmpty())
					value = "0";
				int a = Integer.valueOf(value);
				metActivity.setMinutes(a);
				extraListAdapter.notifyDataSetChanged();
			}
		});

		alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
				
				extraListAdapter.remove(metActivity);
				extraListAdapter.notifyDataSetChanged();
	
			}
		});

		alert.show();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_metlistfragment_savebutton:
			handleSave();
			break;
		}

	}

	private void handleSave() {
		int count = extraListAdapter.getCount();
		for (int i = 0; i < count; i++) {
			MetActivity a = extraListAdapter.getItem(i);
			dbHelper.saveMetActivity(a);
		}
		extraListAdapter.clear();
		extraListAdapter.notifyDataSetChanged();
		Toast.makeText(activity, "Saved", Toast.LENGTH_SHORT).show();
	}

}
