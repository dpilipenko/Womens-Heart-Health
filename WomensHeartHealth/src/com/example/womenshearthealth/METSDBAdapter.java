package com.example.womenshearthealth;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class METSDBAdapter {
	
	private static final String DATABASE_NAME = "mets.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_TABLE_NAME = "Mets";
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_NAME = "Name";
	private static final String COLUMN_METSVALUE = "MetsValue";
	private static final String COLUMN_MINUTESDONE = "MinutesDone";
	private static final String COLUMN_DATESUBMITTEDASLONG = "DateSubmittedAsLong";
	private static final String[] COLUMNS = {
		COLUMN_ID, COLUMN_NAME, COLUMN_METSVALUE, COLUMN_MINUTESDONE, COLUMN_DATESUBMITTEDASLONG
	};
	
	private static final String DATABASE_CREATE_SCRIPT = "create table "+DATABASE_TABLE_NAME+" (\n" +
			COLUMN_ID+" integer primary key autoincrement,\n" +
			COLUMN_NAME+" Text not null,\n" +
			COLUMN_METSVALUE+ " Double not null,\n"+
			COLUMN_MINUTESDONE+" Integer not null,\n" +
			COLUMN_DATESUBMITTEDASLONG+" Long not null\n" +
			");";

	private Context context;
	private METSDBOpenHelper dbHelper;
	private SQLiteDatabase db;
	
	

	public METSDBAdapter(Context context) {
		this.context = context;
		dbHelper = new METSDBOpenHelper(this.context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	private static class METSDBOpenHelper extends SQLiteOpenHelper {

		public METSDBOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
		
		public METSDBOpenHelper(Context context)
		{
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(DATABASE_CREATE_SCRIPT); // creates new sqlite DB
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("TaskDBAdapter", "Upgrading from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all data");
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);
			onCreate(db);
		}

	}


	// Creates
	public void addMetActivity(MetActivity activity, Date date) {
		db = dbHelper.getWritableDatabase();
		ContentValues dbInputValues = new ContentValues();
		
		String name = activity.getName();
		double metsvalue = activity.getMetsvalue();
		int minutes = activity.getMinutes();
		long datelong = date.getTime();
		
		
		dbInputValues.put(COLUMN_NAME, name);
		dbInputValues.put(COLUMN_METSVALUE, metsvalue);
		dbInputValues.put(COLUMN_MINUTESDONE, minutes);
		dbInputValues.put(COLUMN_DATESUBMITTEDASLONG, datelong);
		
		
		db.insert(DATABASE_TABLE_NAME, null, dbInputValues);
		dbHelper.close();
	};
	
	// Reads
	public List<MetActivity> getAllMetActivities() {
		db = dbHelper.getReadableDatabase();
		List<MetActivity> activities = new LinkedList<MetActivity>();
		String orderBy = COLUMN_DATESUBMITTEDASLONG + " DESC";
		Cursor cursor = db.query(DATABASE_TABLE_NAME, COLUMNS, null, null, null, null, orderBy);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			MetActivity a = cursorToMetActivity(cursor);
			activities.add(a);
			cursor.moveToNext();
		}
		dbHelper.close();
		return activities;	
	}
	
	public Set<MetActivity> getMetActivitiesByDateRange(Date start, Date end) {
		
		String tblname = DATABASE_TABLE_NAME;
		String datecol = COLUMN_DATESUBMITTEDASLONG;
		long starttime = start.getTime();
		long endtime = end.getTime();
		
		String query = "SELECT * FROM "+tblname+"\n" +
					   "WHERE "+datecol+" >='"+starttime+"'\n" +
					   "AND "+datecol+" <='"+endtime+"'";
		
		db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		cursor.moveToFirst();
		
		Set<MetActivity> activities = new HashSet<MetActivity>();
		while(!cursor.isAfterLast()) {
			MetActivity a = cursorToMetActivity(cursor);
			activities.add(a);
			cursor.moveToNext();
		}
		dbHelper.close();
		return activities;
					   
		
	}
	
	
	// Updates
	public void updateMetActivity(MetActivity a) {
	
	}
	// Deletes
	public void deleteMetActivity() {
		
	}
	
	
	private MetActivity cursorToMetActivity(Cursor cursor) {
		
		int colName = cursor.getColumnIndex(COLUMN_NAME);
		int colMetsVal = cursor.getColumnIndex(COLUMN_METSVALUE);
		int colMins = cursor.getColumnIndex(COLUMN_MINUTESDONE);
		
		String name = cursor.getString(colName);
		double metsvalue = cursor.getDouble(colMetsVal);
		int minutes = cursor.getInt(colMins);
		
		MetActivity m = new MetActivity(name, metsvalue, minutes);
		
		return m;
	}

}
