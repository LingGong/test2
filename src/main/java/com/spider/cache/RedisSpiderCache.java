package com.spider.cache;


/**   
*    
* 项目名称：test2   
* 类名称：RedisSpiderCache   
* 类描述：   爬虫内容缓存接口
* 创建人：gl  
* 创建时间：2018年2月24日 下午2:07:28   
* 修改人：  
* 修改时间：  
* 修改备注：   
* @version    
*    
*/
public interface RedisSpiderCache<T> {
	
	public void insert(T content);
	
	public void delete(T content);
	
	public void update(T content);
	
	public T query(T content);
}
