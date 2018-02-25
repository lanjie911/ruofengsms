package com.sms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sms.criteria.manager.UserCriteria;
import com.sms.entity.manager.Role;
import com.sms.entity.manager.User;
import com.sms.service.manager.RoleService;
import com.sms.service.manager.UserService;
import com.sms.util.ErrorMessageUtil;


@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@RequestMapping("/query.ajax")
	public @ResponseBody
	Map<String, Object> query(UserCriteria criteria, HttpSession session) {
		
		List<User> records =  userService.query(criteria);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);

		return result;
	}
	
	@RequestMapping("/getUserByUserId.ajax")
	public @ResponseBody User getUserByUserId(@RequestParam("userId") String userId, HttpSession session) {
		return userService.getUserByUserId(Long.parseLong(userId));
	}
	
	@RequestMapping("/getRolesByUserId.ajax")
	public @ResponseBody List<Role> getRolesByUserId(@RequestParam("userId") String userId, HttpSession session) {
		return roleService.getRolesByUserId(Long.parseLong(userId));
	}
	
	@RequestMapping("/getAllRoles.ajax")
	public @ResponseBody List<Role> getAllRoles(HttpSession session) {
		return roleService.getRolesByModuleCode("boss");
	}
	
	@ResponseBody
	@RequestMapping("/addUser.ajax")
	public Map<String, Object> addUser(@Valid User user,BindingResult error, HttpSession session) {
		
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		if(error.hasErrors()){
			result.put("success", false);
			result.put("message", ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		
		try {
			result =userService.addUser(user,result);
		} catch(Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}
		
		return result;
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
		result.put("success", true);
		result.put("message", "修改成功");
		return result;
	}
	
	
	@RequestMapping("/changePassword.ajax")
	public @ResponseBody Map<String, Object> changePassword(@RequestParam("id") String id, @RequestParam("password") String password,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		try {
			userService.changePassword(Long.parseLong(id), password);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "修改密码失败：" + e.getMessage());
			return result;
		}
		
		result.put("success", true);
		result.put("message", "提交成功");
		return result;
	}
	
	@RequestMapping("/changePasswordForLoginUser.ajax")
	public @ResponseBody Map<String, Object> changePasswordForLoginUser(
			@RequestParam("id") String id,@RequestParam("password") String password,
			@RequestParam("newPasswd") String newPasswd,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		try {
			String returnValue=userService.changePasswordForLoginUser(Long.parseLong(id), password,newPasswd);
			if("0".equals(returnValue)){
				result.put("success", true);
				result.put("message", "修改密码成功");
			}else{
				result.put("success", false);
				result.put("message", "原始密码错误");
			}

		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "修改密码失败：" + e.getMessage());
			return result;
		}
		return result;
	}
	
}
	
