package com.spider.cache;

public interface RedisSpiderCache<T> {
	
	public void insert(T content);
}
