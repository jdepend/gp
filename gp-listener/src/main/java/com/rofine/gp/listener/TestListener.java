package com.rofine.gp.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.rofine.gp.domain.organization.target.scheme.SchemeStartedEvent;

@Service
public class TestListener implements ApplicationListener<SchemeStartedEvent> {

	@Override
	public void onApplicationEvent(SchemeStartedEvent event) {
		System.out.println(event.getSource());
	}
}
