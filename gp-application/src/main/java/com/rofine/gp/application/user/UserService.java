package com.rofine.gp.application.user;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rofine.gp.platform.user.User;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	public User getUser(String userId){
		return userRepo.findOne(userId);
	}

}
