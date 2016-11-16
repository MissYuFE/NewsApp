package com.example.administrator.daynews.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.daynews.R;
import com.example.administrator.daynews.adapter.LeadImgAdapter;
import com.example.administrator.daynews.base.ui.BaseActivity;
import com.example.administrator.daynews.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-11-3.
 * 引导界面
 */
public class ActivityLead extends BaseActivity {

    private ActivityUtils activityUtils;
    private ViewPager viewPager;
    private ImageView[] points = new ImageView[4];
    private LeadImgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        initView();
        setListener();
        //xml文件存储--音乐，音效，皮肤
        SharedPreferences preferences = getSharedPreferences("runconfig",MODE_PRIVATE);
        //从文件中获取存储的数据，默认为true
        boolean isFirst = preferences.getBoolean("isFirstRun",true);
        activityUtils = new ActivityUtils(this);
        //如果不是第一次打开，则直接跳转到Logo界面
        if (!isFirst){
            activityUtils.startActivity(this, ActivityLogo.class);
            finish();
            return;
        }

        //设置适配器
        viewPager.setAdapter(adapter);
    }

    private void setPoint(int index){
        for (int i = 0;i<points.length;i++){
            if (i == index){
                points[i].setAlpha(255);//设置成不透明
            }else points[i].setAlpha(100);//设置成半透明
        }
    }

    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener(){
        /*当界面调换后调用*/
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int arg0) {
            //用于控制下面的点
            setPoint(arg0);
            if (arg0 >= 3){
                activityUtils.startActivity(ActivityLead.this, ActivityLogo.class);
                finish();
                SharedPreferences preferences = getSharedPreferences("runconfig",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isFirst",false);//false表示不是第一次进入，以后在进入就直接到主界面，不会在进入引导界面
                editor.commit();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void initView() {
        points[0] = (ImageView) findViewById(R.id.iv_p1);
        points[1] = (ImageView) findViewById(R.id.iv_p2);
        points[2] = (ImageView) findViewById(R.id.iv_p3);
        points[3] = (ImageView) findViewById(R.id.iv_p4);
        setPoint(0);
        //初始化控件
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //设置每一个具体界面的样式
        List<View> viewList = new ArrayList<View>();
        viewList.add(getLayoutInflater().inflate(R.layout.lead_1,null));
        viewList.add(getLayoutInflater().inflate(R.layout.lead_2,null));
        viewList.add(getLayoutInflater().inflate(R.layout.lead_3,null));
        viewList.add(getLayoutInflater().inflate(R.layout.lead_4,null));
        //初始化适配器
        adapter = new LeadImgAdapter(ActivityLead.this);
    }

    @Override
    protected void setListener() {

    }


}
