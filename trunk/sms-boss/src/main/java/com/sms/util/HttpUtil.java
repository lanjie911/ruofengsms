package com.sms.util;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sms.entity.smsmanager.Sms;

@Component
@Service
public class HttpUtil implements Serializable{
	private static final long serialVersionUID = -2975259688934076311L;
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	 public static  String httpPostOld(List<NameValuePair> requestParam,String url){
		 
	        //post请求返回结果
		  	CloseableHttpClient  httpClient = HttpClients.createDefault();
		  	HttpPost method = new HttpPost(url);
	        String response = "";
	        try {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(requestParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(new UrlEncodedFormEntity(requestParam));
	           
	            HttpResponse result = httpClient.execute(method);
	            url = URLDecoder.decode(url, "UTF-8");
	            /**请求发送成功，并得到响应**/
	            if (result.getStatusLine().getStatusCode() == 200) {
	               
	                try {
	                    /**读取服务器返回过来的json字符串数据**/
	                	response = EntityUtils.toString(result.getEntity());
	                	System.out.println("=======>>>>>" + response);
	                } catch (Exception e) {
	                    logger.error("post请求提交失败:" + url, e);
	                }
	            }
	        } catch (IOException e) {
	            logger.error("post请求提交失败:" + url, e);
	        }
	        finally{
	            try{
	            	if (httpClient != null){
	            		httpClient.close();
	     	        }           
	            } catch(Exception e){
	                e.printStackTrace();
	            }
	        }
	        return response;
	    }
	 
	 public static String httpPostNew(String url,JSONObject jsonParam, boolean noNeedResponse){
	        //post请求返回结果
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	        JSONObject jsonResult = null;
	        HttpPost method = new HttpPost(url);
	        String str = "";
	        try {
	            if (null != jsonParam) {
	                //解决中文乱码问题
	                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
	                entity.setContentEncoding("UTF-8");
	                entity.setContentType("application/json");
	                method.setEntity(entity);
	            }
	            HttpResponse result = httpClient.execute(method);
	            url = URLDecoder.decode(url, "UTF-8");
	           //**请求发送成功，并得到响应**//*
	            if (result.getStatusLine().getStatusCode() == 200) {
	                try {
	                    str = EntityUtils.toString(result.getEntity());
	                } catch (Exception e) {
	                    logger.error("post请求提交失败:" + url, e);
	                }
	            }
	        } catch (IOException e) {
	            logger.error("post请求提交失败:" + url, e);
	        }
	        return str;
	    }
	 
	 public  List<NameValuePair> packageRequest(String transCode,Long accountNo, String content) throws UnsupportedEncodingException{
		 
		 	List<NameValuePair> requestParam =  new ArrayList<>();
			String time = UUID.randomUUID().toString().replaceAll("\\-", "");
	        String sign = SignUtil.getSign(time, "9a2f17b88c6d4c840b8d2bcbd8a6256e");
	        requestParam.add(new BasicNameValuePair("salt", time));
	        requestParam.add(new BasicNameValuePair("sign", sign));
	        requestParam.add(new BasicNameValuePair("transCode",transCode));
	        JSONObject j = new JSONObject();
	        j.put("accountNo", accountNo);
	        j.put("msgContent", content);
	        
	    	requestParam.add(new BasicNameValuePair("content",URLEncoder.encode(j.toJSONString(),"UTF-8")));
	    	
	    	return requestParam;
	 }
	 
	 public  List<NameValuePair> packageRequest(String transCode) throws UnsupportedEncodingException{
		 
			List<NameValuePair> requestParam =  new ArrayList<>();
		 	JSONObject jsObject = new JSONObject();
	        jsObject.put("transCode", transCode);
	        
	    	requestParam.add(new BasicNameValuePair("content",URLEncoder.encode(jsObject.toJSONString(),"UTF-8")));
		 
	    	return requestParam;
	 }
	 
	 public static  List<NameValuePair> packageRequest(Sms sms) throws UnsupportedEncodingException{
		 
		 	List<NameValuePair> requestParam =  new ArrayList<>();
		 	String time = UUID.randomUUID().toString().replaceAll("\\-", "");
	        String sign = SignUtil.getSign(time, "9a2f17b88c6d4c840b8d2bcbd8a6256e");
	        requestParam.add(new BasicNameValuePair("salt", time));
	        requestParam.add(new BasicNameValuePair("sign", sign));
	        requestParam.add(new BasicNameValuePair("transCode", sms.getSendSmsType()));
        	
		 	JSONObject jsObject = new JSONObject();
	        jsObject.put("accountNo", sms.getAccountNo());
	        jsObject.put("mobile", sms.getMobile());
	        jsObject.put("content", sms.getContent());
	        jsObject.put("orderFlag", sms.getAppointmentFlag());
	        jsObject.put("reservationDatetime", sms.getReservationDatetime());
	        jsObject.put("messageId", DatetimeUtil.getCurrentDateTime("yyyyMMddHHmms"));
	        
	    	requestParam.add(new BasicNameValuePair("content",URLEncoder.encode(jsObject.toJSONString(),"UTF-8")));
	    	
	    	return requestParam;
	 }
	
	 public static String notifyTradeChange(String transCode,String mobile,String ipAddr,String type,Long accountNo,String url){
		 String jsonResult = null;	
		 try {
		        JSONObject jsObject = new JSONObject();
		        jsObject.put("accountNo", accountNo);		//账户号
		        jsObject.put("mobile", mobile);		//黑名单/白名单
		        jsObject.put("ipAddr", ipAddr);		//ip授信
		        jsObject.put("type", type);		//100- 新增 200- 删除
		        jsObject.put("transCode", transCode);		//类型
		        jsObject.put("password", "boss");	
		        jsonResult = httpPostNew(url, jsObject, false);
			} catch (Exception e) {
				logger.error("HttpUtil Error",e);
			}
		 return jsonResult;
		
		}


}
