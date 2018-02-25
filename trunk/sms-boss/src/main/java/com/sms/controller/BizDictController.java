package com.sms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sms.entity.bizdict.AccountType;
import com.sms.entity.bizdict.ApplayStatus;
import com.sms.entity.bizdict.BatchType;
import com.sms.entity.bizdict.Bizdict;
import com.sms.entity.bizdict.MobileOperatorType;
import com.sms.entity.bizdict.OrderFlag;
import com.sms.service.bizdict.BizDictService;

@Controller
@RequestMapping("/bizdict")
public class BizDictController {
	
	@Autowired
	private BizDictService bizDictService;
	
	@ResponseBody
	@RequestMapping("/getDir.ajax")
	public List<Bizdict> getBizdict(@RequestParam("dirType") String dirType) {
		return bizDictService.getDir(dirType);
	}
	@ResponseBody
	@RequestMapping("/getDirWithAll.ajax")
	public List<Bizdict> getBizdictWithAll(@RequestParam("dirType") String dirType) {
		return bizDictService.getDirWithAll(dirType);
	}
	
	@ResponseBody
	@RequestMapping("/getArea.ajax")
	public List<Bizdict> getArea(@RequestParam("cityName") String cityName) {
		return bizDictService.getArea(cityName);
	}
	
	@ResponseBody
	@RequestMapping("/getBatchType.ajax")
	public List<BatchType> getBatchType() {
		return bizDictService.getBatchType();
	}
	
	@ResponseBody
	@RequestMapping("/getAccountType.ajax")
	public List<AccountType> getAccountType() {
		return bizDictService.getAccountType();
	}
	@ResponseBody
	@RequestMapping("/getOrderFlag.ajax")
	public List<OrderFlag> getOrderFlag() {
		return bizDictService.getOrderFlag();
	}
	
	@ResponseBody
	@RequestMapping("/getApplayStatus.ajax")
	public List<ApplayStatus> getApplayStatus() {
		return bizDictService.getApplayStatus();
	}
	
	@ResponseBody
	@RequestMapping("/getMobileOperatorType.ajax")
	public List<MobileOperatorType> getMobileOperatorType() {
		return bizDictService.getMobileOperatorType();
	}
}