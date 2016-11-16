package com.example.administrator.daynews.biz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;

import com.example.administrator.daynews.utils.LogUtil;

/* 图片下载以及处理缓存类*/
public class ImageLoader {

	// 采用近期最少使用算法LruCache
	private LruCache<String, Bitmap> cache;
	private static ImageLoader imageLoader;
	private Context context;

	private ImageLoader() {
		cache = new LruCache<String, Bitmap>(((int) Runtime.getRuntime()
				.maxMemory() >> 3)) {

			// 计算存入的每张图片缓存的大小
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// return value.getByteCount();
				return value.getRowBytes() * value.getHeight();
			}

		};
	}

	public static ImageLoader getInstance() {
		if (imageLoader == null) {
			synchronized (ImageLoader.class) {
				if (imageLoader == null) {
					imageLoader = new ImageLoader();
				}
			}
		}
		return imageLoader;
	}

	// 回调函数使用的接口
	public interface ImageDownloadListener {
		// 回调函数
		void imageDownloadOk(Bitmap bitmap, String url);
	}

	// 记录传递过来的对象
	private ImageDownloadListener imageDownloadListener;

	// 提供一个设置监听的方法
	public void setImageDownloadListener(
			ImageDownloadListener imageDownloadListener) {
		this.imageDownloadListener = imageDownloadListener;
	}

	/* 使用我的缓存策略来处理图片 */
	public Bitmap getBitmap(String imgUrl, Context context) {
		this.context = context;
		Bitmap bitmap = null;
		// 1. 判断你给我的图片url是否正确
		if (imgUrl == null || imgUrl.length() == 0) {
			return bitmap;
		}

		// 2. 先从Lru缓存中查找图片是否存在
		bitmap = getBitmapFromLruCache(imgUrl);
		// 判断是否拿到图片
		if (bitmap != null) {
			return bitmap;
		}

		// 3. 如果还是没有拿到,则从文件缓存中查找
		bitmap = getBitmapFromFile(imgUrl);
		if (bitmap != null) {
			return bitmap;
		}

		// 4. 从网上下载
		asynDownloadBitmap(imgUrl);
		return null;
	}

	// http://192.168.1.201:8080/Images/xxxxxx.jpg
	private Bitmap getBitmapFromFile(String imgUrl) {
		// 获取手机临时存储区域
		File file = context.getCacheDir();
		LogUtil.i(file.getAbsolutePath());
		// 如果这个文件夹不存在,则创建
		if (!file.exists()) {
			file.mkdirs();
		}
		// 截取图片名称
		String imgName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
		// data/data/包名/123.jpg
		File imgFile = new File(file, imgName);
		return BitmapFactory.decodeFile(imgFile.getAbsolutePath());
	}

	private Bitmap getBitmapFromLruCache(String imgUrl) {
		return cache.get(imgUrl);
	}

	/* 异步任务下载图片 */
	private void asynDownloadBitmap(String imgUrl) {
		// 判断如果是有效的url才下载
		if (imgUrl == null || imgUrl.length() <= 0) {
			return;
		}
		// 创建异步任务对象
		ImageDownloadTask task = new ImageDownloadTask();
		// 当execute函数执行时就会依次的调用task中的onpreExcute->doInBackground->onPostExcute函数
		task.execute(imgUrl);
	}

	// 创建一个类继承于安卓中的异步任务类
	/*
	 * 异步任务类中,三个泛型的作用 1. 下载任务中要用到的参数 2. 只有当用到进度条的时候才需要的进度值 3. 任务结束后需要返回的数据类型
	 */
	private class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {

		private String url;

		// 在执行下载工作前的准备工作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		// 在后台执行的函数(开了一个子线程)
		@Override
		protected Bitmap doInBackground(String... params) {// 类似与Thread
			// 先获取url地址
			url = params[0];

			HttpURLConnection conn = null;
			try {
				// 下载
				conn = (HttpURLConnection) new URL(url).openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(3000);

				if (conn.getResponseCode() == 200) {
					InputStream is = conn.getInputStream();
					Bitmap bitmap = BitmapFactory.decodeStream(is);

					// 当每次下载结束就立即缓存的到两个缓存区域中
					// 1. 存入cache
					saveToLruCache(bitmap, url);

					// 2. 存入缓存文件
					saveToFile(bitmap, url);
					return bitmap;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (conn != null) {
					conn.disconnect();
				}
			}
			return null;
		}

		// 下载结束后应该将图片传递给适配器,让适配器显示
		@Override
		protected void onPostExecute(Bitmap result) {// 类似于Handler
			super.onPostExecute(result);
			imageDownloadListener.imageDownloadOk(result, url);
		}
	}

	public void saveToLruCache(Bitmap bitmap, String url) {
		// 判断缓存中如果已经存在,则不需要重复存储
		if (cache.get(url) == null) {
			// 存入
			cache.put(url, bitmap);
		}
	}

	public void saveToFile(Bitmap bitmap, String url) {
		// 获取手机临时存储区域
		File file = context.getCacheDir();
		// 如果这个文件夹不存在,则创建
		if (!file.exists()) {
			file.mkdirs();
		}
		// 截取图片名称
		String imgName = url.substring(url.lastIndexOf("/") + 1);
		// data/data/包名/123.jpg
		File imgFile = new File(file, imgName);
		OutputStream os = null;
		try {
			os = new FileOutputStream(imgFile);
			//将图片存入指定的文件夹
			bitmap.compress(CompressFormat.JPEG, 80, os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally{
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
