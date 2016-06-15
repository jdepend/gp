package com.rofine.gp.application;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rofine.gp.application.organization.target.execute.audit.AuditFillVO;
import com.rofine.gp.application.organization.target.execute.audit.ObjectTargetExecuteAuditService;
import com.rofine.gp.domain.DomainTestService;
import com.rofine.gp.domain.organization.target.TargetException;
import com.rofine.gp.domain.organization.target.execute.ObjectTargetExecute;
import com.rofine.gp.platform.exception.GpException;
import com.rofine.gp.platform.user.User;
import com.rofine.gp.platform.user.UserUtil;
import com.rofine.gp.platform.user.impl.UserImpl;

@Service
public class AppTestService extends DomainTestService {
	
	@Autowired
	private ObjectTargetExecuteAuditService objectTargetExecuteAuditService;

	/**
	 * 增加审核用户audit_fill_222，audit_fill_333， audit_evaluater_999，audit_evaluater_999；
	 * 
	 * 2016-06-15：
	 * 
	 * 222部门用户filler222进行填报40；
	 * 222部门用户audit_fill_222进行审核；
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
	 * @throws GpException
	 */
	@Override
	protected void execute() throws TargetException {

		fill222();
		
		auditFill222();

		fill333();

		auditFill333();

		evaluate999();

		evaluate888();
	}

	protected void auditFill222() {
		// 填报审核用户222登录
		User auditFillUser222 = new UserImpl();

		auditFillUser222.setId("auditFiller222");
		auditFillUser222.setName("填报审核用户222");
		auditFillUser222.setOrgId("org111");
		auditFillUser222.setDeptId("dept222");// 填报部门
		auditFillUser222.setRoleIds(Arrays.asList("role_audit_fill_111", "role_audit_fill_222"));

		UserUtil.setUser(auditFillUser222);

		// 获取填报审核数据
		List<ObjectTargetExecute> executeFill222s = objectTargetExecuteAuditService.getAuditFillingExecutes(
				scheme.getId(), auditFillUser222);

		assertTrue(executeFill222s.size() == 2);

		List<AuditFillVO> audit222s = new ArrayList<AuditFillVO>();
		AuditFillVO audit222;
		for (ObjectTargetExecute execute : executeFill222s) {
			audit222 = new AuditFillVO();
			audit222.setExecuteId(execute.getId());
			audit222.setResult(true);

			audit222s.add(audit222);
		}

		objectTargetExecuteAuditService.auditFill(audit222s, auditFillUser222);

	}
	
	protected void auditFill333() {
		// TODO Auto-generated method stub

	}

}