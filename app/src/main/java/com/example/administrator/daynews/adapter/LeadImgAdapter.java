package com.example.administrator.daynews.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.daynews.ui.ActivityLead;

import java.util.List;

/**
 * Created by Administrator on 16-11-4.
 */
public class LeadImgAdapter extends PagerAdapter{
    private List<View> list;

    public LeadImgAdapter(ActivityLead list){
        this.list= (List<View>) list;
    }


    /** 设置控件中显示界面的数量*/

    public int getCount() {
        return list.size();
    }
    /** 判断是否由对象生成界面*/

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==(View)arg1;
    }
    /**销毁position位置的界面*/

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }
    /** 初始化position位置的界面*/

    public Object instantiateItem(ViewGroup container, int position) {
        View view=list.get(position);
        container.addView(view);
        return view;
    }

}
