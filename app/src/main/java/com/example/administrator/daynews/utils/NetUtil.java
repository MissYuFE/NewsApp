package com.example.administrator.daynews.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class NetUtil {

	//应用程序基础路径
	public static final String BASE_PATH = "http://118.244.212.82:9092/newsClient/";
	//应用程序版本号
	public static final int VERSION = 1;
	//最大连接数
	public static final int MAXTOTALCONN = 9;
	//超时时间
	public static final int TIMEOUT = 3000;

	private NetUtil(){}

	private static HttpClient httpClient;

	/* 返回一个客户端的对象*/
	public static HttpClient getHttpClient(){
		if(httpClient == null){
			//创建参数对象
			HttpParams params = new BasicHttpParams();
			//设置属性
			//设置客户端连接服务器的最大连接数
			ConnManagerParams.setMaxTotalConnections(params, MAXTOTALCONN);
			//定义了从ConnectionManager管理的连接池中取出连接的超时时间
			ConnManagerParams.setTimeout(params, TIMEOUT);
			//定义了通过网络与服务器建立连接的超时时间。HttpClient通过一个异步线程去创建与服务器的Socket连接，这就是该Socket连接的超时时间
			HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);
			//定义了Socket读数据的超时时间，即从服务器获取响应数据需要等待的时间。
			HttpConnectionParams.setSoTimeout(params, TIMEOUT);
			httpClient = new DefaultHttpClient(params);
		}
		return httpClient;
	}

	/* 用于get请求的网络连接*/
	public static String httpGet(String url){
		//创建一个请求对象Get
		HttpGet hg = new HttpGet(url);
		try {
			//发送请求
			HttpResponse res = getHttpClient().execute(hg);
			//判断连接是否成功
			if(res.getStatusLine().getStatusCode() == 200){
				//接收服务器返回的数据
				HttpEntity entity = res.getEntity();
				//将响应体转换成字符串
				String datas = EntityUtils.toString(entity);
				LogUtil.i(datas);
				return datas;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
