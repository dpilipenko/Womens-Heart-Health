package com.example.womenshearthealth;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class METListFragment extends Fragment {

    SQLDatabaseHelper dbHelper;
    Activity activity;
    ListView listView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// load in the fragment_metlist.xml file and display it on screen

	    if (container == null) {
	        return null;
	    }
	    LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.fragment_metlist, container, false);
		listView = (ListView) ll.findViewById(R.id.metsListView);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
				android.R.layout.simple_list_item_1, dbHelper.getMETsList());
		
		listView.setAdapter(adapter);

		return ll;
	}
	
	
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        dbHelper = new SQLDatabaseHelper(activity);
        
        dbHelper.addMET("Running", 23, "June 6, 1972");
        dbHelper.addMET("Kicking", 21, "June 77, 1972");
        dbHelper.addMET("punching", 42, "June 34, 1979");
        
        dbHelper.displayAllMETs();

    }


}

