package com.example.womenshearthealth;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class METListFragment extends Fragment implements OnClickListener,
		OnItemClickListener {

	SQLDatabaseHelper dbHelper;
	Activity activity;

	// ui elems
	private Button loadMetsButton;
	private ListView loadedMetsListView;
	private ListView extraListView;

	// ui adapter elems
	private ArrayAdapter<METActivity> loadedMetsListAdapter;
	private ArrayAdapter<METActivity> extraListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		
		// load mets button
		loadMetsButton = (Button) activity.findViewById(R.id.btn_metlistfragment_loadmets);
		loadMetsButton.setOnClickListener(this);
		
		// selected mets list
		extraListAdapter = new ArrayAdapter<METActivity>(activity, android.R.layout.simple_list_item_1);
		extraListView = (ListView) activity.findViewById(R.id.lstvw_metlistfragment_extralistview);
		extraListView.setAdapter(extraListAdapter);
		extraListView.setOnItemClickListener(this);
		
		// loaded mets list
		loadedMetsListAdapter = new ArrayAdapter<METActivity>(activity, android.R.layout.simple_list_item_1);
		//loadMetsActivities();
		loadedMetsListView = (ListView) activity.findViewById(R.id.lstvw_metlistfragment_loadedmets);
		loadedMetsListView.setAdapter(loadedMetsListAdapter);
		loadedMetsListView.setOnItemClickListener(this);		

	}

	@Override
	public void onStart() {
		super.onStart();

	}


	@Override
	public void onResume() {
		super.onResume();

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
		List<METActivity> l = METSCSVHelper.getAllMetActivities(activity);
		loadedMetsListAdapter.clear();
		for(METActivity a : l) {
			loadedMetsListAdapter.add(a);
		}
		loadedMetsListAdapter.notifyDataSetChanged();
	}

	public List<METActivity> getAllMetActivities() {
		Context context = activity.getApplicationContext();
		return METSCSVHelper.getAllMetActivities(context);
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(activity, "click", Toast.LENGTH_SHORT).show();

		switch (v.getId()) {

		case R.id.btn_metlistfragment_loadmets:
			loadMetsActivities();/*
			LinearLayout ll = (LinearLayout) activity
					.findViewById(R.id.ll_metslistfragment);
			Button b = new Button(activity);
			b.setText("extra button");
			b.setOnClickListener(this);
			ll.addView(b);*/

			break;

		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Toast.makeText(activity, "itemclick", Toast.LENGTH_SHORT).show();

	}

}
