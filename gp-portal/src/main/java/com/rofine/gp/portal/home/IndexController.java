package com.rofine.gp.portal.home;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rofine.gp.platform.user.User;

@Controller
@RequestMapping("")
public class IndexController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index1(){
		return "redirect:/index";
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(){
		
		Subject subject = SecurityUtils.getSubject();
		
		User user = (User)subject.getPrincipal();
		
		return "index";
	}

}
