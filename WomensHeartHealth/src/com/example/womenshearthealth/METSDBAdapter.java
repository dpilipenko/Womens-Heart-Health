package com.example.womenshearthealth;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

	METSDBOpenHelper DBHelper;
	SQLiteDatabase db;
	
	

	public METSDBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new METSDBOpenHelper(this.context, DATABASE_NAME, null, DATABASE_VERSION);
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

	public METSDBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		DBHelper.close();
	}

	//////////////////////////////////
	
	// CRUD OPERATIONS 
	
	// Create
	public void addMetActivity(MetActivity activity, Date date) {
		
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
		
	};
	
	
	public void sandbox() {
	
		
		Cursor c = db.query(DATABASE_TABLE_NAME, COLUMNS, null, null, null, null, null);
		c.moveToFirst();
		c.close();
		return;
	}
	
	// Read
	public List<MetActivity> getAllMetActivities() {
		List<MetActivity> activities = new LinkedList<MetActivity>();
		String orderBy = COLUMN_DATESUBMITTEDASLONG + " DESC";
		Cursor cursor = db.query(DATABASE_TABLE_NAME, COLUMNS, null, null, null, null, orderBy);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			MetActivity a = cursorToMetActivity(cursor);
			activities.add(a);
			cursor.moveToNext();
		}
		return activities;
		
		
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

	public MetActivity getMetActivity() {
		return null;
	};
	
	// Update
	public void updateMetActivity(MetActivity a) {
	
	}
	// Delete
	public void deleteMetActivity() {
		
	}
	
	
	
	
	
	
	
	
	
	/////////////////////////////////////////////////////////////////////////////////
	
	public boolean deleteMET(int rowId) {
		return db.delete(DATABASE_TABLE_NAME, COLUMN_ID + "=" + rowId, null) > 0;
	}

	public Cursor getAllMetActivitiesAsCursor() {
		
		String table = DATABASE_TABLE_NAME;
		String[] columns = {
				COLUMN_ID, COLUMN_NAME, COLUMN_METSVALUE, COLUMN_MINUTESDONE, COLUMN_DATESUBMITTEDASLONG
		};
		String selection = "*";
		String[] selectionArgs = null;
		String groupBy = null;
		String having = null;
		String orderBy = COLUMN_DATESUBMITTEDASLONG + " DESC";
		String limit = null;
		
		Cursor c = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
		
		return c;
	}
	
	public Cursor getMetActivityAsCursor(int rowId) {
		
		String table = DATABASE_TABLE_NAME;
		String[] columns = {
				COLUMN_ID, COLUMN_NAME, COLUMN_METSVALUE, COLUMN_MINUTESDONE, COLUMN_DATESUBMITTEDASLONG
		};
		String selection = COLUMN_ID+"="+rowId;
		String[] selectionArgs = null;
		String groupBy = null;
		String having = null;
		String orderBy = null;
		String limit = null;
		
		Cursor c = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
		
		if (c != null) {
			c.moveToFirst();
		}
		return c;
		
	}
	
}
