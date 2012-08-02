package com.eliotpearson.pingcheck.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PingCheckDao {
	private SQLiteDatabase database;
	private DBHelper dbHelper;
	private String[] allColumns = { DBHelper.COLUMN_ID,
			DBHelper.COLUMN_URL };
	
	public PingCheckDao(Context context) {
		dbHelper = new DBHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public PingCheck createPingCheck(String url) {
		ContentValues values = new ContentValues();
		values.put(DBHelper.COLUMN_URL, url);
		long insertId = database.insert(DBHelper.TABLE_PING_CHECK,null, values);
		Cursor cursor = database.query(DBHelper.TABLE_PING_CHECK, allColumns, 
				DBHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		
		cursor.moveToFirst();
		
		PingCheck pingCheck = cursorToPingCheck(cursor);
		
		cursor.close();
		
		return pingCheck;
	}
	
	public void deletePingCheck(PingCheck pingCheck) {
		long id = pingCheck.getId();
		
		database.delete(DBHelper.TABLE_PING_CHECK, 
				DBHelper.COLUMN_ID + " = " + id, null);
	}
	
	public List<PingCheck> getAllPingChecks() {
		List<PingCheck> pingCheckList = new ArrayList<PingCheck>();
		
		Cursor cursor = database.query(DBHelper.TABLE_PING_CHECK, allColumns,
				null, null, null, null, null);
		
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			PingCheck pingCheck = cursorToPingCheck(cursor);
			pingCheckList.add(pingCheck);
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return pingCheckList;
	}
	
	
	private PingCheck cursorToPingCheck(Cursor cursor) {
		PingCheck pingCheck = new PingCheck();
		
		pingCheck.setId(cursor.getLong(0));
		pingCheck.setUrl(cursor.getString(1));
		
		return pingCheck;
	}

	public PingCheck getPingCheckById(long id) {
		Cursor cursor = database.query(DBHelper.TABLE_PING_CHECK, allColumns, 
				DBHelper.COLUMN_ID + " = " + id, null, null, null, null);
		
		cursor.moveToFirst();
		
		PingCheck pingCheck = cursorToPingCheck(cursor);
		
		cursor.close();
		
		return pingCheck;
	}
}
