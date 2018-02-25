package com.godai.trade.util;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.godai.trade.common.exception.TradeException;
/**
 * 
 * @Description 校验器工具类
 * @author bqct_bya
 * @CreateTime 2016年11月24日 上午11:15:54
 */
public class ValidatorUtil {
	
	/**
	 * 检查具体业务数据上送字段
	 * 
	 * @param params
	 * @param request
	 * @return
	 * @throws TradeException
	 */
	public static boolean checkData(String[] params, Map<String, Object> request) throws TradeException {
		if (null != params && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				if (StringUtils.isBlank((String) request.get(params[i]))) {
					throw new TradeException("input_data_not_enough",
							 "必传字段:" + params[i]+"为空");
				}
			}
		}
		return true;
	}
	
	/**
	 * 检查具体业务数据上送字段
	 * 
	 * @param params
	 * @param request
	 * @return
	 * @throws TradeException
	 */
	public static boolean checkDataByJson(String[] params, JSONObject request) throws TradeException {
		if (null != params && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				if (StringUtils.isBlank(request.getString(params[i]))) {
					throw new TradeException("input_data_not_enough",
							 "必传字段:" + params[i]+"为空");
				}
			}
		}
		return true;
	}
}
