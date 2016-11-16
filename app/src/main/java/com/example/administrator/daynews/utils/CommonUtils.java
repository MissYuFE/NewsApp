package com.example.administrator.daynews.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/* 常用函数*/
public class CommonUtils {

	public static String parseDateToString(long lastModify) {
		// 将long类型转换成日期
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(lastModify);
		Date date = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
				Locale.CHINA);
		String time = sdf.format(date);
		return time;
	}


}
