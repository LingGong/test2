package com.spider.template;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;







import com.spider.model.Article;
import com.spider.model.SpiderContent;
import com.spider.thread.ArticleThread;
import com.spider.util.SpiderAnalysis;


/**   
*    
* 项目名称：test2   
* 类名称：Jk99Spider   
* 类描述： 99健康网 女性养生类文章爬区  
* 创建人：gl   
* 创建时间：2018年2月22日 上午10:25:53   
* 修改人：
* 修改时间：
* 修改备注：   
* @version    
*    
*/

public class Jk99Spider<T> implements SpiderAnalysis {
	
	
	public SpiderContent<Article> getContent(String url) {
		String oldurl="http://nv.99.com.cn/nxbg/619270.htm";//测试数据
		AtomicBoolean  flag = new AtomicBoolean(true);
		AtomicReference<String> nexturl=new AtomicReference<String>(url); 
		ExecutorService executor = Executors.newFixedThreadPool(4);
		while(flag.get()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			executor.execute(new ArticleThread(flag,nexturl,oldurl,url));
		}
		executor.shutdown();//等待线程完成任务后  关闭线程池 否则它里面的线程会一直处于运行状态
		return null;
	}
	
	public static void main(String[] args) {
		Jk99Spider<Article> j=new Jk99Spider<Article>();
		j.getContent("http://nv.99.com.cn/nxbg/");
	}
}
