package com.spider.model;

import java.util.List;

import com.base.model.BaseModel;

public class SpiderContent<T> extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String url;//爬区页面
	private String title;//标题
	private String content;//文本类容
	private List<T> list;//包含页面列表内容
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	

}
