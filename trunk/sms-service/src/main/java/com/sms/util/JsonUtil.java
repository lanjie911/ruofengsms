package com.sms.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;

public class JsonUtil {
	
	/**
	 * Json转换为JavaBean
	 * @param jsonString
	 * @param beanCalss
	 * @return 
	 */
	public static <T> T jsonToBean(String jsonString, Class<T> beanCalss) {
		JSONObject parse = (JSONObject) JSON.parse(jsonString);
        return TypeUtils.castToJavaBean(parse, beanCalss);
    }
	
	public static <T> T requestStr2bean(String requestStr, Class<T> beanClass) {
		JSONObject context = JSON.parseObject(requestStr);
		return JsonUtil.jsonToBean(context.toJSONString(), beanClass);
	}
	
	/**
	 * JavaBean转换为Json
	 * @param obj
	 * @return
	 */
	public static String bean2Json(Object obj ){
		return JSON.toJSONString(obj);
	}
	
}
