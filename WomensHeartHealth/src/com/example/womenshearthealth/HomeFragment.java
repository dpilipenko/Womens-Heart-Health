package com.example.womenshearthealth;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fima.chartview.ChartView;
import com.fima.chartview.LinearSeries;
import com.fima.chartview.LinearSeries.LinearPoint;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeFragment extends Fragment {
		
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
	LinearLayout targetHRCard = (LinearLayout) this.getActivity().findViewById(R.id.target_hr_card);
	LinearLayout totalsCard = (LinearLayout) this.getActivity().findViewById(R.id.totals_card);
	
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
		
		int bpm50 = CalculationsHelper.getHeartRateFromAge(age, CalculationsHelper.HRTARGET_50);
		int bpm85 = CalculationsHelper.getHeartRateFromAge(age, CalculationsHelper.HRTARGET_85);
		int bpm100 = CalculationsHelper.getHeartRateFromAge(age, CalculationsHelper.HRTARGET_MAX);
		
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
		LinearSeries series = new LinearSeries();
		series.setLineColor(0xFF0099CC);
		series.setLineWidth(2);
		
		
		List<LinearPoint> points = getLinearPointsForTheWeek();
		for(LinearPoint p: points) {
			series.addPoint(p);
		}
		
		c.addSeries(series);
		
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
	
}
