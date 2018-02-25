package com.sms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sms.entity.merccountrecord.CountRecord;
import com.sms.service.merccountrecord.CountRecordService;


@Controller
@RequestMapping("/countrecord")
public class CountRecordController {

	@Autowired
	private CountRecordService countRecordService;
	
	
	@ResponseBody
	@RequestMapping("/getChannelRecord.ajax")
	public Map<String, Object> getRecord(HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		List<CountRecord> records = countRecordService.getChannelRecord();
		
		if(records.size()==0){
			result.put("countTime", 0);
			result.put("recordList", null);
			return result;
		}
		for (CountRecord countRecord : records) {
			List<Long> list = new ArrayList<>();
			countRecord.setCountTime(countRecord.getCountTime().substring(0,8));
			list.add(countRecord.getSucCount());
			list.add(countRecord.getFailCount());
			countRecord.setColumnValueGroup(list);
		}
		
		result.put("countTime", records.get(0).getCountTime().substring(0,8));
		result.put("recordList", records);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getPlatformRecord.ajax")
	public Map<String, Object> getPlatformRecord(HttpSession session) {
		 
		Map<String, Object> result = new HashMap<>();
		
		List<CountRecord> records = countRecordService.getPlatformRecord();
		
		List<String> cata = new ArrayList<>();
		List<Long> sum = new ArrayList<>();
		List<Long> suc = new ArrayList<>();
		List<Long> fail = new ArrayList<>();
		
		if(records.size()==0){
			result.put("countTime", 0);
			result.put("categories", null);
			result.put("recordList", null);
			return result;
		}
		for (CountRecord countRecord : records) {
			cata.add(countRecord.getCountTime().substring(8,10)+":00");
			sum.add(countRecord.getSumCount());
			suc.add(countRecord.getSucCount());
			fail.add(countRecord.getFailCount());
		}
		CountRecord sumTemp = new CountRecord();
		sumTemp.setColumnName("发送总量");
		sumTemp.setColumnValueGroup(sum);
		
		CountRecord sucTemp = new CountRecord();
		sucTemp.setColumnName("发送成功量");
		sucTemp.setColumnValueGroup(suc);
		
		CountRecord failTemp = new CountRecord();
		failTemp.setColumnName("发送失败量");
		failTemp.setColumnValueGroup(fail);
		
		CountRecord[] columnValueGroup = {sumTemp,sucTemp,failTemp};
		
		result.put("countTime", records.get(0).getCountTime().substring(0,8));
		result.put("categories", cata);
		result.put("recordList", columnValueGroup);
		
		return result;
	}
}	
