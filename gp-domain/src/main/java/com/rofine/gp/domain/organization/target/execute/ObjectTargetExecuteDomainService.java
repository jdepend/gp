//Source file: E:\\my_workspace\\workprojects\\201605��Ʒ�з�\\project\\gp-domain\\src\\main\\java\\com\\rofine\\gp\\domain\\DesignModel\\DesignElement\\domain\\organization\\target\\execute\\ObjectTargetExecuteDomainService.java

package com.rofine.gp.domain.organization.target.execute;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rofine.gp.domain.organization.target.TargetException;
import com.rofine.gp.domain.organization.target.scheme.Scheme;
import com.rofine.gp.domain.organization.target.scheme.SchemeDomainService;
import com.rofine.gp.domain.organization.target.scheme.SchemeStartedEvent;
import com.rofine.gp.domain.organization.target.scheme.Target;
import com.rofine.gp.platform.user.User;
import com.rofine.gp.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class ObjectTargetExecuteDomainService {
	@Autowired
	private ObjectTargetExecuteRepo executeRepo;

	@Autowired
	private SchemeDomainService schemeDomainService;

	@Autowired
	private ApplicationContext applicationContext;

	public List<ObjectTargetExecute> getFillingExecutes(String schemeId, User user) {
		Date sysDate = DateUtil.getSysDate();
		return executeRepo.findBySchemeIdAndObjectCodeAndStateAndStartDateLessThanAndEndDateGreaterThan(schemeId,
				user.getDeptId(), ObjectTargetExecute.State_Filling, sysDate, sysDate);
	}

	public List<ObjectTargetExecute> getEvaluatingExecutes(String schemeId, User user) {
		Date sysDate = DateUtil.getSysDate();
		return executeRepo.findBySchemeIdAndSubjectIdAndStateAndStartDateLessThanAndEndDateGreaterThan(schemeId,
				user.getDeptId(), ObjectTargetExecute.State_Evaluating, sysDate, sysDate);
	}

	public List<ObjectTargetExecute> getOperatedExecutes(String schemeId, User user) {
		return executeRepo.findOperateExecutes(schemeId, user.getId());
	}

	/**
	 * @param evaluates
	 * @param userId
	 * @throws TargetException
	 * @roseuid 573A8DF9021C
	 */
	public void evaluate(List<EvaluateVO> evaluates, User user) throws TargetException {
		Map<String, Float> evaluateMap = new HashMap<String, Float>();
		for (EvaluateVO evaluate : evaluates) {
			evaluateMap.put(evaluate.getExecuteId(), evaluate.getScore());
		}

		List<ObjectTargetExecute> executes = executeRepo.findByIdIn(new ArrayList<String>(evaluateMap.keySet()));
		for (ObjectTargetExecute execute : executes) {
			execute.setEvaluateScore(evaluateMap.get(execute.getId()));
			execute.setEvaluateId(user.getId());
			execute.setEvaluateDate(DateUtil.getSysDate());
			execute.setState(ObjectTargetExecute.State_Evaluated);

			schemeDomainService.syncScore(execute);
		}

		executeRepo.save(executes);

		applicationContext.publishEvent(new ObjectTargetExecuteEvalutedEvent(executes));
	}

	public void fill(List<FillVO> fills, User user) {
		Map<String, Float> fillMap = new HashMap<String, Float>();
		for (FillVO fill : fills) {
			fillMap.put(fill.getExecuteId(), fill.getScore());
		}

		List<ObjectTargetExecute> executes = executeRepo.findByIdIn(new ArrayList<String>(fillMap.keySet()));
		for (ObjectTargetExecute execute : executes) {
			execute.setFillScore(fillMap.get(execute.getId()));
			execute.setFillId(user.getId());
			execute.setFillDate(DateUtil.getSysDate());
			execute.setState(ObjectTargetExecute.State_Evaluating);
		}

		executeRepo.save(executes);

		applicationContext.publishEvent(new ObjectTargetExecuteFilledEvent(executes));
	}

	/**
	 * @param schemeId
	 * @roseuid 573BC90901EE
	 */
	public List<Target> getTargetStats(String schemeId) {

		Scheme scheme = schemeDomainService.getScheme(schemeId);

		Map<String, TargetStatVO> targetStatVOMap = this.getTargetStatVOs(schemeId);

		for (Target target : scheme.getTargets()) {
			target.setTargetStatVO(targetStatVOMap.get(target.getId()));
		}

		return scheme.getTargets();
	}

	private Map<String, TargetStatVO> getTargetStatVOs(String schemeId) {
		List<ObjectTargetExecute> executes = executeRepo.findBySchemeId(schemeId);
		Map<String, TargetStatVO> targetStatVOMap = new HashMap<String, TargetStatVO>();
		TargetStatVO targetStatVO;
		Date sysDate = DateUtil.getSysDate();
		for (ObjectTargetExecute execute : executes) {
			targetStatVO = targetStatVOMap.get(execute.getTarget().getId());
			if (targetStatVO == null) {
				targetStatVO = new TargetStatVO();
				targetStatVO.setTargetId(execute.getTarget().getId());
				targetStatVOMap.put(execute.getTarget().getId(), targetStatVO);
			}
			if (execute.getState().equals(ObjectTargetExecute.State_Evaluated)) {
				targetStatVO.setEvaluatedCount(targetStatVO.getEvaluatedCount() + 1);
			} else if (execute.getState().equals(ObjectTargetExecute.State_Evaluating)) {
				if (execute.getRemindDate().after(sysDate)) {

				} else if (execute.getRemindDate().before(sysDate) && execute.getStartDate().after(sysDate)) {
					targetStatVO.setWaitEvaluateCount(targetStatVO.getWaitEvaluateCount() + 1);
				} else if (execute.getStartDate().before(sysDate) && execute.getEndDate().after(sysDate)) {
					targetStatVO.setEvaluatingCount(targetStatVO.getEvaluatingCount() + 1);
				} else if (execute.getEndDate().before(sysDate) && execute.getLimitDate().after(sysDate)) {
					targetStatVO.setOverdueEvaluateCount(targetStatVO.getOverdueEvaluateCount() + 1);
				} else if (execute.getLimitDate().before(sysDate)) {
					targetStatVO.setOverdueEvaluateCount(targetStatVO.getOverdueEvaluateCount() + 1);
				}
			}
		}

		return targetStatVOMap;
	}

	public void deleteExecutes(List<String> executeIds) {
		for (String executeId : executeIds) {
			executeRepo.delete(executeId);
		}
	}
}
