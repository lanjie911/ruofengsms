package com.sms.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sms.criteria.smsmanager.SmsCriteria;
import com.sms.entity.smsmanager.Sms;
import com.sms.service.smsmanager.SmsService;
import com.sms.util.ErrorMessageUtil;

@Controller
@RequestMapping("/sms")
public class SmsController {

	@Autowired
	private SmsService smsService;

	@ResponseBody
	@RequestMapping("/query.ajax")
	public Map<String, Object> query(SmsCriteria criteria, HttpSession session) {
		List<Sms> records = smsService.query(criteria);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);
		return result;
	}

	@ResponseBody
	@RequestMapping("/addSms.ajax")
	public Map<String, Object> addSms(@Valid Sms sms, BindingResult error, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);

		if (error.hasErrors()) {
			result.put("success", false);
			result.put("message", ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		
		try {
			result = smsService.addSms(sms, result, (String) session.getAttribute("USER_NAME"));
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}
		return result;
	}

	/*
	 * @ResponseBody
	 * @RequestMapping("/getChannelByChannelId.ajax") public Channel
	 * getChannelByChannelId(@RequestParam("channelId") Long channelId,
	 * HttpSession session) { return
	 * channelService.getChannelByChannelId(channelId); }
	 * 
	 * @ResponseBody
	 * @RequestMapping("/editChannel.ajax") public Map<String, Object>
	 * editChannel(@Valid Channel channel, BindingResult error,HttpSession
	 * session){ Map<String, Object> result = new HashMap<String, Object>(2);
	 * 
	 * if(error.hasErrors()){ result.put("success", false);
	 * result.put("message", ErrorMessageUtil.getErrorMessage(error)); return
	 * result; } try { result=channelService.editChannel(channel,result);
	 * result.put("success", true); result.put("message", "修改成功"); } catch
	 * (Exception e) { e.printStackTrace(); result.put("success", false);
	 * result.put("message", "修改失败"); } return result; }
	 */

	public static void main(String[] args) throws UnsupportedEncodingException {
		String keyWord = URLDecoder.decode(
				"51287974808970%2C17305495135%2CDELIVRD%2C1509274268%3B67865047137392%2C15132981158%2CDELIVRD%2C1509274272",
				"utf-8");
		System.out.println(keyWord);
	}
}