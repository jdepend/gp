//Source file: E:\\my_workspace\\workprojects\\201605��Ʒ�з�\\project\\gp-domain\\src\\main\\java\\com\\rofine\\gp\\domain\\DesignModel\\DesignElement\\domain\\organization\\target\\target\\score\\TargetScoreCalculatorFactory.java

package com.rofine.gp.domain.organization.target.target.score;

import com.rofine.gp.domain.organization.target.target.score.impl.def.DefalutObjectScoreCalculator;
import com.rofine.gp.domain.organization.target.target.score.impl.def.DefalutObjectTargetExecuteScoreCalculator;
import com.rofine.gp.domain.organization.target.target.score.impl.def.DefalutObjectTargetScoreCalculator;

public class TargetScoreCalculatorFactory {

	/**
	 * @roseuid 573AD3160195
	 */
	private static ObjectTargetExecuteScoreCalculator getObjectTargetExecuteScoreCalculator() {
		return new DefalutObjectTargetExecuteScoreCalculator();
	}

	/**
	 * @roseuid 573AD4120065
	 */
	public static ObjectTargetScoreCalculator getObjectTargetScoreCalculator() {
		return new DefalutObjectTargetScoreCalculator();

	}

	/**
	 * @roseuid 573AD4220362
	 */
	public static ObjectScoreCalculator getSchemeObjectCalculator() {
		return new DefalutObjectScoreCalculator();
	}
}
