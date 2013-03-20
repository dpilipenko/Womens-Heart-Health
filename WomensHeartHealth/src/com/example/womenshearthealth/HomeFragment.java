package com.example.womenshearthealth;


import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebView.FindListener;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeFragment extends Fragment {
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// load in the fragment_home.xml file and display it on screen
		return inflater.inflate(R.layout.fragment_home, container, false);
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		populateUI();
	}

	/**
	 * Loads saved data and fills in the UI elements
	 */
	private void populateUI() {
		//loads saved data, or -1 when no there is no such saved data yet
		//the passed-in parameter to use SettingsHelper should always be 'this'
		int age = SettingsHelper.getAge(this.getActivity());
		
		int bpm50 = CalculationsHelper.getHeartRateFromAge(age, CalculationsHelper.HRTARGET_50);
		int bpm85 = CalculationsHelper.getHeartRateFromAge(age, CalculationsHelper.HRTARGET_85);
		int bpm100 = CalculationsHelper.getHeartRateFromAge(age, CalculationsHelper.HRTARGET_MAX);
		
		double met = 690.0; // TODO Implement mets
		double cals = CalculationsHelper.getCaloriesFromMet(SettingsHelper.getWeight(this.getActivity()),met);
		
		//Grab text boxes from UI
		TextView BPM1 = (TextView)this.getActivity().findViewById(R.id.BPM1);
		TextView BPM2= (TextView)this.getActivity().findViewById(R.id.BPM2);
		TextView BPM3 = (TextView)this.getActivity().findViewById(R.id.BPM3);
		
		TextView METs = (TextView)this.getActivity().findViewById(R.id.METs);
		TextView Calories = (TextView)this.getActivity().findViewById(R.id.Calories);
		
		//Display text boxes
		BPM1.setText(bpm50 + " BPM\t     50% MHR");
		BPM2.setText(bpm85 + " BPM\t     85% MHR");
		BPM3.setText(bpm100 + " BPM\t    100% MHR");
		
		METs.setText(met + " METS x Minutes");
		Calories.setText(cals + " Calories");
		
		buildGraph();
		jsGraph();
	}
	
	private void jsGraph() {
		WebView view =(WebView)getView().findViewById(R.id.webview);
		WebSettings webSettings = view.getSettings();
		webSettings.setJavaScriptEnabled(true);
		
		view.loadUrl("file:///android_asset/test.html");
		
	}
	
	/**
	 * Builds the graph
	 */
	private void buildGraph() {
		// init example series data
		GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
			new GraphViewData(0, 1.0d),
			new GraphViewData(1, 2.0d),
			new GraphViewData(2, 3.0d),
			new GraphViewData(3, 4.5d),
			new GraphViewData(4, 6.0d),
			new GraphViewData(5, 7.0d),
			new GraphViewData(6, 10.0d)
			});
		
		LineGraphView graphView = new LineGraphView(this.getActivity(), "GraphViewDemo");
		graphView.addSeries(exampleSeries);
		graphView.setDrawBackground(true);
		
		LinearLayout graph = (LinearLayout)this.getActivity().findViewById(R.id.graph);
		graph.addView(graphView);		
	}
}
