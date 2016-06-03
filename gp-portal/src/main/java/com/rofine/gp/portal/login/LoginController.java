//Source file: E:\\my_workspace\\workprojects\\201605��Ʒ�з�\\project\\gp-portal\\src\\main\\java\\com\\rofine\\gp\\portal\\DesignModel\\DesignElement\\portal\\organization\\target\\execute\\ExecuteController.java

package com.rofine.gp.portal.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rofine.gp.domain.organization.target.TargetException;
import com.rofine.gp.platform.user.UserUtil;
import com.rofine.gp.platform.user.impl.UserImpl;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

	@RequestMapping(value = "/user/{userId}/org/{orgId}/dept/{deptId}/role/{roleIds}", method = RequestMethod.GET)
	public String login(@PathVariable String userId, @PathVariable String orgId, @PathVariable String deptId, @PathVariable String roleIds, Model model) {
		
		model.addAttribute("userId", userId);
		model.addAttribute("orgId", orgId);
		model.addAttribute("deptId", deptId);
		model.addAttribute("roleIds", roleIds);
		
		return "login";
	}

	@RequestMapping(value = "/user/{userId}/org/{orgId}/dept/{deptId}/role/{roleIds}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(@ModelAttribute("user") UserImpl user) throws TargetException {

		UserUtil.setUser(user);
	
		Map<String, Object> rtn = new HashMap<String, Object>();

		rtn.put("code", "1");
		rtn.put("msg", "操作成功");

		return rtn;
	}

	
}
