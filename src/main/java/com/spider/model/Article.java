package com.spider.model;

import com.base.model.BaseModel;

public class Article extends BaseModel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String url;//爬区的url
	private String title;//文章标题
	private String time;//文章发布时间
	private String author;//文章发布作者
	private String content;//文章内容
	private String source;//文章来源
	private String span;//文章标签 用逗号分隔
	private Integer recommend;//是否推荐 0 非推荐 1推荐
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSpan() {
		return span;
	}
	public void setSpan(String span) {
		this.span = span;
	}
	public Integer getRecommend() {
		return recommend;
	}
	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}
	
}
