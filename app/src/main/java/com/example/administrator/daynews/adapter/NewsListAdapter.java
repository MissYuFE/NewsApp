package com.example.administrator.daynews.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.daynews.R;
import com.example.administrator.daynews.base.adapter.MyBaseAdapter;
import com.example.administrator.daynews.biz.ImageLoader;
import com.example.administrator.daynews.entity.News;
import com.example.administrator.daynews.utils.LogUtil;


public class NewsListAdapter extends MyBaseAdapter<News> implements ImageLoader.ImageDownloadListener {

	private ImageLoader imageLoader;
	private ListView lv;
	private boolean state;//控制滚动状态属性

	public void setState(boolean state) {
		this.state = state;
	}

	public NewsListAdapter(Context context) {
		super(context);
		//创建ImageLoader对象
		imageLoader = ImageLoader.getInstance();
		imageLoader.setImageDownloadListener(this);
	}

	public void setListView(ListView lv){
		this.lv = lv;
	}

	@Override
	public View getMyView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if(convertView == null){
			vh = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_list_news, null);
			vh.iv = (ImageView) convertView.findViewById(R.id.item_list_news_iv);
			vh.tv1 = (TextView) convertView.findViewById(R.id.item_list_news_tv1);
			vh.tv2 = (TextView) convertView.findViewById(R.id.item_list_news_tv2);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		//获取数据
		News news = infos.get(position);
		String imgUrl = news.getIcon();
		LogUtil.i(imgUrl);
		//给ImageView设置一个图片的url
		vh.iv.setTag(imgUrl);
//		vh.iv.setImageResource(R.drawable.cccc);
		//判断滚动状态,如果没有滚动才加载图片
		if(!state){
			//缓存获取图片地址
			Bitmap bitmap = imageLoader.getBitmap(imgUrl, context);
			if(bitmap != null){
				vh.iv.setImageBitmap(bitmap);
			}
		}else{
			vh.iv.setImageResource(R.drawable.cccc);
		}

		vh.tv1.setText(news.getTitle());
		vh.tv2.setText(news.getSummary());
		return convertView;
	}

	class ViewHolder{
		ImageView iv;
		TextView tv1,tv2;
	}

	//网上下载的
	@Override
	public void imageDownloadOk(Bitmap bitmap,String imgUrl) {
		//设置在ImageView上
		//findViewWithTag 根据tag中的参数来获取对应的视图对象
		ImageView iv = (ImageView) lv.findViewWithTag(imgUrl);
		LogUtil.i(bitmap+","+imgUrl+","+iv);
		//判断bitmap是否为空
		if(bitmap != null && iv != null){
			iv.setImageBitmap(bitmap);
		}
	}
}
