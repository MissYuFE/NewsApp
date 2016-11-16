package com.example.administrator.daynews.utils;

import android.util.Log;

public final class LogUtil {

	private static boolean isLog = true;
	private static final String TAG = "everyDayNews";

	/**
	 * 打印普通信息的日志
	 * @param msg	日志的内容
	 */
	public static void d(String tag,String msg){
		if(isLog){
			Log.d(tag, msg);
		}
	}

	/**
	 * 打印info信息的日志
	 * @param msg	日志的内容
	 */
	public static void i(String msg){
		if(isLog){
			Log.i(TAG, msg);
		}
	}

	/**
	 * 打印严重信息的日志
	 * @param msg	日志的内容
	 */
	public static void w(String tag,String msg){
		if(isLog){
			Log.w(tag, msg);
		}
	}

}
