package com.example.administrator.daynews.ui;

/**
 * Created by Administrator on 16-11-4.
 */

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.administrator.daynews.R;
import com.example.administrator.daynews.base.ui.BaseActivity;
import com.example.administrator.daynews.utils.ActivityUtils;

/** Logo界面 ***/
    public class ActivityLogo extends BaseActivity {

        private ActivityUtils activityUtils;
        private Animation animation;
        private ImageView logo;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_logo);
            initView();
            setListener();
            logo.setAnimation(animation);
        }

        @Override
        protected void initView() {
            //制作动画
            activityUtils = new ActivityUtils(this);
            logo = (ImageView) findViewById(R.id.iv_logo);
            animation = AnimationUtils.loadAnimation(this, R.anim.logo);
            animation.setFillAfter(true);
        }

        @Override
        protected void setListener() {
            animation.setAnimationListener(new Animation.AnimationListener() {
                // 动画启动时调用
                @Override
                public void onAnimationStart(Animation animation) {
                }

                // 动画重复时调用
                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                // 动画结束时调用
                @Override
                public void onAnimationEnd(Animation animation) {
                    activityUtils.startActivity(ActivityLogo.this,
                            MainActivity.class);
                    ActivityLogo.this.finish();
                }
            });
        }
}
