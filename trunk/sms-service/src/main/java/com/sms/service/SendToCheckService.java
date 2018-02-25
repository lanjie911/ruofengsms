package com.sms.service;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sms.util.TradeException;

@Service
public class SendToCheckService {
	
	private static Logger logger = Logger.getLogger(SendToCheckService.class);
	
	@Value("${sms.api.url}")
	private String smsUrl;
	
	public void doTemplate(Long accountNo, String msgContent) throws TradeException {
        JSONObject j = new JSONObject();
		j.put("msgContent", msgContent);
		j.put("accountNo", accountNo.toString());
        JSONObject jsonResult = httpPost(smsUrl,"200",j);
        if(!jsonResult.getString("code").equals("0000"))
        	throw new TradeException("2006","{\"msg\":\"模板未报备\",\"code\":\"2006\"}");
        logger.info("模板命中请求返回结果"+jsonResult);
	}
	
	public void doSensitive(Long accountNo, String msgContent) throws TradeException {
	        JSONObject j = new JSONObject();
			j.put("msgContent", msgContent);
			j.put("accountNo", accountNo.toString());
	        JSONObject jsonResult = httpPost(smsUrl,"100", j);
	        if(!jsonResult.getString("code").equals("0000"))
	        	throw new TradeException("2007", "{\"msg\":\"包含敏感字\",\"code\":\"2007\"}");
	}
	
	public void doAll(Long accountNo, String content) throws TradeException {
		 JSONObject j = new JSONObject();
			j.put("msgContent", content);
			j.put("accountNo", accountNo.toString());
	        JSONObject jsonResult = httpPost(smsUrl,"300", j);
	        if(!jsonResult.getString("code").equals("0000"))
	        	throw new TradeException("2007", "{\"msg\":\"校验失败\",\"code\":\"2007\"}");
	}
	
	public JSONObject httpPost(String url, String transCode, JSONObject j){
        //post请求返回结果
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost method = new HttpPost(url);
        String response = "";
        JSONObject jsonResult = null;
        try {
        	List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        	nvps.add(new BasicNameValuePair("transCode", transCode));
        	nvps.add(new BasicNameValuePair("content",URLEncoder.encode(j.toJSONString(),"UTF-8")));
            method.setEntity(new UrlEncodedFormEntity(nvps));
           
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            if (result.getStatusLine().getStatusCode() == 200) {
                try {
                	response = EntityUtils.toString(result.getEntity());
                	jsonResult = JSONObject.parseObject(response);
                } catch (Exception e) {
                }
                httpClient.close();
            }
        } catch (IOException e) {
        }
        return jsonResult;
	}

}
