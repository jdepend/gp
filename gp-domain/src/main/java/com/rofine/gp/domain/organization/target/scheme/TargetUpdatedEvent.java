package com.rofine.gp.domain.organization.target.scheme;

import org.springframework.context.ApplicationEvent;

public class TargetUpdatedEvent extends ApplicationEvent{

	public TargetUpdatedEvent(Object source) {
		super(source);
	}

}
