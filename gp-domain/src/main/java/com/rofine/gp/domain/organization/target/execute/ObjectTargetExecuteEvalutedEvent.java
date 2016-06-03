package com.rofine.gp.domain.organization.target.execute;

import org.springframework.context.ApplicationEvent;

public class ObjectTargetExecuteEvalutedEvent extends ApplicationEvent{

	public ObjectTargetExecuteEvalutedEvent(Object source) {
		super(source);
	}

}
