package com.base.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.StringUtils;

import com.base.shiro.MyFormAuthenticationFilter;
import com.base.shiro.SystemUserReal;
import com.system.dao.ResourceDao;
/**
 * 
 * @author gl
 *遇到的神坑  把@Configuration写成了@Configurable  结果配置无效  找了三天多才发现    我的天啊
 *
 */


@Configuration
public class ShiroConfig {

	
	@Resource
	private ResourceDao resourceDao;
	
	
	// 保证实现了Shiro内部lifecycle函数的bean执行 
	//在此重点说明这个方法，如果不设置为静态方法会导致bean对象无法注入进来，
	   @Bean(name = "lifecycleBeanPostProcessor")
	    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
	        return new LifecycleBeanPostProcessor();
	    }
	  
	    @Bean(name="ehCacheManager")
	    public EhCacheManager ehCacheManager() {  
	        EhCacheManager em = new EhCacheManager();  
	        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");  
	        return em;  
	    } 
	   
	   
	  	@Bean(name="systemUserReal")
	    public  SystemUserReal systemUserReal(){  
	  		SystemUserReal realm = new SystemUserReal(); 
	  	   //启用缓存,默认false
	  		realm.setCachingEnabled(true);
	  	    //  启用身份验证缓存，即缓存AuthenticationInfo信息，默认false；
	  		realm.setAuthenticationCachingEnabled(true);
	  	    //  缓存AuthenticationInfo信息的缓存名称,即配置在ehcache.xml中的cache name
	  		realm.setAuthenticationCacheName("authenticationCache");
	  	    //  启用授权缓存，即缓存AuthorizationInfo信息，默认false；
	  		realm.setAuthorizationCachingEnabled(true);
	  	    //  缓存AuthorizationInfo信息的缓存名称；
	  		realm.setAuthorizationCacheName("authorizationCache");
	  		return realm;
	    }  
	  	
	    /**
	     * cookie对象;
	     * rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
	     * @return
	    */
	   @Bean
	   public SimpleCookie rememberMeCookie(){
	         //System.out.println("ShiroConfiguration.rememberMeCookie()");
	         //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
	         SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
	         //<!-- 记住我cookie生效时间30天 ,单位秒;-->
	         simpleCookie.setMaxAge(259200);
	         return simpleCookie;
	   }

	   /**
	     * cookie管理对象;
	     * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
	     * @return
	    */
	   @Bean
	   public CookieRememberMeManager rememberMeManager(){
	         //System.out.println("ShiroConfiguration.rememberMeManager()");
	         CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
	         cookieRememberMeManager.setCookie(rememberMeCookie());
	         //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
	         cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
	         return cookieRememberMeManager;
	   }
	  	
		@Bean(name="securityManager")
	    public SecurityManager  securityManager(@Qualifier("systemUserReal") SystemUserReal authRealm){
	        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
	        //设置realm
	        securityManager.setRealm(authRealm);
	        securityManager.setCacheManager(ehCacheManager());
	        securityManager.setRememberMeManager(rememberMeManager());
	        return securityManager;
	    }
		
//	    @Bean(name="myFormAuthenticationFilter")
//	    public MyFormAuthenticationFilter myFormAuthenticationFilter(){
//	    	return new MyFormAuthenticationFilter();
//	    }
	    
		
	    @Bean(name="shirFilter")
	    public  ShiroFilterFactoryBean  shirFilter(@Qualifier("securityManager") SecurityManager securityManager){
	        System.out.println("**************shirFilter**************");
	        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
			shiroFilterFactoryBean.setSecurityManager(securityManager);
	        shiroFilterFactoryBean.setLoginUrl("/login");
	        // 登录成功后要跳转的链接
	        shiroFilterFactoryBean.setSuccessUrl("/index");
	        //未授权界面
	        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
	        Map<String, Filter> filtersMap = shiroFilterFactoryBean.getFilters();
	        //限制相同账号重复登录问题
	        filtersMap.put("authc",new MyFormAuthenticationFilter());
	      	shiroFilterFactoryBean.setFilters(filtersMap);
	      
	        //拦截器
	        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();

	        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
	        filterChainDefinitionMap.put("/logout", "logout");
	        filterChainDefinitionMap.put("/static/**","anon");
	        //remberme情况shiro默认只能登陆不敏感页面  所以暂时只能访问首页
	        filterChainDefinitionMap.put("/index", "user");
	        
	        //<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
	        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
	        //自定义加载权限资源关系
	        List<com.system.model.Resource> resourcesList = resourceDao.queryAll();
	         for(com.system.model.Resource resource:resourcesList){
	            if (!StringUtils.isEmpty(resource.getResourceUrl())){
	                String permission = "perms[" + resource.getResourceUrl()+ "]";
	                filterChainDefinitionMap.put(resource.getResourceUrl(),permission);
	            }
	        }
	         filterChainDefinitionMap.put("/**", "authc");


	        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
	        return shiroFilterFactoryBean;
	    }
	   
	    
	    /**
	     *  开启shiro aop注解(RequiresRoles等)支持  Controller才能使用@RequiresPermissions
	     *  使用代理方式;所以需要开启代码支持;
	     * @param securityManager
	     * @return
	     */
	    @Bean
	    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager")SecurityManager securityManager){
	        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
	        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
	        return authorizationAttributeSourceAdvisor;
	    }
	    
	    
	    @Bean 
	    @DependsOn({"lifecycleBeanPostProcessor"})
		public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
			DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator(); 
			daap.setProxyTargetClass(true); 
			return daap; 
		} 

	   
	    
	    

//	    @Bean
//	    protected CacheManager cacheManager() {
//	        return new MemoryConstrainedCacheManager();
//	    }

}
