package com.example.womenshearthealth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class METListFragment extends Fragment {

    SQLDatabaseHelper dbHelper;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// load in the fragment_metlist.xml file and display it on screen
		
		
		return inflater.inflate(R.layout.fragment_metlist, container, false);
	}
	
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dbHelper = new SQLDatabaseHelper(activity);
        
        dbHelper.addMET("Running", 23, "June 6, 1972");
        dbHelper.addMET("Kicking", 21, "June 77, 1972");
        dbHelper.addMET("punching", 42, "June 34, 1979");

        dbHelper.loadMETSDB();

    }


}

