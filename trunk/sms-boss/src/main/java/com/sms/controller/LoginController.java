package com.sms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sms.entity.manager.Permission;
import com.sms.entity.manager.User;
import com.sms.service.manager.LoginService;


@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	LoginService loginService;

	@ResponseBody
	@RequestMapping("/login.ajax")
	public Map<String, Object> login(@RequestParam("username") String username,
			@RequestParam("password") String password,HttpSession session) {
		
		Map<String, Object> result = new HashMap<String, Object>(2);
				
		//check username and password
		User u = loginService.checkUser(username, password);

		if (u == null) {
			result.put("success", false);
			result.put("message", "用户名或密码错误");
		} else {
			try{
				session.setAttribute("USER_NAME", u.getName());
				session.setAttribute("USER_ID", u.getId());
				
				// set user permissons
				List<Permission> permissionsList = loginService.getUserPermissions(u.getId());
				session.setAttribute("USER_PERMISSIONS", permissionsList);
		
				result.put("success", true);
				result.put("message", "登录成功");
			}catch(NumberFormatException nfe){
				nfe.printStackTrace();
				result.put("success", false);
				result.put("message", "动态口令错误，请重新输入");
			}
			
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/logout.ajax")
	public Map<String, Object> logout(HttpServletRequest req, HttpServletResponse res,HttpSession session) {
		session.invalidate();
		Map<String, Object> result = new HashMap<String, Object>(1);
		result.put("success", true);
		return result;
	}


	@RequestMapping("/getCurrentUserName.ajax")
	public @ResponseBody
	Map<String, Object> getCurrentUserName(HttpServletRequest req,
			HttpServletResponse res, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(1);
		result.put("username", session.getAttribute("USER_NAME"));
		result.put("userId", session.getAttribute("USER_ID"));
		return result;
	}

	@RequestMapping("/getCurrentMenus.ajax")
	public @ResponseBody
	Map<String, Object> getCurrentMenus(HttpServletRequest req,
			HttpServletResponse res, HttpSession session) {

		long userId = (Long) session.getAttribute("USER_ID");
		return loginService.getUserMenus(userId,null);
	}

	@RequestMapping("/checkPermission.ajax")
	public @ResponseBody
	Map<String, Object> checkPermission(
			@RequestParam("permissionCode") String permissionCode,
			HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Permission> permissons = (List<Permission>) session.getAttribute("USER_PERMISSIONS");

		boolean find = false;
		for (Permission p : permissons) {
			if (permissionCode.equals(p.getPermissionCode())) {
				find = true;
				break;
			}
		}

		result.put("success", find);
		return result;
	}
}
