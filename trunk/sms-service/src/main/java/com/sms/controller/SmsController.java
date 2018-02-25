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
import com.sms.service.SendSmsService;
import com.sms.service.send.ValidatePramService;
import com.sms.util.TradeException;

@Controller
@RequestMapping("/sms")
public class SmsController {
	
	private static Logger logger = LoggerFactory.getLogger(SmsController.class);
	
	@Autowired
	private ValidatePramService validatePramService;
	
	@Autowired
	private SendSmsService sendSmsService;
	
	@Autowired
	private PrepareParamService prepareParamService;
	
	/**
	 * 行业短信
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/trade")
	public Callable<String> sendMsg(HttpServletRequest request, HttpServletResponse response){
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				String reqIp = getIpAddr(request);
				logger.info("SmsController.sendMsg-Request:{}", reqIp);	//获取请求IP
				BufferedReader reader = null;
				String jsonStr = null;
				StringBuilder requestBuilder = new StringBuilder();
                String resultStr = "{\"msg\":\"SUCCESS\",\"code\":\"0000\"}";
				try {
					/*if(null != prepareParamService.getConvinceIp(reqIp))
						throw new TradeException("9988","{\"msg\":\"IP未授信\",\"code\":\"9988\"}");*/
					reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
	    	    	while ((jsonStr = reader.readLine()) != null)
	    	    		requestBuilder.append(jsonStr);
	    	    	reader.close();// 关闭输入流
	    	    	
		        	JSONObject jsonObject = JSONObject.parseObject(requestBuilder.toString()); // 取一个json转换为对象
		        	validatePramService.validateSign(jsonObject,reqIp);
		        	sendSmsService.sendMsg(jsonObject);
		        	resultStr = "{\"msg\":\"SUCCESS\",\"code\":\"0000\",\"messageId\":\""+jsonObject.getString("messageId")+"\"}";
		        	response.setCharacterEncoding("UTF-8");
            		response.setContentType("application/json; charset=utf-8");
                } catch (TradeException e) {
                	resultStr = e.getErrorMsg();
                	logger.error("短信接口处理失败："+e.getErrorMsg());
                } catch (Exception e) {
                	resultStr = "{\"msg\":\"系统内部错误\",\"code\":\"9999\"}";
                	logger.error("短信接口处理异常："+e.getMessage());
                } finally{
                	if(reader != null)
                		reader.close();
                }
				logger.info("SmsController.sendMsg-Reponse："+resultStr);
				return resultStr;
			}
		};
	}

	/**
	 * 个性短信
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/tradediff")
	public Callable<String> sendMsgDiff(HttpServletRequest request, HttpServletResponse response){
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				String reqIp = getIpAddr(request);
				logger.info("sendMsgDiff synchro start request :{}", reqIp);	//获取请求IP
				BufferedReader reader = null;
				String jsonStr = null;
				StringBuilder result = new StringBuilder();
                String resultStr = "{\"msg\":\"SUCCESS\",\"code\":\"0000\"}";
				try {
					//if(null != prepareParamService.getConvinceIp(reqIp))
						//throw new TradeException("9988","{\"msg\":\"IP未授信\",\"code\":\"9988\"}");
					reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
	    	    	while ((jsonStr = reader.readLine()) != null)
	    	    		result.append(jsonStr);
	    	    	reader.close();// 关闭输入流
	    	    	
		        	JSONObject jsonObject = JSONObject.parseObject(result.toString()); // 取一个json转换为对象
		        	validatePramService.validateSignDiff(jsonObject,reqIp);
		        	sendSmsService.sendMsgDiff(jsonObject);
		        	
		        	response.setCharacterEncoding("UTF-8");
            		response.setContentType("application/json; charset=utf-8");
                } catch (TradeException e) {
                	resultStr = e.getErrorMsg();
                	logger.error("短信接口处理失败："+e.getErrorMsg());
                } catch (Exception e) {
                	resultStr = "{\"msg\":\"系统内部错误\",\"code\":\"9999\"}";
                	logger.error("短信接口处理异常："+e.getMessage());
                } finally{
                	if(reader != null)
                		reader.close();
                }
				logger.info("查询接口处理结果："+resultStr);
				return resultStr;
			}
		};
	}
	
	/**
	 * 营销短信
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/trademarket")
	public Callable<String> sendMsgMarket(HttpServletRequest request, HttpServletResponse response){
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				String reqIp = getIpAddr(request);
				logger.info("sendMsgMarket synchro start request :{}", reqIp);	//获取请求IP
				BufferedReader reader = null;
				String jsonStr = null;
				StringBuilder result = new StringBuilder();
                String resultStr = "{\"msg\":\"SUCCESS\",\"code\":\"0000\"}";
				try {
					//if(null != prepareParamService.getConvinceIp(reqIp))
						//throw new TradeException("9988","{\"msg\":\"IP未授信\",\"code\":\"9988\"}");
					reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
	    	    	while ((jsonStr = reader.readLine()) != null)
	    	    		result.append(jsonStr);
	    	    	reader.close();// 关闭输入流
	    	    	
		        	JSONObject jsonObject = JSONObject.parseObject(result.toString()); // 取一个json转换为对象
		        	validatePramService.validateSign(jsonObject,reqIp);
		        	sendSmsService.sendMsgMarket(jsonObject);
		        	
		        	response.setCharacterEncoding("UTF-8");
            		response.setContentType("application/json; charset=utf-8");
                } catch (TradeException e) {
                	resultStr = e.getErrorMsg();
                	logger.error("短信接口处理失败："+e.getErrorMsg());
                } catch (Exception e) {
                	resultStr = "{\"msg\":\"系统内部错误\",\"code\":\"9999\"}";
                	logger.error("短信接口处理异常："+e.getMessage());
                } finally{
                	if(reader != null)
                		reader.close();
                }
				logger.info("查询接口处理结果："+resultStr);
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
	
	public static void main(String[] args) {
		String ss = "19281911010011001211291829182918191";
		System.out.println(ss.hashCode());
	}
	
}