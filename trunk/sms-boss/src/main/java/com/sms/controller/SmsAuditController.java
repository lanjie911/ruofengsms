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

import com.sms.criteria.smsmanager.SmsAuditCriteria;
import com.sms.entity.smsmanager.SmsAudit;
import com.sms.service.smsmanager.SmsAuditService;


@Controller
@RequestMapping("/smsaudit")
public class SmsAuditController {

	@Autowired
	private SmsAuditService smsAuditService;
	
	
	@ResponseBody
	@RequestMapping("/query.ajax")
	public Map<String, Object> query(SmsAuditCriteria criteria, HttpSession session) {
		 
		List<SmsAudit> records = smsAuditService.query(criteria);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);

		return result;
	}
	
	@ResponseBody
	@RequestMapping("/smsaudit.ajax")
	public Map<String, Object> smsaudit(@Param("batchNo") String batchNo,@Param("auditStatus") Integer auditStatus,
				@Param("beforeStatus") Integer beforeStatus,  HttpSession session) {
		 
		Map<String, Object> result = smsAuditService.smsaudit(batchNo,auditStatus,beforeStatus,(String) session.getAttribute("USER_NAME"));

		return result;
	}
	
}
	
