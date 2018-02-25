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

import com.sms.criteria.smsmanager.BlackCriteria;
import com.sms.entity.smsmanager.Black;
import com.sms.service.smsmanager.BlackService;
import com.sms.util.ErrorMessageUtil;


@Controller
@RequestMapping("/black")
public class BlackController {

	@Autowired
	private BlackService blackService;

	@ResponseBody
	@RequestMapping("/query.ajax")
	public Map<String, Object> query(BlackCriteria criteria, HttpSession session) {
		 
		List<Black> records = blackService.query(criteria);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);

		return result;
	}
	
	@ResponseBody
	@RequestMapping("/updateStatus.ajax")
	public Map<String, Object> updateStatus(@Param("unsubscribeId") Long unsubscribeId,@Param("beforeStatus") Integer beforeStatus,@Param("afterStatus") Integer afterStatus,  HttpSession session) {
		 
		Map<String, Object> result = blackService.updateStatus(unsubscribeId,beforeStatus,afterStatus,(String) session.getAttribute("USER_NAME"));

		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getBlackById.ajax")
	public Black getBlackById(@Param("unsubscribeId") Long unsubscribeId) {
		 
		Black black = blackService.getBlackById(unsubscribeId);

		return black;
	}
	
	@ResponseBody
	@RequestMapping("/addBlack.ajax")
	public  Map<String, Object> addBlack(@Valid Black black, BindingResult error,HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		if(error.hasErrors()){
			result.put("success", false);
			result.put("message",ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		try {
			result=blackService.addBlack(black,result);
		} catch(Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/editBlack.ajax")
	public  Map<String, Object> editBlack(@Valid Black black, BindingResult error,HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		if(error.hasErrors()){
			result.put("success", false);
			result.put("message",ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		try {
			result=blackService.editBlack(black,result);
		} catch(Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}
		return result;
	}
	
}
	
