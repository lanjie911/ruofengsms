package com.sms.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sms.service.PrepareParamService;
import com.sms.service.SmsQueryService;
import com.sms.service.send.ValidatePramService;
import com.sms.util.TradeException;

@Controller
@RequestMapping("/query")
public class QueryController {
	
	private static Logger logger = LoggerFactory.getLogger(QueryController.class);
	
	@Autowired
	private ValidatePramService validatePramService;
	
	@Autowired
	private SmsQueryService smsQueryService;
	
	@Autowired
	private PrepareParamService prepareParamService;

	/**
	 * 结果查询 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/result")
	public Callable<String> result(HttpServletRequest request, HttpServletResponse response){
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				String reqIp = getIpAddr(request);
				logger.info("sendMsg synchro start request :{}", reqIp);	//获取请求IP
				BufferedReader reader = null;
				String jsonStr = null;
				StringBuilder result = new StringBuilder();
                String resultStr = "{\"msg\":\"SUCCESS\",\"code\":\"0000\"}";
				try {
					if(null != prepareParamService.getConvinceIp(reqIp))
						throw new TradeException("9988","{\"msg\":\"非法IP\",\"code\":\"9988\"}");
					reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
	    	    	while ((jsonStr = reader.readLine()) != null)
	    	    		result.append(jsonStr);
	    	    	reader.close();// 关闭输入流
	    	    	
		        	JSONObject jsonObject = JSONObject.parseObject(result.toString()); // 取一个json转换为对象
		        	validatePramService.validateSignResult(jsonObject,reqIp);
		        	resultStr = smsQueryService.resultQuery(jsonObject);
		        	
		        	response.setCharacterEncoding("UTF-8");
            		response.setContentType("application/json; charset=utf-8");
            		logger.info("查询接口处理成功："+resultStr);
                } catch (TradeException e) {
                	resultStr = e.getErrorMsg();
                	logger.error("查询接口处理失败："+e.getErrorMsg());
                } catch (Exception e) {
                	resultStr = "{\"msg\":\"系统内部错误\",\"code\":\"9999\"}";
                	logger.error("查询接口处理异常："+e.getMessage());
                } finally{
                	if(reader != null)
                		reader.close();
                }
				return resultStr;
			}
		};
	}
	
	/**
	 * 余额查询 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/balance")
	public Callable<String> balance(HttpServletRequest request, HttpServletResponse response){
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				String reqIp = getIpAddr(request);
				logger.info("sendMsg synchro start request :{}", reqIp);	//获取请求IP
				BufferedReader reader = null;
				String jsonStr = null;
				StringBuilder result = new StringBuilder();
                String resultStr = "{\"msg\":\"SUCCESS\",\"code\":\"0000\"}";
				try {
					if(null != prepareParamService.getConvinceIp(reqIp))
						throw new TradeException("9988","{\"msg\":\"非法IP\",\"code\":\"9988\"}");
					reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
	    	    	while ((jsonStr = reader.readLine()) != null)
	    	    		result.append(jsonStr);
	    	    	reader.close();// 关闭输入流
	    	    	
		        	JSONObject jsonObject = JSONObject.parseObject(result.toString()); // 取一个json转换为对象
		        	validatePramService.validateSignBalance(jsonObject,reqIp);
		        	resultStr =  smsQueryService.balanceQuery(jsonObject);
		        	
		        	response.setCharacterEncoding("UTF-8");
            		response.setContentType("application/json; charset=utf-8");
            		logger.info("查询接口处理成功："+resultStr);
                } catch (TradeException e) {
                	resultStr = e.getErrorMsg();
                	logger.error("查询接口处理失败："+e.getErrorMsg());
                } catch (Exception e) {
                	resultStr = "{\"msg\":\"系统内部错误\",\"code\":\"9999\"}";
                	logger.error("查询接口处理异常："+e.getMessage());
                } finally{
                	if(reader != null)
                		reader.close();
                }
				return resultStr;
			}
		};
	}
	
	/**
	 * BOSS更新  100-商户，300-白名单，200-黑名单, 400-IP
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reload")
	public Callable<String> reload(HttpServletRequest request, HttpServletResponse response){
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				String reqIp = getIpAddr(request);
				logger.info("sendMsg synchro start request :{}", reqIp);	//获取请求IP
				BufferedReader reader = null;
				String jsonStr = null;
				StringBuilder result = new StringBuilder();
                String resultStr = "{\"msg\":\"SUCCESS\",\"code\":\"0000\"}";
				try {
					if(null != prepareParamService.getConvinceIp(reqIp))
						throw new TradeException("9988","{\"msg\":\"非法IP\",\"code\":\"9988\"}");
					reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
	    	    	while ((jsonStr = reader.readLine()) != null)
	    	    		result.append(jsonStr);
	    	    	reader.close();// 关闭输入流
	    	    	
		        	JSONObject jsonObject = JSONObject.parseObject(result.toString()); // 取一个json转换为对象
		        	validatePramService.validateSignReload(jsonObject);
		        	switch (jsonObject.getString("transCode")) {
					case "100":
						prepareParamService.reloadAccount(jsonObject.getLong("accountNo"));
						break;
					case "200":
						prepareParamService.reloadBlack(jsonObject.getString("mobile"),jsonObject.getString("type"));
						break;
					case "300":
						prepareParamService.reloadWhite(jsonObject.getString("mobile"),jsonObject.getString("type"));
						break;
					case "400":
						prepareParamService.reloadIp(jsonObject.getString("ipAddr"),jsonObject.getString("type"));
						break;
					case "500":
						prepareParamService.reloadParam();
						break;
					}
		        	
		        	response.setCharacterEncoding("UTF-8");
            		response.setContentType("application/json; charset=utf-8");
            		logger.info("查询接口处理成功："+resultStr);
                } catch (TradeException e) {
                	resultStr = e.getErrorMsg();
                	logger.error("查询接口处理失败："+e.getErrorMsg());
                } catch (Exception e) {
                	resultStr = "{\"msg\":\"系统内部错误\",\"code\":\"9999\"}";
                	logger.error("查询接口处理异常："+e.getMessage());
                } finally{
                	if(reader != null)
                		reader.close();
                }
				return resultStr;
			}
		};
	}
	
	private String getIpAddr(HttpServletRequest request) {
    	String ip = request.getHeader("X-Real-IP");		//先从nginx自定义配置获取
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
	        ip = request.getRemoteAddr();  
	    
	    return ip;  
	}
	
}
