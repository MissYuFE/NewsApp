package com.example.administrator.daynews.biz;

import com.example.administrator.daynews.entity.News;
import com.example.administrator.daynews.utils.LogUtil;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/* 新闻数据解析的业务处理*/
public class ParserNews {

	public static ArrayList<News> parserNews(String json){

		ArrayList<News> datas = new ArrayList<News>();
		try {
			LogUtil.i(json + "");//null
			//转换成JSONObject
			JSONObject obj = new JSONObject(json);
			String msg = obj.getString("message");
			if(msg.equals("OK")){//数据获取成功
				//继续解析
				JSONArray arr = obj.getJSONArray("data");
				for(int x = 0; x < arr.length(); x++){
					JSONObject ob = arr.getJSONObject(x);
					//获取具体的新闻数据
					int id = ob.getInt("nid");
					String icon = ob.getString("icon");
					String summary = ob.getString("summary");
					int type = ob.getInt("type");
					String url = ob.getString("link");
					String stamp = ob.getString("stamp");
					String title = ob.getString("title");

					//创建一个新闻对象,存入数据
					News news = new News(id,type,title,icon,url,stamp,summary);
					//存入集合
					datas.add(news);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return datas;
	}
	
}
