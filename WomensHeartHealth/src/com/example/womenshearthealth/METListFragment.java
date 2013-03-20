package com.example.womenshearthealth;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class METListFragment extends Fragment implements OnClickListener {

    SQLDatabaseHelper dbHelper;
    Activity activity;
    ListView listView;
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// load in the fragment_metlist.xml file and display it on screen

	    if (container == null) {
	        return null;
	    }
	    LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.fragment_metlist, container, false);
		listView = (ListView) ll.findViewById(R.id.metsListView);
		activity = getActivity();
		dbHelper = new SQLDatabaseHelper(activity);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
				android.R.layout.simple_list_item_1, dbHelper.getMETsList());
		
		listView.setAdapter(adapter);
		Button btn = (Button)ll.findViewById(R.id.btn_addmets);
		btn.setOnClickListener(this);
		return ll;
	}
	
    
    public void addMets() {
    	
        dbHelper.addMET("Running", 23, "June 6, 1972");
        dbHelper.addMET("Kicking", 21, "June 77, 1972");
        dbHelper.addMET("punching", 42, "June 34, 1979");
        
        dbHelper.displayAllMETs();
    }

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btn_addmets:
			addMets();
		}
		
	}


}

