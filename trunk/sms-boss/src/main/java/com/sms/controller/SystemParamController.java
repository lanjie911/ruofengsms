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

import com.sms.criteria.systemParam.SystemParamCriteria;
import com.sms.entity.systemParam.SystemParam;
import com.sms.service.manager.SystemParamService;

@Controller
@RequestMapping("/systemParam")
public class SystemParamController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SystemParamService systemParamService;
	
	@ResponseBody
	@RequestMapping("/query.ajax")
	public Map<String, Object> query(SystemParamCriteria criteria, HttpSession session) {
		List<SystemParam> records = systemParamService.query(criteria);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getParamByParamId.ajax")
	public SystemParam getParamByParamId(@RequestParam("paramid") String paramid, HttpSession session) {
		return systemParamService.getParamById(Long.parseLong(paramid));
	}
	
	@ResponseBody
	@RequestMapping("/addParam.ajax")
	public  Map<String, Object> addParam(@Valid SystemParam param, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		try {
			systemParamService.addParam(param);
			result.put("success", true);
			result.put("message", "操作成功");
		} catch(Exception e) {
			logger.error("SystemParamController.addParam-Exception{}", e);
			result.put("success", false);
			result.put("message", e.toString());
		}
		return result;
	}
	
	@RequestMapping("/editParam.ajax")
	public @ResponseBody Map<String, Object> editParam(@Valid SystemParam param, HttpSession session) throws ParseException, IOException {
		Map<String, Object> result = new HashMap<String, Object>(2);
		try {
			systemParamService.editParam(param);
			result.put("success", true);
			result.put("message", "修改成功");
			
		} catch (Exception e) {
			logger.error("SystemParamController.editParam-Exception{}", e);
			result.put("success", false);
			result.put("message", e.getMessage());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getParamByParamCode.ajax")
	public SystemParam getSystemParamByCode(@RequestParam("paramCode") String paramCode, HttpSession session) {
		return systemParamService.getSystemParamByCode(paramCode);
	}
}