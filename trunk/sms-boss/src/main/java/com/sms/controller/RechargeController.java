package com.sms.controller;

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

import com.sms.criteria.fundsandaccount.RechargeCriteria;
import com.sms.entity.fundsandaccount.Recharge;
import com.sms.service.fundsandaccount.RechargeService;
import com.sms.util.ErrorMessageUtil;


@Controller
@RequestMapping("/recharge")
public class RechargeController {

	@Autowired
	private RechargeService rechargeService;
	
	
	@ResponseBody
	@RequestMapping("/query.ajax")
	public Map<String, Object> query(RechargeCriteria criteria, HttpSession session) {
		 
		List<Recharge> records = rechargeService.query(criteria);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);

		return result;
	}
	
	@ResponseBody
	@RequestMapping("/addRecharge.ajax")
	public  Map<String, Object> addRecharge(@Valid Recharge recharge, BindingResult error,HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		if(error.hasErrors()){
			result.put("success", false);
			result.put("message",ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		try {
			recharge.setOperator((String) session.getAttribute("USER_NAME"));
			result=rechargeService.addRecharge(recharge,result);
		} catch(Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getRechargeById.ajax")
	public  Recharge getRechargeById(@RequestParam("depositId") Long depositId, HttpSession session) {
		return rechargeService.getRechargeById(depositId);
	}
	
}
	
