package com.system.service;

import java.util.List;

import com.system.model.User;

public interface UserService {
	public List<User> getUserList(User user);
	public void insertUser(User user);
	public User queryByUserName(String name);
	public String testcache(String id);
}
