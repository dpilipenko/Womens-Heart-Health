package com.example.womenshearthealth;


import java.util.LinkedList;
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

public class METListFragment extends Fragment implements OnClickListener {

    SQLDatabaseHelper dbHelper;
    Activity activity;
    ListView listView;
    
    ListView selectedMetsListView;
    ListView availableMetsListView;
    Button saveButton;
    
    ArrayAdapter<METActivity> selectedMetsListAdapter;
    ArrayAdapter<METActivity> availableMetsListAdapter;
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      
      Button btn = (Button)getActivity().findViewById(R.id.btn_addmets);
      btn.setOnClickListener(this);
      
      selectedMetsListView = (ListView)this.getActivity().findViewById(R.id.lstvw_mets_selectedMetsList);
      availableMetsListView = (ListView)this.getActivity().findViewById(R.id.lstvw_mets_availableMetsList);
		
      selectedMetsListAdapter = new ArrayAdapter<METActivity>(this.getActivity(), android.R.layout.simple_list_item_1);
      availableMetsListAdapter = new ArrayAdapter<METActivity>(this.getActivity(), android.R.layout.simple_list_item_1, getAllMetActivities());
      
      selectedMetsListView.setOnItemClickListener(new OnItemClickListener() {
    	  @Override
    	  public void onItemClick(AdapterView<?> parent, View view,
    			  int position, long id) {
    		  // tap on selected mets list item
    	  }
      });
		
      availableMetsListView.setOnItemClickListener(new OnItemClickListener() {
    	  @Override
    	  public void onItemClick(AdapterView<?> parent, View view,
    			  int position, long id) {
    		  // tap on available mets list item
    	  }
      });
      
      selectedMetsListView.setAdapter(selectedMetsListAdapter);
      availableMetsListView.setAdapter(availableMetsListAdapter);
    }
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// load in the fragment_metlist.xml file and display it on screen
		/*
		if (container == null) {
	        return null;
	    }
	    
		listView = (ListView) ll.findViewById(R.id.metsListView);
		activity = getActivity();
		dbHelper = new SQLDatabaseHelper(activity);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
				android.R.layout.simple_list_item_1, dbHelper.getMETsList());
		
		listView.setAdapter(adapter);
		*/
		
		
		
		
		
		
		
		
		
		
		//loadAllMetsActivities();
		
		return inflater.inflate(R.layout.fragment_metlist, container, false);
		
	}
	
    
    public void addMets() {
    	/*
        dbHelper.addMET("Running", 23, "June 6, 1972");
        dbHelper.addMET("Kicking", 21, "June 77, 1972");
        dbHelper.addMET("punching", 42, "June 34, 1979");
        
        dbHelper.displayAllMETs();
        */
    }
    
    
    public void loadAllMetsActivities() {
    	Context context = availableMetsListAdapter.getContext();
    	availableMetsListAdapter.clear();
    	availableMetsListAdapter.notifyDataSetInvalidated();
    	
    	
    	
    	List<METActivity> activities = new LinkedList<METActivity>();
    	activities = METSCSVHelper.getAllMetActivities(context);
    	availableMetsListAdapter = new ArrayAdapter<METActivity>(getActivity(),
    			android.R.layout.simple_list_item_1, activities);
    	availableMetsListView.setAdapter(availableMetsListAdapter);
    	/*
    	for (METActivity a : activities)
    		availableMetsListAdapter.add(a);
    	availableMetsListView.setAdapter(availableMetsListAdapter);
    	*/
    }
    
    public List<METActivity> getAllMetActivities() {
    	Context context = getActivity().getApplicationContext();
    	return METSCSVHelper.getAllMetActivities(context);
    }

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btn_addmets:
			loadAllMetsActivities();
		}
		
	}


}

