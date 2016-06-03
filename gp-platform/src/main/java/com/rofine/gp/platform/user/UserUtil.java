package com.rofine.gp.platform.user;

import com.rofine.gp.platform.exception.GpException;

public class UserUtil {
	
	private static User user = null;

	public static User getUser() throws GpException {

		if(user == null){
			throw new GpException("没有登录");
		}

		return user;
	}
	
	public static void setUser(User u){
		user = u;
	}

}
