//Source file: E:\\my_workspace\\workprojects\\201605��Ʒ�з�\\project\\gp-application\\src\\main\\java\\com\\rofine\\gp\\application\\DesignModel\\DesignElement\\application\\organization\\target\\execute\\ExecuteAppService.java

package com.rofine.gp.application.organization.target.execute;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rofine.gp.application.notification.NotificationService;
import com.rofine.gp.domain.organization.target.TargetException;
import com.rofine.gp.domain.organization.target.execute.EvaluateVO;
import com.rofine.gp.domain.organization.target.execute.FillVO;
import com.rofine.gp.domain.organization.target.execute.ObjectTargetExecute;
import com.rofine.gp.domain.organization.target.execute.ObjectTargetExecuteDomainService;
import com.rofine.gp.domain.organization.target.scheme.Target;
import com.rofine.gp.platform.user.User;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExecuteAppService {
	@Autowired
	private ObjectTargetExecuteDomainService executeDomainService;
	
	@Autowired
	private NotificationService notificationService; 

	public List<ObjectTargetExecute> getFillingExecutes(String schemeId, User user) {
		return executeDomainService.getFillingExecutes(schemeId, user);
	}

	public List<ObjectTargetExecute> getEvaluatingExecutes(String schemeId, User user) {
		return executeDomainService.getEvaluatingExecutes(schemeId, user);
	}
	
	public void fill(List<FillVO> fills, User user) {
		executeDomainService.fill(fills, user);
	}

	/**
	 * @param evaluateVOs
	 * @param userId
	 * @throws TargetException
	 * @roseuid 573A8A9F01C3
	 */
	public void evaluate(List<EvaluateVO> evaluates, User user) throws TargetException {
		executeDomainService.evaluate(evaluates, user);
	}

	public List<ObjectTargetExecute> getOperatedExecutes(String schemeId, User user) {
		return executeDomainService.getOperatedExecutes(schemeId, user);
	}
	
	/**
	 * @param schemeId
	 * @roseuid 573B1224022D
	 */
	public List<Target> monitor(String schemeId) {
		return executeDomainService.getTargetStats(schemeId);
	}

	public void deleteExecutes(List<String> executeIds) {
		executeDomainService.deleteExecutes(executeIds);
	}
}
