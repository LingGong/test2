package com;


import javax.annotation.Resource;








import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.App;
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
		String id="4";
		System.out.println("开始测试*****88");
		userService.testcache(id);
		String resute=userService.testcache(id);
		System.out.println("缓存结果**********"+resute+"************");

	}

}
