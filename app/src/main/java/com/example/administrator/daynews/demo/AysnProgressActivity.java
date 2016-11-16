package com.example.administrator.daynews.demo;

import java.io.IOException;
import java.io.InputStream;



import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.daynews.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class AysnProgressActivity extends Activity {

	private ProgressBar pb;
	private TextView tv;
	private static final String IMG_URL = "http://118.244.212.82:9092/Images/20160517115649.jpg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aysn_progress);
		pb = (ProgressBar) findViewById(R.id.pb);
		tv = (TextView) findViewById(R.id.tv);

		//开启一个任务,下载图片
		MyTask task = new MyTask();
		task.execute(IMG_URL);

	}

	class MyTask extends AsyncTask<String, Integer, Bitmap>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//下载前,显示进度条
			pb.setVisibility(View.VISIBLE);
			//更改下面的文字
			tv.setText("开始下载图片，请等待...");
		}

		//当进度更新的时候,当调用publishProgress()函数时会触发此函数
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			//设置在进度条上
			pb.setProgress(values[0]);
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			DefaultHttpClient client = new DefaultHttpClient();
			//创建参数对象
			HttpParams param = new BasicHttpParams();
			//设置属性
			//设置客户端连接服务器的最大连接数
			ConnManagerParams.setMaxTotalConnections(param, 8);
			//定义了从ConnectionManager管理的连接池中取出连接的超时时间
			ConnManagerParams.setTimeout(param, 3000);
			//定义了通过网络与服务器建立连接的超时时间。HttpClient通过一个异步线程去创建与服务器的Socket连接，这就是该Socket连接的超时时间
			HttpConnectionParams.setConnectionTimeout(param, 3000);
			//定义了Socket读数据的超时时间，即从服务器获取响应数据需要等待的时间。
			HttpConnectionParams.setSoTimeout(param, 3000);
			HttpGet hg = new HttpGet(params[0]);

			try {
				HttpResponse res = client.execute(hg);

				if(res.getStatusLine().getStatusCode() == 200){
					HttpEntity entity = res.getEntity();
					InputStream is = entity.getContent();
					//先获取图片的最大字节数
					long max = entity.getContentLength();
					int count = 0;
					while(count < max){
						count+=100;
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						publishProgress((int)(count / (float)max * 100));
					}

					Bitmap bitmap = BitmapFactory.decodeStream(is);
					return bitmap;
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			//下载结束,隐藏进度条
			pb.setVisibility(View.INVISIBLE);
			tv.setText("图片下载成功!");
		}

	}
}
