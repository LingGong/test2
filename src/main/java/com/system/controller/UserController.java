package com.system.controller;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.system.model.User;
import com.system.service.UserService;
@Controller
public class UserController {
	@Resource
	private UserService userService;
	
	@RequestMapping("/user/list")
	public ModelAndView helloJsp(Map<String,Object> map,@RequestParam(required=true)String name){
		ModelAndView model=new ModelAndView();
		model.addObject("list",userService.queryByUserName(name));
		model.setViewName("system/userList");
		return model;
	}
	
	@RequestMapping("/insert")
	public  ModelAndView insert(){
		ModelAndView model=new ModelAndView();
		User user=new User();
		user.setUsername("GG");
		userService.insertUser(user);
		model.addObject("list",userService.getUserList(new User()) );
		model.setViewName("userList");
		return model;
	}
	
}
