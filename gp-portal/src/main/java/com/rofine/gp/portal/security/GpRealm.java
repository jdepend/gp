package com.rofine.gp.portal.security;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.rofine.gp.platform.exception.GpException;
import com.rofine.gp.platform.user.User;
import com.rofine.gp.platform.user.UserUtil;
import com.rofine.gp.platform.user.impl.UserImpl;

public class GpRealm extends IniRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {

		AuthenticationInfo info = super.doGetAuthenticationInfo(token);

		String userId = (String) token.getPrincipal();

		User user = new UserImpl();
		user.setId(userId);
		UserUtil.setUser(user);

		return info;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		AuthorizationInfo info = super.doGetAuthorizationInfo(principals);

		try {
			User user = UserUtil.getUser();
			user.setRoleIds((List<String>) info.getRoles());
		} catch (GpException e) {
			e.printStackTrace();
		}

		return info;
	}

}
