package com.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.dao.UserDao;
import com.system.model.User;
import com.system.service.UserService;
@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserDao userDao;
	
	
	
	public List<User> getUserList(User user) {
		
		return userDao.getUserList(user);
	}


	public void insertUser(User user) {
		userDao.insertUser(user);
	}


	@Cacheable(key="#name" ,value="users")
	public User queryByUserName(String name) {
		System.out.println("查数据库*******");
		return userDao.queryByUserName(name);
	}


	@Cacheable(key="#id" ,value="users")
	public String testcache(String id) {
		System.out.println("进入了数据库测试缓存****");
		return id;
	}
	
	
	
}
