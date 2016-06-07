package com.rofine.gp.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rofine.gp.domain.organization.target.scheme.Scheme;
import com.rofine.gp.domain.organization.target.scheme.SchemeAdminDomainService;
import com.rofine.gp.domain.organization.target.scheme.SchemeDomainService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class DomainTest {
	
	@Autowired
	private SchemeDomainService schemeDomainService;
	
	@Autowired
	private SchemeAdminDomainService schemeAdminDomainService; 
	
	@Before
	public void testReSetDB(){
		
		//清空方案信息
		Pageable pageable = new PageRequest(0, 1000000);
		Page<Scheme> schemes = schemeDomainService.listScheme(pageable);
		
		schemeAdminDomainService.deleteSchemes(schemes.getContent());
		
	}
	
	@Test
	public void testCreateScheme(){
		
//		Scheme scheme = new Scheme();
//		scheme.setName("我的方案");
//		schemeDomainService.createScheme(scheme, user);
		
		
	}

}
