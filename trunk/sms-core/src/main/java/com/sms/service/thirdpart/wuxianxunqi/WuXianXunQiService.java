package com.sms.service.thirdpart.wuxianxunqi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sms.service.PrepareParamService;
import com.sms.service.thirdpart.HttpClientUtil;

@Service
public class WuXianXunQiService {
	private Logger logger = LoggerFactory.getLogger(WuXianXunQiService.class);
	
	@Autowired
	private PrepareParamService prepareParamService;
	
	public Map<String, Object> smsSendByXunQi(String mobiles,  String content, 
			Integer accountType, Integer opertorType){
		Map<String,Object> result = new HashMap<>();
		try {
			String uri = prepareParamService.getParam("XUNQI_REQ_URI").getParamValue();
			String userId = prepareParamService.getParam("XUNQI_USERID_"+opertorType+"_"+accountType).getParamValue();
			String account = prepareParamService.getParam("XUNQI_ACCOUNT_"+opertorType+"_"+accountType).getParamValue();
			String pwd = prepareParamService.getParam("XUNQI_PWD_"+opertorType+"_"+accountType).getParamValue();
			String smsContent = content;
			HttpPost httpPost = new HttpPost(uri);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("action", "send"));
			nvps.add(new BasicNameValuePair("userid", userId));
			nvps.add(new BasicNameValuePair("account", account));
			nvps.add(new BasicNameValuePair("password", pwd));
			nvps.add(new BasicNameValuePair("content", smsContent));
			nvps.add(new BasicNameValuePair("mobile", mobiles));
			nvps.add(new BasicNameValuePair("sendTime", ""));
			nvps.add(new BasicNameValuePair("extno", "8688"));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
			
			logger.info("WuXianXunQiService.smsSendByXunQi-userId：{}，account：{}，Request：{}", userId, account, nvps.toString());
			String reponseJsonStr = HttpClientUtil.httpPost(uri, nvps);
			logger.info("WuXianXunQiService.smsSendByXunQi-userId：{}，account：{}，Reponse：{}", userId, account, reponseJsonStr);
			if(null == reponseJsonStr) {
				result.put("status", false);
				return result;
			}
			
			JSONObject jsonObj = JSON.parseObject(reponseJsonStr);
			String returnStatus = jsonObj.getString("returnstatus");
			if("Success".equalsIgnoreCase(returnStatus)) {
				String taskID = jsonObj.getString("taskID");
				result.put("status", true);
				result.put("reqMsgId", taskID);
			} else {
				result.put("status", false);
			}
			
		} catch (Exception e) {
			logger.error("WuXianXunQiService.smsSendByXunQi-Exception:{}", e);
		}
		return result;
	}
	public Map<String, Object> smsQueryByXunQi(String taskid,Integer accountType, Integer opertorType){
		Map<String,Object> result = new HashMap<>();
		try {
			String uri = prepareParamService.getParam("XUNQI_STA_URI").getParamValue();
			String userId = prepareParamService.getParam("XUNQI_USERID_"+opertorType+"_"+accountType).getParamValue();
			String account = prepareParamService.getParam("XUNQI_ACCOUNT_"+opertorType+"_"+accountType).getParamValue();
			String pwd = prepareParamService.getParam("XUNQI_PWD_"+opertorType+"_"+accountType).getParamValue();
			HttpPost httpPost = new HttpPost(uri);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("action", "query"));
			nvps.add(new BasicNameValuePair("userid", userId));
			nvps.add(new BasicNameValuePair("account", account));
			nvps.add(new BasicNameValuePair("password", pwd));
			nvps.add(new BasicNameValuePair("statusNum", ""));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
			
			logger.info("WuXianXunQiService.smsQueryByXunQi-userId：{}，account：{}，Request：{}", userId, account, nvps.toString());
			String reponseJsonStr = HttpClientUtil.httpPost(uri, nvps);
			logger.info("WuXianXunQiService.smsQueryByXunQi-userId：{}，account：{}，Reponse：{}", userId, account, reponseJsonStr);
			if(null == reponseJsonStr) {
				result.put("status", false);
				return result;
			}
			
			JSONObject jsonObj = JSON.parseObject(reponseJsonStr);
			String returnStatus = jsonObj.getString("error");
			if("1".equalsIgnoreCase(returnStatus)) {
				result.put("status", true);
			} else {
				result.put("status", false);
			}
			
		} catch (Exception e) {
			logger.error("WuXianXunQiService.smsQueryByXunQi-Exception:{}", e);
		}
		return result;
	}

	public static void main(String[] args) {
	//	sendByWuXianXunQiTest();
	//queryByWuXianXunQiTest();
		String reponseJsonStr = "{\"error\":\"1\",\"remark\":\"成功\",\"statusbox\":[{\"mobile\":\"13271230890\",\"taskid\":\"1712123430380115\",\"receivetime\":\"2017-12-12 10:30:42\",\"errorcode\":\"DELIVRD\"},{\"mobile\":\"13651056688\",\"taskid\":\"1712123430380115\",\"receivetime\":\"2017-12-12 10:30:44\",\"errorcode\":\"DELIVRD\"}]}";
		JSONObject jsonObj = JSON.parseObject(reponseJsonStr);
		String error = jsonObj.getString("error");
		String remark = jsonObj.getString("remark");
		System.out.println("error:" + error);
		System.out.println("remark:" + remark);
		String statusbox = jsonObj.getString("statusbox");
		JSONArray statusboxArr = JSON.parseArray(statusbox);
		for (Object object : statusboxArr) {
			JSONObject parseObject = JSON.parseObject(object.toString());
			String mobile = parseObject.getString("mobile");
			String taskid = parseObject.getString("taskid");
			String receivetime = parseObject.getString("receivetime");
			String errorcode = parseObject.getString("errorcode");
			System.out.println("mobile:" + mobile);
			System.out.println("taskid:" + taskid);
			System.out.println("receivetime:" + receivetime);
			System.out.println("errorcode:" + errorcode);
		}
		
	}
	
	public static void sendByWuXianXunQiTest() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			int randNum = 1 + (int) (Math.random() * ((999999 - 1) + 1));
