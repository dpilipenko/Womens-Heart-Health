package com.example.womenshearthealth;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class METListFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// load in the fragment_metlist.xml file and display it on screen
		return inflater.inflate(R.layout.fragment_metlist, container, false);
	}

}
