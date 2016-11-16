package com.example.administrator.daynews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "news.db";
	public static final String TABLE_NAME = "news";
	private static final int DB_VERSION = 1;
	
	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public DBOpenHelper(Context context) {
		this(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table "+TABLE_NAME+"(_id integer primary key autoincrement,"
				+ "nid integer,title text,summary text,stamp text,icon text,link text,type integer)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
