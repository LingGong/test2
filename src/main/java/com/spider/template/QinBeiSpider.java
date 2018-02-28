package com.spider.template;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.spider.model.Article;
import com.spider.model.SpiderContent;
import com.spider.util.JsoupUtil;

/**   
*    
* 项目名称：test2   
* 类名称：QinBeiSpider   
* 类描述：   亲呗网爬取美容养颜类文章
* 创建人：gl  
* 创建时间：2018年2月11日 上午9:17:16   
* 修改人：  
* 修改时间： 
* 修改备注：   
* @version    
*    
*/
public class QinBeiSpider<T> implements SpiderAnalysis {

	public SpiderContent<Article> getContent(String url) {
		Document document=JsoupUtil.getDocument(url);
		Elements e=document.select(".item-subject-hot h3 a");
		List<Article> list=new ArrayList<Article>();
		for (Element element : e) {
			Article a=new Article();
			a.setUrl(element.attr("href"));
			a.setTitle(element.text());
			list.add(a);
		}
		SpiderContent<Article> spiderContent=new SpiderContent<Article>();
		spiderContent.setList(list);
		return spiderContent;
	}

}
