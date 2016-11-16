package com.example.administrator.daynews.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast������
 * 
 * @author yuanc
 */
public class ToastUtil {

	private static Toast toast = null;

	public static void showToast(Context context, String text, int duration) {
		if (toast == null) {
			toast = Toast.makeText(context, text, duration);
		}
		toast.setText(text);
		toast.setDuration(duration);
		toast.show();
	}
}