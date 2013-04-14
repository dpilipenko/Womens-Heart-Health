package com.example.womenshearthealth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.example.womenshearthealth.helpers.CalculationsHelper;
import com.example.womenshearthealth.helpers.SQLDatabaseHelper;
import com.example.womenshearthealth.helpers.SettingsHelper;
import com.example.womenshearthealth.models.MetActivity;
import com.fima.chartview.ChartView;
import com.fima.chartview.LinearSeries;
import com.fima.chartview.LinearSeries.LinearPoint;
import com.fima.chartview.ValueLabelAdapter;
import com.fima.chartview.ValueLabelAdapter.LabelOrientation;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
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

//labels

public class HomeFragment extends Fragment implements OnClickListener {

	private Activity activity;
	private SQLDatabaseHelper dbHelper;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// load in the fragment_home.xml file and display it on screen
		View homeFragment = inflater.inflate(R.layout.fragment_home, container, false);
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

		LinearLayout graphCard = (LinearLayout) this.getActivity()
				.findViewById(R.id.graph_card);
		graphCard.setOnClickListener(this);
		LinearLayout targetHRCard = (LinearLayout) this.getActivity()
				.findViewById(R.id.target_hr_card);
		targetHRCard.setOnClickListener(this);

		LinearLayout graphCard2 = (LinearLayout) this.getActivity()
				.findViewById(R.id.graph_card2);
		graphCard2.setOnClickListener(this);

		LinearLayout randomFactsCard = (LinearLayout) this.getActivity()
				.findViewById(R.id.random_facts_card);
		randomFactsCard.setOnClickListener(this);

		graphCard.setVisibility(8);
		targetHRCard.setVisibility(8);
		graphCard2.setVisibility(8);
		randomFactsCard.setVisibility(8);

		View graphCardDropshadow = (View) this.getActivity().findViewById(
				R.id.graphCardDropshadow);
		View targetHRCardDropshadow = (View) this.getActivity().findViewById(
				R.id.targerHRCardDropshadow);
		View graphCardDropshadow2 = (View) this.getActivity().findViewById(
				R.id.graphCardDropshadow2);
		View randomFactCardDropshadow = (View) this.getActivity().findViewById(
				R.id.factsCardDropshadow);

