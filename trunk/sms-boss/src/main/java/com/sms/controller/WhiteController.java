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

import com.sms.criteria.smsmanager.WhiteCriteria;
import com.sms.entity.smsmanager.White;
import com.sms.service.smsmanager.WhiteService;
import com.sms.util.ErrorMessageUtil;


@Controller
@RequestMapping("/white")
public class WhiteController {

	@Autowired
	private WhiteService whiteService;
	
	
	@ResponseBody
	@RequestMapping("/query.ajax")
	public Map<String, Object> query(WhiteCriteria criteria, HttpSession session) {
		 
		List<White> records = whiteService.query(criteria);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);

		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getWhiteByMobile.ajax")
	public White getWhiteByMobile(@Param("mobile") String mobile) {
		 
		White white = whiteService.getWhiteByMobile(mobile);

		return white;
	}
	
	@ResponseBody
	@RequestMapping("/addWhite.ajax")
	public  Map<String, Object> addWhite(@Param("mobile") String mobile,@Param("remark") String remark,HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		try {
			result=whiteService.addWhite(mobile,remark,result);
		} catch(Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/editWhite.ajax")
	public  Map<String, Object> editWhite(@Valid White white, BindingResult error,HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		if(error.hasErrors()){
			result.put("success", false);
			result.put("message",ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		try {
			result=whiteService.editWhite(white,result);
		} catch(Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delete.ajax")
	public Map<String, Object> delete(@Param("mobile") String mobile) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		try {
			result=whiteService.delete(mobile,result);
		} catch(Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}

		return result;
	}
	
}
	
