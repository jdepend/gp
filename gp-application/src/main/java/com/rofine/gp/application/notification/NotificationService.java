package com.rofine.gp.application.notification;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class NotificationService {
	
	public void notify(NotificationVO notificationVO){
		
	}
}
