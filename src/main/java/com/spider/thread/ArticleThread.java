package com.spider.thread;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.base.util.SpringContextUtils;
import com.spider.cache.RedisArticleCache;
import com.spider.model.Article;
import com.spider.util.JsoupUtil;

public class ArticleThread implements Runnable {
	
	private RedisArticleCache cache = (RedisArticleCache) SpringContextUtils.getBean(RedisArticleCache.class);
	
    private AtomicBoolean  flag = null;
    private AtomicReference<String> nexturl=null; 
	private String oldUrl;//数据库中已经爬取最新的文章url
    private String startUrl;//文章原始url
    
    public ArticleThread(AtomicBoolean flag,AtomicReference<String> nexturl,String oldUrl,String startUrl){
    	this.flag=flag;
    	this.nexturl=nexturl;
    	this.oldUrl=oldUrl;
    	this.startUrl=startUrl;
    }
    
	public void run() {
			if(!flag.get()){//确保线程安全
				return;
			}else{
				Document document=null;
				synchronized (nexturl) {
					 document=JsoupUtil.getDocument(nexturl.get());
					 nexturl.set(startUrl+document.select(".list_page a:contains(下一页)").attr("href"));
					 System.out.println("当前"+Thread.currentThread().getName()+":"+"nextUrl:"+nexturl);
				}
				Elements e=document.select(".DlistWfc");//每一个大的div  包括文章标题 是否推荐 来源 url等信息
				for (Element element : e) {//判断该文章是否已经被爬取入库  已经入库则停止爬取
					Element aElement=element.selectFirst("h2 a");
					if(aElement.attr("href").equals(oldUrl)){
						flag.set(false);
						break;
					}
					Article a=new Article();
					a.setUrl(aElement.attr("href"));
					a.setTitle(aElement.text());
					if(element.selectFirst(".ico_tj")!=null){//是否推荐
						a.setRecommend(1);
					}
					LinkedList<String> spanlist=new LinkedList<String>();
					for(Element spanElement :element.select(".fengqw")){
						spanlist.add(spanElement.text());
					}
					a.setSpan(StringUtil.join(spanlist, ","));
//					a.setSource(element.selectFirst(".fengP1").selectFirst(cssQuery));
					LinkedList<String> datelist=new LinkedList<String>();
					for(Element dateElement :element.select(".fenghk")){
						datelist.add(dateElement.text());
					}
					a.setTime(StringUtil.join(datelist, " "));	
					cache.insertArticle(a);
				}
			}
			
	}

	


	public String getOldUrl() {
		return oldUrl;
	}

	public void setOldUrl(String oldUrl) {
		this.oldUrl = oldUrl;
	}

	public String getStartUrl() {
		return startUrl;
	}

	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}

}
