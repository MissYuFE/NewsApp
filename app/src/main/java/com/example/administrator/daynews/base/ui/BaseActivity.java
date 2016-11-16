package com.example.administrator.daynews.base.ui;

import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	protected abstract void initView();
	protected abstract void setListener();
	
}
