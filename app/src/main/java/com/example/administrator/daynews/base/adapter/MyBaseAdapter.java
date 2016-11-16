package com.example.administrator.daynews.base.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

//MyBaseAdapter adapter = new MyBaseAdapter<AppInfo>();
public abstract class MyBaseAdapter<T> extends BaseAdapter {

	protected ArrayList<T> infos;
	protected LayoutInflater inflater;
	protected Context context;

	// 传递数据
	public void addDatas(ArrayList<T> appInfos,boolean isClear) {
		if(isClear){
			infos.clear();
		}
		infos.addAll(appInfos);
	}

	public ArrayList<T> getDatas() {
		return infos;
	}

	public MyBaseAdapter(Context context) {
		this.context = context;
		infos = new ArrayList<T>();
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return infos.size();
	}

	@Override
	public T getItem(int position) {
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getMyView(position,convertView,parent);
	}

	public abstract View getMyView(int position, View convertView, ViewGroup parent);

}
