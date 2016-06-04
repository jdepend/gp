package com.rofine.gp.platform;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rofine.gp.portal.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class CacheTest {
	
	@Autowired
	private CacheTestService cacheTestService;
	
	@Test
	public void testRedis(){
		cacheTestService.testRedis();
	}
	
	@Test
	public void testEhcache() {
		cacheTestService.testEhcache("test");
	}


}
