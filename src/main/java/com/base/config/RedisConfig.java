package com.base.config;



import java.util.Arrays;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;



/**
 * @ClassName: RedisConfig
 * @Description: TODO
 * @author: gl
 * @date: 2018年1月17日 下午3:48:34
 */
@Configuration
@EnableCaching
public class RedisConfig {
	
	
	  @Bean(name="redisTemplate")
	    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
	        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
	        template.setConnectionFactory(factory);
//	        template.setKeySerializer(new StringRedisSerializer());
//	        template.setValueSerializer(new RedisObjectSerializer());
	        return template;
	    }
	
	//将redis缓存设置到spring缓存机制中    后续可以通过注解启动缓存
    @Bean(name="cacheManager")
    public CacheManager cacheManager(@Qualifier("redisTemplate")RedisTemplate<Object, Object> redisTemplate) {
        String[] cacheNames = {"app_default", "users", "blogs", "goods", "configs", "info"};
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate, Arrays.asList(cacheNames));
        redisCacheManager.setDefaultExpiration(18000);
        return redisCacheManager;
    }
    
  
    

    

  
}
