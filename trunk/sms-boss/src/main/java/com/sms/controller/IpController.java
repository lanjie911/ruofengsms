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

import com.sms.criteria.smsmanager.IpCriteria;
import com.sms.entity.smsmanager.Ip;
import com.sms.service.smsmanager.IpService;


@Controller
@RequestMapping("/ip")
public class IpController {

	@Autowired
	private IpService ipService;
	
	
	@ResponseBody
	@RequestMapping("/query.ajax")
	public Map<String, Object> query(IpCriteria criteria, HttpSession session) {
		 
		List<Ip> records = ipService.query(criteria);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);

		return result;
	}
	
	@ResponseBody
	@RequestMapping("/addIp.ajax")
	public  Map<String, Object> addIp(@Param("ipAddress") String ipAddress,HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		try {
			result=ipService.addIp(ipAddress,result);
		} catch(Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}
		return result;
	}

	
	@ResponseBody
	@RequestMapping("/delete.ajax")
	public Map<String, Object> delete(@Param("ipAddress") String ipAddress) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		try {
			result=ipService.delete(ipAddress,result);
		} catch(Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}

		return result;
	}
	
}
	
