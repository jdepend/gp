package com.rofine.gp.domain;

import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
import com.rofine.gp.domain.organization.target.scheme.SchemeObject;
import com.rofine.gp.domain.organization.target.scheme.Target;
import com.rofine.gp.domain.organization.target.scheme.TargetType;
import com.rofine.gp.platform.bean.ApplicationContextUtil;
import com.rofine.gp.platform.exception.GpException;
import com.rofine.gp.platform.user.User;
import com.rofine.gp.platform.user.UserUtil;
import com.rofine.gp.platform.user.impl.UserImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@FixMethodOrder(MethodSorters.DEFAULT)
public class DomainTest {

	@Autowired
	private SchemeDomainService schemeDomainService;

	@Autowired
	private SchemeAdminDomainService schemeAdminDomainService;

	@BeforeClass
	public static void testInit() {
		// 初始化Context
		ApplicationContext applicationContext = SpringApplication.run(Application.class);
		ApplicationContextUtil.setApplicationContext(applicationContext);
		// 初始化User
		User user = new UserImpl();

		user.setId("test");
		user.setName("测试用户");
		user.setOrgId("org111");
		user.setDeptId("dept111");
		user.setRoleIds(Arrays.asList("role111", "role222"));

		UserUtil.setUser(user);

	}

	@Test
	public void testDomain() throws GpException {
		// 清空方案信息
		Pageable pageable = new PageRequest(0, 1000000);
		Page<Scheme> schemes = schemeDomainService.listScheme(pageable);

		schemeAdminDomainService.deleteSchemes(schemes.getContent());
		
		//创建方案
		User user = UserUtil.getUser();

		Scheme scheme = new Scheme();
		scheme.setName("我的方案");
		scheme.setYear(2016);
		scheme.setTargetLevelCount(4);

		schemeDomainService.createScheme(scheme, user);
		
		//创建被考核对象
		SchemeObject object = new SchemeObject();

		object.setObjectId("dept222");
		object.setName("被考核部门222");
		object.setType(SchemeObject.TYPE_DEPT);
		object.setScheme(scheme);

		schemeDomainService.createSchemeObject(object);
		
		//创建指标层次
		TargetType targetType_a = new TargetType();
		targetType_a.setName("指标类型a");
		targetType_a.setScheme(scheme);
		
		schemeDomainService.createTargetType(targetType_a);
		
		TargetType targetType_b = new TargetType();
		targetType_b.setName("指标类型b");
		targetType_b.setParent(targetType_a);
		targetType_b.setScheme(scheme);
		
		schemeDomainService.createTargetType(targetType_b);
		
		TargetType targetType_c1 = new TargetType();
		targetType_c1.setName("指标类型c1");
		targetType_c1.setParent(targetType_b);
		targetType_c1.setScheme(scheme);
		targetType_c1.setWeight(50);
		
		schemeDomainService.createTargetType(targetType_c1);
		
		TargetType targetType_c2 = new TargetType();
		targetType_c2.setName("指标类型c2");
		targetType_c2.setParent(targetType_b);
		targetType_c2.setScheme(scheme);
		targetType_c2.setWeight(50);
		
		schemeDomainService.createTargetType(targetType_c2);
		
		//创建指标
		Target target = new Target();
		schemeDomainService.createTarget(target);
		
	}

	

}
