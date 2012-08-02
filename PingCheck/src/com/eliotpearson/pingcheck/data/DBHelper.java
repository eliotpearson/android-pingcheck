package com.eliotpearson.pingcheck.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	public static final String TABLE_PING_CHECK = "pingcheck";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_URL = "url";

	private static final String DATABASE_NAME = "check.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_PING_CHECK + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_URL
			+ " text not null);";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DBHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PING_CHECK);
		onCreate(db);
	}

} 