		graphCardDropshadow.setVisibility(8);
		targetHRCardDropshadow.setVisibility(8);
		graphCardDropshadow2.setVisibility(8);
		randomFactCardDropshadow.setVisibility(8);

	}

	@Override
	public void onResume() {
		super.onResume();
		startAnimationPopOut(R.id.graph_card);
		startAnimationPopOut(R.id.graphCardDropshadow);
		startAnimationPopOut(R.id.target_hr_card);
		startAnimationPopOut(R.id.targerHRCardDropshadow);
		startAnimationPopOut(R.id.graph_card2);
		startAnimationPopOut(R.id.graphCardDropshadow2);
		startAnimationPopOut(R.id.random_facts_card);
		startAnimationPopOut(R.id.factsCardDropshadow);

		populateUI();
	}

	private void startAnimationPopOut(int id) {
		View myLayout = (View) getActivity().findViewById(id);
		myLayout.setVisibility(0);
		Animation animation = AnimationUtils.loadAnimation(getActivity(),
				R.anim.slide_up_from_right);

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

		Resources res = getResources();
		String[] randomFacts = res.getStringArray(R.array.random_facts_array);
		Random rand = new Random();

		int age = SettingsHelper.getAge(this.getActivity());

		int bpm50 = CalculationsHelper.getTargetHeartRateFromAge(age,
				CalculationsHelper.TARGET_50);
		int bpm85 = CalculationsHelper.getTargetHeartRateFromAge(age,
				CalculationsHelper.TARGET_85);
		int bpm100 = CalculationsHelper.getTargetHeartRateFromAge(age,
				CalculationsHelper.TARGET_MAX);

		// Grab text boxes from UI
		TextView BPM1 = (TextView) this.getActivity().findViewById(R.id.BPM1);
		TextView BPM2 = (TextView) this.getActivity().findViewById(R.id.BPM2);
		TextView BPM3 = (TextView) this.getActivity().findViewById(R.id.BPM3);


		TextView randomFactsTextView = (TextView) this.getActivity()
				.findViewById(R.id.randomFact);

		randomFactsTextView.setText(randomFacts[rand
				.nextInt(randomFacts.length)]);

		// Display text boxes
		BPM1.setText(bpm50 + " BPM: 50% MHR");
		BPM2.setText(bpm85 + " BPM: 85% MHR");
		BPM3.setText(bpm100 + " BPM: 100% MHR");


		TextView targetHR = (TextView) this.getActivity().findViewById(
				R.id.targertHeartRatesTextView);
		TextView metsLogged = (TextView) this.getActivity().findViewById(
				R.id.metsLoggedTextView);

		Typeface myTypeface = Typeface.createFromAsset(getActivity()
				.getAssets(), "font/roboto.ttf");

		targetHR.setTypeface(myTypeface);
		metsLogged.setTypeface(myTypeface);

		buildMetsChart();
		buildCalsChart();
	}

	private void buildMetsChart() {
		ChartView c = (ChartView) activity.findViewById(R.id.chart_view);

		LinearSeries weeklyMetsSeries = new LinearSeries();
		weeklyMetsSeries.setLineColor(0xFFFF99CC);
		weeklyMetsSeries.setLineWidth(4);

		List<LinearPoint> points1 = getLinearPointsForTheWeek();
		for (LinearPoint p : points1) {
			weeklyMetsSeries.addPoint(p);
		}

		c.addSeries(weeklyMetsSeries);

		// label
		c.setLeftLabelAdapter(new ValueLabelAdapter(getActivity(),
				LabelOrientation.VERTICAL));
		//c.setBottomLabelAdapter(new ValueLabelAdapter(getActivity(),
		//		LabelOrientation.HORIZONTAL));

	}

	private void buildCalsChart() {
		ChartView c = (ChartView) activity.findViewById(R.id.chart_view2);

		LinearSeries weeklyCalSeries = new LinearSeries();
		weeklyCalSeries.setLineColor(0xFFFF99CC);
		weeklyCalSeries.setLineWidth(4);

		List<LinearPoint> points1 = getCaloriePointsForTheWeek();
		for (LinearPoint p : points1) {
			weeklyCalSeries.addPoint(p);
		}

		// int age = SettingsHelper.getAge(activity);
		// double targetLevel = CalculationsHelper.TARGET_85;
		// double capacity =
		// CalculationsHelper.getTargetPredictedExerciseCapacityFromAge(age,
		// targetLevel);

		// int a = (int)capacity;
		// c.setLineHeight(a);
		c.addSeries(weeklyCalSeries);

		// labels

		c.setLeftLabelAdapter(new ValueLabelAdapter(getActivity(),
				LabelOrientation.VERTICAL));
		//c.setBottomLabelAdapter(new ValueLabelAdapter(getActivity(),
		//		LabelOrientation.HORIZONTAL));

	}

	private List<LinearPoint> getCaloriePointsForTheWeek() {
		List<Set<MetActivity>> days = getMetActivitiesForTheWeek();

		ArrayList<LinearPoint> points = new ArrayList<LinearPoint>();
		for (int day = 0; day < 7; day++) {
			Set<MetActivity> activities = days.get(day);
			int count = 0;
			for (MetActivity activity : activities) {
				count += activity.getMetMinutes();
			}
			int weight = SettingsHelper.getWeight(getActivity());
			double cals = CalculationsHelper.getCaloriesFromMetMinutes(weight,
					count);
			points.add(new LinearPoint(day, cals));
			Log.v("Cals", "day:" + day + " cals:" + cals);

		}

		return points;
	}

	/**
	 * This returns a list of 7 linear points for each of the 7 days of the
	 * week. The y-value is the highest recorded met activity for that day.
	 * 
	 * @return
	 */
	private List<LinearPoint> getLinearPointsForTheWeek() {

		List<Set<MetActivity>> days = getMetActivitiesForTheWeek();

		ArrayList<LinearPoint> points = new ArrayList<LinearPoint>();
		for (int day = 0; day < 7; day++) {

			double max = 0; // there is no such thing as negative met values
			Set<MetActivity> activities = days.get(day);
			for (MetActivity activity : activities) {
				double metval = activity.getMetsvalue();
				if (max < metval) {
					max = metval;
				}
			}

			points.add(new LinearPoint(day, max));
			Log.v("Mets", "day:" + day + " max:" + max);

		}

		return points;
	}

	/**
	 * This method returns all the met activities saved for the current calendar
	 * week. (Sun - Sat) The List represents a 7 day, each element of the list
	 * represents a day of the week Each Set is a collection of all the met
	 * activities for that day
	 * 
	 * @return
	 */
	private List<Set<MetActivity>> getMetActivitiesForTheWeek() {

		Calendar c = Calendar.getInstance();

		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int daysSinceSunday = dayOfWeek - Calendar.SUNDAY;
		int daysUntilSaturday = Calendar.SATURDAY - dayOfWeek;

		Calendar upperLimit = Calendar.getInstance();
		upperLimit.roll(Calendar.DAY_OF_WEEK, daysUntilSaturday);
		Calendar lowerLimit = Calendar.getInstance();
		lowerLimit.roll(Calendar.DAY_OF_WEEK, -1 * daysSinceSunday);

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

	@Override
	public void onClick(View v) {
		Intent intent = null;

		switch (v.getId()) {
		case R.id.graph_card: // Display past activities
			intent = new Intent(getActivity(), ShowAllMetsActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.target_hr_card: // Display info on target heart rates
			intent = new Intent(getActivity(),
					TargetHRInformationActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.graph_card2: // Display past activites
			intent = new Intent(getActivity(), MetsInformationActivity.class);
			getActivity().startActivity(intent);
			break;
		}
	}

}
