package com.rofine.gp.listener.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.rofine.gp.application.notification.NotificationService;
import com.rofine.gp.application.notification.NotificationVO;
import com.rofine.gp.domain.organization.target.scheme.Scheme;
import com.rofine.gp.domain.organization.target.scheme.SchemeCreatedEvent;

@Service
public class NotificationSchemeCreatedListener implements ApplicationListener<SchemeCreatedEvent> {

	@Autowired
	private NotificationService notificationService;
	@Override
	public void onApplicationEvent(SchemeCreatedEvent event) {
		
		Scheme scheme = (Scheme)event.getSource();
		
		NotificationVO notificationVO = new NotificationVO();
		
		//notificationVO.setSourceId(scheme.getc);
		
		notificationService.notify(notificationVO );
	}
}