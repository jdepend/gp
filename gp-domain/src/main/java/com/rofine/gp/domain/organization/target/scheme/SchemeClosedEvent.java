package com.rofine.gp.domain.organization.target.scheme;

import org.springframework.context.ApplicationEvent;

public class SchemeClosedEvent extends ApplicationEvent{

	public SchemeClosedEvent(Object source) {
		super(source);
	}

}
