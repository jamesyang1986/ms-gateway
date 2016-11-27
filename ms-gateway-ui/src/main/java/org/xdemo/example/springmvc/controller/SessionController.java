package org.xdemo.example.springmvc.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.xdemo.example.springmvc.bean.User;

/**
 * @author <a href="http://www.xdemo.org">xdemo.org</a>
 */
@Controller
@SessionAttributes(value="user")
@RequestMapping("/session")
public class SessionController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String setUser(ModelMap map){
		User user=new User();
		user.setAddress("xxx");
		user.setUserName("yyy");
		map.put("user", user);
		return "request/userForm";
	}
	
	@ResponseBody
	@RequestMapping(value="getUser",method=RequestMethod.GET)
	public String getUser(@ModelAttribute("user")User user){
		System.out.println(user.getUserName());
		return user.getUserName();
	}

}
