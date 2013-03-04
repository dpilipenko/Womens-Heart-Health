package com.example.womenshearthealth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class METSDBAdapter {
	static final String TAG = "METSDBAdapter";
	private static final String DATABASE_NAME = "metsDatabase.db";
	private static final String DATABASE_TABLE = "METS";
	private static final int DATABASE_VERSION = 1;

	public static final String KEY_ID = "_id";
	public static final String KEY_METS_NAME_COLUMN = "METS_NAME_COLUMN";
	public static final String KEY_METS_AMOUNT_COLUMN = "METS_AMOUNT_COLUMN";
	public static final String KEY_METS_DATE_COLUMN = "METS_DATE_COLUMN";

	private static final String DATABASE_CREATE = "create table "
			+ DATABASE_TABLE + " (" + KEY_ID
			+ " integer primary key autoincrement, " + KEY_METS_NAME_COLUMN
			+ " text not null, " + KEY_METS_AMOUNT_COLUMN + " float, "
			+ KEY_METS_DATE_COLUMN + " integer);";

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
				db.execSQL(DATABASE_CREATE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("TaskDBAdapter", "Upgrading from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all data");
			db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
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

	public long insertMETSActivity(String name, int mets, String date) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_METS_NAME_COLUMN, name);
		initialValues.put(KEY_METS_AMOUNT_COLUMN, mets);
		initialValues.put(KEY_METS_DATE_COLUMN, date);
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	public boolean deleteMET(long rowId) {
		return db.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) > 0;
	}

	public Cursor getAllMETS() {
		Cursor cur = db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_METS_NAME_COLUMN,
				KEY_METS_AMOUNT_COLUMN, KEY_METS_DATE_COLUMN }, null, null, null, null, null);
		return cur;
	}

	public Cursor getMET(long rowId) throws SQLException {
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
				KEY_ID, KEY_METS_NAME_COLUMN, KEY_METS_AMOUNT_COLUMN, KEY_METS_DATE_COLUMN }, KEY_ID + "=" + rowId,
				null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public boolean updateMET(long rowId, String name, String amount, String date) {
		ContentValues args = new ContentValues();
		args.put(KEY_METS_NAME_COLUMN, name);
		args.put(KEY_METS_AMOUNT_COLUMN, amount);
		args.put(KEY_METS_DATE_COLUMN, amount);
		return db.update(DATABASE_TABLE, args, KEY_ID + "=" + rowId, null) > 0;
	}
}
