package com.example.administrator.daynews.db;

import java.util.ArrayList;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.daynews.db.DBOpenHelper;
import com.example.administrator.daynews.entity.News;
import com.example.administrator.daynews.utils.LogUtil;

public class DBHelper {

	private DBOpenHelper helper;
	private SQLiteDatabase db;

	public DBHelper(Context context){
		if(helper == null){
			helper = new DBOpenHelper(context);
		}
		db = helper.getWritableDatabase();
	}

	/* 添加新闻数据到数据库*/
	public void addNews(ArrayList<News> datas){

		//打开数据库
		db = helper.getWritableDatabase();

		for(News news:datas){
			/*
			 * 参数1.表名
			 * 参数2.随意填入一个不为空的列名
			 * 参数3.数据
			 * */
			ContentValues cv = new ContentValues();
//			_id integer primary key autoincrement,"
//					+ "nid integer,title text,summary text,stamp text,icon text,link text,type integer
			cv.put("nid", news.getnId());
			cv.put("title", news.getTitle());
			cv.put("summary", news.getSummary());
			cv.put("stamp", news.getStamp());
			cv.put("icon", news.getIcon());
			cv.put("link", news.getLink());
			cv.put("type", news.getType());
			db.insert(DBOpenHelper.TABLE_NAME, "link", cv);
		}

		//关闭数据库
		db.close();
	}

	/* 查询新闻表中有多少条数据*/
	public boolean queryNewsCount(){
		//打开数据库
		db = helper.getWritableDatabase();

		//select count(*) from news;  6
		/* 参数1.sql查询语句
		 * 参数2. 查询所需要的条件*/
		Cursor cursor = db.rawQuery("select count(*) from news",null);
		//先将指针指向第一行
		cursor.moveToFirst();
		//获取数据
		long count = cursor.getLong(0);
		LogUtil.i(count + "");
		//关闭cursor
		cursor.close();
		//关闭数据库
		db.close();
		return count == 0?false:true;
	}

	/* 查询所有的新闻数据*/
	public ArrayList<News> queryAllNewsWithLimit(int startId,int count){//从第几个开始,要查几个

		ArrayList<News> datas = new ArrayList<News>();
		//打开数据库
		db = helper.getWritableDatabase();
		//select summary from news limit 2 offset 3;
//		Cursor cursor = db.query(DBOpenHelper.TABLE_NAME, null, null, null, null, null, null, null);
		LogUtil.i("select * from news limit "+count+" offset "+startId);
		Cursor cursor = db.rawQuery("select * from news limit "+count+" offset "+startId,null);
//		db.execSQL("");
		//有数据
		if(cursor.moveToFirst()){
			do{
//				db.execSQL("create table "+TABLE_NAME+"(_id integer primary key autoincrement,"
//						+ "nid integer,title text,summary text,stamp text,icon text,link text,type integer)");
				int nid = cursor.getInt(cursor.getColumnIndex("nid"));
				String title = cursor.getString(cursor.getColumnIndex("title"));
				String summary = cursor.getString(cursor.getColumnIndex("summary"));
				String stamp = cursor.getString(cursor.getColumnIndex("stamp"));
				String icon = cursor.getString(cursor.getColumnIndex("icon"));
				String link = cursor.getString(cursor.getColumnIndex("link"));
				int type = cursor.getInt(cursor.getColumnIndex("type"));
//				public News(int nId, int type, String title, String icon, String link,
//						String stamp, String summary) {
				News news = new News(nid,type,title,icon,link,stamp,summary);
				datas.add(news);
			}while(cursor.moveToNext());
		}
//		db.query(DBOpenHelper.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
//		db.query(DBOpenHelper.TABLE_NAME, null, "_id = 1", null, null, null, null);
		//?表示占位符
//		db.query(DBOpenHelper.TABLE_NAME, new String[]{"summary"}, "_id = ?", new String[]{"1"}, null, null, "desc");

		cursor.close();
		//关闭数据库
		db.close();
		return datas;
	}
}
