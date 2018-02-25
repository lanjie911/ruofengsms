package com.sms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sms.criteria.smsmanager.SmsInboxCriteria;
import com.sms.entity.smsmanager.SmsInbox;
import com.sms.service.smsmanager.SmsInboxService;


@Controller
@RequestMapping("/smsinbox")
public class SmsInboxController {

	@Autowired
	private SmsInboxService smsInboxService;
	
	
	@ResponseBody
	@RequestMapping("/query.ajax")
	public Map<String, Object> query(SmsInboxCriteria criteria, HttpSession session) {
		 
		List<SmsInbox> records = smsInboxService.query(criteria);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);

		return result;
	}
	
}
	
