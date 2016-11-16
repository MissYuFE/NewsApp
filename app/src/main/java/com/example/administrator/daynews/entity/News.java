package com.example.administrator.daynews.entity;

import java.io.Serializable;
//新闻对象序列化,方便在网络上传递
public class News implements Serializable{

/*	{"message":"OK",
		"status":0,
		"data":[
		        {"summary":"这种与巨头间的平衡关系在财报上也有体现。猎豹的广告收入中有70%来自BAT三家公司，总收入中有58%来自BAT。从上述来看，雷军在对金山改组、选将、外交上的智慧，为猎豹的发展铺下了坚实的路。",
		        	"icon":"http:\/\/localhost:8080\/Images\/2014050909082044669.png",
		        	"stamp":"2016-10-20 10:26:35.0",
		        	"title":"写在猎豹上市：起承转合这几年",
		        	"nid":4,
		        	"link":"http:\/\/tech.163.com\/14\/0509\/09\/9RPSM3TJ000915BF.html",
		        	"type":1
		        },*/


	private int nId;
	private int type;
	private String title;
	private String icon;
	private String link;
	private String stamp;
	private String summary;
	public int getnId() {
		return nId;
	}
	public int getType() {
		return type;
	}
	public String getTitle() {
		return title;
	}
	public String getIcon() {
		return icon;
	}
	public String getLink() {
		return link;
	}
	public String getStamp() {
		return stamp;
	}
	public String getSummary() {
		return summary;
	}
	public News(int nId, int type, String title, String icon, String link,
				String stamp, String summary) {
		super();
		this.nId = nId;
		this.type = type;
		this.title = title;
		this.icon = icon;
		this.link = link;
		this.stamp = stamp;
		this.summary = summary;
	}
	@Override
	public String toString() {
		return "News [nId=" + nId + ", type=" + type + ", title=" + title
				+ ", icon=" + icon + ", link=" + link + ", stamp=" + stamp
				+ ", summary=" + summary + "]";
	}
}
