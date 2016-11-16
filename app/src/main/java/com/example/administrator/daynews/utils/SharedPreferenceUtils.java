package com.example.administrator.daynews.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceUtils {

	private static SharedPreferences sp;
	private static final String KEY_FIRST_STATE = "key_first_state";

	/* 第一进入应用时存入一个true*/
	public static void saveFirstApp(Context context,boolean state){
		/*
		 * 获取简单编辑器对象时,会创建一个XML文件,参数1.给XML文件命名	2.文件的访问权限
		 * */
		sp = context.getSharedPreferences("state", Context.MODE_PRIVATE);
		//编辑器
		Editor editor = sp.edit();
		editor.putBoolean(KEY_FIRST_STATE, state);
		//提交
		editor.commit();
	}

	/* 取出boolean值判断是否是一次进入应用*/
	public static boolean getFirstApp(Context context){
		sp = context.getSharedPreferences("state", Context.MODE_PRIVATE);
		//参数2如果找不到,则使用的默认值
		return sp.getBoolean(KEY_FIRST_STATE, false);
	}}
