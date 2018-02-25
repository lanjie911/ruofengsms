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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sms.criteria.channelmanager.ChannelCriteria;
import com.sms.entity.channelmanager.Channel;
import com.sms.service.channelmanager.ChannelService;
import com.sms.util.ErrorMessageUtil;

@Controller
@RequestMapping("/channel")
public class ChannelController {

	@Autowired
	private ChannelService channelService;

	@ResponseBody
	@RequestMapping("/query.ajax")
	public Map<String, Object> query(ChannelCriteria criteria, HttpSession session) {

		List<Channel> records = channelService.query(criteria);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);

		return result;
	}

	@ResponseBody
	@RequestMapping("/getChannelByAttr.ajax")
	public List<Channel> getChannelByAttr(@Param("attr") Long attr) {

		List<Channel> records = channelService.getChannelByAttr(attr);
		return records;
	}

	@ResponseBody
	@RequestMapping("/getChannelByAttrs.ajax")
	public List<Channel> getChannelByAttrs(@Param("accountType") String accountType,
			@Param("supportOperators") String supportOperators) {

		List<Channel> records = channelService.getChannelByAttrs(accountType, supportOperators);
		return records;
	}

	@ResponseBody
	@RequestMapping("/getAllChannel.ajax")
	public List<Channel> getAllChannel() {

		List<Channel> records = channelService.getAllChannel();
		return records;
	}

	@ResponseBody
	@RequestMapping("/addChannel.ajax")
	public Map<String, Object> addChannel(@Valid Channel channel, BindingResult error, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);

		if (error.hasErrors()) {
			result.put("success", false);
			result.put("message", ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		try {
			result = channelService.addChannel(channel, result);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", e.toString());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/getChannelByChannelId.ajax")
	public Channel getChannelByChannelId(@RequestParam("channelId") Long channelId, HttpSession session) {
		return channelService.getChannelByChannelId(channelId);
	}

	@ResponseBody
	@RequestMapping("/editChannel.ajax")
	public Map<String, Object> editChannel(@Valid Channel channel, BindingResult error, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>(2);

		if (error.hasErrors()) {
			result.put("success", false);
			result.put("message", ErrorMessageUtil.getErrorMessage(error));
			return result;
		}
		try {
			result = channelService.editChannel(channel, result);
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