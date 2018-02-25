package com.sms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sms.entity.manager.User;
import com.sms.service.manager.UserService;
import com.sms.util.ErrorMessageUtil;


@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@RequestMapping("/getUser.ajax")
	public @ResponseBody User getUser(HttpSession session) {
		return (User) session.getAttribute("USER");
	}
	
	@RequestMapping("/editUser.ajax")
	public @ResponseBody Map<String, Object> editUser(@Valid User user, BindingResult error, HttpSession session) {
		
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		if(error.hasErrors()){
			result.put("success", false);
			result.put("message", ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		result =userService.editUser(user,result);
		User u=userService.getUserByUserId(user.getId()) ;
		session.setAttribute("USER", u);
		return result;
	}
	
	
}
	
