package com.rofine.gp.domain.organization.target.target.score.impl.def;

import com.rofine.gp.domain.organization.target.TargetException;
import com.rofine.gp.domain.organization.target.execute.ObjectTargetExecute;
import com.rofine.gp.domain.organization.target.scheme.ObjectTarget;
import com.rofine.gp.domain.organization.target.target.score.ObjectTargetScoreCalculator;

public class DefalutObjectTargetScoreCalculator implements ObjectTargetScoreCalculator {

	@Override
	public void calculate(ObjectTarget objectTarget) throws TargetException {
		Float score = 0.0F;
		for (ObjectTargetExecute execute : objectTarget.getObjectTargetExecutes()) {
			if (execute.getEvaluateScore() != null) {
				score += execute.getEvaluateScore();
			}
		}
		objectTarget.setScore(score);
	}

}
