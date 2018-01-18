package com.system.dao;

import java.util.List;

import com.system.model.User;

public interface UserDao {
	public List<User> getUserList(User user);
	public void insertUser(User user);
	public User queryByUserName(String name);
}
