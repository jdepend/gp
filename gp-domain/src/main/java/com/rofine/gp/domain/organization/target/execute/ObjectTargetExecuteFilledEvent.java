package com.rofine.gp.domain.organization.target.execute;

import org.springframework.context.ApplicationEvent;

public class ObjectTargetExecuteFilledEvent extends ApplicationEvent{

	public ObjectTargetExecuteFilledEvent(Object source) {
		super(source);
	}

}
