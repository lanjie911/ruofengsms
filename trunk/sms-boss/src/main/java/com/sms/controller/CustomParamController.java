package com.sms.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sms.criteria.CustomParamCriteria;
import com.sms.entity.bizdict.CustomParam;
import com.sms.service.bizdict.CustomParamService;
import com.sms.util.ErrorMessageUtil;


@Controller
@RequestMapping("/customParam")
public class CustomParamController {

	@Autowired
	private CustomParamService customParamService;
	
	
	@ResponseBody
	@RequestMapping("/query.ajax")
	public Map<String, Object> query(CustomParamCriteria criteria, HttpSession session) {
		
		List<CustomParam> records = customParamService.query(criteria);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);

		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getParamByParamId.ajax")
	public CustomParam getParamByParamId(@RequestParam("paramid") String paramid, HttpSession session) {
		CustomParam param = customParamService.getParamById(Long.parseLong(paramid));
		return param;
	}
	
	@ResponseBody
	@RequestMapping("/addParam.ajax")
	public  Map<String, Object> addParam(@Valid CustomParam param, BindingResult error,HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		if(error.hasErrors()){
			result.put("success", false);
			result.put("message",ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		try {
			result=customParamService.addParam(param,result);
		} catch(Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}
		return result;
	}
	
	@RequestMapping("/editParam.ajax")
	public @ResponseBody Map<String, Object> editParam(@Valid CustomParam param,  BindingResult error,HttpSession session) throws ParseException, IOException {
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		if(error.hasErrors()){
			result.put("success", false);
			result.put("message", ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		try {
			result=customParamService.editParam(param,result);
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
	
