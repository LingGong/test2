package com.spider.template;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.spider.model.SpiderContent;
import com.spider.util.JsoupUtil;
import com.spider.util.SpiderAnalysis;

public class BaiduSpider<T> implements SpiderAnalysis {

	public  SpiderContent<T> getContent(String url) {
		Document document=JsoupUtil.getDocument(url);
		Element e=document.select("input[type='submit']").get(0);
		SpiderContent<T> s=new SpiderContent<T>();
		s.setContent(e.val());
		return s;
	}

}
