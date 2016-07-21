package com.rofine.gp.portal.security;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.rofine.gp.application.user.UserService;
import com.rofine.gp.platform.user.User;

public class GpRealm extends IniRealm {
	
	@Autowired
	private UserService userService;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {

		AuthenticationInfo info = super.doGetAuthenticationInfo(token);

		String userId = (String) token.getPrincipal();

		User user = userService.getUser(userId);
		
		SimpleAccount simpleAccount = (SimpleAccount)info;
		user.setRoleIds((List<String>)simpleAccount.getRoles());
		
		SimplePrincipalCollection principals = (SimplePrincipalCollection)info.getPrincipals();
		principals.clear();
		principals.add(user, "userId");
		
		return info;
	}
}
