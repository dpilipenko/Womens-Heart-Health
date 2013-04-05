package com.example.womenshearthealth;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.fima.chartview.ChartView;
import com.fima.chartview.LinearSeries;
import com.fima.chartview.LinearSeries.LinearPoint;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeFragment extends Fragment implements OnClickListener {
		
	private Activity activity;
	private SQLDatabaseHelper dbHelper;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// load in the fragment_home.xml file and display it on screen
		View homeFragment =  inflater.inflate(R.layout.fragment_home, container, false);
		return homeFragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		dbHelper = new SQLDatabaseHelper(activity);
		
	}
	
	@Override
	public void onStart() {
	super.onStart();
	
	LinearLayout graphCard = (LinearLayout) this.getActivity().findViewById(R.id.graph_card);
	graphCard.setOnClickListener(this);
	LinearLayout targetHRCard = (LinearLayout) this.getActivity().findViewById(R.id.target_hr_card);
	targetHRCard.setOnClickListener(this);
	LinearLayout totalsCard = (LinearLayout) this.getActivity().findViewById(R.id.totals_card);
	totalsCard.setOnClickListener(this);
	
	graphCard.setVisibility(8);
	targetHRCard.setVisibility(8);
	totalsCard.setVisibility(8);
	
	View graphCardDropshadow = (View) this.getActivity().findViewById(R.id.graphCardDropshadow);
	View targetHRCardDropshadow = (View) this.getActivity().findViewById(R.id.targerHRCardDropshadow);
	View totalsCardDropshadow = (View) this.getActivity().findViewById(R.id.totalsCardDropshadow);

	graphCardDropshadow.setVisibility(8);
	targetHRCardDropshadow.setVisibility(8);
	totalsCardDropshadow.setVisibility(8);
	
	}
	
	@Override
	public void onResume() {
		super.onResume();
		startAnimationPopOut(R.id.graph_card);
		startAnimationPopOut(R.id.graphCardDropshadow);
		startAnimationPopOut(R.id.target_hr_card);
		startAnimationPopOut(R.id.targerHRCardDropshadow);
		startAnimationPopOut(R.id.totals_card);
		startAnimationPopOut(R.id.totalsCardDropshadow);
		
		populateUI();
	}
	
	private void startAnimationPopOut(int id) {         
	    View myLayout = (View) getActivity().findViewById(id);
	    myLayout.setVisibility(0);
	    Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_up_from_right);

	    animation.setAnimationListener(new AnimationListener() {                  
	        @Override
	        public void onAnimationStart(Animation animation) {

	        }

	        @Override
	        public void onAnimationRepeat(Animation animation) {

	        }

	        @Override
	        public void onAnimationEnd(Animation animation) {

	        }
	    });

	    myLayout.clearAnimation();
	    myLayout.startAnimation(animation);

	}


	/**
	 * Loads saved data and fills in the UI elements
	 */
	private void populateUI() {
		//loads saved data, or -1 when no there is no such saved data yet
		//the passed-in parameter to use SettingsHelper should always be 'this'
		int age = SettingsHelper.getAge(this.getActivity());
		
		int bpm50 = CalculationsHelper.getTargetHeartRateFromAge(age, CalculationsHelper.TARGET_50);
		int bpm85 = CalculationsHelper.getTargetHeartRateFromAge(age, CalculationsHelper.TARGET_85);
		int bpm100 = CalculationsHelper.getTargetHeartRateFromAge(age, CalculationsHelper.TARGET_MAX);
		
		double met = getMetsForWeek();
		double cals = CalculationsHelper.getCaloriesFromMet(SettingsHelper.getWeight(this.getActivity()),met);
		
		//Grab text boxes from UI
		TextView BPM1 = (TextView)this.getActivity().findViewById(R.id.BPM1);
		TextView BPM2= (TextView)this.getActivity().findViewById(R.id.BPM2);
		TextView BPM3 = (TextView)this.getActivity().findViewById(R.id.BPM3);
		
		TextView METs = (TextView)this.getActivity().findViewById(R.id.METs);
		TextView Calories = (TextView)this.getActivity().findViewById(R.id.Calories);
		
		//Display text boxes
		BPM1.setText(bpm50 + " BPM: 50% MHR");
		BPM2.setText(bpm85 + " BPM: 85% MHR");
		BPM3.setText(bpm100 + " BPM: 100% MHR");
		
		METs.setText(met + " \tMETs");
		
		Calories.setText(String.format("%.3f", cals/1000.0) + " \tkCal");
		
		TextView targetHR = (TextView)this.getActivity().findViewById(R.id.targertHeartRatesTextView);
		TextView weekTotal = (TextView)this.getActivity().findViewById(R.id.weeksTotalTextView);
		TextView metsLogged = (TextView)this.getActivity().findViewById(R.id.metsLoggedTextView);


		Typeface myTypeface = Typeface.createFromAsset(
		                          getActivity().getAssets(),
		                          "font/roboto.ttf");

		targetHR.setTypeface(myTypeface);
		weekTotal.setTypeface(myTypeface);
		metsLogged.setTypeface(myTypeface);

		buildChart();
	}
	
	private double getMetsForWeek() {
		double count = 0;
		List<Set<MetActivity>> days = getMetActivitiesForTheWeek();
		for (Set<MetActivity> day: days) {
			for (MetActivity activity: day) {
				count += activity.getMetMinutes();
			}
		}
		return count;
	}

	private void buildChart() {
		ChartView c = (ChartView)activity.findViewById(R.id.chart_view);
		
		LinearSeries weeklyMetsSeries = new LinearSeries();
		weeklyMetsSeries.setLineColor(0xFFFF99CC);
		weeklyMetsSeries.setLineWidth(4);
		
		List<LinearPoint> points1 = getLinearPointsForTheWeek();
		for(LinearPoint p: points1) {
			weeklyMetsSeries.addPoint(p);
		}
		
		
		
		LinearSeries targetMetsSeries = new LinearSeries();
		targetMetsSeries.setLineColor(0xFF0099CC);
		targetMetsSeries.setLineWidth(6);
		
		int age = SettingsHelper.getAge(getActivity());
		double target = CalculationsHelper.TARGET_85;
		double capacity = CalculationsHelper.getTargetPredictedExerciseCapacityFromAge(age, target);
		ArrayList<LinearPoint> points2 = new ArrayList<LinearPoint>();
		points2.add(new LinearPoint((double)1, (double)50));
		points2.add(new LinearPoint((double)5, (double)50));/*
		for (int day = 0; day < 7; day++) {
			points2.add(new LinearPoint(day, 50.0));
		}
		*/
		for(LinearPoint p: points2) {
			targetMetsSeries.addPoint(p);
		}
		
		

		c.addSeries(weeklyMetsSeries);
		c.addSeries(targetMetsSeries);
	}
	
	private List<Set<MetActivity>> getMetActivitiesForTheWeek() {
		
		Calendar c = Calendar.getInstance();
		
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int daysSinceSunday = dayOfWeek - Calendar.SUNDAY;
		int daysUntilSaturday = Calendar.SATURDAY - dayOfWeek;
		
		Calendar upperLimit = Calendar.getInstance();
		upperLimit.roll(Calendar.DAY_OF_WEEK, daysUntilSaturday);
		Calendar lowerLimit = Calendar.getInstance();
		lowerLimit.roll(Calendar.DAY_OF_WEEK, -1*daysSinceSunday);
		
		Calendar dayCursor = lowerLimit;
		dayCursor.roll(Calendar.MONTH, true);
		Date sunday = dayCursor.getTime();
		dayCursor.roll(Calendar.DATE, true);
		Date monday = dayCursor.getTime();
		dayCursor.roll(Calendar.DATE, true);
		Date tuesday = dayCursor.getTime();
		dayCursor.roll(Calendar.DATE, true);
		Date wednesday = dayCursor.getTime();
		dayCursor.roll(Calendar.DATE, true);
		Date thursday = dayCursor.getTime();
		dayCursor.roll(Calendar.DATE, true);
		Date friday = dayCursor.getTime();
		dayCursor.roll(Calendar.DATE, true);
		Date saturday = dayCursor.getTime();
		
		ArrayList<Set<MetActivity>> days = new ArrayList<Set<MetActivity>>();
		days.add(0, dbHelper.getMetActivitiesForDay(sunday));
		days.add(1, dbHelper.getMetActivitiesForDay(monday));
		days.add(2, dbHelper.getMetActivitiesForDay(tuesday));
		days.add(3, dbHelper.getMetActivitiesForDay(wednesday));
		days.add(4, dbHelper.getMetActivitiesForDay(thursday));
		days.add(5, dbHelper.getMetActivitiesForDay(friday));
		days.add(6, dbHelper.getMetActivitiesForDay(saturday));
		
		return days;
	}
	
	private List<LinearPoint> getLinearPointsForTheWeek() {

		List<Set<MetActivity>> days = getMetActivitiesForTheWeek();
		
		ArrayList<LinearPoint> points = new ArrayList<LinearPoint>();
		for (int day = 0; day < 7; day++) {
			int count = 0;
			Set<MetActivity> activities = days.get(day);
			for(MetActivity ma: activities) {
				count += ma.getMetMinutes();
			}
			Log.v("Mets","day:"+day+" count:"+count);
			points.add(new LinearPoint(day, count));
		}
		
		
		return points;
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		
		switch (v.getId()) {
		case R.id.graph_card: //Display past activities
			intent = new Intent(getActivity(), WeeksMetActivities.class);
			getActivity().startActivity(intent);
			break;
		case R.id.target_hr_card: //Display info on target heart rates
			intent = new Intent(getActivity(), TargetHRInformationActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.totals_card: //Display info on METs
			intent = new Intent(getActivity(), MetsInformationActivity.class);
			getActivity().startActivity(intent);
			break;
		}
	}
	
}
