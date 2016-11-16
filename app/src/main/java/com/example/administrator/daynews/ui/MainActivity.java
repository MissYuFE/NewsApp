package com.example.administrator.daynews.ui;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.administrator.daynews.DayNewsApplication;
import com.example.administrator.daynews.R;
import com.example.administrator.daynews.adapter.NewsListAdapter;
import com.example.administrator.daynews.base.ui.BaseActivity;
import com.example.administrator.daynews.biz.ParserNews;
import com.example.administrator.daynews.com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.example.administrator.daynews.db.DBHelper;
import com.example.administrator.daynews.entity.News;
import com.example.administrator.daynews.utils.ActivityUtils;
import com.example.administrator.daynews.utils.NetUtil;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends BaseActivity {

	/*private ArrayList<News> datas;
	private ListView lv;
	private NewsListAdapter adapter;
	private DBHelper helper;
	private ProgressDialog dialog;
	// 分页加载时需要记录的条目数
	private int count = 9;
	private int startId;
	private ActivityUtils activityUtils;
	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {

			// 将获取到的数据传递到适配器
			adapter.addDatas(datas, true);
			// 更新适配器,更新UI
			adapter.notifyDataSetChanged();
			// 缓存新闻数据到本地数据库
			helper.addNews(datas);// 主线程
			// 销毁进度条
			dialog.dismiss();

		}

		;

	};
	*//**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 *//*
	private GoogleApiClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newslist);



		initView();

		setListener();

		adapter.setListView(lv);
		lv.setAdapter(adapter);

		// 判断数据库中是否存在本地缓存数据,如果存在从数据库中查询,如果没有则下载数据
		if (helper.queryNewsCount()) {// 如果新闻表中有数据个数则说明有缓存数据
			// 从数据库查询新闻
			queryFromDB(startId);
		} else {
			// 下载新闻
			downloadNews();// 子线程
		}
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	private void queryFromDB(int startId) {
		// 一次查询9条
		datas = helper.queryAllNewsWithLimit(startId, count);// 从索引为0开始查询,一共查询9条
		//LogUtil.i(datas.toString() + "------");
		adapter.addDatas(datas, false);
		adapter.notifyDataSetChanged();
	}

	private void downloadNews() {
		// 下载前先显示进度条
		dialog = ProgressDialog.show(this, null, "下载中，请稍后...");
		// 获取新闻数据 DefaultHttpClient和HttpGet
		new Thread() {
			public void run() {
				// 发送请求的客户端对象
				String json = NetUtil.httpGet(NetUtil.BASE_PATH
						+ "news_list?ver=" + NetUtil.VERSION
						+ "&subid=1&dir=1&nid=1&stamp=20140321&cnt=20");
				// 解析JSON数据
				datas = ParserNews.parserNews(json);
				// 发送消息给handler
				handler.sendEmptyMessage(0);
			}

			;
		}.start();
	}

	@Override
	protected void initView() {
		activityUtils = new ActivityUtils(this);
		lv = (ListView) findViewById(R.id.newslist_lv);
		adapter = new NewsListAdapter(this);
		// 创建数据库和表
		helper = new DBHelper(this);
	}

	@Override
	protected void setListener() {
		// 列表item的点击事件监听
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				*//*News news = adapter.getItem(position);
				Bundle bundle = new Bundle();
				bundle.putSerializable("news", news);
				activityUtils.startActivityWithBundle(MainActivity.this,
						NewsWebActivity.class, bundle);*//*
				//存入Application
				DayNewsApplication edna = (DayNewsApplication) getApplication();
				News news = adapter.getItem(position);
				Bundle bundle = new Bundle();
				bundle.putSerializable("news", news);
				edna.addData("bundle", bundle);
				activityUtils.startActivity(MainActivity.this,
						NewsWebActivity.class);
			}
		});

		// 设置列表滚动状态监听
		lv.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
					case OnScrollListener.SCROLL_STATE_IDLE:
						adapter.setState(false);
						adapter.notifyDataSetChanged();
						break;
					case OnScrollListener.SCROLL_STATE_FLING:
					case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
						adapter.setState(true);
						break;
				}
			}

			// 当滚动的时候会调用
			*//*
			 * 参数1.眼睛能看见的第一条目的索引
			 * 参数2. visibleItemCount 眼睛能看见的条目个数
			 * 参数3. totalItemCount 总条目个数
			 *//*
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
								 int visibleItemCount, int totalItemCount) {
				// 是否上拉到最底部
				*//*
				 * 第一个要判断列表上的数据量要达到
				 * 第二个要判断列表是否已经上拉到最后的索引位置
				 *//*
				if (totalItemCount >= 7
						&& lv.getLastVisiblePosition() + 1 == totalItemCount) {
					//LogUtil.i("上拉加载");
					queryFromDB(totalItemCount);
				}
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Main Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app deep link URI is correct.
				Uri.parse("android-app://com.example.administrator.daynews.ui/http/host/path")
		);
		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Main Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app deep link URI is correct.
				Uri.parse("android-app://com.example.administrator.daynews.ui/http/host/path")
		);
		AppIndex.AppIndexApi.end(client, viewAction);
		client.disconnect();
	}*/




	private SlidingMenu sm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// configure the SlidingMenu
		sm = new SlidingMenu(this);
		//设置模式，左菜单还是右菜单
		sm.setMode(SlidingMenu.LEFT_RIGHT);
		//手指滑动模式，表示手指在任意一地方滑动都可以（手势滑动范围）
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		//设置菜单拉出后空闲的宽度
		sm.setBehindOffsetRes(150);
		//将菜单添加到Activity中去
		sm.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		//设置一级菜单，左边菜单
		sm.setMenu(R.layout.fragment_menu_left);
		//设置二级菜单，右边菜单
		sm.setSecondaryMenu(R.layout.fragment_menu_right);

	}

	@Override
	protected void initView() {

	}

	@Override
	protected void setListener() {

	}
}
