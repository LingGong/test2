package com.spider.template;


import com.spider.model.SpiderContent;

/**   
*    
* 项目名称：test2   
* 类名称：SpiderAnalysis   
* 类描述： 解析document 接口 
* 创建人：gl 
* 创建时间：2018年2月10日 下午4:55:20   
* 修改人： 
* 修改时间： 
* 修改备注：   
* @version    
*    
*/

public interface SpiderAnalysis {
	@SuppressWarnings("rawtypes")
	public SpiderContent getContent(String url);
}