//			HttpPost httpPost = new HttpPost("http://58.253.87.82:8718/smsgwhttp/sms/submit");
			HttpPost httpPost = new HttpPost("http://47.92.96.185/smsJson.aspx");
			
			String content = "【聚合信息】您的注册验证码为" + randNum;
			String mobile = "13271230890,13651056688";
			
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			// 移动的
//			nvps.add(new BasicNameValuePair("spid", "80011"));
//			nvps.add(new BasicNameValuePair("password", "DASfds"));
//			nvps.add(new BasicNameValuePair("ac", "489011"));
			// 联通的
			nvps.add(new BasicNameValuePair("action", "send"));
			nvps.add(new BasicNameValuePair("userid", "Hl2800002"));
			nvps.add(new BasicNameValuePair("account", "Hl2800002"));
			nvps.add(new BasicNameValuePair("password", "800002456"));
	//		nvps.add(new BasicNameValuePair("ac", "106905563347"));
			nvps.add(new BasicNameValuePair("content", content));
			nvps.add(new BasicNameValuePair("mobile", mobile));
			nvps.add(new BasicNameValuePair("sendTime", ""));
			nvps.add(new BasicNameValuePair("extno", "8688"));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
			response = httpclient.execute(httpPost);
			
			System.out.println("HTTP响应码：" + response.getStatusLine());
			
			HttpEntity entity = response.getEntity();
			InputStream instreams = entity.getContent();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(instreams));
			StringBuilder respStr = new StringBuilder();
			String jsonStr = null;
			while ((jsonStr = reader.readLine()) != null)
				respStr.append(jsonStr);
			reader.close(); // 关闭输入流
			jsonStr = URLDecoder.decode(respStr.toString(), "UTF-8");
			System.out.println("响应信息：" + jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public static void queryByWuXianXunQiTest() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost("http://47.92.96.185/statusJsonApi.aspx");
			String taskid ="1712123430380115";
			
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("action", "query"));
			nvps.add(new BasicNameValuePair("userid", "Hl2800002"));
			nvps.add(new BasicNameValuePair("account", "Hl2800002"));
			nvps.add(new BasicNameValuePair("password", "800002456"));
			nvps.add(new BasicNameValuePair("statusNum", ""));
			nvps.add(new BasicNameValuePair("taskid", taskid));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
			response = httpclient.execute(httpPost);
			
			System.out.println("HTTP响应码：" + response.getStatusLine());
			
			HttpEntity entity = response.getEntity();
			InputStream instreams = entity.getContent();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(instreams));
			StringBuilder respStr = new StringBuilder();
			String jsonStr = null;
			while ((jsonStr = reader.readLine()) != null)
				respStr.append(jsonStr);
			reader.close(); // 关闭输入流
			jsonStr = URLDecoder.decode(respStr.toString(), "UTF-8");
			System.out.println("响应信息：" + jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
}