package com.sms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sms.criteria.mercmanager.MercUserCriteria;
import com.sms.entity.mercmanager.MercUser;
import com.sms.service.mercmanager.MercUserService;
import com.sms.util.ErrorMessageUtil;


@Controller
@RequestMapping("/mercuser")
public class MercUserController {

	@Autowired
	private MercUserService mercUserService;
	
	
	@ResponseBody
	@RequestMapping("/query.ajax")
	public Map<String, Object> query(MercUserCriteria criteria, HttpSession session) {
		
		List<MercUser> records = mercUserService.query(criteria);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);

		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/addMercUser.ajax")
	public  Map<String, Object> addMercUser(@Valid MercUser mercUser, BindingResult error,HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		if(error.hasErrors()){
			result.put("success", false);
			result.put("message",ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		try {
			result=mercUserService.addMercUser(mercUser,result);
		} catch(Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getMercUserByMobile.ajax")
	public  MercUser getMercUserByMobile(@Param("mobile")Long mobile) {
		return mercUserService.getMercUserByMobile(mobile);
	}
	
	@ResponseBody
	@RequestMapping("/getMercUserById.ajax")
	public  MercUser getMercUserById(@Param("operatorId")Long operatorId) {
		return mercUserService.getMercUserById(operatorId);
	}
	
	@ResponseBody
	@RequestMapping("/resetPassword.ajax")
	public  Map<String, Object> resetPassword(@Param("operatorId")Long operatorId) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		try {
			result=mercUserService.resetPassword(operatorId,result);
		} catch(Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}
		return result;
	}
	
	 @ResponseBody
	@RequestMapping("/editMercUser.ajax")
	public Map<String, Object> editMercUser(@Valid MercUser mercUser,  BindingResult error,HttpSession session){
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		if(error.hasErrors()){
			result.put("success", false);
			result.put("message", ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		try {
			result=mercUserService.editMercUser(mercUser,result);
			result.put("success", true);
			result.put("message", "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("message", "修改失败");
		}
		return result;
	}
}
	
