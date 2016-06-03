package com.rofine.gp.domain.organization.target.scheme;

import org.springframework.context.ApplicationEvent;

public class SchemeStartBeforeEvent extends ApplicationEvent{

	public SchemeStartBeforeEvent(Object source) {
		super(source);
	}

}
