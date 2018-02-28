package com;


import javax.annotation.Resource;




























import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.App;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spider.model.Article;
import com.spider.model.SpiderContent;
import com.spider.template.BaiduSpider;
import com.spider.template.Jk99Spider;
import com.spider.template.QinBeiSpider;
import com.spider.template.SpiderAnalysis;
import com.spider.util.JsoupUtil;
import com.system.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTests {
	
	@Resource
	private UserService userService;
	
	@Resource
    private RedisTemplate<String,Object> redisTemplate;

	
	
	@Before
	  public void setUp() throws Exception {
    }
	
	
	@Test
	public void contextLoads() {
//		//redis 测试
//		ValueOperations<String, Object> vo=redisTemplate.opsForValue();
//         vo.set("java", 1);
//		String id="4";
//		System.out.println("开始测试*****88");
//		userService.testcache(id);
//		String resute=userService.testcache(id);
//		System.out.println("缓存结果**********"+resute+"************");
		//爬虫测试
//		@SuppressWarnings("rawtypes")
//		SpiderAnalysis an=new QinBeiSpider();
//		@SuppressWarnings("rawtypes")
//		SpiderContent sc=an.getContent("http://www.qinbei.com/tag/153586/");
//		try {
//			System.out.println(new ObjectMapper().writeValueAsString(sc));
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
		SpiderAnalysis an=new Jk99Spider();
		an.getContent("http://nv.99.com.cn/nxbg/");
		
		

	}

}
