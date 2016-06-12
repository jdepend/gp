package com.rofine.gp.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.springframework.transaction.annotation.Transactional;

import com.rofine.gp.domain.organization.target.scheme.Scheme;
import com.rofine.gp.domain.organization.target.scheme.SchemeAdminDomainService;
import com.rofine.gp.domain.organization.target.scheme.SchemeDomainService;
import com.rofine.gp.domain.organization.target.scheme.SchemeObject;
import com.rofine.gp.domain.organization.target.scheme.SchemeObjectVO;
import com.rofine.gp.domain.organization.target.scheme.Target;
import com.rofine.gp.domain.organization.target.scheme.TargetType;
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

		// 创建方案
		User user = UserUtil.getUser();

		Scheme scheme = new Scheme();
		scheme.setName("我的方案");
		scheme.setYear(2016);
		scheme.setTargetLevelCount(4);

		schemeDomainService.createScheme(scheme, user);

		// 创建被考核对象
		SchemeObject object222 = new SchemeObject();

		object222.setObjectId("dept222");
		object222.setName("被考核部门222");
		object222.setType(SchemeObject.TYPE_DEPT);
		object222.setScheme(scheme);

		schemeDomainService.createSchemeObject(object222);
		
		SchemeObject object333 = new SchemeObject();

		object333.setObjectId("dept333");
		object333.setName("被考核部门333");
		object333.setType(SchemeObject.TYPE_DEPT);
		object333.setScheme(scheme);

		schemeDomainService.createSchemeObject(object333);
		
		SchemeObject object444 = new SchemeObject();

		object444.setObjectId("dept444");
		object444.setName("被考核部门444");
		object444.setType(SchemeObject.TYPE_DEPT);
		object444.setScheme(scheme);

		schemeDomainService.createSchemeObject(object444);
		
		SchemeObject object555 = new SchemeObject();

		object555.setObjectId("dept555");
		object555.setName("被考核部门555");
		object555.setType(SchemeObject.TYPE_DEPT);
		object555.setScheme(scheme);

		schemeDomainService.createSchemeObject(object555);

		// 创建指标层次
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

		// 创建指标
		Target target1 = new Target();

		target1.setName("指标1");
		target1.setParent(targetType_c1);
		target1.setWeight(30);
		target1.setScheme(scheme);
		target1.setSubjectId("dept999");
		target1.setFrequencyType(Target.TargetFrequencyType_Year);

		schemeDomainService.createTarget(target1);

		Target target2 = new Target();

		target2.setName("指标2");
		target2.setParent(targetType_c1);
		target2.setWeight(70);
		target2.setScheme(scheme);
		target2.setSubjectId("dept888");
		target2.setFrequencyType(Target.TargetFrequencyType_Year);

		schemeDomainService.createTarget(target2);
		
		Target target3 = new Target();

		target3.setName("指标3");
		target3.setParent(targetType_c2);
		target3.setWeight(50);
		target3.setScheme(scheme);
		target3.setSubjectId("dept777");
		target3.setFrequencyType(Target.TargetFrequencyType_HalfYear);

		schemeDomainService.createTarget(target3);

		Target target4 = new Target();

		target4.setName("指标4");
		target4.setParent(targetType_c2);
		target4.setWeight(50);
		target4.setScheme(scheme);
		target4.setSubjectId("dept666");
		target4.setFrequencyType(Target.TargetFrequencyType_HalfYear);

		schemeDomainService.createTarget(target4);
		
		//将指标关联被考核对象
		List<SchemeObjectVO> objects;
		SchemeObjectVO object;
		
		objects = new ArrayList<SchemeObjectVO>();
		
		object = new SchemeObjectVO();
		object.setObjectId(object222.getId());
		objects.add(object);
		
		object = new SchemeObjectVO();
		object.setObjectId(object333.getId());
		objects.add(object);
		
		schemeDomainService.target2object(scheme.getId(), target1.getId(), objects);
		schemeDomainService.target2object(scheme.getId(), target2.getId(), objects);
		
		objects = new ArrayList<SchemeObjectVO>();
		
		object = new SchemeObjectVO();
		object.setObjectId(object444.getId());
		objects.add(object);
		
		object = new SchemeObjectVO();
		object.setObjectId(object555.getId());
		objects.add(object);
		
		schemeDomainService.target2object(scheme.getId(), target3.getId(), objects);
		schemeDomainService.target2object(scheme.getId(), target4.getId(), objects);

	}

}
