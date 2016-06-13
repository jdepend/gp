package com.rofine.gp.platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.rofine.gp.platform.user.User;
import com.rofine.gp.platform.user.impl.UserImpl;

@Service
public class CacheTestService {

	@Autowired
	private StringRedisTemplate redisTemplate;

	public void testRedis() {
		redisTemplate.opsForValue().append("test", "test");
	}

	@Cacheable(value = "userCache", key = "#userId")
	public User testEhcache(String userId) {
		return new UserImpl();
	}

	@CacheEvict(value = "userCache", key = "#userId")
	public void testDeleteEhcache(String userId) {

	}

}
