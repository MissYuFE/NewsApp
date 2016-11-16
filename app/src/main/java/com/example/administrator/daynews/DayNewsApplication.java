package com.example.administrator.daynews;

import java.util.HashMap;

import android.app.Application;
/*
 * 应用程序的入口
 * 入口点  onCreate
 * 作用
 * 1. 可以重写Application类中的很多函数,用于控制整个应用程序的初始化/内存过低/应用程序结束等工作
 * 2. 可以将应用程序中多个Activity之间共同要使用的数据存储在Application中
 * */
public class DayNewsApplication extends Application {

	//为了存储数据
	private HashMap<String,Object> maps;

	/* 存放数据到集合*/
	public void addData(String key,Object obj){
		maps.put(key, obj);
	}

	/* 获取集合中的数据*/
	public Object getData(String key){
		if(maps.containsKey(key)){
			return maps.get(key);
		}
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		//对于整个应用程序的初始化工作
		maps = new HashMap<String,Object>();
	}

	//当内存过低
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		System.out.println("memroy low!");
	}

	//当整个应用程序结束
	@Override
	public void onTerminate() {
		super.onTerminate();
		System.out.println("DayNewsApplicationDestroy");
		maps.clear();
	}
}
