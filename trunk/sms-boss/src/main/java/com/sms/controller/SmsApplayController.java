package com.sms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sms.criteria.smsmanager.SmsApplayCriteria;
import com.sms.entity.smsmanager.SmsApplay;
import com.sms.service.smsmanager.SmsApplayService;

@Controller
@RequestMapping("/smsApplay")
public class SmsApplayController {

	@Autowired
	private SmsApplayService smsApplayService;
	
	@ResponseBody
	@RequestMapping("/query.ajax")
	public Map<String, Object> query(SmsApplayCriteria criteria) {
		 
		List<SmsApplay> records = smsApplayService.query(criteria);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);

		return result;
	}
	
	@ResponseBody
	@RequestMapping("/approve.ajax")
	public Map<String, Object> approve(@Param("smsApplayId") Integer smsApplayId, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		try {
			smsApplayService.approve(smsApplayId);
			result.put("success", true);
			result.put("message", "操作成功：审核通过");
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", e.getMessage());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/reject.ajax")
	public Map<String, Object> reject(@Param("smsApplayId") Integer smsApplayId, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		try {
			smsApplayService.reject(smsApplayId);
			result.put("success", true);
			result.put("message", "操作成功：审核拒绝");
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", e.getMessage());
		}
		return result;
	}
}