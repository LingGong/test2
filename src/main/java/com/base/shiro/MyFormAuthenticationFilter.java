package com.base.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.system.model.User;

/**
 * @ClassName: MyFormAuthenticationFilter
 * @Description: 解决同一个浏览器相同用户重复登录问题
 * @author: gl
 * @date: 2018年1月16日 下午5:14:51
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
	 @Override
	    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
	    {
	        if (isLoginRequest(request, response))
	        {
	            if (isLoginSubmission(request, response))
	            {
	                //本次用户登陆账号
	                String account = this.getUsername(request);

	                Subject subject = this.getSubject(request, response);
	                //之前登陆的用户
	                User user = (User) subject.getPrincipal();
	                //如果两次登陆的用户不一样，则先退出之前登陆的用户
	                if (account != null && user != null )
	                {	
	                	if(!account.equals(user.getUsername())){
	                		 subject.logout();
	                	}else{
	                		return false;
	                	}
	                   
	                }
	            }
	        }

	        return super.isAccessAllowed(request, response, mappedValue);
	    }
	 
	 /**
	  * @Description: 解决登录成功后再登录回到前一个请求的问题，改为重定向首页
	  * @author: gl
	  * @date: 2018年1月19日
	  */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		WebUtils.getAndClearSavedRequest(request);//清除原来的地址
		WebUtils.redirectToSavedRequest(request, response, "/index");
		return false;
	}
	 
	 
}
