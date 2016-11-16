package com.example.administrator.daynews.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.administrator.daynews.DayNewsApplication;
import com.example.administrator.daynews.R;
import com.example.administrator.daynews.base.ui.BaseActivity;
import com.example.administrator.daynews.entity.News;


public class NewsWebActivity extends BaseActivity {

	private ProgressBar pb;
	private WebView wv;
	private News news;
	private ImageButton ib;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_web);

		initView();

		// 获取传递过来的News对象
//		news = (News) getIntent().getBundleExtra("bundle").getSerializable(
//				"news");--------------------没懂
		DayNewsApplication edna = (DayNewsApplication) getApplication();
		Bundle bundle = (Bundle) edna.getData("bundle");
		news = (News) bundle.getSerializable("news");

		// 设置网页如果发现没有网络,采用无网络缓存模式
		WebSettings setting = wv.getSettings();
		setting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

		setListener();

		// 设置要加载的网页
		wv.loadUrl(news.getLink());

	}

	private WebViewClient webViewClient = new WebViewClient() {
		// 每次点击链接都会调用此函数,url就是点击的链接地址
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// 需要在我的webview中加载点击的链接
			wv.loadUrl(url);
			return true;
		};
	};

	private WebChromeClient webChromeClient = new WebChromeClient() {
		// 当网页加载进度改变的时候会调用,newProgress就是当前加载的网页进度
		public void onProgressChanged(WebView view, int newProgress) {
			// 给进度条设置当前加载的进度
			pb.setProgress(newProgress);

			// 判断网页是否加载完毕
			if (newProgress >= 100) {
				pb.setVisibility(View.GONE);
			}
		};
	};

	@Override
	protected void initView() {
		pb = (ProgressBar) findViewById(R.id.news_web_pb);
		wv = (WebView) findViewById(R.id.news_web_wv);
		ib = (ImageButton) findViewById(R.id.title_bar);
	}

	@Override
	protected void setListener() {
		//设置titlebar上的按钮,点击退出网页
		ib.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				NewsWebActivity.this.finish();
			}
		});
		// 设置一个监听用来监听网页加载过程
		wv.setWebChromeClient(webChromeClient);

		// 设置当点击了网页中的链接的时候的监听
		wv.setWebViewClient(webViewClient);
	}
}
