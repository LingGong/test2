package com.base.shiro;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.Lazy;

import com.system.model.Role;
import com.system.model.User;
import com.system.service.UserService;
/**
 * shiro验证类
 * @author gl 
 * @time  2018-01-09
 */
public class SystemUserReal extends AuthorizingRealm  {
	
	//@lazy这个注解为了解决缓存问题  应为shiro的bean在rediscach之前注入  导致springboot会默认吧ehcache注入到cache  导致redis缓存配置无效的情况
	//好像通过改变注入顺序  advice实现  具体源码不懂  
	@Resource
	@Lazy
	private UserService userService;
	
	//权限认证
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
	     SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		  User user= (User) SecurityUtils.getSubject().getPrincipal();
		  User newUser=userService.queryByUserName(user.getUsername());
		  Role role=newUser.getRole();
		  if(role!=null){
			  List<com.system.model.Resource> resourceList=role.getList();
			  // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
			  for (com.system.model.Resource resource : resourceList) {
				  info.addStringPermission(resource.getResourceUrl());
			}
		  }
	  
		return info;
	}
	
	//身份认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		//获取登录时的用户输入的账号密码
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		//此处的username字段一定要命名为username  因为登录时自动调用sbuject的login()方法  此时token中username字段自动把前端提交数据用户名set进去
		//如果不相同则用户名取出来的是空的  不过可以自定义令牌(暂时不知道怎么搞  后续补上) 可以通过重写formAuthenticationFilter实现
		String username = (String)upToken.getPrincipal();
		String password=new String( upToken.getPassword());
		//根据用户名获取数据库中的用户信息
		User dUser=userService.queryByUserName(username);
		if(dUser.getPassword().equals(password)){//此处省略密码加密验证 可自定义密码验证类  再返回info时shiro框架会自动进行密码验证
			//设置用户信息session   shiro中的session
			 	Session session = SecurityUtils.getSubject().getSession();
		        session.setAttribute("userSession", dUser);
		        session.setAttribute("userSessionId", dUser.getId());
			return new SimpleAuthenticationInfo(dUser, password, dUser.getUsername());//此处应是真正用户名称 不是账号 此处省略 后续补上
		}
		return null;
	}

}
