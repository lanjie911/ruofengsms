package com.sms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sms.criteria.fundsandaccount.RechargeCriteria;
import com.sms.entity.fundsandaccount.Recharge;
import com.sms.entity.manager.User;
import com.sms.service.fundsandaccount.RechargeService;


@Controller
@RequestMapping("/recharge")
public class RechargeController {

	@Autowired
	private RechargeService rechargeService;
	
	
	@ResponseBody
	@RequestMapping("/query.ajax")
	public Map<String, Object> query(RechargeCriteria criteria, HttpSession session) {
		 
		User u = (User) session.getAttribute("USER");
		criteria.setMerchantId(u.getMerchantId());
		List<Recharge> records = rechargeService.query(criteria);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);

		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getRechargeById.ajax")
	public  Recharge getRechargeById(@RequestParam("depositId") Long depositId, HttpSession session) {
		return rechargeService.getRechargeById(depositId);
	}
	
}
	
