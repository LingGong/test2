package com.spider.cache;


import javax.annotation.Resource;


import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spider.model.Article;

/**   
*    
* 项目名称：test2   
* 类名称：RedisArticleCache   
* 类描述：文章类爬区内容存入缓存中   
* 创建人：gl   
* 创建时间：2018年2月24日 下午2:12:19   
* 修改人：   
* 修改时间：   
* 修改备注：   
* @version    
*    
*/
@Component
public class RedisArticleCache implements RedisSpiderCache<Article> {
	
	@Lazy
	@Resource
    private  RedisTemplate<String,String> redisTemplate;
	

	public void insert(Article article) {
		ValueOperations<String, String> vo=redisTemplate.opsForValue();
		try {
			vo.set(article.getUrl(),new ObjectMapper().writeValueAsString(article));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(Article content) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Article content) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Article query(Article content) {
		// TODO Auto-generated method stub
		return null;
	}
}
