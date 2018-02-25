package com.sms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sms.entity.bizdict.Bizdict;
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
	
}
