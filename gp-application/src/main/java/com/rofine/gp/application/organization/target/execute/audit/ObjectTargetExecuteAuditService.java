package com.rofine.gp.application.organization.target.execute.audit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rofine.gp.domain.organization.target.execute.ObjectTargetExecute;
import com.rofine.gp.domain.organization.target.execute.ObjectTargetExecuteRepo;
import com.rofine.gp.platform.user.User;

@Service
@Transactional(rollbackFor = Exception.class)
public class ObjectTargetExecuteAuditService {

	@Autowired
	private ObjectTargetExecuteAuditLoader auditLoader;

	@Autowired
	private ObjectTargetExecuteAuditRepo auditRepo;
	
	@Autowired
	private ObjectTargetExecuteRepo executeRepo;

	public List<ObjectTargetExecute> getAuditFillingExecutes(String schemeId, User user) {
		return auditLoader.findBySchemeIdAndObjectCodeAndObjectTypeAndState(schemeId, user.getDeptId(), "dept",
				ObjectTargetExecuteAudit.State_Fill_Auditing);
	}

	public void auditFill(List<AuditFillVO> audit222s, User auditFillUser222) {
		// TODO Auto-generated method stub

	}

}
