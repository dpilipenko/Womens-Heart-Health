package com.example.womenshearthealth;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;

public class METSCSVHelper {
	
	public static List<METActivity> getAllMetActivities(Context context) {
		LinkedList<METActivity> activities = new LinkedList<METActivity>();
		try {
			AssetManager am = context.getAssets();
			InputStream is = am.open("metactivities.csv");
			CSVReader reader = new CSVReader(new InputStreamReader(is));
			String[] nextLine = reader.readNext();
			while (nextLine != null) {
				METActivity a = new METActivity(nextLine[0], Double.valueOf(nextLine[1]));
				activities.add(a);
				nextLine = reader.readNext();
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
		return activities;
	}

}
