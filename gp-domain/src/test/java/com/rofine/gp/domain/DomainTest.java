package com.rofine.gp.domain;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rofine.gp.domain.organization.target.scheme.Scheme;
import com.rofine.gp.domain.organization.target.scheme.SchemeAdminDomainService;
import com.rofine.gp.domain.organization.target.scheme.SchemeDomainService;
import com.rofine.gp.platform.bean.ApplicationContextUtil;
import com.rofine.gp.platform.exception.GpException;
import com.rofine.gp.platform.user.User;
import com.rofine.gp.platform.user.UserUtil;
import com.rofine.gp.platform.user.impl.UserImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class DomainTest {
	
	@Autowired
	private SchemeDomainService schemeDomainService;
	
	@Autowired
	private SchemeAdminDomainService schemeAdminDomainService; 
	
	@Before
	public void testInitContext(){
		ApplicationContext applicationContext = SpringApplication.run(Application.class);
		ApplicationContextUtil.setApplicationContext(applicationContext);
	}
	
	@Before
	public void testInitUser(){
		
		User user = new UserImpl();
		
		user.setId("test");
		user.setName("测试用户");
		user.setOrgId("org111");
		user.setDeptId("dept111");
		user.setRoleIds(Arrays.asList("role111", "role222"));
		
		UserUtil.setUser(user);
	}
	
	
	
	@Before
	public void testReSetDB(){
		
		//清空方案信息
		Pageable pageable = new PageRequest(0, 1000000);
		Page<Scheme> schemes = schemeDomainService.listScheme(pageable);
		
		schemeAdminDomainService.deleteSchemes(schemes.getContent());
		
	}
	

	
	@Test
	public void testCreateScheme() throws GpException{
		
		User user = UserUtil.getUser();
		
		Scheme scheme = new Scheme();
		scheme.setName("我的方案");
		scheme.setYear(2016);
		scheme.setTargetLevelCount(2);
		
		schemeDomainService.createScheme(scheme, user);
		
		
	}

}
