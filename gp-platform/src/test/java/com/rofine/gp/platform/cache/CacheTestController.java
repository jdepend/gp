package com.rofine.gp.platform.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/test")
public class CacheTestController {
	
	@Autowired
	private CacheTestService cacheTestService;
	
	@RequestMapping(value = "/redis", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> testRedis() {
		
		cacheTestService.testRedis();
		
		Map<String, Object> rtn = new HashMap<String, Object>();

		rtn.put("code", "1");
		rtn.put("msg", "操作成功");

		return rtn;
	}
	
	@RequestMapping(value = "/ehcache", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> testEhcache() {
		
		cacheTestService.testEhcache("test");
		
		Map<String, Object> rtn = new HashMap<String, Object>();

		rtn.put("code", "1");
		rtn.put("msg", "操作成功");

		return rtn;
	}

}
