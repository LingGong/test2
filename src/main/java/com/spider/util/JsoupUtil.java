package com.spider.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





/**
 * @ClassName: JsoupUtil
 * @Description: jsoup工具类
 * @author: gl
 * @date: 2018年2月10日 下午4:39:18
 */
public class JsoupUtil { 
	
	 //定义一个全局的记录器，通过LoggerFactory获取
    private final static Logger logger = LoggerFactory.getLogger(JsoupUtil.class); 
	
	/**  
	* @Title: getDocument 
	* @Description: 获取元素
	* @param  url 请求地址
	* @return Document    返回类型  
	* @throws  
	*/ 
	public static Document getDocument(String url){
		
		   try {
	            //获取整个网站的 docuemnt
	            Document document = Jsoup.connect(url)
	            		.header("Accept", "*/*")
	        			.header("Accept-Encoding", "gzip, deflate")
	        			.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
	        			.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
	        			.timeout(20000)
	        			.get();
	            return document;
	        } catch (IOException e) {
	            e.printStackTrace();
	            logger.error("jsoupUtil 出错了："+e.getMessage());
	        }
		   return null;
	    }
	
}

