package com.rofine.gp.domain;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.AssertTrue;

import org.apache.log4j.Logger;
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

import com.rofine.gp.domain.organization.target.execute.EvaluateVO;
import com.rofine.gp.domain.organization.target.execute.FillVO;
import com.rofine.gp.domain.organization.target.execute.ObjectTargetExecute;
import com.rofine.gp.domain.organization.target.execute.ObjectTargetExecuteDomainService;
import com.rofine.gp.domain.organization.target.execute.TargetStatVO;
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

	@Autowired
	private ObjectTargetExecuteDomainService objectTargetExecuteDomainService;

	@BeforeClass
	public static void testInit() {
		// 初始化Context
		ApplicationContext applicationContext = SpringApplication.run(Application.class);
		ApplicationContextUtil.setApplicationContext(applicationContext);
	}

	@Test
	public void testDomain() throws GpException {
		// 清空方案信息
		Pageable pageable = new PageRequest(0, 1000000);
		Page<Scheme> schemes = schemeDomainService.listScheme(pageable);

		schemeAdminDomainService.deleteSchemes(schemes.getContent());
		
		// 初始化方案制定User
		User adminUser = new UserImpl();

		adminUser.setId("test");
		adminUser.setName("测试用户");
		adminUser.setOrgId("org111");
		adminUser.setDeptId("dept111");
		adminUser.setRoleIds(Arrays.asList("role111", "role222"));

		UserUtil.setUser(adminUser);

		// 创建方案
		Scheme scheme = new Scheme();
		scheme.setName("我的方案");
		scheme.setYear(2016);
		scheme.setTargetLevelCount(4);

		schemeDomainService.createScheme(scheme, adminUser);

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

		// 将指标关联被考核对象
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

		// 启动方案
		schemeDomainService.startScheme(scheme.getId());

		// 填报用户登录
		User fillUser = new UserImpl();

		fillUser.setId("filler222");
		fillUser.setName("填报用户222");
		fillUser.setOrgId("org111");
		fillUser.setDeptId("dept222");
		fillUser.setRoleIds(Arrays.asList("role_filler_111", "role_filler_222"));

		UserUtil.setUser(fillUser);

		// 获取填报数据
		List<ObjectTargetExecute> executeFills = objectTargetExecuteDomainService.getFillingExecutes(scheme.getId(),
				fillUser);

		assertTrue(executeFills.size() == 2);

		List<FillVO> fills = new ArrayList<FillVO>();
		FillVO fill;
		for (ObjectTargetExecute execute : executeFills) {
			fill = new FillVO();
			fill.setExecuteId(execute.getId());
			fill.setScore(40.0F);

			fills.add(fill);
		}

		objectTargetExecuteDomainService.fill(fills, fillUser);

		// 考核用户登录
		User evaluateUser = new UserImpl();

		evaluateUser.setId("evaluater999");
		evaluateUser.setName("考核用户999");
		evaluateUser.setOrgId("org111");
		evaluateUser.setDeptId("dept999");
		evaluateUser.setRoleIds(Arrays.asList("role_evaluate_999", "role_evaluate_999"));

		UserUtil.setUser(evaluateUser);

		// 获取打分数据
		List<ObjectTargetExecute> executeEvaluates = objectTargetExecuteDomainService.getEvaluatingExecutes(
				scheme.getId(), evaluateUser);

		assertTrue(executeEvaluates.size() == 1);

		List<EvaluateVO> evaluates = new ArrayList<EvaluateVO>();
		EvaluateVO evaluate;
		for (ObjectTargetExecute execute : executeEvaluates) {
			evaluate = new EvaluateVO();
			evaluate.setExecuteId(execute.getId());
			evaluate.setScore(50.0F);

			evaluates.add(evaluate);
		}

		objectTargetExecuteDomainService.evaluate(evaluates, evaluateUser);

		// 监控指标执行
		List<Target> targetStats = objectTargetExecuteDomainService.getTargetStats(scheme.getId());
		TargetStatVO targetStatVO;
		for (Target target : targetStats) {
			targetStatVO = target.getTargetStatVO();

			if (targetStatVO.getTargetId().equals(target1.getId())) {
				assertTrue(targetStatVO.getWaitEvaluateCount() == 0);
				assertTrue(targetStatVO.getEvaluatingCount() == 0);
				assertTrue(targetStatVO.getEvaluatedCount() == 1);
				assertTrue(targetStatVO.getOverdueEvaluateCount() == 0);
			}

			Logger.getLogger(DomainTest.class).info(targetStatVO);
		}
		
		//关闭方案
		schemeDomainService.closeScheme(scheme.getId());

	}

}
