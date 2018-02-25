package com.sms.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sms.criteria.systemParam.MobileOperatorCriteria;
import com.sms.entity.systemParam.MobileOperator;
import com.sms.service.manager.MobileOperatorService;

@Controller
@RequestMapping("/mobileOperator")
public class MobileOperatorController {
	
	private Logger logger = LoggerFactory.getLogger(MobileOperatorController.class);
	
	@Autowired
	private MobileOperatorService mobileOperatorService;
	
	@ResponseBody
	@RequestMapping("/query.ajax")
	public Map<String, Object> query(MobileOperatorCriteria criteria, HttpSession session) {
		List<MobileOperator> records = mobileOperatorService.query(criteria);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getMobileOperatorById.ajax")
	public MobileOperator getMobileOperatorById(@RequestParam("phoneOperatorId") Integer phoneOperatorId, HttpSession session) {
		return mobileOperatorService.getMobileOperatorById(phoneOperatorId);
	}
	
	@ResponseBody
	@RequestMapping("/addMobileOperator.ajax")
	public  Map<String, Object> addMobileOperator(@Valid MobileOperator mobileOperator, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		try {
			mobileOperatorService.addMobileOperator(mobileOperator);
			result.put("success", true);
			result.put("message", "操作成功");
		} catch(Exception e) {
			logger.error("MobileOperatorController.addMobileOperator-Exception{}", e);
			result.put("success", false);
			result.put("message", e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("/editMobileOperator.ajax")
	public @ResponseBody Map<String, Object> editMobileOperator(@Valid MobileOperator mobileOperator, HttpSession session) throws ParseException, IOException {
		Map<String, Object> result = new HashMap<String, Object>(2);
		try {
			mobileOperatorService.editMobileOperator(mobileOperator);
			result.put("success", true);
			result.put("message", "修改成功");
			
		} catch (Exception e) {
			logger.error("MobileOperatorController.editMobileOperator-Exception{}", e);
			result.put("success", false);
			result.put("message", e.getMessage());
		}
		return result;
	}
	
}