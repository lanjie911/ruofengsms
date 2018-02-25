package com.sms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sms.entity.manager.User;
import com.sms.entity.mercmanager.MercAccount;
import com.sms.service.manager.LoginService;
import com.sms.service.mercmanager.MercAccountService;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	LoginService loginService;

	@Autowired
	MercAccountService mercAccountService;

	@ResponseBody
	@RequestMapping("/login.ajax")
	public Map<String, Object> login(@RequestParam("username") String username,
			@RequestParam("password") String password, HttpSession session) {

		Map<String, Object> result = new HashMap<String, Object>(2);

		// check username and password
		User u = loginService.checkUser(username, password);

		if (u == null) {
			result.put("success", false);
			result.put("message", "用户名或密码错误");
		} else {
			try {
				session.setAttribute("USER", u);

				result.put("success", true);
				result.put("message", "登录成功");
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
				result.put("success", false);
				result.put("message", "动态口令错误，请重新输入");
			}

		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/logout.ajax")
	public Map<String, Object> logout(HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		session.invalidate();
		Map<String, Object> result = new HashMap<String, Object>(1);
		result.put("success", true);
		return result;
	}

	@RequestMapping("/getCurrentUserName.ajax")
	public @ResponseBody Map<String, Object> getCurrentUserName(HttpServletRequest req, HttpServletResponse res,
			HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(1);
		User u = (User) session.getAttribute("USER");
		result.put("username", u.getName());
		result.put("userId", u.getId());
		return result;
	}

	@RequestMapping("/getCurrentMenus.ajax")
	public @ResponseBody Map<String, Object> getCurrentMenus(HttpServletRequest req, HttpServletResponse res,
			HttpSession session) {

		return loginService.getMenus();
	}

	@RequestMapping("/getFreeBlance.ajax")
	public @ResponseBody Map<String, Object> getFreeBlance(HttpServletRequest req, HttpServletResponse res,
			HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(1);
		User u = (User) session.getAttribute("USER");
		long merchantId = u.getMerchantId();
		MercAccount mercAccount = mercAccountService.getMerchantById(merchantId);
		if (mercAccount == null) {
			result.put("freeBalance", 0);
		} else {
			result.put("freeBalance", mercAccount.getFreeBalance());
		}
		result.put("merchantId", merchantId);
		return result;
	}

}
