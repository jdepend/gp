package com.rofine.gp.domain;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
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

import com.rofine.gp.domain.organization.score.UnitScore;
import com.rofine.gp.domain.organization.score.UnitScoreService;
import com.rofine.gp.domain.organization.target.TargetException;
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
import com.rofine.gp.platform.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class DomainTest {

	@Autowired
	private SchemeDomainService schemeDomainService;

	@Autowired
	private SchemeAdminDomainService schemeAdminDomainService;

	@Autowired
	private ObjectTargetExecuteDomainService objectTargetExecuteDomainService;

	@Autowired
	private UnitScoreService unitScoreService;
	
	private Scheme scheme;
	
	private Target target1;
	
	private Target target2;
	
	private Target target3;
	
	private Target target4;

	@BeforeClass
	public static void testInit() {
		// 初始化Context
		ApplicationContext applicationContext = SpringApplication.run(Application.class);
		ApplicationContextUtil.setApplicationContext(applicationContext);
	}

	/**
	 * 在单位org111下，有9个部门，5个用户（admin，filler222，filler333，evaluater999，evaluater888）；
	 * 
	 * 2016-01-15：
	 * 
	 * 创建1个方案，4个被考评部门（222，333，444，555），4个叶子指标（1（半年），2（半年），3（年），4（年）），分别由部门999，888，777，666考核；
	 * 建立指标和被考核部门的关联： 
	 * 1->222 1->333; 
	 * 2->222 2->333; 
	 * 3->444 3->555; 
	 * 4->444 4->555;
	 * 
	 * 2016-06-15：
	 * 
	 * 222部门用户filler222进行填报40；
	 * 333部门用户filler333进行填报20；
	 * 999部门用户evaluater999进行打分25；
	 * 888部门用户evaluater888进行打分35；
	 * 
	 * 2016-12-01：
	 * 
	 * 222部门用户filler222进行填报40；
	 * 333部门用户filler333进行填报20；
	 * 999部门用户evaluater999进行打分25；
	 * 888部门用户evaluater888进行打分35；
	 * 
	 * 2017-01-20：
	 * 
	 * 关闭方案
	 * 
	 * 2017-02-20：
	 * 
	 * 汇总上一年度的部门成绩；
	 * 
	 * 确定部门222分数为64分
	 * 其中上半年得分32 【采用加权平均算法（25 * 30 + 35 * 70）/（30 +70）= 32】；
	 * 下半年得分32 【采用加权平均算法（25 * 30 + 35 * 70）/（30 +70）= 32】
	 * 按着【加和获得部门年度成绩策略】，总分为64
	 * 
	 * @throws GpException
	 */
	@Test
	public void testDomain() throws GpException {
		// 清空方案信息
		Pageable pageable = new PageRequest(0, 1000000);
		Page<Scheme> schemes = schemeDomainService.listScheme(pageable);

		schemeAdminDomainService.deleteSchemes(schemes.getContent());
		
		DateUtil.setSysDate(DateUtil.createDate("2016-01-15"));
		
		//创建方案
		this.createScheme();

		// 启动方案
		schemeDomainService.startScheme(scheme.getId());
		
		DateUtil.setSysDate(DateUtil.createDate("2016-06-15"));
		
		//执行
		this.execute();
		
		// 监控指标执行
		List<Target> targetStat1s = objectTargetExecuteDomainService.getTargetStats(scheme.getId());
		TargetStatVO targetStatVO1;
		for (Target target : targetStat1s) {
			targetStatVO1 = target.getTargetStatVO();

			if (targetStatVO1.getTargetId().equals(target1.getId())) {
				assertTrue(targetStatVO1.getWaitEvaluateCount() == 0);
				assertTrue(targetStatVO1.getEvaluatingCount() == 0);
				assertTrue(targetStatVO1.getEvaluatedCount() == 2);
				assertTrue(targetStatVO1.getOverdueEvaluateCount() == 0);
			}

			Logger.getLogger(DomainTest.class).info(targetStatVO1);
		}

		DateUtil.setSysDate(DateUtil.createDate("2016-12-01"));
		
		//执行
		this.execute();
		
		// 监控指标执行
		List<Target> targetStat2s = objectTargetExecuteDomainService.getTargetStats(scheme.getId());
		TargetStatVO targetStatVO2;
		for (Target target : targetStat2s) {
			targetStatVO2 = target.getTargetStatVO();

			if (targetStatVO2.getTargetId().equals(target1.getId())) {
				assertTrue(targetStatVO2.getWaitEvaluateCount() == 0);
				assertTrue(targetStatVO2.getEvaluatingCount() == 0);
				assertTrue(targetStatVO2.getEvaluatedCount() == 4);
				assertTrue(targetStatVO2.getOverdueEvaluateCount() == 0);
			}

			Logger.getLogger(DomainTest.class).info(targetStatVO2);
		}
		
		DateUtil.setSysDate(DateUtil.createDate("2017-01-20"));

		// 关闭方案
		schemeDomainService.closeScheme(scheme.getId());

		
		DateUtil.setSysDate(DateUtil.createDate("2017-02-20"));
		
		// 汇总部门成绩
		unitScoreService.create(2016);

		List<UnitScore> scores = unitScoreService.getScores(2016);

		Logger.getLogger(DomainTest.class).info(scores);

		for (UnitScore unitScore : scores) {
			if (unitScore.getUnitId().equals("dept222") && unitScore.getSource().equals("target")) {
				assertTrue(unitScore.getScore() == 64.0);
			}
		}

	}

	private void createScheme() throws TargetException {
		// 初始化方案制定User
		User adminUser = new UserImpl();

		adminUser.setId("admin");
		adminUser.setName("管理员用户");
		adminUser.setOrgId("org111");
		adminUser.setDeptId("dept111");
		adminUser.setRoleIds(Arrays.asList("role111", "role222"));

		UserUtil.setUser(adminUser);

		// 创建方案
		scheme = new Scheme();
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
		target1 = new Target();

		target1.setName("指标1");
		target1.setParent(targetType_c1);
		target1.setWeight(30);
		target1.setScheme(scheme);
		target1.setSubjectId("dept999");// 设置考核部门
		target1.setFrequencyType(Target.TargetFrequencyType_HalfYear);

		schemeDomainService.createTarget(target1);

		target2 = new Target();

		target2.setName("指标2");
		target2.setParent(targetType_c1);
		target2.setWeight(70);
		target2.setScheme(scheme);
		target2.setSubjectId("dept888");// 设置考核部门
		target2.setFrequencyType(Target.TargetFrequencyType_HalfYear);

		schemeDomainService.createTarget(target2);

		target3 = new Target();

		target3.setName("指标3");
		target3.setParent(targetType_c2);
		target3.setWeight(50);
		target3.setScheme(scheme);
		target3.setSubjectId("dept777");// 设置考核部门
		target3.setFrequencyType(Target.TargetFrequencyType_Year);

		schemeDomainService.createTarget(target3);

		target4 = new Target();

		target4.setName("指标4");
		target4.setParent(targetType_c2);
		target4.setWeight(50);
		target4.setScheme(scheme);
		target4.setSubjectId("dept666");// 设置考核部门
		target4.setFrequencyType(Target.TargetFrequencyType_Year);

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

		// target1->222,333
		schemeDomainService.target2object(scheme.getId(), target1.getId(), objects);
		// target2->222,333
		schemeDomainService.target2object(scheme.getId(), target2.getId(), objects);

		objects = new ArrayList<SchemeObjectVO>();

		object = new SchemeObjectVO();
		object.setObjectId(object444.getId());
		objects.add(object);

		object = new SchemeObjectVO();
		object.setObjectId(object555.getId());
		objects.add(object);

		// target3->444,555
		schemeDomainService.target2object(scheme.getId(), target3.getId(), objects);
		// target4->444,555
		schemeDomainService.target2object(scheme.getId(), target4.getId(), objects);
	}

	private void execute() throws TargetException {

		// 填报用户222登录
		User fillUser222 = new UserImpl();

		fillUser222.setId("filler222");
		fillUser222.setName("填报用户222");
		fillUser222.setOrgId("org111");
		fillUser222.setDeptId("dept222");// 填报部门
		fillUser222.setRoleIds(Arrays.asList("role_fill_111", "role_fill_222"));

		UserUtil.setUser(fillUser222);

		// 获取填报数据
		List<ObjectTargetExecute> executeFill222s = objectTargetExecuteDomainService.getFillingExecutes(scheme.getId(),
				fillUser222);

		assertTrue(executeFill222s.size() == 2);

		List<FillVO> fill222s = new ArrayList<FillVO>();
		FillVO fill222;
		for (ObjectTargetExecute execute : executeFill222s) {
			fill222 = new FillVO();
			fill222.setExecuteId(execute.getId());
			fill222.setScore(40.0F);

			fill222s.add(fill222);
		}

		objectTargetExecuteDomainService.fill(fill222s, fillUser222);

		// 填报用户333登录
		User fillUser333 = new UserImpl();

		fillUser333.setId("filler333");
		fillUser333.setName("填报用户333");
		fillUser333.setOrgId("org111");
		fillUser333.setDeptId("dept333");// 填报部门
		fillUser333.setRoleIds(Arrays.asList("role_fill_111", "role_fill_222"));

		UserUtil.setUser(fillUser333);

		// 获取填报数据
		List<ObjectTargetExecute> executeFill333s = objectTargetExecuteDomainService.getFillingExecutes(scheme.getId(),
				fillUser333);

		assertTrue(executeFill333s.size() == 2);

		List<FillVO> fill333s = new ArrayList<FillVO>();
		FillVO fill333;
		for (ObjectTargetExecute execute : executeFill333s) {
			fill333 = new FillVO();
			fill333.setExecuteId(execute.getId());
			fill333.setScore(20.0F);

			fill333s.add(fill333);
		}

		objectTargetExecuteDomainService.fill(fill333s, fillUser333);

		// 考核用户999登录
		User evaluateUser999 = new UserImpl();

		evaluateUser999.setId("evaluater999");
		evaluateUser999.setName("考核用户999");
		evaluateUser999.setOrgId("org111");
		evaluateUser999.setDeptId("dept999");// 考核部门
		evaluateUser999.setRoleIds(Arrays.asList("role_evaluate_999", "role_evaluate_999"));

		UserUtil.setUser(evaluateUser999);

		// 获取打分数据
		List<ObjectTargetExecute> executeEvaluate999s = objectTargetExecuteDomainService.getEvaluatingExecutes(
				scheme.getId(), evaluateUser999);

		assertTrue(executeEvaluate999s.size() == 2);

		List<EvaluateVO> evaluate999s = new ArrayList<EvaluateVO>();
		EvaluateVO evaluate999;
		for (ObjectTargetExecute execute : executeEvaluate999s) {
			evaluate999 = new EvaluateVO();
			evaluate999.setExecuteId(execute.getId());
			evaluate999.setScore(25.0F);

			evaluate999s.add(evaluate999);
		}

		objectTargetExecuteDomainService.evaluate(evaluate999s, evaluateUser999);

		// 考核用户888登录
		User evaluateUser888 = new UserImpl();

		evaluateUser888.setId("evaluater888");
		evaluateUser888.setName("考核用户888");
		evaluateUser888.setOrgId("org111");
		evaluateUser888.setDeptId("dept888");// 考核部门
		evaluateUser888.setRoleIds(Arrays.asList("role_evaluate_888", "role_evaluate_888"));

		UserUtil.setUser(evaluateUser888);

		// 获取打分数据
		List<ObjectTargetExecute> executeEvaluate888s = objectTargetExecuteDomainService.getEvaluatingExecutes(
				scheme.getId(), evaluateUser888);

		assertTrue(executeEvaluate888s.size() == 2);

		List<EvaluateVO> evaluate888s = new ArrayList<EvaluateVO>();
		EvaluateVO evaluate888;
		for (ObjectTargetExecute execute : executeEvaluate888s) {
			evaluate888 = new EvaluateVO();
			evaluate888.setExecuteId(execute.getId());
			evaluate888.setScore(35.0F);

			evaluate888s.add(evaluate888);
		}

		objectTargetExecuteDomainService.evaluate(evaluate888s, evaluateUser888);
	}

}
