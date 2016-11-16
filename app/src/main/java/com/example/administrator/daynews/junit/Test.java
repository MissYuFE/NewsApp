package com.example.administrator.daynews.junit;

import android.test.AndroidTestCase;

import com.example.administrator.daynews.biz.ParserNews;
import com.example.administrator.daynews.utils.NetUtil;

public class Test extends AndroidTestCase {

	public void testParserNews(){
		String json = NetUtil.httpGet(NetUtil.BASE_PATH +
				"news_list?ver=" + NetUtil.VERSION + "&subid=1&dir=1&nid=1&stamp=20140321&cnt=20");
		ParserNews.parserNews(json);
	}
	
}
