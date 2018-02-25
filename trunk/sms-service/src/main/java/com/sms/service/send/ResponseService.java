package com.sms.service.send;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
/**
 * @author Cao
 * 响应信息
 */
@Service
public class ResponseService {
	
	@Value("${sms.sign.key}")
	private String validateSignKey;
	
	public  JSONObject response(String retcode,String retmsg,String retdata){
		JSONObject json = new JSONObject();
		json.put("code", retcode);
		json.put("message", retmsg);
		if(StringUtils.isNotBlank(retdata))
			json.put("data", retdata);
		return json;
	}
	
	public  JSONObject responseQ(String retcode, String retmsg, List<Map<String,Object>> list){
		JSONObject json = new JSONObject();
		json.put("code", retcode);
		json.put("message", retmsg);
		json.put("resultList", list);
		return json;
	}
}
