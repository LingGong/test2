package com.spider.cache;


import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spider.model.Article;
@Component
public class RedisArticleCache {
	
	@Lazy
	@Resource
    private  RedisTemplate<String,String> redisTemplate;
	
	public  void insertArticle(Article article){
		ValueOperations<String, String> vo=redisTemplate.opsForValue();
		try {
			vo.set(article.getUrl(),new ObjectMapper().writeValueAsString(article));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
