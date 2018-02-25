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

import com.sms.criteria.smsmanager.SenstiveWordCriteria;
import com.sms.entity.smsmanager.SenstiveWord;
import com.sms.service.smsmanager.SenstiveWordService;
import com.sms.util.ErrorMessageUtil;


@Controller
@RequestMapping("/sensword")
public class SenstiveWordController {

	@Autowired
	private SenstiveWordService senstiveWordService;
	
	
	@ResponseBody
	@RequestMapping("/query.ajax")
	public Map<String, Object> query(SenstiveWordCriteria criteria, HttpSession session) {
		 
		List<SenstiveWord> records = senstiveWordService.query(criteria);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);

		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/addSens.ajax")
	public  Map<String, Object> addSens(@Valid SenstiveWord senstiveWord, BindingResult error,HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		if(error.hasErrors()){
			result.put("success", false);
			result.put("message",ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		try {
			result=senstiveWordService.addSens(senstiveWord,result);
		} catch(Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/remove.ajax")
	public  Map<String, Object> remove(@Param("wordId") Long wordId) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		try {
			result=senstiveWordService.remove(wordId,result);
		} catch(Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}
		return result;
	}
}
	
