//Source file: E:\\my_workspace\\workprojects\\201605��Ʒ�з�\\project\\gp-application\\src\\main\\java\\com\\rofine\\gp\\application\\DesignModel\\DesignElement\\application\\organization\\target\\plan\\PlanAppService.java

package com.rofine.gp.application.organization.target.plan;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rofine.gp.domain.organization.target.TargetException;
import com.rofine.gp.domain.organization.target.scheme.Scheme;
import com.rofine.gp.domain.organization.target.scheme.SchemeDomainService;
import com.rofine.gp.domain.organization.target.scheme.SchemeObject;
import com.rofine.gp.domain.organization.target.scheme.SchemeObjectVO;
import com.rofine.gp.domain.organization.target.scheme.Target;
import com.rofine.gp.domain.organization.target.scheme.TargetType;

@Service
@Transactional(rollbackFor = Exception.class)
public class PlanAppService {

	@Autowired
	private SchemeDomainService schemeDomainService;

	/**
	 * @throws TargetException
	 * @roseuid 5739B670019C
	 */
	public void target2object(String schemeId, String targetId, List<String> objectIds) throws TargetException {

		List<SchemeObjectVO> objects = new ArrayList<SchemeObjectVO>();
		SchemeObjectVO object;

		for (String objectId : objectIds) {
			object = new SchemeObjectVO();
			object.setObjectId(objectId);

			objects.add(object);
		}

		schemeDomainService.target2object(schemeId, targetId, objects);

	}

	/**
	 * @param schemeId
	 * @throws TargetException
	 * @roseuid 573C0E7502A7
	 */
	public void startScheme(String schemeId) throws TargetException {
		schemeDomainService.startScheme(schemeId);
	}

	public void createScheme(Scheme scheme) {
		schemeDomainService.createScheme(scheme);

	}

	public void createTarget(Target target) throws TargetException {
		schemeDomainService.createTarget(target);

	}

	public void createSchemeObject(SchemeObject object) throws TargetException {
		schemeDomainService.createSchemeObject(object);

	}

	public void createTargetType(TargetType targetType) throws TargetException {
		schemeDomainService.createTargetType(targetType);

	}

	public TargetType getTargetType(String id) {
		return schemeDomainService.getTargetType(id);
	}

	public Target getTarget(String schemeId, String targetId) {
		return schemeDomainService.getTarget(schemeId, targetId);
	}

	public void updateTarget(Target target) throws TargetException {
		schemeDomainService.updateTarget(target);
	}
}
