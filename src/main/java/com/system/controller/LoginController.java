package com.system.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.system.model.User;


/**
 * @ClassName: LoginController
 * @Description: 登录控制层
 * @author: gl
 * @date: 2018年1月16日 上午9:28:04
 */
@Controller
public class LoginController {
	//拦截方法
	@RequestMapping(value="/login",method=RequestMethod.POST)
    public String login( User user, Model model,HttpServletRequest request){
		  String exceptionClassName = (String)request.getAttribute("shiroLoginFailure");  
	        String error = null;  
	        if(exceptionClassName==null){//表示同一个用户在已登录的情况下  在一个浏览器上重复（不是一个用户的情况 在MyFormAuthenticationFilter处理）
	        	return "base/index";
	        }
	        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {  
	            error = "用户名/密码错误";  
	        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {  
	            error = "用户名/密码错误";  
	        } else if(exceptionClassName != null) {  
	            error = "其他错误：" + exceptionClassName;  
	        }  
	        model.addAttribute("error", error);  
	        return "base/login";  // 跳转到登录页面 
    }
	
		@RequestMapping(value="/index",method=RequestMethod.GET)
		public String loginSussess(){
			return "base/index";
		}
		
	 //没有登录时会被shiro拦截器拦截 然后请求shirocofig配置的登录请求
	   @RequestMapping(value="/login",method=RequestMethod.GET)
	    public String index() {
	        return "base/login";
	    }
	   
	   @RequestMapping(value="/403",method=RequestMethod.GET)
	    public String unAn(Model model) {
	        return "base/403";
	    }
	   @RequestMapping(value="/400",method=RequestMethod.GET)
	    public String un1(Model model) {
		   model.addAttribute("error", "400");  
	        return "base/error";
	    }
	   @RequestMapping(value="/404",method=RequestMethod.GET)
	    public String un2(Model model) {
		   model.addAttribute("error", "404");  
	        return "base/error";
	    }
	   @RequestMapping(value="/500",method=RequestMethod.GET)
	    public String un3(Model model) {
		   model.addAttribute("error", "500");  
	        return "base/error";
	    }
}

